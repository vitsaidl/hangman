package cz.saidl.hangman;

/********************************************************************************
* Class for moving messages between class with game logic (Hangman) and classes 
* responsible for user interface.
*/
public class MessageTuple{
    private boolean flag;
    private String message;
    
    /*****************************************************************************
    * Class constructor - always with flag and message.
    * @param flag True or false depends on the message context.
    * @param message Message content depends on the message context.
    */
    MessageTuple(boolean flag, String message){
        this.flag = flag;
        this.message = message;
    }
    
    /*****************************************************************************
    * Gets message flag.
    * @return Message flag.
    */
    public boolean getFlag(){
        return this.flag;
    }
    
    /*****************************************************************************
    * Gets actual message content.
    * @return Message string content.
    */   
    public String getMessage(){
        return this.message;
    }
}