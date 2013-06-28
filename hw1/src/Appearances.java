import java.util.*;

public class Appearances {
	
	/**
	 * Returns the number of elements that appear the same number
	 * of times in both collections. Static method. (see handout).
	 * @return number of same-appearance elements
	 */
	public static <T> int sameCount(Collection<T> a, Collection<T> b) {
		HashMap<T, Integer> aHash = new HashMap<T, Integer>();
        HashMap<T, Integer> bHash = new HashMap<T, Integer>();

        calculateAppearances(a, aHash);
        calculateAppearances(b, bHash);

        int result = 0;

        for (T value: aHash.keySet())
            if(bHash.containsKey(value)
                    && aHash.get(value).equals(bHash.get(value)))
                result++;


        return result;
	}

    private static <T> void calculateAppearances(Collection<T> a, HashMap<T, Integer> aHash) {
        for (T nextValue : a) {
            if (aHash.containsKey(nextValue))
                aHash.put(nextValue, 1 + aHash.get(nextValue));
            else
                aHash.put(nextValue, 1);
        }
    }

}
