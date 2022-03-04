package Model.adt;
import Exceptions.EmptyStackException;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;

public class TheStack<T> implements IStack<T> {
    Stack<T> stack;

    public TheStack() {
        this.stack = new Stack<T>();
    }

    @Override
    public T pop() throws EmptyStackException {
        if (stack.isEmpty()){
            throw new EmptyStackException("The execution stack is empty!");
        }
        return stack.pop();
    }

    @Override
    public void push(T v) {
        this.stack.push(v);
    }

    @Override
    public T peek() {
        return this.stack.peek();
    }

    @Override
    public Stack<T> getStack() {
        return this.stack;
    }

    @Override
    public boolean isEmpty() {
        return this.stack.isEmpty();
    }

    @Override
    public String toString() {
        return this.stack.toString();
    }

}
