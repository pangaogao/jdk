package diagrams.java.util;

import java.util.AbstractMap;

/**
 * Created by gaopanpan on 17/9/3.
 */
public class MapEntryTest {

    public static void main(String[] args) {
        AbstractMap.SimpleEntry simpleEntry = new AbstractMap.SimpleEntry("key", "value");
        AbstractMap.SimpleImmutableEntry immutableEntry = new AbstractMap.SimpleImmutableEntry("key", "value");
        System.out.println(simpleEntry.equals(immutableEntry));
    }
}
