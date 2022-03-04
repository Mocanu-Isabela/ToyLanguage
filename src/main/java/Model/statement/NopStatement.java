package Model.statement;

import Exceptions.MyException;
import Exceptions.NonExistentKeyDictionaryException;
import Exceptions.TypeException;
import Model.ProgramState;
import Model.adt.IDictionary;
import Model.types.IType;

public class NopStatement implements IStatement {

    public ProgramState execute(ProgramState programState){
        return null;
    }

    @Override
    public String toString(){
        return "no operation";
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnvironment) throws NonExistentKeyDictionaryException, TypeException {
        return typeEnvironment;
    }
}
