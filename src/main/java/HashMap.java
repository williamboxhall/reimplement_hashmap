public class HashMap {
    private int size = 0;
    private Entry[] entries;

    public HashMap() {
        this(30);
    }

    public HashMap(int length) {
        entries = new Entry[length];
    }

    public String get(Hashable key) {
        return entries[collisionResolvedIndexFor(key)].value;
    }

    public void put(Hashable key, String value) {
        int index = collisionResolvedIndexFor(key);
        if (entryExistsAt(index)) {
            updateValueAt(index, value);
        }
        addEntryAt(index, createEntryFor(key, value));
    }

    int collisionResolvedIndexFor(Hashable key) {
        int index = indexFor(key);
        while (entryExistsAtIndexWithDifferent(key, index)) {
            index = nextIndexAfterCollisionAt(index);
        }
        return index;
    }

    int indexFor(Hashable key) {
        return key.hash() % entries.length;
    }

    private void updateValueAt(int index, String value) {
        entries[index].value = value;
    }

    private void addEntryAt(int index, Entry entry) {
        entries[index] = entry;
        size++;
        resizeEntriesIfRequired();
    }

    private void resizeEntriesIfRequired() {
        if (size >= entries.length) {
            resize();
        }
    }

    private void resize() {
        size = 0;
        Entry[] old = entries;
        entries = new Entry[entries.length];
        for (Entry entry : old) {
            int index = collisionResolvedIndexFor(entry.key);
            entries[index] = entry;
        }
    }

    private boolean entryExistsAtIndexWithDifferent(Hashable key, int index) {
        return entryExistsAt(index) && !keyAt(index).equals(key);
    }

    private int nextIndexAfterCollisionAt(int index) {
        return (index + 1) % entries.length;
    }

    private Hashable keyAt(int index) {
        return entries[index].key;
    }

    private boolean entryExistsAt(int index) {
        return entries[index] != null;
    }

    private Entry createEntryFor(Hashable key, String value) {
        return new Entry(key, value);
    }

    private class Entry {
        private Hashable key;
        private String value;

        public Entry(Hashable key, String value) {
            this.key = key;
            this.value = value;
        }
    }
}
