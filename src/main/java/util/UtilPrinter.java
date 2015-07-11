package util;

import java.util.Collection;

/**
 * Full class description here.
 * Created by Grinch on 4/11/2015.
 * util
 */
public class UtilPrinter {

    public static void print(Collection<Object> elements) {
        if (elements != null) {
            for (Object object : elements) {
                if (object != null) {
                    System.out.println(object.toString());
                } else {
                    System.out.println("null");
                }
            }
        }
    }

    public static void print(Object object) {
        if (object != null) {
            System.out.println(object.toString());
        } else {
            System.out.println("null");
        }
    }
}
