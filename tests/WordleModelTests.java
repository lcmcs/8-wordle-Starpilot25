import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class WordleModelTests {

    @Test
    void testProperName() {
        Assertions.assertTrue(WordleModel.properName("Bob"));
        Assertions.assertTrue(WordleModel.properName("Bob"));
        Assertions.assertFalse(WordleModel.properName("BOb"));
        Assertions.assertFalse(WordleModel.properName("bob"));
        Assertions.assertFalse(WordleModel.properName("B"));
        Assertions.assertFalse(WordleModel.properName("B1b"));
    }

    @Test
    void testInteger() {
        Assertions.assertTrue(WordleModel.integer("43.2"));
        Assertions.assertTrue(WordleModel.integer("0"));
        Assertions.assertTrue(WordleModel.integer("0.05"));
        Assertions.assertTrue(WordleModel.integer("0.5"));
        Assertions.assertTrue(WordleModel.integer("+23"));
        Assertions.assertTrue(WordleModel.integer("-23"));
        Assertions.assertFalse(WordleModel.integer("023"));
        Assertions.assertFalse(WordleModel.integer("+023"));
    }

    @Test
    void testAncestor() {
        Assertions.assertTrue(WordleModel.ancestor("grandfather"));
        Assertions.assertTrue(WordleModel.ancestor("great-great-great-grandmother"));
        Assertions.assertTrue(WordleModel.ancestor("father"));
        Assertions.assertFalse(WordleModel.ancestor("greatgrandfather"));
    }

    @Test
    void testPalindrome() {
        Assertions.assertTrue(WordleModel.palindrome("soniccinos"));
        Assertions.assertFalse(WordleModel.palindrome("sonicocnis"));

        //test case-insensitivity
        Assertions.assertTrue(WordleModel.palindrome("soNiCcInos"));
    }

    @Test
    void testWordleMatches() {
        List<WordleResponse> guess1 = Arrays.asList(
                new WordleResponse('t', 0, LetterResponse.WRONG_LETTER),
                new WordleResponse('r', 1, LetterResponse.WRONG_LETTER),
                new WordleResponse('a', 2, LetterResponse.WRONG_LETTER),
                new WordleResponse('i', 3, LetterResponse.WRONG_LETTER),
                new WordleResponse('n', 4, LetterResponse.WRONG_LETTER)
        );
        List<List<WordleResponse>> responses = List.of(guess1);
        List<String> result = WordleModel.wordleMatches(responses);
        Assertions.assertTrue(result.contains("plume"));
        Assertions.assertFalse(result.contains("clasp"));
        Assertions.assertFalse(result.contains("vivid"));
        Assertions.assertFalse(result.contains("brisk"));
    }

    @Test
    void testWordleMatchedButYellow() {
        List<WordleResponse> guess1 = List.of(
                new WordleResponse('s', 0, LetterResponse.WRONG_LOCATION)
        );
        List<List<WordleResponse>> responses = List.of(guess1);
        List<String> result = WordleModel.wordleMatches(responses);
        Assertions.assertTrue(result.contains("brisk"));
        Assertions.assertTrue(result.contains("clasp"));
        Assertions.assertFalse(result.contains("vivid"));
        Assertions.assertFalse(result.contains("plume"));
    }
}
