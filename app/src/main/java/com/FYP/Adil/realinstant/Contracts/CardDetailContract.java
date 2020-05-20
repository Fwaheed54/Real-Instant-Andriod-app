package com.FYP.Adil.realinstant.Contracts;

public interface CardDetailContract {
    void CardDetailCall(String CardNo,String CVV,String ExpDate);

    void getUserCardStatus(String status);
}

