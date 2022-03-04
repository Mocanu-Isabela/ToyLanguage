package Model;
import Exceptions.EmptyStackException;
import Exceptions.MyException;
import Exceptions.NonExistentKeyDictionaryException;
import Exceptions.TypeException;
import Model.adt.*;
import Model.statement.IStatement;
import Model.value.BoolValue;
import Model.value.IValue;
import Model.value.StringValue;

import java.io.BufferedReader;
import java.util.TreeSet;


public class ProgramState {

    IStack<IStatement> executionStack;
    IDictionary<String, IValue> symbolTable;
    IList<IValue> output;
    IStatement originalProgram;
    IDictionary<StringValue, BufferedReader> fileTable;
    IHeap<Integer, IValue> heap;
    static int maxId = 0;
    int id;

    public ProgramState(IStatement program){
        executionStack = new TheStack<IStatement>();
        symbolTable =  new Dictionary<String, IValue>();
        output =  new TheList<IValue>();
        fileTable = new Dictionary<StringValue, BufferedReader>();
        heap = new Heap<>();
        id =1;
        executionStack.push(program);
//        id = ProgramState.maxId;
//        ProgramState.maxId = ProgramState.maxId + 1;
    }

    public ProgramState(IStack<IStatement> e_stack, IDictionary<String, IValue> s_table, IList<IValue> o_list, IStatement program, IDictionary<StringValue, BufferedReader> file_table, IHeap<Integer, IValue> heap_table){
        executionStack = e_stack;
        symbolTable = s_table;
        output = o_list;
        originalProgram = program;
        fileTable = file_table;
        heap = heap_table;
        e_stack.push(program);
        id = ProgramState.maxId;
        ProgramState.maxId = ProgramState.maxId + 1;
    }

    public IStack<IStatement> getExecutionStack() {
        return this.executionStack;
    }

    public IDictionary<String, IValue> getSymbolTable() {
        return this.symbolTable;
    }

    public IList<IValue> getOutputList() {
        return this.output;
    }

    public IStatement getOriginalProgram() {
        return originalProgram;
    }

    public IDictionary<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    public IHeap<Integer, IValue> getHeap()
    {
        return heap;
    }

    public synchronized int getId()
    {
        return id;
    }

    public synchronized void setId(int newId){
        id = newId;
    }

//    public static IStatement deepCopy(IStatement program){
//        return program.deepCopy();
//    }

    @Override
    public String toString() {
        return executionStack.toString() + '\n' + symbolTable.toString() + '\n' + output.toString() + '\n' + fileTable.toString() + '\n' + heap.toString2();
    }

    public String Print() {
        String result;
        result = "---- Print ----\n";
        result += "Id : " + id + '\n';
        result += "FileTable : " + fileTable + '\n';
        result += "ExecutionStack : " + executionStack + '\n';
        result += "SymbolTable : " + symbolTable + '\n';
        result += "OutList : " + output + '\n';
        result += "Heap : " + heap.toString2() + '\n';
        return result;
    }

    public Boolean isNotCompleted(){
        return !executionStack.isEmpty();
    }

    public ProgramState oneStep() throws Exception {
        if (executionStack.isEmpty()) {
            throw new EmptyStackException("The ExecutionStack is empty!");
        }

        IStatement statement = executionStack.pop();
        return statement.execute(this);
    }

    public void typeCheck() throws TypeException, NonExistentKeyDictionaryException, MyException {
        originalProgram.typeCheck(new Dictionary<>());
    }
}