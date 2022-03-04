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
import Model.value.IntValue;
import Model.value.StringValue;

import java.io.*;

public class ReadFileStatement implements IStatement{
    Expression expression;
    String variable_name;

    public ReadFileStatement(Expression expr, String var){
        this.expression = expr;
        this.variable_name = var;
    }

    @Override
    public ProgramState execute(ProgramState ps) throws MyException, NonExistentKeyDictionaryException, DivisionByZeroException, IntegerException {
        System.out.println("Reading...");
        if (ps.getSymbolTable().keyExists(this.variable_name)) {
            IHeap<Integer, IValue> heap = new Heap<Integer, IValue>();
            IValue value = this.expression.eval(ps.getSymbolTable(), heap);
            if (value.getType().equals(new StringType())) {
                StringValue svalue = (StringValue) value;
                if (ps.getFileTable().keyExists(svalue)) {
                    BufferedReader reader = ps.getFileTable().lookup(svalue);
                    try {
                        String line = reader.readLine();
                        IntValue intV;
                        if (line != null) {
                            intV = new IntValue(Integer.parseInt(line));
                        }
                        else {
                            intV = new IntValue();
                        }
                        ps.getSymbolTable().add(variable_name, intV);
                    }
                    catch (IOException e) {
                        throw new MyException(e.toString());
                    }
                }
                else {
                    throw new MyException("This StringValue is not in the FileTable!");
                }
            }
            else {
                throw new MyException("This expression is not of StringType!");
            }
        }
        else {
            throw new MyException("This variable is not defined!");
        }
        return null;
    }

    public String toString(){
        return "ReadFile " + this.expression.toString();
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnvironment) throws NonExistentKeyDictionaryException, TypeException, MyException {
        if (expression.typeCheck(typeEnvironment).equals(new StringType())){
            typeEnvironment.add(variable_name,new StringType());
            return typeEnvironment;}
        else{
            throw new TypeException("Exception in ReadFileStatement: expression is not of StringType");
        }
    }
}
