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
    private int numberOfMisses;
    final private int MISSES_TO_LOOSE_GAME = 5;
    
    private String searchedWord = "";
    private String wordHint = "";
    private char[] wordInLetters;
    private char[] maskedLetters;
    private Random rand = new Random();
 
    
    /******************************************************************************
    * Game initialization. Array maskedLetters contains only _ at the beginning,
    * but in time it is filled by correctly guessed letters.
    */
    Hangman(){
        try {
            this.provideRandomWord();
        }catch(IOException | NullPointerException exception) {
            System.out.println("Problem - file with words for guessing hasn't been loaded. Game will be shutted down.");
            exception.printStackTrace();
            System.exit(1);
        }

        int searchedWordLength = this.searchedWord.length();

        this.numberOfTries = 0;
        this.numberOfMisses = 0;
        this.wordInLetters = new char[searchedWordLength];
        this.maskedLetters = new char[searchedWordLength];
        
        for (int letterIndex=0; letterIndex<searchedWordLength; letterIndex++){
            wordInLetters[letterIndex] = this.searchedWord.charAt(letterIndex);
            maskedLetters[letterIndex] = '_';
        }
    }
    
    /***********************************************************************************
    * Chooses and sets one random word from file with list of words 'searchedWord'
    * instance variable. Hints are read in the same fashion.
    */
    private void provideRandomWord() throws IOException, NullPointerException {
        List<String> potentialWords = new ArrayList<>();
        List<String> potentialHints = new ArrayList<>();
        InputStream  wordsInputStream = getClass().getResourceAsStream ("/words_list.txt");
        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(wordsInputStream))  ){
            String row;
            while ((row = bufferedReader.readLine()) != null) {
                String[] processedRow = row.split("&"); 
                potentialWords.add(processedRow[0]);
                potentialHints.add(processedRow[1]);
            }
        }
        int wordIndex = rand.nextInt(potentialWords.size());
        this.searchedWord = potentialWords.get(wordIndex);
        this.wordHint = potentialHints.get(wordIndex);
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
        numberOfMisses++;
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
    * Tests if game is lost due to reaching the misses threshold.
    *
    * @return Returns true number of player misses is higher than threshold value.
    */    
    public boolean isGameLost(){
        return this.numberOfMisses >= MISSES_TO_LOOSE_GAME;
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
    * Gets number of players incorrect guesses.
    *
    * @return Number of guesses.
    */
    public int getNumberOfMisses(){
        return this.numberOfMisses;
    }
    
    /********************************************************************************
    * Gets searched word in String (not array) form.
    *
    * @return Searched word.
    */
    public String getSearchedWord(){
        return this.searchedWord;
    }
    
    /********************************************************************************
    * Gets hint for searched word.
    *
    * @return Hint for earched word.
    */
    public String getWordHint(){
        return this.wordHint;
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