package hashtable;


public class AssosiativeArray {
    public static int capacity = 16;
    public static final double LOAD_FACTOR = 0.75;
    public int size;
    HashNode[] bucket = new HashNode[capacity];

    public void put(String key, String value) {
        //find index to put
        int index = hash(key);

        if (size > LOAD_FACTOR * capacity) {
            System.out.println("Rehashing");
            rehash();
        }
        insert(index,key, value);

    }



    private void insert(int index,String key, String value) {
        if (bucket[index] == null) {
            bucket[index] = new HashNode(key,value);
        } else {
            HashNode curr = bucket[index];
            while (true) {

                if (key.equals(curr.key)){
                    System.out.println(curr.value);
                    curr.setValue(value);
                    break;
                }

                if (curr.next == null) {
                    curr.next = new HashNode();
                    curr.next.setValue(value);
                    break;
                }
                curr = curr.next;
            }
        }
        size++;
    }

    private static int hash(String key) {
        return Math.abs(key.hashCode() % (capacity - 1));
    }

    public void remove(String key) {
        int index = hash(key);
        if (bucket[index] == null) {
            return;
        } else {
            if (bucket[index].next != null) {
                HashNode curr = bucket[index];
                while (curr.next != null) {
                    if (key.equals(curr.key)) {
                        if (curr.next != null) {
                            HashNode nextNode = curr.next;
                            curr.key = nextNode.key;
                            curr.value = nextNode.value;
                            curr.next = null;
                        } else {
                            curr.setEmpty();
                        }
                        break;
                    }
                    curr = curr.next;
                }

            } else {
                bucket[index] = null;
            }
        }
        size--;

    }

    private void rehash() {
        capacity += (LOAD_FACTOR * capacity);

        HashNode[] old = bucket;
        bucket = new HashNode[capacity];
        for (HashNode anOld : old) {
            if (anOld != null) {
                put(anOld.key, anOld.value);
            }
        }
    }

    class HashNode {
        HashNode next;

        String key;
        String value;

        HashNode() {

        }

        HashNode(String key, String value) {
            this.key = key;
            this.value = value;
        }
        public boolean isEmpty() {
            return value == null;
        }


        public void setValue(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public void setEmpty() {
            key = value = null;
            next = null;
        }
    }



    public static void main(String[] args) {
        AssosiativeArray hashmap = new AssosiativeArray();

        hashmap.put("Almas", "123-456-789");
        hashmap.put("Almas", "789-123-456");
        hashmap.put("Dauren", "156-156-143");
        for (HashNode aBucket : hashmap.bucket) {
            if (aBucket != null) {
                System.out.println("Key : " + aBucket.key
                        + "\nValue [" + aBucket.value + "]");
            }
        }
    }
}
