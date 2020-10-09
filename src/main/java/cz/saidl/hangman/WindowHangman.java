package cz.saidl.hangman;

import javax.swing.*;
import java.awt.*;


/*****************************************************************************************
* Class responsible for game GUI.
*/
public class WindowHangman{
    String[] LETTERS_TO_CHOOSE = {
            "a","b","c","d","e","f","g","h",
            "i","j","k","l","m","n","o","p",
            "q","r","s","t","u","v","w","x","y","z"
    };
    Hangman hangman;
    JFrame jfrm;
    JPanel wordPanel;
    GallowsPanel gallowsPanel;
    JLabel actualHintLabel;
    
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
        
        JComboBox<String> letterChoiceBox = new JComboBox<>(LETTERS_TO_CHOOSE);
        gridcon.gridx=0;
        gridcon.gridy=0;
        jfrm_container.add(letterChoiceBox, gridcon);
        
        JButton tryLetterButton = new JButton("Try chosen letter");
        gridcon.gridx=0;
        gridcon.gridy=1;
        jfrm_container.add(tryLetterButton, gridcon);
        
        JLabel wordHintLabel = new JLabel("Hint:");
        gridcon.gridx=0;
        gridcon.gridy=2;
        jfrm_container.add(wordHintLabel, gridcon);   

        this.actualHintLabel = new JLabel(this.hangman.getWordHint());
        gridcon.gridx=0;
        gridcon.gridy=3;
        jfrm_container.add(this.actualHintLabel, gridcon);         
        
        this.wordPanel = new JPanel();
        this.wordPanel.setLayout(new FlowLayout());
        gridcon.gridx=0;
        gridcon.gridy=4;
        jfrm_container.add(this.wordPanel, gridcon);
        
        maskedArrayToInterface();
        
        JTextArea messageArea = new JTextArea("Try to find the hidden word.");
        messageArea.setEditable(false);
        gridcon.gridx=0;
        gridcon.gridy=5;  
        jfrm_container.add(messageArea, gridcon);    

        JTextArea numberTriesArea = new JTextArea("Game has not started yet.");
        numberTriesArea.setEditable(false);
        gridcon.gridx=0;
        gridcon.gridy=6;  
        jfrm_container.add(numberTriesArea, gridcon);           

        tryLetterButton.addActionListener(event ->{
            char chosenLetter = letterChoiceBox.getSelectedItem().toString().charAt(0);
            MessageTuple message = hangman.checkAndFillLetter(chosenLetter);
            maskedArrayToInterface();
            messageArea.setText(message.getMessage());
            numberTriesArea.setText("Number of tries: " + hangman.getNumberOfTries());
            if (this.hangman.wholeWordFound()){
                JOptionPane.showMessageDialog(
                    jfrm_container, 
                    "The word has been found in " + hangman.getNumberOfTries() + " tries.",
                    "Player won",
                    JOptionPane.INFORMATION_MESSAGE
                );
                tryLetterButton.setEnabled(false);
            }
            else if (this.hangman.isGameLost()){
                JOptionPane.showMessageDialog(
                    jfrm_container, 
                    "The player wasn't able to guess word \"" + hangman.getSearchedWord() +"\" in " + hangman.getNumberOfTries() + " tries.",
                    "Player lost",
                    JOptionPane.INFORMATION_MESSAGE
                );
                tryLetterButton.setEnabled(false);             
            }
        });
        
        JButton resetGameButton = new JButton("Reset game");
        gridcon.gridx=0;
        gridcon.gridy=7;
        jfrm_container.add(resetGameButton, gridcon);
        resetGameButton.addActionListener(event ->{
            resetGame();
            tryLetterButton.setEnabled(true);
            messageArea.setText("Try to find the hidden word.");
            numberTriesArea.setText("Game has not started yet.");
        });
        
        gallowsPanel = new GallowsPanel();
        gridcon.gridx=0;
        gridcon.gridy=8;
        jfrm_container.add(gallowsPanel, gridcon);        
        
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
        this.actualHintLabel.setText(this.hangman.getWordHint());
        maskedArrayToInterface();
    }

    /*********************************************************************************
    * Redraw parts of interface - letters and gallows area.
    */
    private void maskedArrayToInterface(){
        char[] maskedArray = this.hangman.getMaskedWordArray();
        this.wordPanel.removeAll();
        for (char maskedLetter: maskedArray){
            JTextField wordField = new JTextField(String.valueOf(maskedLetter));
            wordField.setEditable(false);
            this.wordPanel.add(wordField);
        }
        this.wordPanel.revalidate();
        this.wordPanel.repaint();  
        
        try{
            this.gallowsPanel.setGallowsValue(this.hangman.getNumberOfMisses());
            this.gallowsPanel.revalidate();
            this.gallowsPanel.repaint(); 
        }catch(NullPointerException exception) {}
    }
    
    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                new WindowHangman();
            }
        });
    }
}