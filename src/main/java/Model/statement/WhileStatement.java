package Model.statement;

import Exceptions.MyException;
import Exceptions.NonExistentKeyDictionaryException;
import Exceptions.TypeException;
import Model.ProgramState;
import Model.adt.IDictionary;
import Model.expression.Expression;
import Model.types.BoolType;
import Model.types.IType;
import Model.value.BoolValue;
import Model.value.IValue;

public class WhileStatement implements IStatement{
    Expression condition;
    IStatement statement;

    public WhileStatement(Expression condition, IStatement statement)
    {
        this.condition = condition;
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws Exception {
        IValue evaluated = this.condition.eval(programState.getSymbolTable(), programState.getHeap());
        System.out.println(evaluated.toString());
        if(evaluated.getType().equals(new BoolType()))
        {
            BoolValue evaluatedBool = (BoolValue) evaluated;
            if(evaluatedBool.getValue())
            {
                programState.getExecutionStack().push(this);
                programState.getExecutionStack().push(this.statement);
            }
        }
        else
        {
            throw new MyException("The value is not a BoolType!");
        }
        return null;
    }

    public String toString(){
        return "(while(" + condition + ")" + statement.toString() + ")";
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnvironment) throws NonExistentKeyDictionaryException, TypeException, MyException {
        IType type_expression = condition.typeCheck(typeEnvironment);
        if (type_expression.equals(new BoolType())) {
            statement.typeCheck(typeEnvironment.deepCopy());
            return typeEnvironment;
        }
        else
            throw new TypeException("Exception in WhileStatement: The condition has not the type bool");
    }
}
