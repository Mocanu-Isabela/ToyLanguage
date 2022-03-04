package Model.value;

import Model.types.BoolType;
import Model.types.IType;

public class BoolValue implements IValue{
    public static final BoolValue TRUE = new BoolValue(true);
    public static final BoolValue FALSE = new BoolValue(false);

    private final boolean value;

    public BoolValue(){
        this.value = false;
    }

    public BoolValue(boolean i){
        this.value = i;
    }

    @Override
    public boolean equals(Object o){
        if(o == null || o.getClass() != this.getClass())
            return false;
        BoolValue o_value = (BoolValue) o;
        return o_value.value == this.value;
    }

    public boolean getValue(){
        return this.value;
    }

    @Override
    public IType getType() {
        return BoolType.T;
    }

    @Override
    public String toString(){
        return "" + this.value;
    }

    @Override
    public IValue deepCopy() {
        return new BoolValue(this.value);
    }
}
