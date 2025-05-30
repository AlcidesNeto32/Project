package service;

public interface CardPayment {
   default double payment_card(int quota, double value){
       return  1.10 * value / quota;
   }
}
