package cz.saidl.hangman;

import java.util.Random;

public class Hangman{
    private String searchedWord;
    private int numberOfTries;
    private char[] wordInLetters;
    private char[] maskedLetters;
    private String[] potentialWords = {"nazdar", "ahoj", "konec"};
    private Random rand = new Random();
    
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
    
    public boolean wholeWordFound(){
        for (int letterIndex=0;letterIndex<this.wordInLetters.length;letterIndex++){
            if (this.wordInLetters[letterIndex] != this.maskedLetters[letterIndex]){
                return false;
            }
        }  
        return true;        
    }
    
    public int getNumberOfTries(){
        return this.numberOfTries;
    }
    
    public char[] getMaskedWordArray(){
        return this.maskedLetters;
    }
}