package com.netflix.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.dto.ResponseDto;
import com.netflix.enums.Status;
import com.netflix.security.JWTUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebFilter(filterName = "JWTAuthFilter")
public class JWTAuthFilter implements Filter {

    private JWTUtils jwtUtils;
    private ObjectMapper MAPPER;
    ResponseDto responseDto = new ResponseDto();

    @Override
    public void init(FilterConfig filterConfig) {
        jwtUtils = new JWTUtils();
        MAPPER = new ObjectMapper();
        responseDto = new ResponseDto();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        System.out.println("In JWT filter..");

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        PrintWriter out = httpServletResponse.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String authHeader = httpServletRequest.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            if (!jwtUtils.isValidToken(token)) {
                responseDto.setStatus(Status.FAILURE);
                responseDto.setMessage("Invalid JWT");
                httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
                out.println(MAPPER.writeValueAsString(responseDto));
                return;
            }
        } else {
            responseDto.setStatus(Status.FAILURE);
            responseDto.setMessage("Missing or invalid Authorization header");
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            out.println(MAPPER.writeValueAsString(responseDto));
            return;
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}