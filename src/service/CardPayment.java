package service;

public class CardPayment {
   static double value_installments = 0;
    public static double installments(double value,int installments){
        value_installments = value / installments;
        String.format("%.2f%n", value_installments);
        return value_installments;
    }
}
