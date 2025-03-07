package org.example.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.users.billing.Card;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    private int status;
    private String login;
    private String password;
    private Card card;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
