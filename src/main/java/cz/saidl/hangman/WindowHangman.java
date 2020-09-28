package cz.saidl.hangman;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/*****************************************************************************************
* Class responsible for game GUI.
*/
public class WindowHangman{
    String[] LETTERS_TO_CHOOSE = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
    Hangman hangman;
    JFrame jfrm;
    JPanel wordPanel;
    
    /************************************************************************************
    * Class initialization creates Swing frame. The window is created only once (even in cases
    * when player restarts game).
    */
    WindowHangman(){
        this.createHangman();
        
        this.jfrm = new JFrame("A hangman app");
        this.jfrm.setSize(640,480);
        this.jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container jfrm_container = this.jfrm.getContentPane();
        jfrm_container.setLayout(new GridBagLayout());
        GridBagConstraints gridcon = new GridBagConstraints();
        
        JComboBox letterChoiceBox = new JComboBox(LETTERS_TO_CHOOSE);
        gridcon.gridx=0;
        gridcon.gridy=0;
        jfrm_container.add(letterChoiceBox, gridcon);
        
        JButton tryLetterButton = new JButton("Try chosen letter");
        gridcon.gridx=0;
        gridcon.gridy=1;
        jfrm_container.add(tryLetterButton, gridcon);
        
        this.wordPanel = new JPanel();
        this.wordPanel.setLayout(new FlowLayout());
        gridcon.gridx=0;
        gridcon.gridy=2;
        jfrm_container.add(this.wordPanel, gridcon);
        
        maskedArrayToInterface();
        
        JTextArea messageArea = new JTextArea("Try to find the hidden word.");
        messageArea.setEditable(false);
        gridcon.gridx=0;
        gridcon.gridy=3;  
        jfrm_container.add(messageArea, gridcon);    

        JTextArea numberTriesArea = new JTextArea("Game has not started yet.");
        numberTriesArea.setEditable(false);
        gridcon.gridx=0;
        gridcon.gridy=4;  
        jfrm_container.add(numberTriesArea, gridcon);           

        tryLetterButton.addActionListener(event ->{
            char chosenLetter = letterChoiceBox.getSelectedItem().toString().charAt(0);
            MessageTuple message = hangman.checkAndFillLetter(chosenLetter);
            maskedArrayToInterface();
            messageArea.setText(message.getMessage());
            numberTriesArea.setText("Number of tries: " + hangman.getNumberOfTries());
        });
        
        JButton resetGameButton = new JButton("Reset game");
        gridcon.gridx=0;
        gridcon.gridy=5;
        jfrm_container.add(resetGameButton, gridcon);
        resetGameButton.addActionListener(event ->{
            resetGame();
            messageArea.setText("Try to find the hidden word.");
            numberTriesArea.setText("Game has not started yet.");
        });
        
        jfrm.setVisible(true);
    }
    
    /********************************************************************************
    * Creates instance of game logic. Called at the beginning of game and when player
    restarts the game.
    */
    private void createHangman(){
        this.hangman = new Hangman();
    }
    
    /********************************************************************************
    * Restarts game by creating indirectly new Hangman instance and by redrawing part
    * of interface with guessed letters.
    */
    private void resetGame(){
        createHangman();
        maskedArrayToInterface();
    }

    /*********************************************************************************
    * Redraw part of interface with currently correctly guessed letters based on the
    * content of Hangman instance.
    */
    private void maskedArrayToInterface(){
        char[] maskedArray = this.hangman.getMaskedWordArray();
        this.wordPanel.removeAll();
        for (int i=0;i<maskedArray.length;i++){
            JTextField wordField = new JTextField(String.valueOf(maskedArray[i]));
            wordField.setEditable(false);
            this.wordPanel.add(wordField);           
        }
        this.wordPanel.revalidate();
        this.wordPanel.repaint();        
    }
    
    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                new WindowHangman();
            }
        });
    }
}