package com.vitter.rest.util;

import java.util.HashMap;
import java.util.Map;

public class TextUtils {
    public int countLetters(String input) {
        return input.replace(" ", "").length();
    }

    public int countWords(String input) {
        int counter = 0;
        boolean isSpace = true;
        for (int i=0 ; i<input.length() ; i++) {
            if (input.charAt(i) == ' ') {
                isSpace = true;
            } else if(input.charAt(i) != ' ' && isSpace) {
                counter++;
                isSpace = false;
            }
        }
        return counter;
    }
    public String mostRepeatedWord(String input) {
        String [] words = input.split(" ");
        HashMap <String, Integer> wordsCounter = new HashMap<String, Integer>();

        for (int i=0 ; i<words.length ; i++) {
            String refinedWord = words[i];
            if (refinedWord.endsWith(".")) {
                // remove last character if the word ends with a dot
                refinedWord = refinedWord.substring(0, refinedWord.length() - 2);
            }
            if (wordsCounter.containsKey(refinedWord)) {
                int currentValue = wordsCounter.get(refinedWord);
                wordsCounter.put(refinedWord, currentValue+1);
            } else {
                wordsCounter.put(refinedWord, 1);
            }
        }

        // find word with highest number (most repeated)
        String mostRepeated = "";
        int numberOfRepeats = 0;

        for (Map.Entry<String, Integer> entry : wordsCounter.entrySet()) {
            if (entry.getValue() > numberOfRepeats ) {
                mostRepeated = entry.getKey();
                numberOfRepeats = entry.getValue();
            }
        }
        return mostRepeated;
    }
}
