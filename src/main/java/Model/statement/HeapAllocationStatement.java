package Model.statement;

import Exceptions.MyException;
import Exceptions.NonExistentKeyDictionaryException;
import Exceptions.TypeException;
import Model.ProgramState;
import Model.adt.IDictionary;
import Model.adt.IHeap;
import Model.expression.Expression;
import Model.types.IType;
import Model.types.ReferenceType;
import Model.value.IValue;
import Model.value.ReferenceValue;

public class HeapAllocationStatement implements IStatement{
    private String variableName;
    private Expression expression;

    public HeapAllocationStatement(String varName, Expression expr)
    {
        variableName = varName;
        expression = expr;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws Exception {
        IDictionary<String, IValue> symbolsTable = programState.getSymbolTable();
        IHeap<Integer, IValue> heap = programState.getHeap();
        if(symbolsTable.keyExists(variableName))
        {
            if(symbolsTable.lookup(variableName) instanceof ReferenceValue)
            {
                IValue value = expression.eval(symbolsTable, heap);
                ReferenceValue refValue = (ReferenceValue) symbolsTable.lookup(variableName);
                if (refValue.getLocationType().equals(value.getType()))
                {
                    Integer address = heap.putOnNextFreeAddress(value);
                    ReferenceValue newRefValue = new ReferenceValue(address, refValue.getLocationType());
                    symbolsTable.update(variableName, newRefValue);
                }
                else throw new MyException("The expression does not reference the correct type of value");
            }
            else throw new MyException("The variable is not of reference type");
        }
        else throw new MyException("Undefined variable");
        return null;
    }

    public String toString(){
        return "HeapAllocating(" + variableName + "->" + expression.toString() + ")";
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnvironment) throws NonExistentKeyDictionaryException, TypeException {
        IType type_var = typeEnvironment.lookup(variableName);
        IType type_expression = expression.typeCheck(typeEnvironment);
        if (type_var.equals(new ReferenceType(type_expression)))
            return typeEnvironment;
        else
            throw new TypeException("Exception in HeapAllocationStatement: the left and right sides have different types");
    }

}
