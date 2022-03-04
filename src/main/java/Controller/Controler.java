package Controller;
import Exceptions.*;
import Exceptions.EmptyStackException;
import Model.ProgramState;
import Model.adt.*;
import Model.statement.*;
import Model.value.IValue;
import Model.value.ReferenceValue;
import Model.value.StringValue;
import Repo.IRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controler {
    IRepository repo;
    boolean flag;
    ExecutorService executor;

    public Controler(IRepository repository){
        this.repo = repository;
        this.flag = true;
    }

    public void addProgram(ProgramState programState) {
        repo.addProgram(programState);
    }

    public void oneStepForAllPrg(List<ProgramState> programStateList) {
        List<Callable<ProgramState>> callList = programStateList.stream()
                .map((ProgramState ps) -> (Callable<ProgramState>)(() -> {return ps.oneStep();}))
                .collect(Collectors.toList());
        List<ProgramState> newProgramList = null;
        try{

            newProgramList = executor.invokeAll(callList). stream()
                    . map(future ->{ try { return future.get();}
                    catch(InterruptedException | ExecutionException e) {
                        System.out.println("One step failed" + e.toString());
                    }
                        return null;
                    })
                    .filter(ps -> ps!=null)
                    .collect(Collectors.toList());}
        catch (InterruptedException e){
            System.out.println("Error" + e.toString());
        }
//add the new created threads to the list of existing threads
        programStateList.addAll(newProgramList);
//------------------------------------------------------------------------------
//after the execution, print the PrgState List into the log file
        //prgList.forEach(prg ->repo.logPrgStateExec(prg));
        System.out.println( "PrgListSize = " + repo.getProgramList().size());
//Save the current programs in the repository
        repo.setProgramList(programStateList);
        programStateList.forEach(pg -> {
            try {
                repo.logProgramStateExec(pg);
            } catch (Exceptions.RepositoryException e) {
                e.printStackTrace();
            } catch (Exceptions.MyException e) {
                e.printStackTrace();
            }
        });
    }


    public void allStep() throws Exception {
        executor = Executors.newFixedThreadPool(2);
        List<ProgramState> programStateList = removeCompletedPrograms(repo.getProgramList());
        programStateList.forEach(program -> {
            try {
                repo.logProgramStateExec(program);
            } catch (Exceptions.RepositoryException e) {
                e.printStackTrace();
            } catch (Exceptions.MyException e) {
                e.printStackTrace();
            }
        });
        while(programStateList.size() > 0){
            garbageCollectorForAll(programStateList);
            oneStepForAllPrg(programStateList);
            programStateList = removeCompletedPrograms(repo.getProgramList());
        }
        executor.shutdownNow();
        repo.setProgramList(programStateList);

    }

    public void executeOneStep() throws MyException {
        executor = Executors.newFixedThreadPool(2);
        repo.setProgramList(removeCompletedPrograms(repo.getProgramList()));
        List<ProgramState> programStates = repo.getProgramList();
        if(programStates.size() > 0)
        {
            oneStepForAllPrg(repo.getProgramList());
            repo.setProgramList(removeCompletedPrograms(repo.getProgramList()));
            executor.shutdownNow();
//            garbCollectorForAll(programStates);
        }
    }

    List<ProgramState> removeCompletedPrograms(List<ProgramState> inProgramList){
        return inProgramList.stream().filter(p -> p.isNotCompleted()).collect(Collectors.toList());
    }

    public void garbageCollectorForAll(List<ProgramState> list){
        list.forEach(prg->prg.getHeap().set_content((HashMap<Integer, IValue>) garbageCollector(getAddressesFromSymbolTable(prg.getSymbolTable()),getHeapTableAddresses(prg.getHeap()), prg.getHeap().toHashMap())));
    }


    Map<Integer, IValue> garbageCollector(List<Integer> symbolTableAddresses, List<Integer> referencedKeys, Map<Integer, IValue> heap)
    {
        return heap.entrySet().stream().filter(entry->symbolTableAddresses.contains(entry.getKey()) ||
                referencedKeys.contains(entry.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    List<Integer> getAddressesFromSymbolTable(final IDictionary<String, IValue> symbolTable)
    {
        return symbolTable.values()
                .stream()
                .filter(ref -> ref instanceof ReferenceValue)
                .map(value -> {
                    ReferenceValue refValue = (ReferenceValue) value;
                    return refValue.getAddress();
                })
                .collect(Collectors.toList());
    }

    List<Integer> getHeapTableAddresses(final IHeap<Integer, IValue> heapTable) {
        return heapTable.values()
                .stream()
                .filter(ref -> ref instanceof ReferenceValue)
                .map(value -> {
                    ReferenceValue refValue = (ReferenceValue) value;
                    return refValue.getAddress();
                })
                .collect(Collectors.toList());
    }

    List<Integer> getReferencedKeys(Model.adt.IHeap<Integer, IValue> heap)
    {
        return heap.keys().stream().filter(heap::is_referenced).collect(Collectors.toList());
    }

    public void typeCheck() throws Exceptions.MyException, Exceptions.TypeException, Exceptions.NonExistentKeyDictionaryException {
        repo.getProgramList().get(0).typeCheck();
    }

    public IRepository getRepository() {
        return repo;
    }

}