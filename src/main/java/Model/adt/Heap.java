package Model.adt;

import Exceptions.NonExistentKeyDictionaryException;
import Model.value.IValue;
import Model.value.ReferenceValue;

import java.util.*;

public class Heap<K, V> implements IHeap<Integer, IValue> {
    HashMap<Integer, IValue> heap;
    int nextFreeAddress = 1;

    public Heap() {
        this.heap = new HashMap<Integer, IValue>();
    }

    @Override
    public void clear() {
        this.heap.clear();
    }

    @Override
    public boolean keyExists(Integer key) {
        return this.heap.containsKey(key);
    }

    @Override
    public IValue lookup(Integer key) {
        return this.heap.get(key);
    }

    @Override
    public boolean isEmpty() {
        return this.heap.isEmpty();
    }

    @Override
    public Set<Integer> keys() {
        return this.heap.keySet();
    }

    @Override
    public Collection<IValue> values() {
        return this.heap.values();
    }

    @Override
    public Map<Integer, IValue> get_content() {
        return this.heap;
    }

    @Override
    public IValue add(Integer key, IValue value) {
        return this.heap.put(key, value);
    }

    @Override
    public IValue remove(Integer id) {
        return this.heap.remove(id);
    }

    @Override
    public int size() {
        return this.heap.size();
    }

    @Override
    public String toString() {
        return this.heap.toString();
    }

    @Override
    public StringBuilder toString2() {
        StringBuilder string = new StringBuilder();
        for(Integer address : keys())
        {
            string.append("{").append(address).append("->").append(this.heap.get(address)).append("}, ");
        }
        return string;
    }

    @Override
    public Integer putOnNextFreeAddress(IValue value) {
        this.heap.put(nextFreeAddress, value);
        nextFreeAddress++;
        return nextFreeAddress-1;
    }

    @Override
    public void set_content(HashMap<Integer, IValue> map) {
        this.heap = map;
    }

    @Override
    public boolean is_referenced(Integer key) {
        for(Integer k : keys())
            if(!k.equals(key))
            {
                IValue value = this.heap.get(k);

                while(value instanceof ReferenceValue) {
                    if(((ReferenceValue) value).getAddress() == Integer.parseInt(key.toString())) {
                        return true;
                    }
                    value = this.heap.get(((ReferenceValue) value).getAddress());
                }
            }
        return false;
    }

    @Override
    public HashMap<Integer, IValue> toHashMap() {
        return this.heap;
    }
}
