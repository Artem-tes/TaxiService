package org.example.users;

import lombok.*;
import org.example.users.billing.Card;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class WorkerUser extends User{
    private String mode;
    private Card card;

    public WorkerUser(String login, String password, String mode) {
        super(login, password);
        this.mode = mode;
    }
}
