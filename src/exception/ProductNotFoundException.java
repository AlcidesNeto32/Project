package exception;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(){
        super("[ERROR]: product does not exist!");
    }
}
