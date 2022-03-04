package Model.types;

import Model.value.IValue;
import Model.value.ReferenceValue;

public class ReferenceType implements IType{
    private final IType inner;

    public ReferenceType(IType inner)
    {
        this.inner = inner;
    }

    @Override
    public IValue defaultValue() {
        return new ReferenceValue(0, inner);
    }


    public IType getInner(){
        return inner;
    }

    @Override
    public boolean equals(Object o) {
//        if (o == null || o.getClass() != this.getClass())
//            return false;
//        return true;

        if(o instanceof ReferenceType) {
            return inner.equals(((ReferenceType) o).getInner());
        }

        return false;
    }

    @Override
    public String toString(){
        return "Ref(" + inner.toString() + ")";
    }

    @Override
    public IType deepCopy(){
//        IType type = inner.deepCopy();
//        return new ReferenceType(type);
        return new ReferenceType(inner);
    }
}
