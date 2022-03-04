package Model.statement;

import Exceptions.MyException;
import Exceptions.NonExistentKeyDictionaryException;
import Exceptions.TypeException;
import Model.ProgramState;
import Model.adt.IDictionary;
import Model.adt.IStack;
import Model.types.IType;

public class CompoundStatement implements IStatement {
    private final IStatement first;
    private final IStatement second;

    public CompoundStatement(IStatement first, IStatement second) {
        this.first = first;
        this.second = second;
    }

    public ProgramState execute(ProgramState programState) throws Exception{
        IStack<IStatement> executionStack = programState.getExecutionStack();

        executionStack.push(second);
        executionStack.push(first);
        return null;
    }

    @Override
    public String toString() {
        return "(" + first + ";" + second + ")";
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnvironment) throws NonExistentKeyDictionaryException, TypeException, MyException {
        return second.typeCheck(first.typeCheck(typeEnvironment));
    }
}
