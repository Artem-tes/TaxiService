package org.example.config;
import lombok.Getter;
import org.example.repositories.models.RequestModel;
import org.example.repositories.models.UserLoginModel;
import org.example.repositories.models.WorkerLoginModel;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class HibernateUtil {
    @Getter
    private static final SessionFactory sessionFactory = new Configuration()
            .configure("hibernate.cfg.xml")
            .addAnnotatedClass(UserLoginModel.class)
            .addAnnotatedClass(WorkerLoginModel.class)
            .addAnnotatedClass(RequestModel.class)
            .buildSessionFactory();
}

