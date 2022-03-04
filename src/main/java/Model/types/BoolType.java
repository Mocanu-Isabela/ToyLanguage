package Model.types;

import Model.value.BoolValue;
import Model.value.IValue;

public class BoolType implements IType{
    public static final BoolType T = new BoolType();

    @Override
    public IValue defaultValue() {
        return BoolValue.FALSE;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || o.getClass() != this.getClass())
            return false;
        return true;
    }

    @Override
    public IType deepCopy() {
        return new BoolType();
    }

    @Override
    public String toString(){
        return "bool";
    }
}
