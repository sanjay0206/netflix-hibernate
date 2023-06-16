package com.netflix.dao;

import com.netflix.dto.LoginDto;
import com.netflix.entities.User;
import com.netflix.security.ApplicationUserRole;
import com.netflix.utils.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.NativeQuery;

import java.util.List;

public class LoginDao {
    public boolean validateCredentials(LoginDto loginDto) {
        //validate the username and password against the database or any other method
        Transaction transaction = null;
        Session session = null;
        boolean isValidCred = false;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            NativeQuery<User> query = session.createNativeQuery(sql, User.class);
            query.setParameter(1, loginDto.getUsername());
            query.setParameter(2, loginDto.getPassword());
            List<User> result = query.list();
            transaction.commit();
            System.out.println(result);
            if (!result.isEmpty()) {
                isValidCred = true;
            }
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
        } finally {
            if (session != null)
                session.close();
        }
        return isValidCred;
    }

    public ApplicationUserRole getUserRole(LoginDto loginDto) {
        //validate the username and password against the database or any other method
        Transaction transaction = null;
        Session session = null;
        ApplicationUserRole userRole = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Criteria criteria = session.createCriteria(User.class);
            criteria.add(Restrictions.eq("username", loginDto.getUsername()));
            User subscriber = (User) criteria.uniqueResult();
            transaction.commit();
            userRole = subscriber.getApplicationUserRole();
            System.out.println(subscriber);
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
        } finally {
            if (session != null)
                session.close();
        }
        return userRole;
    }
}
