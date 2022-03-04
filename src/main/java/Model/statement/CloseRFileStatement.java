package Model.statement;

import Exceptions.*;
import Model.ProgramState;
import Model.adt.Heap;
import Model.adt.IDictionary;
import Model.adt.IHeap;
import Model.expression.Expression;
import Model.types.IType;
import Model.types.StringType;
import Model.value.IValue;
import Model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFileStatement implements IStatement{
    Expression expression;

    public CloseRFileStatement(Expression expr){
        this.expression = expr;
    }

    @Override
    public ProgramState execute(ProgramState ps) throws MyException, NonExistentKeyDictionaryException, DivisionByZeroException, IntegerException {
        System.out.println("File is closing...");
        IHeap<Integer, IValue> heap = new Heap<Integer, IValue>();
        IValue value = this.expression.eval(ps.getSymbolTable(), heap);
        if(value.getType().equals(new StringType())){
            StringValue svalue = (StringValue) value;
            if(ps.getFileTable().keyExists(svalue)){
                BufferedReader reader = ps.getFileTable().lookup(svalue);
                try{
                    reader.close();
                    ps.getFileTable().remove(svalue);
                }
                catch (IOException | EmptyDictionaryException e) {
                    throw new MyException(e.toString());
                }
            }
            else{
                throw new MyException("This StringValue is not in the FileTable.");
            }
        }
        else{
            throw new MyException("This expression is not of StringType.");
        }
        return null;
    }

    public String toString(){
        return "CloseRFile " + this.expression.toString();
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnvironment) throws NonExistentKeyDictionaryException, TypeException {
        if (expression.typeCheck(typeEnvironment).equals(new StringType())){
            return typeEnvironment;
        }
        else {
            throw new TypeException("Exception in CloseRFileStatement: the expression is not of StringType");
        }
    }
}
