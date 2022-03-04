package Model.statement;

import Exceptions.MyException;
import Exceptions.NonExistentKeyDictionaryException;
import Exceptions.TypeException;
import Model.ProgramState;
import Model.adt.Heap;
import Model.adt.IDictionary;
import Model.adt.IHeap;
import Model.adt.IList;
import Model.expression.Expression;
import Model.types.IType;
import Model.value.IValue;

public class PrintStatement implements IStatement{

    Expression expression;

    public PrintStatement(Expression exp){
        this.expression = exp;
    }

    public ProgramState execute(ProgramState state) throws Exception {
        IDictionary<String, IValue> symbolTable = state.getSymbolTable();
        IList<IValue> outList = state.getOutputList();
        IHeap<Integer, IValue> heap = state.getHeap();

        IValue value = expression.eval(symbolTable, heap);
        outList.add(value);
        return null;
    }

    @Override
    public String toString() {
        return "print(" + expression.toString() + ")";
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnvironment) throws NonExistentKeyDictionaryException, TypeException {
        expression.typeCheck(typeEnvironment);
        return typeEnvironment;
    }
}
