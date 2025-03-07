package org.example.repositories.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "WorkersData")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkerLoginModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer ID;
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;
    @Column(name = "autoName")
    private String autoName;
    @Column(name = "age")
    private String age;
    @Column(name = "mode")
    private String mode;

    public WorkerLoginModel(String login, String password, String autoName, String age, String mode) {
        this.login = login;
        this.password = password;
        this.autoName = autoName;
        this.age = age;
        this.mode = mode;
    }
}
