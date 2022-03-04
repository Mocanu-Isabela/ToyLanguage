package Model.adt;

import Exceptions.EmptyListException;

import java.util.List;

public interface IList<T> {
    void add(T v);
    void remove(int pos) throws EmptyListException;
    boolean isEmpty();
    String toString();
    List getContent();


}
