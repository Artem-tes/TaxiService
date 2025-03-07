package org.example.repositories;

import lombok.Getter;

import java.sql.DriverManager;
import java.sql.SQLException;

@Getter
public class QQQ {
    public static final java.sql.Connection connection;

    static {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:Taxi.db");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
