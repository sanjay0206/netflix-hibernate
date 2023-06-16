package com.netflix.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.dao.ContentDao;
import com.netflix.dto.ContentDto;
import com.netflix.dto.ResponseDto;
import com.netflix.entities.Content;
import com.netflix.enums.Status;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/content")
public class ContentServlet extends HttpServlet {
    private static final ContentDao CONTENT_DAO = new ContentDao();
    static ObjectMapper MAPPER = new ObjectMapper();
    static String message = "";
    static int code = 0;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        ResponseDto responseDto;
        try {
            List<Content> contents = CONTENT_DAO.getAllContents();
            if (contents.isEmpty()) {
                message = "Oops! No contents are present in our database";
                responseDto = new ResponseDto(Status.FAILURE, message);
                out.println(MAPPER.writeValueAsString(responseDto));
            } else {
                out.println(MAPPER.writeValueAsString(contents));
            }
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            message = e.getMessage();
            responseDto = new ResponseDto(Status.FAILURE, message);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println(MAPPER.writeValueAsString(responseDto));
        }
    }

//    public static Content mapDtoTOEntity(ContentDto dto) {
//        this.title = dto.getTitle();
//        this.releaseYear = dto.getReleaseYear();
//        this.contentType = dto.getContentType();
//        this.genre = dto.getGenre();
//        this.description = dto.getDescription();
//        this.duration = dto.getDuration();
//        this.rating = dto.getRating();
//        this.cast = dto.getCast();
//        this.director = dto.getDirector();
//        this.studio = dto.getStudio();
//        this.trailerUrl = dto.getTrailerUrl();
//    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        ResponseDto responseDto;
        try {
            String payload = new BufferedReader(new InputStreamReader(request.getInputStream()))
                    .lines().collect(Collectors.joining(System.lineSeparator()));
            System.out.println(payload);

            ContentDto contentDto = MAPPER.readValue(payload, ContentDto.class);
            System.out.println(contentDto);

            Content content = new Content(contentDto);
            responseDto = CONTENT_DAO.addContent(content);
            code = responseDto.getCode();
            message = responseDto.getMessage();
            if (code == HttpServletResponse.SC_CREATED) {
                responseDto = new ResponseDto(Status.SUCCESS, message);
                response.setStatus(code);
            } else {
                responseDto = new ResponseDto(Status.FAILURE, message);
                response.setStatus(code);
            }
            out.println(MAPPER.writeValueAsString(responseDto));
        } catch (Exception e) {
            message = e.getMessage();
            responseDto = new ResponseDto(Status.FAILURE, message);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println(MAPPER.writeValueAsString(responseDto));
        }
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        ResponseDto responseDto;
        try {
            String payload = new BufferedReader(new InputStreamReader(request.getInputStream()))
                    .lines().collect(Collectors.joining(System.lineSeparator()));
            System.out.println(payload);

            long contentId = Long.parseLong(request.getParameter("content_id"));
            ContentDto contentDto = MAPPER.readValue(payload, ContentDto.class);
            System.out.println(contentDto);

            Content updatedContent = new Content(contentDto);
            responseDto = CONTENT_DAO.updateContent(contentId, updatedContent);
            code = responseDto.getCode();
            message = responseDto.getMessage();
            if (code == HttpServletResponse.SC_OK) {
                responseDto = new ResponseDto(Status.SUCCESS, message);
                response.setStatus(code);
            } else {
                responseDto = new ResponseDto(Status.FAILURE, message);
                response.setStatus(code);
            }
            out.println(MAPPER.writeValueAsString(responseDto));
        } catch (Exception e) {
            message = e.getMessage();
            responseDto = new ResponseDto(Status.FAILURE, message);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println(MAPPER.writeValueAsString(responseDto));
        }
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        new ResponseDto();
        ResponseDto responseDto;
        try {
            long subscriberId = Long.parseLong(request.getParameter("content_id"));
            responseDto = CONTENT_DAO.deleteContent(subscriberId);
            code = responseDto.getCode();
            message = responseDto.getMessage();
            if (code == HttpServletResponse.SC_OK) {
                responseDto = new ResponseDto(Status.SUCCESS, message);
                response.setStatus(code);
            } else {
                responseDto = new ResponseDto(Status.FAILURE, message);
                response.setStatus(code);
            }
            out.println(MAPPER.writeValueAsString(responseDto));
        } catch (Exception e) {
            message = e.getMessage();
            responseDto = new ResponseDto(Status.FAILURE, message);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println(MAPPER.writeValueAsString(responseDto));
        }
    }
}
