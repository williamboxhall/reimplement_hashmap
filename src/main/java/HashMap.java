public class HashMap {
    private final String[] values;

    public HashMap() {
        this(30);
    }

    public HashMap(int size) {
        values = new String[size];
    }

    public String get(Hashable key) {
        return values[indexFor(key)];
    }

    public void put(Hashable key, String value) {
        values[indexFor(key)] = value;
    }

    int indexFor(Hashable key) {
        return key.hash() % values.length;
    }
}
