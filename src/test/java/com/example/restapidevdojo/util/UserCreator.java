package com.example.restapidevdojo.util;

import com.example.restapidevdojo.domain.Anime;
import com.example.restapidevdojo.domain.User;

public class UserCreator {
    public static User createUserToBeSaved(){
        return User.builder()
                .name("Dev Dojo")
                .username("devdojo")
                .password("{bcrypt}$2a$10$utl5HwoI1Qzcqe6K.O/1C.LWQ2s1bPh2kFd8JielVTD.WqWC/13nG")
                .authorities("ROLE_USER")
                .build();
    }
    public static User createAdminToBeSaved(){
        return User.builder()
                .name("Fagner")
                .username("fagner")
                .password("{bcrypt}$2a$10$utl5HwoI1Qzcqe6K.O/1C.LWQ2s1bPh2kFd8JielVTD.WqWC/13nG")
                .authorities("ROLE_ADMIN")
                .build();
    }
}
