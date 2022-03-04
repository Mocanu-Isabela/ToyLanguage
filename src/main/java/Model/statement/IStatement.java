package Model.statement;

import Exceptions.MyException;
import Exceptions.NonExistentKeyDictionaryException;
import Exceptions.TypeException;
import Model.ProgramState;
import Model.adt.IDictionary;
import Model.types.IType;

public interface IStatement {
        public ProgramState execute(ProgramState programState) throws Exception;
        String toString();
        IDictionary<String,IType> typeCheck(IDictionary<String, IType> typeEnvironment) throws NonExistentKeyDictionaryException, TypeException, MyException;
}
