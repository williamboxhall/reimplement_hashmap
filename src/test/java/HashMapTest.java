import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HashMapTest {
    private static String VALUE_1 = "value1";
    private static String VALUE_2 = "value2";
    private static final int HASH_1 = 100;
    private static final int HASH_2 = 200;

    @Mock
    private Hashable key1;
    @Mock
    private Hashable key2;

    @Before
    public void setUp() {
        when(key1.hash()).thenReturn(HASH_1);
        when(key1.hash()).thenReturn(HASH_2);
    }

    @Test
    public void storesSingleValueWithKey() {
        HashMap hashMap = new HashMap();
        hashMap.put(key1, VALUE_1);
        assertThat(hashMap.get(key1), is(VALUE_1));
    }

    @Test
    public void tranformsKeyHashToArrayIndexByModulusOfEntriesArrayLength() {
        Hashable key = mock(Hashable.class);

        HashMap hashMap = new HashMap(4);

        when(key.hash()).thenReturn(1);
        assertThat(hashMap.indexFor(key), is(1));

        when(key.hash()).thenReturn(4);
        assertThat(hashMap.indexFor(key), is(0));

        when(key.hash()).thenReturn(5);
        assertThat(hashMap.indexFor(key), is(1));
    }

    @Test
    public void storesMultipleValuesWithMultipleKeys() {
        HashMap hashMap = new HashMap();
        hashMap.put(key1, VALUE_1);
        hashMap.put(key2, VALUE_2);
        assertThat(hashMap.get(key2), is(VALUE_2));
        assertThat(hashMap.get(key1), is(VALUE_1));
    }

    @Test
    public void canReplaceValueAtAKey() {
        HashMap hashMap = new HashMap();

        hashMap.put(key1, VALUE_1);
        assertThat(hashMap.get(key1), is(VALUE_1));

        hashMap.put(key1, VALUE_2);
        assertThat(hashMap.get(key1), is(VALUE_2));

    }

    @Test
    public void canResolveIndexCollisions() {
        HashMap hashMap = new HashMap(4);

        when(key1.hash()).thenReturn(1);
        when(key2.hash()).thenReturn(5);
        assertThat(hashMap.indexFor(key1), is(hashMap.indexFor(key2)));

        hashMap.put(key1, VALUE_1);
        hashMap.put(key2, VALUE_2);
        assertThat(hashMap.get(key2), is(VALUE_2));
        assertThat(hashMap.get(key1), is(VALUE_1));
    }

    @Test
    public void canResolveHashCollisions() {
        when(key1.hash()).thenReturn(HASH_1);
        when(key2.hash()).thenReturn(HASH_1);

        HashMap hashMap = new HashMap();
        hashMap.put(key1, VALUE_1);
        hashMap.put(key2, VALUE_2);
        assertThat(hashMap.get(key2), is(VALUE_2));
        assertThat(hashMap.get(key1), is(VALUE_1));
    }
}
