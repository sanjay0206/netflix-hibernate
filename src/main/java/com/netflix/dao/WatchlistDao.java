package com.netflix.dao;

import com.netflix.dto.ResponseDto;
import com.netflix.entities.Content;
import com.netflix.entities.User;
import com.netflix.entities.Watchlist;
import com.netflix.enums.Favorite;
import com.netflix.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class WatchlistDao {
    public ResponseDto addContentToWatchlist(long subscriberId, long contentId, Favorite favorite) {
        Transaction transaction = null;
        Session session = null;
        ResponseDto responseDto = new ResponseDto();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            User subscriber = session.get(User.class, subscriberId);
            Content content = session.get(Content.class, contentId);
            Watchlist watchlist = new Watchlist(subscriber, content, favorite);
            session.save(watchlist);
            transaction.commit();
            responseDto.setMessage("New watchlist has been added");
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

    public List<Watchlist> getAllWatchlistOfSubscriber(long id) {
        List<Watchlist> watchlists = new ArrayList<>();
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            watchlists = session.createNativeQuery("SELECT * FROM watchlist WHERE subscriber_id = :subscriber_id", Watchlist.class)
                    .setParameter("subscriber_id", id)
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
        return watchlists;
    }

    public ResponseDto deleteWatchlist(long id) {
        Transaction transaction = null;
        Session session = null;
        ResponseDto responseDto = new ResponseDto();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Watchlist watchlist = session.get(Watchlist.class, id);
            session.delete(watchlist);
            transaction.commit();
            responseDto.setMessage("watchlist with id " + id + " is deleted");
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
