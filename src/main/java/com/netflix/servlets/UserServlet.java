package com.netflix.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.dao.UserDao;
import com.netflix.dto.ResponseDto;
import com.netflix.dto.UserDto;
import com.netflix.entities.User;
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

@WebServlet("/subscriber")
public class UserServlet extends HttpServlet {
    private static final UserDao USER_DAO = new UserDao();
    static ObjectMapper MAPPER = new ObjectMapper();
    static String message = "";
    static int code = 0;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        ResponseDto responseDto;
        try {
            List<User> subscribers = USER_DAO.getAllUsers();
            if (subscribers.isEmpty()) {
                message = "Oops! No subscribers are present in our database";
                responseDto = new ResponseDto(Status.FAILURE, message);
                out.println(MAPPER.writeValueAsString(responseDto));
            } else {
                out.println(MAPPER.writeValueAsString(subscribers));
            }
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            message = e.getMessage();
            responseDto = new ResponseDto(Status.FAILURE, message);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println(MAPPER.writeValueAsString(responseDto));
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        ResponseDto responseDto;
        try {
            String payload = new BufferedReader(new InputStreamReader(request.getInputStream()))
                    .lines().collect(Collectors.joining(System.lineSeparator()));
            System.out.println(payload);

            UserDto userDto = MAPPER.readValue(payload, UserDto.class);
            User user = new User(userDto);
            responseDto = USER_DAO.addNewUser(user);
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

            long userId = Long.parseLong(request.getParameter("userId"));
            UserDto userDto = MAPPER.readValue(payload, UserDto.class);
            User updatedUser = new User(userDto);
            responseDto = USER_DAO.updateUser(userId, updatedUser);
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
        ResponseDto responseDto;
        try {
            long userId = Long.parseLong(request.getParameter("user_id"));
            responseDto = USER_DAO.deleteUser(userId);
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
