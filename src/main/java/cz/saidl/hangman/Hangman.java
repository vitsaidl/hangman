package cz.saidl.hangman;

import java.util.Random;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*********************************************************************************
* Class Hangman contains game logic. 
*/
public class Hangman{
    private int numberOfTries;
    private char[] wordInLetters;
    private char[] maskedLetters;
    private Random rand = new Random();
    
    /******************************************************************************
    * Game initialization. Array maskedLetters contains only _ at the beginning,
    * but in time it is filled by correctly guessed letters.
    */
    Hangman(){
        String searchedWord = "";
        try {
            searchedWord = this.provideRandomWord();
        }catch(IOException exception) {
            exception.printStackTrace();
        }
        int searchedWordLength = searchedWord.length();

        this.numberOfTries = 0;
        this.wordInLetters = new char[searchedWordLength];
        this.maskedLetters = new char[searchedWordLength];
        
        for (int letterIndex=0; letterIndex<searchedWordLength; letterIndex++){
            wordInLetters[letterIndex] = searchedWord.charAt(letterIndex);
            maskedLetters[letterIndex] = '_';
        }
    }
    
    /***********************************************************************************
    * Chooses and returns one random word from file with list of words.
    * 
    * @return Word which will be guessed.
    */
    private String provideRandomWord() throws IOException {
        List<String> potentialWords = new ArrayList<>();
        InputStream  wordsInputStream = getClass().getResourceAsStream ("/words_list.txt");
        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(wordsInputStream))  ){
            String row;
            while ((row = bufferedReader.readLine()) != null) {
                potentialWords.add(row);
            }
        }
        //catch (IOException exc) {
        //    System.out.println("I/O error: " + exc);
        //}
        return potentialWords.get(rand.nextInt(potentialWords.size()));
    }
    
    /***********************************************************************************
    * Checks if guessed letter is in searched word and if yes, corresponding dummy _ char
    * in array maskedLetters is replaced by the guessed char.
    * 
    * @param letter Letter guessed by player.
    * @return Message whose string part is used by CLI/GUI.
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
        if (letterPresent){
            return new MessageTuple(true, "Searched word contains letter " + letter);
        }
        return new MessageTuple(false, "Searched word doesn't contain letter " + letter);
    }
    
    /********************************************************************************
    * Tests if all letters in searched word has been found.
    *
    * @return Returns true if arrays wordInLetters and maskedLetters contains same chars.
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
    * @return Number of guesses.
    */
    public int getNumberOfTries(){
        return this.numberOfTries;
    }
    
    /********************************************************************************
    * Gets maskedLetters aka array with currently correctly guessed letters.
    *
    * @return Array with currently correctly guessed letters.
    */
    public char[] getMaskedWordArray(){
        return this.maskedLetters;
    }
}