package Model.value;

import Model.types.StringType;
import Model.types.IType;

public class StringValue implements IValue{
    String value;

    public StringValue(){
        this.value = "";
    }

    public StringValue(String val){
        this.value = val;
    }

    @Override
    public boolean equals(Object o){
        if(o == null || o.getClass() != this.getClass())
            return false;
        StringValue o_value = (StringValue) o;
        return o_value.value.equals(this.value);
    }

    public String getValue(){
        return this.value;
    }

    @Override
    public IType getType() {
        return new StringType();
    }

    @Override
    public String toString(){
        return this.value;
    }

    @Override
    public IValue deepCopy() {
        return new StringValue(this.value);
    }
}