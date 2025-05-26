package exception;

public class QuantityException extends RuntimeException{
    QuantityException(){
        super("[ERROR]: quantity surpass");
    }
}
