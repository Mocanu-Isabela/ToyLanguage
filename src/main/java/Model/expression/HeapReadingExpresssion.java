package Model.expression;

import Exceptions.*;
import Model.adt.IDictionary;
import Model.adt.IHeap;
import Model.types.IType;
import Model.types.ReferenceType;
import Model.value.IValue;
import Model.value.ReferenceValue;

public class HeapReadingExpresssion implements Expression{
    private Expression expression;

    public HeapReadingExpresssion(Expression expression){
        this.expression = expression;
    }

    @Override
    public IValue eval(IDictionary<String, IValue> symbolTable, IHeap<Integer, IValue> heap) throws MyException, IntegerException, DivisionByZeroException, NonExistentKeyDictionaryException {
        IValue expression_value = expression.eval(symbolTable, heap);
        if(expression_value instanceof ReferenceValue)
        {
            ReferenceValue referenceValue = (ReferenceValue) expression_value;
            int address = referenceValue.getAddress();

            if (heap.keyExists(address))
            {
                return heap.lookup(address);
            }
            else throw new MyException("Heap does not contain key " + address + ".");
        }
        else throw new MyException("The value of the expression must evaluate to a reference value");

    }

    @Override
    public IType typeCheck(IDictionary<String, IType> typeEnvironment) throws TypeException, NonExistentKeyDictionaryException {
        IType type = expression.typeCheck(typeEnvironment);
        if (type instanceof ReferenceType) {
            ReferenceType ref_type = (ReferenceType) type;
            return ref_type.getInner();
        } else
            throw new TypeException("Exception in HeapReadingExpression: the expression is not a Reference Type");
    }

    public String toString(){
        return "HeapReading(" + expression.toString() + ")";
    }
}
