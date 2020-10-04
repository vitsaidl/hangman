package cz.saidl.hangman;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;


public class TestHangman{
    Hangman hangman;
    
    @BeforeEach
    void setUp(){
        hangman = new Hangman();        
    }
    
    @Test
    void testMaskedLettersArrayLargerThanOne(){
        char[] wordArray = hangman.getMaskedWordArray();
        int wordArrayLength = wordArray.length;
        assertTrue(wordArrayLength>1, "Array hangman.getMaskedWordArray has length <=1");  
    }

    @Test
    void testMaskedArrayContainsOnlyUnderscoreAtTheBeginning(){
        char[] actualWordArray = hangman.getMaskedWordArray(); 
        char[] expectedWordArray = {'_', '_', '_', '_', '_'};
        assertArrayEquals(expectedWordArray, actualWordArray);        
    }
    
    @Test
    void testForLetterOnceInWordPositiveMessageReturned(){
        char chosenLetter = 's';
        MessageTuple message = hangman.checkAndFillLetter(chosenLetter);
        assertTrue(message.getFlag());
        assertTrue(message.getMessage().equals("Searched word contains letter s"));
    }
    
    @Test
    void testForLetterOnceInWordOnePlaceInArrayChanged(){
        char chosenLetter = 's';
        hangman.checkAndFillLetter(chosenLetter);
        char[] actualWordArray = hangman.getMaskedWordArray(); 
        char[] expectedWordArray = {'s', '_', '_', '_', '_'};
        assertArrayEquals(expectedWordArray, actualWordArray);   
    }
    
    @Test
    void testForLetterTwiceInWordPositiveMessageReturned(){
        char chosenLetter = 'o';
        MessageTuple message = hangman.checkAndFillLetter(chosenLetter);
        assertTrue(message.getFlag());
        assertTrue(message.getMessage().equals("Searched word contains letter o"));
    }

    @Test
    void testForLetterTwiceInWordTwoPlacesInArrayChanged(){
        char chosenLetter = 'o';
        hangman.checkAndFillLetter(chosenLetter);
        char[] actualWordArray = hangman.getMaskedWordArray(); 
        char[] expectedWordArray = {'_', '_', 'o', '_', 'o'};
        assertArrayEquals(expectedWordArray, actualWordArray);   
    }
    
    @Test
    void testForLetterNotInWordNegativeMessageReturned(){
        char chosenLetter = 'x';
        MessageTuple message = hangman.checkAndFillLetter(chosenLetter);
        assertFalse(message.getFlag());
        assertTrue(message.getMessage().equals("Searched word doesn't contain letter x"));
    }
    
    @Test
    void testForLetterNotInWordNoPlaceInArrayChanged(){
        char chosenLetter = 'x';
        hangman.checkAndFillLetter(chosenLetter);
        char[] actualWordArray = hangman.getMaskedWordArray(); 
        char[] expectedWordArray = {'_', '_', '_', '_', '_'};
        assertArrayEquals(expectedWordArray, actualWordArray);   
    }
    
    @Test
    void testAfterMissingLetterNumberOfTriesIncremented(){
        int originalNumberOfTries = hangman.getNumberOfTries();
        char chosenLetter = 'x';
        hangman.checkAndFillLetter(chosenLetter); 
        int updatedNumberOfTries = hangman.getNumberOfTries();
        int difference = updatedNumberOfTries - originalNumberOfTries;
        assertEquals(1, difference);        
    }
    
    @Test
    void testAfterCorrectGuessLetterNumberOfTriesIncremented(){
        int originalNumberOfTries = hangman.getNumberOfTries();
        char chosenLetter = 'o';
        hangman.checkAndFillLetter(chosenLetter); 
        int updatedNumberOfTries = hangman.getNumberOfTries();
        int difference = updatedNumberOfTries - originalNumberOfTries;
        assertEquals(1, difference);        
    }
    
    @Test
    void testForIncompleteGuessingWholeWordFoundReturnsFalse(){
        hangman.checkAndFillLetter('s');
        assertFalse(hangman.wholeWordFound());        
    }
    
    @Test
    void testForCompleteGuessingWholeWordFoundReturnsTrue(){
        hangman.checkAndFillLetter('s');
        hangman.checkAndFillLetter('l');
        hangman.checkAndFillLetter('o');
        hangman.checkAndFillLetter('v');
        assertTrue(hangman.wholeWordFound());        
    }

    
}