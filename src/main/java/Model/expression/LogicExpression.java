package Model.expression;

import Exceptions.*;
import Model.adt.IDictionary;
import Model.adt.IHeap;
import Model.types.BoolType;
import Model.types.IType;
import Model.value.BoolValue;
import Model.value.IValue;

public class LogicExpression implements Expression {
    private String op;
    private Expression exp1, exp2;

    public LogicExpression(String op, Expression exp1, Expression exp2) {
        this.op = op;
        this.exp1 = exp1;
        this.exp2 = exp2;
    }
    
    public IValue eval(IDictionary<String, IValue> symbolTable, IHeap<Integer, IValue> heap) throws MyException, IntegerException, DivisionByZeroException, NonExistentKeyDictionaryException {
        IValue val1 = exp1.eval(symbolTable, heap);
        IValue val2 = exp2.eval(symbolTable, heap);

        if (val1.getType() != BoolType.T) {
            throw new IntegerException("First operand is not a boolean!");
        }
        if (val2.getType() != BoolType.T) {
            throw new IntegerException("Second operand is not a boolean!");
        }

        boolean bool1 = ((BoolValue) val1).getValue();
        boolean bool2 = ((BoolValue) val2).getValue();

        boolean result;
        switch (op) {
            case "&&" -> result = bool1 && bool2;
            case "||" -> result = bool1 || bool2;
            default -> throw new MyException("Invalid operator for LogicExpression");
        }

        if (result) {
            return BoolValue.TRUE;
        }
        else {
            return BoolValue.FALSE;
        }
    }

    @Override
    public IType typeCheck(IDictionary<String, IType> typeEnvironment) throws TypeException, NonExistentKeyDictionaryException {
        IType type1, type2;
        type1 = exp1.typeCheck(typeEnvironment);
        type2 = exp2.typeCheck(typeEnvironment);
        if (type1.equals(new BoolType())) {
            if (type2.equals(new BoolType())) {
                return new BoolType();
            }
            else
                throw new TypeException("Exception in LogicExpression: second operand is not a bool");
        }
        else
            throw new TypeException("Exception in LogicExpression: first operand is not a bool");
    }

    @Override
    public String toString() {
        return exp1.toString() + " " + op + " " + exp2.toString();
    }
}