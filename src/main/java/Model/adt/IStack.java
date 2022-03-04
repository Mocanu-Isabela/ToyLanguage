package Model.adt;

import Exceptions.EmptyStackException;

import java.util.Stack;

public interface IStack<T> {
    void push(T v);
    T pop() throws EmptyStackException;
    T peek();
    Stack<T> getStack();
    boolean isEmpty();
    String toString();
}

