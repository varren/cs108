import java.util.HashSet;


// CS108 HW1 -- String static methods

public class StringCode {

	/**
	 * Given a string, returns the length of the largest run.
	 * A a run is a series of adajcent chars that are the same.
	 * @param str
	 * @return max run length
	 */
	public static int maxRun(String str) {
	    int maxRun =0;
        int currRun;

		for(int i=0;i < str.length(); i+=currRun){
            currRun = calculateNumInRow(str,i);
            if(currRun > maxRun) maxRun = currRun;
        }

        return maxRun;
    }

	private static int calculateNumInRow(String str, int pos){
        if (pos >= str.length()-1 || str.charAt(pos) != str.charAt(pos+1)) return 1;
        else return 1 + calculateNumInRow(str, pos + 1);

    }
	/**
	 * Given a string, for each digit in the original string,
	 * replaces the digit with that many occurrences of the character
	 * following. So the string "a3tx2z" yields "attttxzzz".
	 * @param str
	 * @return blown up string
	 */
	public static String blowup(String str) {
        String result ="";

        for(int i =0; i <str.length();i++){
               if(Character.isDigit(str.charAt(i)))
                   result+= generateNChars(str, i);
                else result+= str.charAt(i);
        }
        return result;
	}

	 private static String generateNChars(String str,int pos){
         String result = "";
         if ( pos < str.length() - 1){
             char nextChar = str.charAt(pos + 1);

             int numToReplace = Character.getNumericValue(str.charAt(pos));
             for(int i =0;i<numToReplace;i++)
                 result+= nextChar;
         }
         return result;
     }
	/**
	 * Given 2 strings, consider all the substrings within them
	 * of length len. Returns true if there are any such substrings
	 * which appear in both strings.
	 * Compute this in linear time using a HashSet. Len will be 1 or more.
	 */
	public static boolean stringIntersect(String a, String b, int len) {
        HashSet<String> firstStrSubs = new HashSet<String>();

        for (int i =0;i <= a.length() - len;i++){
            String substring = a.substring(i,i + len);
            firstStrSubs.add(substring);
        }

        for (int i =0;i <= b.length() - len;i++){
            String substring = b.substring(i,i + len);
            if(firstStrSubs.contains(substring))return true;
        }

        return false;
	}
}
