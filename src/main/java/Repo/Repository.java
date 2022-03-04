package Repo;
import Exceptions.EmptyRepositoryException;
import Exceptions.MyException;
import Exceptions.RepositoryException;
import Model.ProgramState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {

    List<ProgramState> myProgramStates;
    String logFilePath;

    public Repository(String file) {
        this.myProgramStates = new ArrayList<>();
        logFilePath = file;
    }

    public Repository(ProgramState initial_program, String file) {
        this.myProgramStates = new ArrayList<>();
        logFilePath = "D:\\Facultate\\ANUL II\\an 2 sem I\\Metode avansate de programare(MAP)\\Lab\\hw5+6\\"+file;
        this.myProgramStates.add(initial_program);
    }

    @Override
    public void addProgram(ProgramState newProgram) {
        this.myProgramStates.add(newProgram);
    }

//    @Override
//    public ProgramState getCurrentProgram() throws RepositoryException {
//        if (this.myProgramStates.size() == 0) {
//            throw new EmptyRepositoryException("Repository is empty");
//        }
//        return myProgramStates.get(myProgramStates.size()-1);
//    }

    @Override
    public void logProgramStateExec(ProgramState ps) throws RepositoryException, MyException {
        try{
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
            logFile.write("Id: \n" +
                    ps.getId() + "\n" +
                    "Execution Stack: \n" +
                    ps.getExecutionStack().toString() + "\n" +
                    "Symbol Table: \n" +
                    ps.getSymbolTable().toString() + "\n" +
                    "Output List: \n" +
                    ps.getOutputList().toString() + "\n" +
                    "File Table: \n" +
                    ps.getFileTable().toString() + "\n" +
                    "Heap: \n" +
                    ps.getHeap().toString() + "\n" +
                    "\n");
            logFile.flush();
            logFile.close();
        }
        catch (IOException e){
            throw new MyException(e.toString());
        }
    }

    public List<ProgramState> getProgramList() {
        return myProgramStates;
    }

    public void setProgramList(List<ProgramState> list){
        this.myProgramStates = list;
    }

    @Override
    public ProgramState getProgramWithId(int id) {
        for(ProgramState progr : myProgramStates){
            if(progr.getId() == id)
                return progr;
        }
        return null;
    }
}
