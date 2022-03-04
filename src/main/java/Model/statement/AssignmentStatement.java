package Model.statement;

import Exceptions.MyException;
import Exceptions.NonExistentKeyDictionaryException;
import Exceptions.TypeException;
import Exceptions.VariableNotDefinedInSymbolTableException;
import Model.ProgramState;
import Model.adt.Heap;
import Model.adt.IDictionary;
import Model.adt.IHeap;
import Model.expression.Expression;
import Model.types.IType;
import Model.value.IValue;

public class AssignmentStatement implements IStatement{
    private final String id;
    private final Expression expression;

    public AssignmentStatement(String id, Expression exp){
        this.id = id;
        this.expression = exp;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws Exception {
        IDictionary<String, IValue> symbolTable = programState.getSymbolTable();

        if (!symbolTable.keyExists(id)) {
            throw new VariableNotDefinedInSymbolTableException("Variable " + id + " is not defined");
        }

        IHeap<Integer, IValue> heap = new Heap<Integer, IValue>();
        IValue value = expression.eval(symbolTable, heap);
        IValue oldValue = symbolTable.lookup(id);
        IType idType = oldValue.getType();

        if (value.getType() != idType && !value.getType().equals(idType)) {
            throw new MyException("Type of expression and type of variable " + id + " do not match");
        }
        symbolTable.add(id, value);
        return null;
    }

    @Override
    public String toString(){
        return this.id +  " = " + this.expression.toString();
    }

    @Override
    public IDictionary<String,IType> typeCheck(IDictionary<String, IType> typeEnvironment) throws NonExistentKeyDictionaryException, TypeException {
        IType type_var = typeEnvironment.lookup(id);
        IType type_expression = expression.typeCheck(typeEnvironment);
        if (type_var.equals(type_expression))
            return typeEnvironment;
        else
            throw new TypeException("Exception in AssignmentStatement: the left and right sides have different types");
    }
}
