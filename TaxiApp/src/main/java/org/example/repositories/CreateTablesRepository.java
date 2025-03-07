package org.example.repositories;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.PreparedStatement;

@Component
public class CreateTablesRepository {
    Connection connection = QQQ.connection;

    @SneakyThrows
    @PostConstruct
    public void createTables(){
        PreparedStatement statement = connection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS UsersData(ID Integer PRIMARY KEY AUTOINCREMENT , login TEXT, password TEXT)");
        PreparedStatement preparedStatement = connection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS WorkersData(ID Integer PRIMARY KEY AUTOINCREMENT, login TEXT, password TEXT, autoName TEXT, age TEXT, mode TEXT)");
        PreparedStatement preparedStatement1 = connection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS Requests" +
                        "(ID INTEGER PRIMARY KEY AUTOINCREMENT, loginUser TEXT, dotStreet TEXT, toStreet, loginWorker TEXT, status TEXT, price TEXT, mode TEXT)");
        preparedStatement1.executeUpdate();
        preparedStatement.executeUpdate();
        statement.executeUpdate();
    }
}
