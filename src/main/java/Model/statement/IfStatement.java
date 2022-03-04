package Model.statement;

import Exceptions.MyException;
import Exceptions.NonExistentKeyDictionaryException;
import Exceptions.TypeException;
import Model.ProgramState;
import Model.adt.Heap;
import Model.adt.IDictionary;
import Model.adt.IHeap;
import Model.adt.IStack;
import Model.expression.Expression;
import Model.types.BoolType;
import Model.types.IType;
import Model.value.BoolValue;
import Model.value.IValue;

public class IfStatement implements IStatement {
    private final Expression condition;
    private final IStatement thenStatement;
    private final IStatement elseStatement;

    public IfStatement(Expression cond, IStatement thenStatement, IStatement elseStatement) {
        this.condition = cond;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws Exception{
        IDictionary<String, IValue> symbolTable = programState.getSymbolTable();
        IHeap<Integer, IValue> heap = new Heap<Integer, IValue>();
        IValue conditionValue = condition.eval(symbolTable, heap);

        if (conditionValue.getType() != BoolType.T) {
            throw new MyException("Conditional expression is not a boolean");
        }

        IStack<IStatement> executionStack = programState.getExecutionStack();
        if (conditionValue == BoolValue.TRUE) {
            executionStack.push(thenStatement);
        }
        else {
            executionStack.push(elseStatement);
        }
        return null;
    }

    @Override
    public String toString() {
        return "(IF(" + condition.toString() + ")THEN(" + thenStatement.toString() + ")ELSE(" + elseStatement.toString() + "))";
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnvironment) throws NonExistentKeyDictionaryException, TypeException, MyException {
        IType type_expression = condition.typeCheck(typeEnvironment);
        if (type_expression.equals(new BoolType())) {
            thenStatement.typeCheck(typeEnvironment.deepCopy());
            elseStatement.typeCheck(typeEnvironment.deepCopy());
            return typeEnvironment;
        }
        else
            throw new TypeException("Exception in IfStatement: The condition has not the type bool");
    }
}
