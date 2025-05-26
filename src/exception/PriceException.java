package exception;

public class PriceException extends RuntimeException{
    public PriceException(){
        super("[ERROR]: money insufficient!");
    }
}
