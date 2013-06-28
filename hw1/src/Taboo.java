/*
 HW1 Taboo problem class.
 Taboo encapsulates some rules about what objects
 may not follow other objects.
 (See handout).
*/

import java.util.*;

public class Taboo<T> {
    HashMap<T, HashSet<T>> database;

    /**
     * Constructs a new Taboo using the given rules (see handout.)
     *
     * @param rules rules for new Taboo
     */

    public Taboo(List<T> rules) {
        database = new HashMap<T, HashSet<T>>();
        T key = null;
        for (T value : rules) {
            if (key != null && value != null) {

                if (!database.containsKey(key))
                    database.put(key, new HashSet<T>());

                database.get(key).add(value);
            }

            key = value;

        }
    }

    /**
     * Returns the set of elements which should not follow
     * the given element.
     *
     * @param elem  given elem
     * @return elements which should not follow the given element
     */
    public Set<T> noFollow(T elem) {
        if(database.containsKey(elem))
            return database.get(elem);
        else
            return Collections.emptySet();
    }

    /**
     * Removes elements from the given list that
     * violate the rules (see handout).
     *
     * @param list collection to reduce
     */
    public void reduce(List<T> list) {

        T currValue = null;
        int listSize = list.size();
        for (int i = 0; i < listSize; i++) {
            T nextValue = list.get(i);

            if (currValue != null && nextValue != null
                    && database.containsKey(currValue)
                    && database.get(currValue).contains(nextValue)) {
                list.remove(i);
                i--;
                listSize--;

            } else
                currValue = nextValue;
        }
    }
}
