package Model.statement;

import Exceptions.MyException;
import Exceptions.NonExistentKeyDictionaryException;
import Exceptions.TypeException;
import Model.ProgramState;
import Model.adt.IDictionary;
import Model.adt.TheStack;
import Model.types.IType;
import Model.value.IValue;

public class ForkStatement implements IStatement{
    IStatement statement;

    public ForkStatement(IStatement statem){
        this.statement = statem;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws Exception {
        IDictionary<String, IValue> newSymTable = programState.getSymbolTable().deepCopy();
        ProgramState childProgram = new ProgramState(new TheStack<IStatement>(), newSymTable, programState.getOutputList(), statement, programState.getFileTable(), programState.getHeap());
        return childProgram;
    }

    public IDictionary<String, IType> typeCheck(IDictionary<String,IType> typeEnvironment) throws TypeException, NonExistentKeyDictionaryException, MyException {
        statement.typeCheck(typeEnvironment);
        return typeEnvironment;
    }

    @Override
    public String toString(){
        return "fork(" + statement.toString() + ")";
    }
}
