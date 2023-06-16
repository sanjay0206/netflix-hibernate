package com.netflix.utils;

import com.netflix.entities.Content;
import com.netflix.entities.User;
import com.netflix.entities.Watchlist;
import com.netflix.enums.*;
import com.netflix.security.ApplicationUserRole;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import java.util.ArrayList;

public class NetflixUtil {
    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            // create new users
            ArrayList<User> users = new ArrayList<>();
            users.add(new User("john.smith", "john.smith@example.com", "john123", SubscriptionPlan.STANDARD, AccountStatus.ACTIVE, Region.NORTH_AMERICA, ApplicationUserRole.SUBSCRIBER));
            users.add(new User("jane.doe", "jane.doe@example.com", "jane123", SubscriptionPlan.PREMIUM, AccountStatus.ACTIVE, Region.EUROPE, ApplicationUserRole.SUBSCRIBER));
            users.add(new User("taylor.swift", "taylor.swift@example.com", "taylor123", SubscriptionPlan.BASIC, AccountStatus.SUSPENDED, Region.SOUTH_AMERICA, ApplicationUserRole.ADMIN));

            // create new content
            ArrayList<Content> content = new ArrayList<>();
            content.add(new Content("Action Movie", 2020, ContentType.MOVIE, Genre.ACTION, "A thrilling action movie", 120, 4, "John Doe, Jane Smith", "John Smith", "Acme Studios", "http://example.com/trailer1"));
            content.add(new Content("Comedy TV Show", 2019, ContentType.TV_SHOW, Genre.COMEDY, "A hilarious TV show", 30, 3, "Bob Johnson, Jane Doe", "Jane Doe", "XYZ Studios", "http://example.com/trailer2"));
            content.add(new Content("Drama Movie", 2018, ContentType.MOVIE, Genre.DRAMA, "An emotional drama movie", 90, 5, "John Smith, Jane Doe", "John Doe", "ABC Studios", "http://example.com/trailer3"));

            // create new watchlist entries
            ArrayList<Watchlist> watchlist = new ArrayList<>();
            watchlist.add(new Watchlist(users.get(0), content.get(0), Favorite.YES));
            watchlist.add(new Watchlist(users.get(1), content.get(1), Favorite.NO));
            watchlist.add(new Watchlist(users.get(2), content.get(2), Favorite.YES));


            // save users
            for (User user : users) {
                session.save(user);
            }

            // save content
            for (Content con : content) {
                session.save(con);
            }

            // save watchlist entries
            for (Watchlist entry : watchlist) {
                session.save(entry);
            }

            String hql = "SELECT * FROM users WHERE id = :id";
            NativeQuery<User> query = session.createNativeQuery(hql, User.class);
            query.setParameter("id", 1);
            User subscriber = query.getSingleResult();
            System.out.println("printing---------------------" + subscriber);
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            sessionFactory.close();
        }
    }

}
