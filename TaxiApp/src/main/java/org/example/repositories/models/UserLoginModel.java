package org.example.repositories.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "UsersData")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer ID;
    @Column
    private String login;
    @Column
    private String password;

    public UserLoginModel(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
