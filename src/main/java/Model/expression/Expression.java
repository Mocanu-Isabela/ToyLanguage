package Model.expression;

import Exceptions.*;
import Model.adt.IDictionary;
import Model.adt.IHeap;
import Model.types.IType;
import Model.value.IValue;

public interface Expression {
    IValue eval(IDictionary<String, IValue> symbolTable, IHeap<Integer, IValue> heap) throws MyException, IntegerException, DivisionByZeroException, NonExistentKeyDictionaryException;
    IType typeCheck(IDictionary<String, IType> typeEnvironment) throws NonExistentKeyDictionaryException, TypeException;
}