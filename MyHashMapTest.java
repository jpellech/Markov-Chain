import java.util.AbstractMap.SimpleEntry;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MyHashMapTest {


    @Test
    void size() {
        MyHashMap myHashMap = new MyHashMap<>();
        assertEquals(0, myHashMap.size());
        myHashMap.put("Key1", 1);
        assertEquals(1, myHashMap.size());
    }

    @Test
    void isEmpty() {
        MyHashMap myHashMap = new MyHashMap<>();
        assertTrue(myHashMap.isEmpty());
        myHashMap.put("Key1", 1);
        assertFalse(myHashMap.isEmpty());
    }

    @Test
    void clear() {
        MyHashMap myHashMap = new MyHashMap<>();
        myHashMap.put("Key1", 1);
        myHashMap.clear();
        assertEquals(0, myHashMap.size());
        assertTrue(myHashMap.isEmpty());
    }

    @Test
    void find() {
        MyHashMap myHashMap = new MyHashMap<>();
        myHashMap.put("Key1", 1);
        myHashMap.put("Key2", 2);

        assertEquals(1, myHashMap.find("Key1"));
        assertEquals(2, myHashMap.find("Key2"));
        assertNull(myHashMap.find("Key3"));
    }

    @Test
    void get() {
        MyHashMap myHashMap = new MyHashMap<>();
        myHashMap.put("Key1", 1);
        myHashMap.put("Key2", 2);

        assertEquals(1, myHashMap.get("Key1"));
        assertEquals(2, myHashMap.get("Key2"));
        assertNull(myHashMap.get("Key3") );
    }

    @Test
    void insert() {
        MyHashMap myHashMap = new MyHashMap<>();
        myHashMap.insert(new SimpleEntry<>("Key1", 1));
        assertEquals(1, myHashMap.size());
        assertEquals(1, myHashMap.get("Key1"));
    }

    @Test
    void resize() {
        MyHashMap myHashMap = new MyHashMap<>();
        myHashMap.put("Key 1", 1);
        myHashMap.put("Key 2", 1);
        myHashMap.put("Key 3", 1);
        myHashMap.put("Key 4", 1);
        myHashMap.put("Key 5", 1);
        myHashMap.put("Key 6", 1);
        myHashMap.put("Key 7", 1);
        myHashMap.put("Key 8", 1);
        myHashMap.put("Key 9", 1);
        myHashMap.put("Key 10", 1);
        myHashMap.put("Key 11", 1);
        assertEquals(20, myHashMap.size());
    }

    @Test
    void put() {
        MyHashMap<String, Integer> myHashMap = new MyHashMap<>();

        // Save a number value for a new String key and ensure put returns null
        assertNull(myHashMap.put("Key1", 42));
        assertEquals(1, myHashMap.size());
        assertEquals(42, myHashMap.get("Key1"));

        // Save a new number value for an existing String key and ensure put returns the previous value
        Integer previousValue = myHashMap.put("Key1", 99);
        assertEquals(42, previousValue);
        assertEquals(1, myHashMap.size());
        assertEquals(99, myHashMap.get("Key1"));
    }

    @Test
    void testRemove() {
        MyHashMap<String, Integer> myHashMap = new MyHashMap<>();
        myHashMap.put("one", 1);
        assertEquals(1, myHashMap.size());
        myHashMap.remove("one");
        assertEquals(0, myHashMap.size());
    }

    @Test
    void testContainsKey() {
        MyHashMap<String, Integer> myHashMap = new MyHashMap<>();
        myHashMap.put("one", 1);
        assertTrue(myHashMap.containsKey("one"));
        assertFalse(myHashMap.containsKey("blob"));
    }

    @Test
    void testContainsValue() {
        MyHashMap<String, Integer> myHashMap = new MyHashMap<>();
        myHashMap.put("one", 1);
        assertTrue(myHashMap.containsValue(1));
        assertFalse(myHashMap.containsValue(2));
    }
    

    @Test
    void testEntrySet() {
        
    }

}