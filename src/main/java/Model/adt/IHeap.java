package Model.adt;

import Exceptions.NonExistentKeyDictionaryException;
import Model.value.IValue;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public interface IHeap<K, V> {
    void clear();
    boolean keyExists(K key);
    V lookup(K key) throws NonExistentKeyDictionaryException;
    boolean isEmpty();
    Set<K> keys();
    Collection<V> values();
    Map<K, V> get_content();
    V add(K key, V value);
    V remove(K id);
    int size();
    String toString();
    StringBuilder toString2();
    Integer putOnNextFreeAddress(IValue value);
    void set_content(HashMap<K, V> map);
    boolean is_referenced(K key);
    HashMap<Integer, V> toHashMap();
}
