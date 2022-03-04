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

import java.io.*;

public class OpenRFileStatement implements IStatement{
    Expression expression;

    public OpenRFileStatement(Expression expr){
        this.expression = expr;
    }

    @Override
    public ProgramState execute(ProgramState ps) throws MyException, NonExistentKeyDictionaryException, DivisionByZeroException, IntegerException {
        System.out.println("File is opening...");
        IHeap<Integer, IValue> heap = new Heap<Integer, IValue>();
        IValue value = this.expression.eval(ps.getSymbolTable(), heap);
        if(value.getType().equals(new StringType())){
            StringValue svalue = (StringValue) value;
            if(!ps.getFileTable().keyExists(svalue)){
                try{
                    ps.getFileTable().add(svalue, new BufferedReader((new FileReader(svalue.getValue()))));
                }
                catch (IOException e) {
                    throw new MyException(e.toString());
                }
            }
            else{
                throw new MyException("This StringValue is already in the FileTable");
            }
        }
        else{
            throw new MyException("This expression is not of StringType");
        }
        return null;
    }

    public String toString(){
        return "OpenRFile " + this.expression.toString();
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnvironment) throws NonExistentKeyDictionaryException, TypeException {
        if (expression.typeCheck(typeEnvironment).equals(new StringType())){
            return typeEnvironment;}
        else {
            throw new TypeException("TypeCheck: Open File: Expression is not of StringType");
        }
    }
}
