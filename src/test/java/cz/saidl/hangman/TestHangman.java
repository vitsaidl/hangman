package cz.saidl.hangman;

import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

//udelat setup

public class TestHangman{
    @Test
    void testMaskedLettersArrayLargerThanOne(){
        Hangman hangman = new Hangman();
        char[] wordArray = hangman.getMaskedWordArray();
        int wordArrayLength = wordArray.length;
        assertTrue(wordArrayLength>1, "Array hangman.getMaskedWordArray has length <=1");  
    }
    
    void testMaskedArrayContainsOnlyUnderscoreAtTheBeginning(){
        Hangman hangman = new Hangman();
        Character[] wordArray = hangman.getMaskedWordArray(); 
        Set<Character> lettersInMaskedArray = new HashSet<Character>(Arrays.asList(wordArray)); 
        assertTrue(lettersInMaskedArray.contains('_'), "blabla");        
    }

    
}