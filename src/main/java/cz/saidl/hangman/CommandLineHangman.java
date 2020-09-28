package cz.saidl.hangman;

import java.util.Scanner;

public class CommandLineHangman{

    private static void oneWordGuess(){
        Hangman hangman = new Hangman();
        boolean wholeWordFound = false;
        
        while (!wholeWordFound){
            System.out.println("Current word state: " + String.valueOf(hangman.getMaskedWordArray()));
            Scanner scanner = new Scanner(System.in);
            String userInput = scanner.nextLine();
            if (userInput.length()==0){
                System.out.println("User wants to end");
                System.exit(0);
            }
            else if (userInput.length()==1){
                char letter = userInput.charAt(0);
                MessageTuple message = hangman.checkAndFillLetter(letter);
                System.out.println(message.getMessage());
                System.out.println("Number of tries: " + hangman.getNumberOfTries());wholeWordFound = hangman.wholeWordFound();                
            }
            else{
                System.out.println("Input not valid. Enter either letter or nothing (if you want to leave).");
            }
        }
        System.out.println("Word found");        
    }
    
    public static void main(String[] args){
        boolean userWantsPlayAgain;
        do{
            userWantsPlayAgain = false;
            oneWordGuess();
            System.out.println("Do you want to play again (y/n)?");
            Scanner scanner = new Scanner(System.in);
            String userInput = scanner.nextLine().trim();
            if (userInput.equals("y") || userInput.equals("Y") || userInput.equals("yes") || userInput.equals("Yes")){
                userWantsPlayAgain = true;
            }
        }while(userWantsPlayAgain);
    }
}