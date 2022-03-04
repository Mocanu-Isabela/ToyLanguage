package Model.adt;

import Exceptions.EmptyListException;

import java.util.ArrayList;
import java.util.List;

public class TheList<T> implements IList<T> {
    List<T> theList = new ArrayList<T>();

    @Override
    public void add(T v){
        theList.add(v);
    }

    @Override
    public void remove(int pos) throws EmptyListException {
        if (theList.isEmpty()){
            throw new EmptyListException("The output list is empty!");
        }
        theList.remove(pos);
    }

    @Override
    public boolean isEmpty() {
        return theList.isEmpty();
    }

    @Override
    public String toString() {
//        for(var item: theList)
//        {
//            return "[" + item.toString() + "]";
//        }
//        return " ";
        StringBuilder sbuilder = new StringBuilder();
        for(var elem:theList){
            sbuilder.append(elem.toString()).append("\n");
        }
        return sbuilder.toString();
    }

    @Override
    public List getContent() {
        return theList;
    }
}
