package Model.expression;

import Exceptions.*;
import Model.adt.IDictionary;
import Model.adt.IHeap;
import Model.types.IType;
import Model.types.IntType;
import Model.value.IValue;
import Model.value.IntValue;

public class ArithmeticExpression implements Expression {
  private  char op;
  private Expression exp1, exp2;

    public ArithmeticExpression(char op, Expression exp1, Expression exp2) {
        this.op = op;
        this.exp1 = exp1;
        this.exp2 = exp2;
    }

    public IValue eval(IDictionary<String, IValue> symbolTable, IHeap<Integer, IValue> heap) throws IntegerException, DivisionByZeroException, MyException, NonExistentKeyDictionaryException {
        IValue val1 = exp1.eval(symbolTable, heap);
        IValue val2 = exp2.eval(symbolTable, heap);

        if (val1.getType() != IntType.T) {
            throw new IntegerException("First operand is not an integer!");
        }
        if (val2.getType() != IntType.T) {
            throw new IntegerException("Second operand is not an integer!");
        }

        int num1 = ((IntValue) val1).getValue();
        int num2 = ((IntValue) val2).getValue();

            switch (op) {
                case '+':
                    return new IntValue(num1+num2);
                case '-':
                    return new IntValue(num1-num2);
                case '*':
                    return new IntValue(num1*num2);
                case '/':
                    if(num2==0) {
                        throw new DivisionByZeroException("Can't divide by 0!");
                    }
                    else {
                        return new IntValue(num1 / num2);
                    }
                default: {
                    throw new MyException("Invalid operator for ArithmeticExpression");
                }
            }
    }

    @Override
    public IType typeCheck(IDictionary<String, IType> typeEnvironment) throws TypeException, NonExistentKeyDictionaryException, TypeException {
        IType type1, type2;
        type1 = exp1.typeCheck(typeEnvironment);
        type2 = exp2.typeCheck(typeEnvironment);
        if (type1.equals(new IntType())) {
            if (type2.equals(new IntType())) {
                return new IntType();
            }
            else
                throw new TypeException("Exception in ArithmeticExpression: the second operand is not an integer");
        }
        else
            throw new TypeException("Exception in ArithmeticExpression: the first operand is not an integer");
    }

    public String toString() { return exp1.toString() + " " + op + " " + exp2.toString(); }
}
