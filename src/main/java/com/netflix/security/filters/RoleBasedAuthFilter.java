package com.netflix.security.filters;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.dto.ResponseDto;
import com.netflix.enums.Status;
import com.netflix.security.ApplicationUserRole;
import com.netflix.security.HttpMethods;
import com.netflix.security.JWTUtils;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebFilter(filterName = "RoleBasedAuthFilter")
public class RoleBasedAuthFilter implements Filter {

    private Map<String, HttpMethods[]> subscriberEndpoints;
    private Map<String, HttpMethods[]> adminEndpoints;
    private List<String> excludedEndpoints;
    private ObjectMapper MAPPER;
    private JWTUtils jwtUtils;
    ResponseDto responseDto = new ResponseDto();

    @Override
    public void init(FilterConfig filterConfig) {
        MAPPER = new ObjectMapper();
        jwtUtils = new JWTUtils();
        responseDto = new ResponseDto();

        excludedEndpoints = Arrays.asList("/login", "/register");
        subscriberEndpoints = new HashMap<>();
        subscriberEndpoints.put("/content", new HttpMethods[]{HttpMethods.GET});
        subscriberEndpoints.put("/subscriber", new HttpMethods[]{HttpMethods.GET});
        subscriberEndpoints.put("/watchlist", new HttpMethods[]
                {HttpMethods.GET, HttpMethods.POST, HttpMethods.PUT, HttpMethods.DELETE});


        adminEndpoints = new HashMap<>();
        adminEndpoints.put("/content", new HttpMethods[]
                {HttpMethods.GET, HttpMethods.POST, HttpMethods.PUT, HttpMethods.DELETE});
        adminEndpoints.put("/subscriber", new HttpMethods[]
                {HttpMethods.GET, HttpMethods.POST, HttpMethods.PUT, HttpMethods.DELETE});
        adminEndpoints.put("/watchlist", new HttpMethods[]{
                HttpMethods.GET, HttpMethods.POST, HttpMethods.PUT, HttpMethods.DELETE});
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("In Role based authentication filter..");

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        PrintWriter out = httpServletResponse.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String endpoint = httpServletRequest.getServletPath();
        String method = httpServletRequest.getMethod();

        if (excludedEndpoints.contains(endpoint)) {
            chain.doFilter(request, response);
            return;
        }

        String authHeader = httpServletRequest.getHeader("Authorization");
        String token = authHeader.substring(7);
        JSONObject claims = jwtUtils.parsClaims(token);
        String userRole = claims.getString("aud");
        Map<String, HttpMethods[]> allowedEndpoints = userRole.equals(ApplicationUserRole.SUBSCRIBER.toString()) ? subscriberEndpoints : adminEndpoints;
        if (!allowedEndpoints.containsKey(endpoint) || !isMethodAllowed(allowedEndpoints.get(endpoint), HttpMethods.valueOf(method))) {
            responseDto.setStatus(Status.FAILURE);
            responseDto.setMessage("You do not have access to this endpoint");
            httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            out.println(MAPPER.writeValueAsString(responseDto));
            return;
        }
        chain.doFilter(request, response);
    }

    private boolean isMethodAllowed(HttpMethods[] allowedMethods, HttpMethods method) {
        boolean isMethodAllowed = false;
        for (HttpMethods allowedMethod : allowedMethods) {
            if (allowedMethod.equals(method)) {
                isMethodAllowed = true;
                break;
            }
        }
        return isMethodAllowed;
    }

    @Override
    public void destroy() {
        // cleanup code (if any)
    }
}


