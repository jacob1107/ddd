package com.jacob.ddd.domain;

import com.jacob.ddd.domain.user.UserProfile;

public class DomainFactory {

    public static UserProfile getUserProfile(){
        return new UserProfile();
    }

}
