import java.util.AbstractMap.SimpleEntry;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Set;

/**
 * The MyHashMap class implements the Map ADT using a hashtable.
 * 
 * @author Your Name Here!
 */
public class MyHashMap<K, V> {
    //private ArrayList<LinkedList<SimpleEntry<K, V>> hashtable;
    private int size;
    private LinkedList<SimpleEntry<K, V>>[] hashtable;

    /** The initial length to use for the hashtable. */
    private static final int INITIAL_LENGTH = 11;

    /** The load factor to use for deciding when to resize our hashtable. */
    private static final float LOAD_FACTOR = 0.75f;

    /** An array of possible table sizes. */
    private static final int TABLE_SIZE[] = { 5, 11, 23, 47, 97, 197, 397, 797, 1597, 3203, 6421, 12853, 25717, 51437,
            102877, 205759, 411527, 823117, 1646237, 3292489, 6584983, 13169977, 26339969, 52679969, 105359939,
            210719881, 421439783, 842879579, 1685759167 };

    /**
     * Creates a new MyHashMap.
     */
    public MyHashMap() {
        size = 0;
        hashtable = createTable(INITIAL_LENGTH);
    }

    /**
     * Create a new array of {@link LinkedList}s with a prime length.
     * 
     * @param length The minimum length of the array
     * 
     * @return The new array with empty {@link LinkedList}s
     */
    @SuppressWarnings("unchecked")
    private LinkedList<SimpleEntry<K, V>>[] createTable(int length) {
        // get the closest prime length
        for (int primeCapacity : TABLE_SIZE) {
            if (primeCapacity >= length) {
                length = primeCapacity;
                break;
            }
        }

        // create the array of empty lists
        LinkedList<SimpleEntry<K, V>>[] list = (LinkedList<SimpleEntry<K, V>>[]) new LinkedList[length];
        for (int i = 0; i < list.length; i++) {
            list[i] = new LinkedList<SimpleEntry<K, V>>();
        }

        return list;
    }

    /**
     * Returns the number of key/value pairs stored in the MyHashMap and its hashtable.
     * 
     * @return The number of key/value pairs.
     */
    public int size(){
        int count = 0;
        for (LinkedList<SimpleEntry<K, V>> list : hashtable){
            for (SimpleEntry<K, V> entry : list){
                count++;
            }
        }
        return count;
    }
    /**
     * Returns true if the MyHashMap is empty, else false.
     * 
     * @return true if the MyHashMap is empty, else false.
     */
    public boolean isEmpty(){
        if (size == 0){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Removes all LinkedLists from hashtable.
     */
    public void clear(){
        for (int i = 0; i < hashtable.length; i++){
            hashtable[i].clear();
        }
        size = 0;
    }


    /**
    * Finds the index in the hashtable for the specified key.
    * 
    * @param key The key to find the index for.
    * @return The index in the hashtable for the specified key.
    */
    private int findIndex(K key) {
        int hash = key.hashCode() & Integer.MAX_VALUE;
        int index = hash % hashtable.length;
        return index;
    }
    
    /**
    * Associates the specified value with the specified key in the hashtable.
    * 
    * @param key The key with which the specified value is to be associated.
    * @param value The value to be associated with the specified key.
    * @return The previous value associated with the specified key, or null if there was no mapping for the key.
    */
    public SimpleEntry<K, V> find(K key){
        int hash = key.hashCode() & Integer.MAX_VALUE;
        int index = hash % hashtable.length;
        for (SimpleEntry<K, V> entry : hashtable[index]){
            if (entry.getKey().equals(key)){
                return entry;
            }
        }
        return null;
    }

    /**
    * Returns the value associated with the specified key.
    * 
    * @param key The key whose associated value is to be returned.
    * @return The value associated with the specified key, or null if the key was not found.
    */
    public V get(K key){
        if (find(key) == null){
            return null;
        }
        return find(key).getValue();
    }

    /**
    * Inserts the specified entry into the hashtable.
    * 
    * @param entry The entry to be inserted.
    */
    public void insert(SimpleEntry<K, V> entry){
        K key = entry.getKey();
        int hash = key.hashCode() & Integer.MAX_VALUE;
        int index = hash % hashtable.length;
        LinkedList list = hashtable[index];
        list.add(entry);
    }

    /**
    * Resizes the hashtable to accommodate more entries.
    */
    public void resize(){
        LinkedList<SimpleEntry<K, V>>[] newArray = createTable(hashtable.length * 2);
        for (int i = 0; i < hashtable.length; i++){
            newArray[i] = hashtable[i];
            for (SimpleEntry<K, V> entry : hashtable[i]){
                newArray[i].add(entry);
            }
        }
    }


    /**
    * Associates the specified value with the specified key in the hashtable.
    * 
    * @param key The key with which the specified value is to be associated.
    * @param value The value to be associated with the specified key.
    * @return The previous value associated with the specified key, or null if there was no mapping for the key.
    */
    public V put(K key, V value) {
        V saved = null;
        int index = findIndex(key);

        if (index != -1) {
           // Key found in the linked list at hashtable[index]
            LinkedList<SimpleEntry<K, V>> list = hashtable[index];
            for (SimpleEntry<K, V> entry : list) {
                if (Objects.equals(entry.getKey(), key)) {
                    saved = entry.getValue();
                    entry.setValue(value);
                    return saved;
                }
            }
        } else {
            // Key not found, add a new entry
            SimpleEntry<K, V> newEntry = new SimpleEntry<>(key, value);
            insert(newEntry);
            size++;
        }

        if (size() > LOAD_FACTOR * hashtable.length) {
            resize();
        }

        return saved;
    }
    
    /**
    * Removes the specified entry from the hashtable.
    * 
    * @param entry The entry to be removed.
    */
    private void removeEntry(SimpleEntry<K, V> entry){
        K key = entry.getKey();
        int hash = key.hashCode() & Integer.MAX_VALUE;
        int index = hash % hashtable.length;
        LinkedList list = hashtable[index];
        list.remove();
    }

    /**
    * Removes the entry with the specified key from the hashtable.
    * 
    * @param key The key whose entry is to be removed.
    * @return The value associated with the removed key, or null if the key was not found.
    */
    public V remove(K key){
        if (find(key) != null){
            V saved = find(key).getValue();
            removeEntry(find(key));
            size--;
            return saved;
        }
        else{
            return null;
        }
    }

    /**
    * Returns a set view of all entries in the hashtable.
    * 
     * @return A set of all entries in the hashtable.
    */
    public Set<SimpleEntry<K, V>> entrySet(){
        HashSet<SimpleEntry<K, V>> hashSet = new HashSet();
        for (LinkedList list : hashtable){
            hashSet.addAll(list);
        }
        return hashSet;
    }

    /**
     * Determines whether the specified key is already a key in the hashtable.
     * 
     * @param key The key to check.
     * @return true if the key is present, otherwise false.
     */
    public boolean containsKey(K key){
        if (find(key) != null){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Determines whether the specified value is present in the hashtable.
     * 
     * @param value The value to check.
     * @return true if the value is present, otherwise false.
     */
    public boolean containsValue(V value){
        Set<SimpleEntry<K, V>> entrySet = entrySet();
        for (SimpleEntry<K, V> entry : entrySet){
            if (entry.getValue() == value){
                return true;
            }
        }
        return false;
    }
}