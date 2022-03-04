package Model.adt;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import Exceptions.EmptyDictionaryException;
import Exceptions.NonExistentKeyDictionaryException;
import Model.value.IValue;

public class Dictionary<K,V> implements IDictionary<K,V> {
    Map<K, V> dictionary;

    public Dictionary() {
        dictionary = new HashMap<K,V>();
    }

    @Override
    public void add(K key, V value) throws RuntimeException {
        try {
            dictionary.put(key,value);
        }
        catch(RuntimeException e){
            throw new RuntimeException("Can't add null key/values into a dictionary.");
        }
    }

    @Override
    public void update(K v1, V v2) {
        this.dictionary.replace(v1, v2);
    }

    @Override
    public void remove(K key) throws NonExistentKeyDictionaryException, EmptyDictionaryException {
        if (dictionary.isEmpty()){
            throw new EmptyDictionaryException("The symbol table is empty.");
        }
        else if (dictionary.containsKey(key)){
            dictionary.remove(key);
        }
        else{
            throw new NonExistentKeyDictionaryException("The key is not in the symbol table.");
        }
    }

    @Override
    public boolean keyExists(K key) {
        return dictionary.containsKey(key);
    }

    @Override
    public V lookup(K key) throws NonExistentKeyDictionaryException {
        if (dictionary.containsKey(key)){
            return dictionary.get(key);
        }
        else{
            throw new NonExistentKeyDictionaryException("This key is not in the symbol table!");
        }
    }

    @Override
    public String toString() {
        return dictionary.toString();
    }

    @Override
    public boolean isEmpty() {
        return dictionary.isEmpty();
    }

    @Override
    public Set<K> keys() {
        return dictionary.keySet();
    }

    @Override
    public Collection<V> values() {
        return dictionary.values();
    }

    @Override
    public Map get_content() {
        return dictionary;
    }

    @Override
    public IDictionary<K, V> deepCopy() throws NonExistentKeyDictionaryException {
        Dictionary<K, V> copyDict = new Dictionary<>();
        for (K element : dictionary.keySet())
        {
            copyDict.add(element, this.lookup(element));
        }
        return copyDict;
    }
}
