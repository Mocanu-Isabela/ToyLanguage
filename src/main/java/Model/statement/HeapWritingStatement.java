package Model.statement;

import Exceptions.*;
import Model.ProgramState;
import Model.adt.IDictionary;
import Model.adt.IHeap;
import Model.expression.Expression;
import Model.types.IType;
import Model.types.ReferenceType;
import Model.value.IValue;
import Model.value.ReferenceValue;

public class HeapWritingStatement implements IStatement{
    private String variableName;
    private Expression expression;

    public HeapWritingStatement(String variableName, Expression expression)
    {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws Exception {
        IDictionary<String, IValue> symbolsTable = programState.getSymbolTable();
        IHeap<Integer, IValue> heap = programState.getHeap();
        if(symbolsTable.keyExists(variableName))
        {
            IValue value = symbolsTable.lookup(variableName);
            if(value.getType() instanceof ReferenceType)
            {
                ReferenceValue refValue = (ReferenceValue) value;
                int address = refValue.getAddress();
                if (heap.keyExists(address))
                {
                    IValue expressionValue = expression.eval(symbolsTable, heap);
                    if(expressionValue.getType().equals(refValue.getLocationType()))
                    {
                        heap.add(address, expressionValue);
                    }
                }
                else throw new MyException("There is no variable defined on the heap at the address " + address);
            }
            else throw new MyException("The value of the variable is not of reference type");
        }
        else throw new MyException("This variable is not in the symbol table");
        return null;
    }

    public String toString(){
        return "HeapWriting(" + variableName + "->" + expression.toString() + ")";
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnvironment) throws NonExistentKeyDictionaryException, TypeException {
        IType type_var = typeEnvironment.lookup(variableName);
        IType type_expression = expression.typeCheck(typeEnvironment);
        if (type_var.equals(new ReferenceType(type_expression)))
            return typeEnvironment;
        else
            throw new TypeException("Exception in HeapWritingStatement: the left and right sides have different types");
    }
}
