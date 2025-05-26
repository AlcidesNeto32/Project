package exception;

public class ProductNotExistException extends RuntimeException{
    public ProductNotExistException(){
        super("[ERROR]: product does not exist!");
    }
}
