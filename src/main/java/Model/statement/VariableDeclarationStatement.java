package Model.statement;


import Exceptions.MyException;
import Exceptions.NonExistentKeyDictionaryException;
import Exceptions.TypeException;
import Model.ProgramState;
import Model.adt.IDictionary;
import Model.types.IType;
import Model.value.IValue;

public class VariableDeclarationStatement implements IStatement{
    private final String name;
    private final IType type;

    public VariableDeclarationStatement(String name, IType type){
        this.name = name;
        this.type = type;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws MyException {
        IDictionary<String, IValue> symbolTable = programState.getSymbolTable();

        if (symbolTable.keyExists(name)) {
            throw new MyException("Variable is already defined");
        }

        symbolTable.add(name, type.defaultValue());
        return null;
    }

    @Override
    public String toString() {
        return type + " " + name;
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnvironment) throws NonExistentKeyDictionaryException, TypeException, MyException {
        typeEnvironment.add(name,type);
        return typeEnvironment;
    }
}
