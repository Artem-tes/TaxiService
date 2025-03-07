package org.example.repositories.models;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Table(name = "Requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RequestModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer ID;
    @Column(name = "loginUser")
    private String loginUser;
    @Column(name = "dotStreet")
    private String dotStreet;
    @Column(name = "toStreet")
    private String toStreet;
    @Column(name = "loginWorker")
    private String loginWorker;
    @Column(name = "status")
    private String status;
    @Column(name = "price")
    private String price;
    @Column(name = "mode")
    private String mode;
}
