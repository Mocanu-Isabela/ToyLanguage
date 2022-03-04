package Model.value;

import Model.types.ReferenceType;
import Model.types.IType;

public class ReferenceValue implements IValue{
    int address;
    IType locationType;

    public ReferenceValue(int address, IType locationType){
        this.address = address;
        this.locationType = locationType;
    }

    public int getAddress()
    {
        return address;
    }

    @Override
    public String toString()
    {
        return "referenceValue(" + address + "-> " + locationType + ")";
    }

    @Override
    public IType getType() {
        return new ReferenceType(locationType);
    }

    @Override
    public IValue deepCopy() {
//        IType type = locationType.deepCopy();
//        return new ReferenceValue(address, type);
        return new ReferenceValue(address, locationType);
    }

    public IType getLocationType() {
        return locationType;
    }
}
