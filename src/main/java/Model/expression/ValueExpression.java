package Model.expression;
import Exceptions.MyException;
import Exceptions.NonExistentKeyDictionaryException;
import Model.adt.IDictionary;
import Model.adt.IHeap;
import Model.types.IType;
import Model.value.IValue;

public class ValueExpression implements Expression {
    IValue number;

    public ValueExpression(IValue value) {
        this.number = value;
    }

    public IValue eval(IDictionary<String, IValue> symbolTable, IHeap<Integer, IValue> heap) {
        return this.number;
    }

    @Override
    public IType typeCheck(IDictionary<String, IType> typeEnvironment) {
        return number.getType();
    }
    public String toString() {
        return number.toString();
    }
}
