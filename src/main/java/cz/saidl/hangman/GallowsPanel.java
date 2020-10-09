package cz.saidl.hangman;

import javax.swing.JPanel;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

/*************************************************************************
* Class responsible for incremental painting the gallows.
*/
class GallowsPanel extends JPanel {
    private int gallowsValue = 0;

    /*********************************************************************
    * Initialization. It is almost empty - gallows is being created with the game progress.
    */
    public GallowsPanel() {
        setBorder(BorderFactory.createLineBorder(Color.black));
    }

    /******************************************************************************************
    * Sets the preferred dimensions. Called by java, not by user.
    */
    public Dimension getPreferredSize() {
        return new Dimension(250,200);
    }

    /******************************************************************************************
    * Paints gallows. Number of its painted pars depends on number of players bad guesses.
    *
    * @param graphics Drawing onject.
    */
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);   
        graphics.setColor(Color.BLACK);         
        switch(this.gallowsValue){
            case 5:
                graphics.drawOval(148, 65, 25, 25);
            case 4:
                graphics.fillRect(160, 40, 2, 25);
            case 3:
                graphics.fillRect(70,20,100,20);    
            case 2:
                graphics.fillRect(50,20,20,160); 
            case 1:
                graphics.fillRect(25,180,200,20);           
        }
    }

    /****************************************************************************************
    * Sets gallows value.
    *
    * @param numberOfMisses Number of players incorrect guesses.
    */
    public void setGallowsValue(int numberOfMisses){
        this.gallowsValue = numberOfMisses;
    }    
}
