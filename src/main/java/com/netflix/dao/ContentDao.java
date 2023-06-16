package com.netflix.dao;

import com.netflix.dto.ResponseDto;
import com.netflix.entities.Content;
import com.netflix.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


public class ContentDao {
    public ResponseDto addContent(Content content) {
        Transaction transaction = null;
        Session session = null;
        ResponseDto responseDto = new ResponseDto();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(content);
            transaction.commit();
            responseDto.setMessage("Content successfully added");
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

    public List<Content> getAllContents() {
        List<Content> contents = new ArrayList<>();
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            contents = session.createNativeQuery("SELECT * FROM content", Content.class)
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
        return contents;
    }

    @Transactional
    public ResponseDto updateContent(long id, Content updatedContent) {
        Transaction transaction = null;
        Session session = null;
        ResponseDto responseDto = new ResponseDto();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Content oldContent = session.get(Content.class, id);
            if (updatedContent.getTitle() != null && !updatedContent.getTitle().isEmpty()) {
                oldContent.setTitle(updatedContent.getTitle());
            }
            if (updatedContent.getReleaseYear() != 0) {
                oldContent.setReleaseYear(updatedContent.getReleaseYear());
            }
            if (updatedContent.getContentType() != null && !updatedContent.getContentType().toString().isEmpty()) {
                oldContent.setContentType(updatedContent.getContentType());
            }
            if (updatedContent.getGenre() != null && !updatedContent.getGenre().toString().isEmpty()) {
                oldContent.setGenre(updatedContent.getGenre());
            }
            if (updatedContent.getDescription() != null && !updatedContent.getDescription().isEmpty()) {
                oldContent.setDescription(updatedContent.getDescription());
            }
            if (updatedContent.getDuration() != 0) {
                oldContent.setDuration(updatedContent.getDuration());
            }
            if (updatedContent.getRating() != 0) {
                oldContent.setRating(updatedContent.getRating());
            }
            if (updatedContent.getCast() != null && !updatedContent.getCast().isEmpty()) {
                oldContent.setCast(updatedContent.getCast());
            }
            if (updatedContent.getDirector() != null && !updatedContent.getDirector().isEmpty()) {
                oldContent.setDirector(updatedContent.getDirector());
            }
            if (updatedContent.getStudio() != null && !updatedContent.getStudio().isEmpty()) {
                oldContent.setStudio(updatedContent.getStudio());
            }
            if (updatedContent.getTrailerUrl() != null && !updatedContent.getTrailerUrl().isEmpty()) {
                oldContent.setTrailerUrl(updatedContent.getTrailerUrl());
            }
            session.update(oldContent);
            transaction.commit();
            responseDto.setMessage("Content with id " + id + " is updated");
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

    public ResponseDto deleteContent(long id) {
        Transaction transaction = null;
        Session session = null;
        ResponseDto responseDto = new ResponseDto();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Content content = session.get(Content.class, id);
            session.delete(content);
            transaction.commit();
            responseDto.setMessage("content with id " + id + " is deleted");
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
