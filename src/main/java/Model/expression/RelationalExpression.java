package Model.expression;

import Exceptions.*;
import Model.adt.IDictionary;
import Model.adt.IHeap;
import Model.types.BoolType;
import Model.types.IType;
import Model.types.IntType;
import Model.value.BoolValue;
import Model.value.IValue;
import Model.value.IntValue;

public class RelationalExpression implements Expression {
    private String op;
    private Expression exp1, exp2;

    public RelationalExpression(String op, Expression exp1, Expression exp2) {
        this.op = op;
        this.exp1 = exp1;
        this.exp2 = exp2;
    }

    public IValue eval(IDictionary<String, IValue> symbolTable, IHeap<Integer, IValue> heap) throws MyException, NonExistentKeyDictionaryException, DivisionByZeroException, IntegerException {
        IValue value1 = exp1.eval(symbolTable, heap);
        IntType type = new IntType();
        if (value1.getType().equals(type)) {
            IValue value2 = exp2.eval(symbolTable, heap);
            if (value2.getType().equals(type)) {
                IntValue intV1 = (IntValue) value1;
                IntValue intV2 = (IntValue) value2;
                int nr1 = intV1.getValue();
                int nr2 = intV2.getValue();
                boolean result = false;
                if (op.equals("<")){
                    if (nr1 < nr2){
                        result = true;
                    }
                }
                if (op.equals("<=")){
                    if (nr1 <= nr2){
                        result = true;
                    }
                }
                if (op.equals(">")){
                    if (nr1 > nr2){
                        result = true;
                    }
                }
                if (op.equals(">=")){
                    if (nr1 >= nr2){
                        result = true;
                    }
                }
                if (op.equals("==")){
                    if (nr1 == nr2){
                        result = true;
                    }
                }
                if (op.equals("!=")){
                    if (nr1 != nr2){
                        result = true;
                    }
                }
                return new BoolValue(result);
            }
            else throw new MyException("The second operator is not an integer!");
        }
        else throw new MyException("The first operator is not an integer!");
    }

    @Override
    public IType typeCheck(IDictionary<String, IType> typeEnvironment) throws TypeException, NonExistentKeyDictionaryException {
        IType type1, type2;
        type1 = exp1.typeCheck(typeEnvironment);
        type2 = exp2.typeCheck(typeEnvironment);
        if (type1.equals(new IntType())) {
            if (type2.equals(new IntType())) {
                return new BoolType();
            }
            else
                throw new TypeException("Exception in RelationalExpression: second operand is not an integer");
        }
        else
            throw new TypeException("Exception in RelationalExpression: first operand is not an integer");
    }

    @Override
    public String toString() {
        return exp1.toString() + " " + op + " " + exp2.toString();
    }
}
