package com.netflix.dao;

import com.netflix.dto.ResponseDto;
import com.netflix.entities.User;
import com.netflix.security.ApplicationUserRole;
import com.netflix.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


public class UserDao {
    public ResponseDto addNewUser(User user) {
        Transaction transaction = null;
        Session session = null;
        ResponseDto responseDto = new ResponseDto();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            if (user.getApplicationUserRole() == null) {
                user.setApplicationUserRole(ApplicationUserRole.SUBSCRIBER);
            }
            session.save(user);
            transaction.commit();
            responseDto.setMessage("User successfully added");
            responseDto.setCode(HttpServletResponse.SC_CREATED);
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
            responseDto.setMessage(e.getMessage());
            responseDto.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } finally {
            if (session != null)
                session.close();
        }
        return responseDto;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            users = session.createNativeQuery("SELECT * FROM users", User.class)
                    .getResultList();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
        } finally {
            if (session != null)
                session.close();
        }
        return users;
    }

    @Transactional
    public ResponseDto updateUser(long id, User updatedUser) {
        Transaction transaction = null;
        Session session = null;
        ResponseDto responseDto = new ResponseDto();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            User oldSubscriber = session.get(User.class, id);
            if (updatedUser.getUsername() != null && !updatedUser.getUsername().isEmpty()) {
                oldSubscriber.setUsername(updatedUser.getUsername());
            }
            if (updatedUser.getEmail() != null && !updatedUser.getEmail().isEmpty()) {
                oldSubscriber.setEmail(updatedUser.getEmail());
            }
            if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                oldSubscriber.setPassword(updatedUser.getPassword());
            }
            if (updatedUser.getSubscriptionPlan() != null && !updatedUser.getSubscriptionPlan().toString().isEmpty()) {
                oldSubscriber.setSubscriptionPlan(updatedUser.getSubscriptionPlan());
            }
            if (updatedUser.getAccountStatus() != null && !updatedUser.getAccountStatus().toString().isEmpty()) {
                oldSubscriber.setAccountStatus(updatedUser.getAccountStatus());
            }
            if (updatedUser.getRegion() != null && !updatedUser.getRegion().toString().isEmpty()) {
                oldSubscriber.setRegion(updatedUser.getRegion());
            }
            session.update(oldSubscriber);
            transaction.commit();
            responseDto.setMessage("Subscriber with id " + id + " is updated");
            responseDto.setCode(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
            responseDto.setMessage(e.getMessage());
            responseDto.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } finally {
            if (session != null)
                session.close();
        }
        return responseDto;
    }

    public ResponseDto deleteUser(long id) {
        Transaction transaction = null;
        Session session = null;
        ResponseDto responseDto = new ResponseDto();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            User subscriber = session.get(User.class, id);
            session.delete(subscriber);
            transaction.commit();
            responseDto.setMessage("subscriber with id " + id + " is deleted");
            responseDto.setCode(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
            responseDto.setMessage(e.getMessage());
            responseDto.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } finally {
            if (session != null)
                session.close();
        }
        return responseDto;
    }
}
