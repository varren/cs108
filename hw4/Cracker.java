import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class Cracker {
	// Array of chars used to produce strings
	public static final char[] CHARS = "abcdefghijklmnopqrstuvwxyz0123456789.,-!".toCharArray();	


    /*
     Given a byte[] array, produces a hex String,
     such as "234a6f". with 2 chars for each byte in the array.
     (provided code)
    */
	public static String hexToString(byte[] bytes) {
		StringBuffer buff = new StringBuffer();
		for (int i=0; i<bytes.length; i++) {
			int val = bytes[i];
			val = val & 0xff;  // remove higher bits, sign
			if (val<16) buff.append('0'); // leading 0
			buff.append(Integer.toString(val, 16));
		}
		return buff.toString();
	}
	
	/*
	 Given a string of hex byte values such as "24a26f", creates
	 a byte[] array of those values, one byte value -128..127
	 for each 2 chars.
	 (provided code)
	*/
	public static byte[] hexToArray(String hex) {
		byte[] result = new byte[hex.length()/2];
		for (int i=0; i<hex.length(); i+=2) {
			result[i/2] = (byte) Integer.parseInt(hex.substring(i, i+2), 16);
		}
		return result;
	}
	
	// possible test values:
	// a 86f7e437faa5a7fce15d1ddcb9eaeaea377667b8
	// fm adeb6f2a18fe33af368d91b09587b68e3abcb9a7
	// a! 34800e15707fae815d7c90d49de44aca97e2d759
	// xyz 66b27417d37e024c46526c2f6d358a754fc552f3

    private static final String TEST_WORD = "molly";
    private static final String TEST_HASH = "4181eecbd7a755d19fdf73887c54837cbecf63fd";
    private static final int TEST_THREADS_NUM = 8;

    private static final int NO_ARGUMENTS = 0;
    private static final int GENERATE_HASH_MODE = 1;
    private static final int CRACK_HASH_MODE = 3;

    public static void main(String[] args){
        Cracker cracker = new Cracker();

        if(args.length == NO_ARGUMENTS){
          System.out.println(
                  "No args given...Default test args are: \n" +
                  "word: " + TEST_WORD + " : " + cracker.generateHash(TEST_WORD) + "\n" +
                  "hash: " + TEST_HASH+ " : " + cracker.crackHash(TEST_HASH, TEST_WORD.length(), TEST_THREADS_NUM)+ "\n" +
                  "threads: " + TEST_THREADS_NUM);
        }
        else if(args.length == GENERATE_HASH_MODE)
            System.out.println(cracker.generateHash(args[0]));
        else if(args.length == CRACK_HASH_MODE)
            System.out.println(cracker.crackHash(args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2])));
        else{
            System.out.println(
                    "Wrong input format. Should be " +
                    "\n To make Hash: 1) word" +
                    "\n To hack Hash: 1) hash 2) word length 3) num of treads ");
        }
    }
    private static ArrayList<Thread> threadList = new ArrayList<Thread>();
    private static String hashToFind;
    private static String resultWord;

    private String crackHash(String hash, int wordLength, int threadsNum) {
        // still don't know, why i need CountDownLatch. Was recommended in HW4 Threads handout
        CountDownLatch foundWord = new CountDownLatch(1);
        hashToFind = hash;

        for(int i = 0 ;i < threadsNum; i++){
            int step = CHARS.length/threadsNum;
            threadList.add(new Worker(i * step, (i + 1) * step, wordLength, foundWord));
        }

        for(Thread t: threadList) t.start();

        try {
            foundWord.await();
            for(Thread t: threadList) t.interrupt();
            for(Thread t: threadList) t.join();
        } catch (InterruptedException ignored) {}

        return resultWord;
    }

    private class Worker extends Thread {
        private int from;
        private int to;
        private int wordLength;
        private CountDownLatch foundWord;

        public Worker(int from, int to, int wordLength,  CountDownLatch foundWord){
            this.to = to;
            this.from = from;
            this.wordLength = wordLength;
            this.foundWord = foundWord;
        }

        @Override
        public void run() {
            for(int i = from; i < to; i++)
                searchHash(wordLength, "" +CHARS[i]);
        }

        private void searchHash(int maxWordLength, String word) {
            if(word.length() > maxWordLength) return;

            if (generateHash(word).equals(hashToFind)) {
                resultWord = word;
                foundWord.countDown();
            }

            for (char CHAR : CHARS) {
                if (isInterrupted()) break;
                searchHash(maxWordLength, word + CHAR);

            }
        }
    }


    private String generateHash(String word) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA");
            md.update(word.getBytes());
            return hexToString(md.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }


}
