package Model.adt;

import Exceptions.MyException;
import Exceptions.NonExistentKeyDictionaryException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FileTable<Filename, FileDescriptor> implements IDictionary<Filename, FileDescriptor> {
    Map<Filename, FileDescriptor> map = new HashMap<Filename, FileDescriptor>();

//    @Override
//    public void clear() {
//        map.clear();
//    }
//
//    @Override
//    public boolean containsFilename(Filename filename) {
//        return map.containsKey(filename);
//    }
//
//    @Override
//    public FileDescriptor get(Filename filename) {
//        return map.get(filename);
//    }

    @Override
    public void add(Filename filename, FileDescriptor fileDescriptor) throws MyException {
        try{
            map.put(filename, fileDescriptor);
        }
        catch (RuntimeException e){
            throw new MyException("You can't add null values into a dictionary.");
        }
    }

    @Override
    public void update(Filename v1, FileDescriptor v2) {
        map.replace(v1, v2);
    }

    @Override
    public void remove(Filename filename) {
        map.remove(filename);
    }

    @Override
    public boolean keyExists(Filename key) {
        return map.containsKey(key);
    }

    @Override
    public FileDescriptor lookup(Filename key) {
        return map.get(key);
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public Set<Filename> keys() {
        return map.keySet();
    }

    @Override
    public Collection<FileDescriptor> values() {
        return map.values();
    }

    @Override
    public Map<Filename, FileDescriptor> get_content() {
        return map;
    }

    public IDictionary<Filename, FileDescriptor> deepCopy() throws NonExistentKeyDictionaryException {
        return null;
    }
//
//    @Override
//    public int size() {
//        return map.size();
//    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Map.Entry<Filename, FileDescriptor> h : map.entrySet()) {
            s.append("File ").append(h.getKey()).append("\n");
        }
        return s.toString();
    }
}
