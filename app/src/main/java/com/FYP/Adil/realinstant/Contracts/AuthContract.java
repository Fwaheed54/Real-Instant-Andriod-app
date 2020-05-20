package com.FYP.Adil.realinstant.Contracts;

import com.FYP.Adil.realinstant.Models.AuthMassage;
import com.FYP.Adil.realinstant.Models.UserLogin;

public interface AuthContract {

    void getMessage(AuthMassage Massege);
    void Login(UserLogin userLogin);
}
