package Model.adt;

import Exceptions.EmptyDictionaryException;
import Exceptions.MyException;
import Exceptions.NonExistentKeyDictionaryException;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface IDictionary<K, V>{

    void add(K key, V value) throws RuntimeException, MyException;
    void update(K v1, V v2);
    void remove(K id) throws NonExistentKeyDictionaryException, EmptyDictionaryException;
    boolean keyExists(K key);
    V lookup(K key) throws NonExistentKeyDictionaryException;
    String toString();
    boolean isEmpty();
    Set<K> keys();
    Collection<V> values();
    Map get_content();
    IDictionary<K,V> deepCopy() throws NonExistentKeyDictionaryException;
}
