package cz.saidl.hangman;

import java.util.Random;

/*********************************************************************************
* Class Hangman contains game logic. 
*/
public class Hangman{
    private String searchedWord;
    private int numberOfTries;
    private char[] wordInLetters;
    private char[] maskedLetters;
    private String[] potentialWords = {"nazdar", "ahoj", "konec"};
    private Random rand = new Random();
    
    /******************************************************************************
    * Game initialization. Array maskedLetters contains only _ at the beginning,
    * but in time it is filled by correctly guessed letters.
    */
    Hangman(){
        String searchedWord = potentialWords[rand.nextInt(potentialWords.length)];
        int searchedWordLength = searchedWord.length();
        this.searchedWord = searchedWord;
        this.numberOfTries = 0;
        this.wordInLetters = new char[searchedWordLength];
        this.maskedLetters = new char[searchedWordLength];
        
        for (int letterIndex=0; letterIndex<searchedWordLength; letterIndex++){
            wordInLetters[letterIndex] = this.searchedWord.charAt(letterIndex);
            maskedLetters[letterIndex] = '_';
        }
    }
    
    /***********************************************************************************
    * Checks if guessed letter is in searched word and if yes, corresponding dummy _ char
    * in array maskedLetters is replaced by the guessed char.
    * 
    * @param letter Letter guessed by player.
    * @returns Message whose string part is used by CLI/GUI.
    */
    public MessageTuple checkAndFillLetter(char letter){
        boolean letterPresent = false;
        this.numberOfTries++;
        
        for (int letterIndex=0;letterIndex<this.wordInLetters.length;letterIndex++){
            if (this.wordInLetters[letterIndex]==letter){
                letterPresent = true;
                this.maskedLetters[letterIndex]=letter;
            }
        }
        if (letterPresent==true){
            return new MessageTuple(letterPresent, "Searched word contains letter " + letter);
        }
        return new MessageTuple(letterPresent, "Searched word doesn't contain letter " + letter);
    }
    
    /********************************************************************************
    * Tests if all letters in searched word has been found.
    *
    * @returns Returns true if arrays wordInLetters and maskedLetters contains same chars.
    */
    public boolean wholeWordFound(){
        for (int letterIndex=0;letterIndex<this.wordInLetters.length;letterIndex++){
            if (this.wordInLetters[letterIndex] != this.maskedLetters[letterIndex]){
                return false;
            }
        }  
        return true;        
    }
    
    /********************************************************************************
    * Gets number of players guesses.
    *
    * @returns Number of guesses.
    */
    public int getNumberOfTries(){
        return this.numberOfTries;
    }
    
    /*********************************************************************************
    * Gets maskedLetters array (array with correctly guessed letters).
    * @returns Correctly guessed letters.
    */
    public char[] getMaskedWordArray(){
        return this.maskedLetters;
    }
}