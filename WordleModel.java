import java.util.*;

public class WordleModel {

    public static boolean properName(String s) {
        return s.matches("[A-Z][a-z]+");
    }

    public static boolean integer(String s) {
        return s.matches("[+-]?(0|[1-9][0-9]*)(\\.[0-9]+)?");
    }

    public static boolean ancestor(String s) {
        return s.matches("(great-)*(grand)?(father|mother)");
    }

    public static boolean palindrome(String s) {
        return s.matches("(?i)([a-z])([a-z])([a-z])([a-z])([a-z])\\5\\4\\3\\2\\1");
    }

    public static List<String> wordleMatches(List <List <WordleResponse> > responses) {
        //i had chatGPT generate me 10 random 5 letter words
        List<String> wordList = Arrays.asList("brisk", "latch", "grove", "plume", "snare", "drift", "clasp", "thorn", "glint", "vivid");

        Set<Character> grayLetters = new HashSet<>();
        Set<Character> yellowLetters = new HashSet<>();
        Map<Character, Integer> yellowPositions = new HashMap<>();
        Map<Integer, Character> greenLetters = new HashMap<>();

        for (List <WordleResponse> guess : responses) {
            for (WordleResponse wr : guess) {
                if (wr.resp == LetterResponse.WRONG_LETTER) {
                    grayLetters.add(wr.c);
                } else if (wr.resp == LetterResponse.WRONG_LOCATION){
                    yellowLetters.add(wr.c);
                    yellowPositions.put(wr.c, wr.index);
                } else if (wr.resp == LetterResponse.CORRECT_LOCATION) {
                    greenLetters.put(wr.index, wr.c);
                }
            }
        }
        List<String> results = new ArrayList<>();

        for(String word : wordList) {
            boolean valid = true;

            for (char c : word.toCharArray()) {
                if (grayLetters.contains(c)) {
                    valid = false;
                }
            }

            for (char c : yellowLetters) {
                if (!word.contains(String.valueOf(c))) {
                    valid = false;
                }
                if (yellowPositions.containsKey(c)) {
                    int wrongPosition = yellowPositions.get(c);
                    if (word.charAt(wrongPosition) == c) {
                        valid = false;
                    }
                }
            }

            for (Map.Entry<Integer, Character> entry : greenLetters.entrySet()) {
                int position = entry.getKey();
                char letter = entry.getValue();
                if (word.charAt(position) != letter) {
                    valid = false;
                }
            }

            if (valid) results.add(word);
        }
        return results;
    }
}
