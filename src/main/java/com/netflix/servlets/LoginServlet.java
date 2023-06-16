package com.netflix.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.dao.LoginDao;
import com.netflix.dto.LoginDto;
import com.netflix.security.JWTUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    static ObjectMapper MAPPER = new ObjectMapper();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String payload = new BufferedReader(new InputStreamReader(request.getInputStream()))
                .lines()
                .collect(Collectors.joining(System.lineSeparator()));
        System.out.println("Incoming payload: " + payload);
        // validate the username and password
        LoginDto loginDto = MAPPER.readValue(payload, LoginDto.class);
        LoginDao loginDao = new LoginDao();
        if (loginDao.validateCredentials(loginDto)) {
            System.out.println("Valid credentials..");
            //generate the JWT token
            JWTUtils jwtUtils = new JWTUtils();
            String jwt = jwtUtils.createJWT(loginDto);
            System.out.println(jwt);
            //set the JWT token in the response header
            response.setHeader("Authorization", "Bearer " + jwt);
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            //return error if invalid credentials
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }


}
