package cz.saidl.hangman;

public class MessageTuple{
    private boolean flag;
    private String message;
    
    MessageTuple(boolean flag, String message){
        this.flag = flag;
        this.message = message;
    }
    
    public boolean getFlag(){
        return this.flag;
    }
    
    public String getMessage(){
        return this.message;
    }
}