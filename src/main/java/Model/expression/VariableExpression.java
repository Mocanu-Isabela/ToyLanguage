package Model.expression;
import Exceptions.MyException;
import Exceptions.NonExistentKeyDictionaryException;
import Exceptions.TypeException;
import Model.adt.IDictionary;
import Model.adt.IHeap;
import Model.types.IType;
import Model.value.IValue;

public class VariableExpression implements Expression {
    String id;

    public VariableExpression(String id){
        this.id = id;
    }

    public IValue eval(IDictionary<String, IValue> symbolTable, IHeap<Integer, IValue> heap) throws NonExistentKeyDictionaryException {
        return symbolTable.lookup(id);
    }

    @Override
    public IType typeCheck(IDictionary<String, IType> typeEnvironment) throws TypeException, NonExistentKeyDictionaryException {
        return typeEnvironment.lookup(id);
    }

    public String toString() {return id;}
}
