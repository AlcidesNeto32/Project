package service;

public interface CardPayment {
   default double payment_card(int parcel, double value){
       return  1.10 * value / parcel;
   }
}
