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
    void testMaskedLettersArrayLengthEqualsWordLength(){
        char[] wordArray = hangman.getMaskedWordArray();
        int wordArrayLength = wordArray.length;
        assertEquals(5, wordArrayLength);  
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
        assertEquals("Searched word contains letter s", message.getMessage());
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
        assertEquals("Searched word contains letter o", message.getMessage());
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
        assertEquals("Searched word doesn't contain letter x", message.getMessage());
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
    void testAfterMissingLetterNumberOfMissesIncremented(){
        int originalNumberOfMisses = hangman.getNumberOfMisses();
        char chosenLetter = 'x';
        hangman.checkAndFillLetter(chosenLetter); 
        int updatedNumberOfMisses = hangman.getNumberOfMisses();
        int difference = updatedNumberOfMisses - originalNumberOfMisses;
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
    void testAfterCorrectGuessLetterNumberOfMissesNotIncremented(){
        int originalNumberOfMisses = hangman.getNumberOfMisses();
        char chosenLetter = 'o';
        hangman.checkAndFillLetter(chosenLetter); 
        int updatedNumberOfMisses = hangman.getNumberOfMisses();
        int difference = updatedNumberOfMisses - originalNumberOfMisses;
        assertEquals(0, difference);        
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

    @Test
    void testForNumberOfMissesUnderThresholdGameNotLost(){
        hangman.checkAndFillLetter('x');
        assertFalse(hangman.isGameLost());        
    }
    
    @Test
    void testForNumberOfMissesOverThresholdGameLost(){
        hangman.checkAndFillLetter('x');
        hangman.checkAndFillLetter('x');
        hangman.checkAndFillLetter('x');
        hangman.checkAndFillLetter('x');
        hangman.checkAndFillLetter('x');
        hangman.checkAndFillLetter('x');
        assertTrue(hangman.isGameLost());        
    }
    
}