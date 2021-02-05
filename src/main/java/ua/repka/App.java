package ua.repka;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ua.repka.model.Book;
import ua.repka.model.User;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class App {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(App.class.getClassLoader().getResource("hibernate.properties").getFile()));
            configuration.setProperties(properties);

        } catch (IOException | NullPointerException e) {
            throw new IllegalStateException();
        }
        configuration.addResource("User.hbm.xml");
        configuration.addAnnotatedClass(Book.class);
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        // language=HQL
        User user = session.createQuery("from User user where user.id = 1", User.class).getSingleResult();
        session.beginTransaction();
        session.save(new User("Polia", "Repka"));
        session.getTransaction().commit();
        System.out.println(user);
        //language=HQL
        List<Book> books = session.createQuery("from Book book", Book.class).getResultList();
        books.forEach(System.out::println);
    }
}
