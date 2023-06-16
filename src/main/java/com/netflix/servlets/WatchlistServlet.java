package com.netflix.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.dao.WatchlistDao;
import com.netflix.dto.ResponseDto;
import com.netflix.dto.WatchlistDto;
import com.netflix.entities.Watchlist;
import com.netflix.enums.Favorite;
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

@WebServlet("/watchlist")
public class WatchlistServlet extends HttpServlet {
    private static final WatchlistDao WATCHLIST_DAO = new WatchlistDao();
    static ObjectMapper MAPPER = new ObjectMapper();
    static String message = "";
    static int code = 0;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        ResponseDto responseDto;
        try {
            long subscriberId = Long.parseLong(request.getParameter("subscriber_id"));
            List<Watchlist> watchlists = WATCHLIST_DAO.getAllWatchlistOfSubscriber(subscriberId);
            if (watchlists.isEmpty()) {
                message = "Oops! No watchlists are present in our database";
                responseDto = new ResponseDto(Status.FAILURE, message);
                out.println(MAPPER.writeValueAsString(responseDto));
            } else {
                out.println(MAPPER.writeValueAsString(watchlists));
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

            WatchlistDto watchlistDto = MAPPER.readValue(payload, WatchlistDto.class);
            System.out.println(watchlistDto);

            Favorite favorite = watchlistDto.getIsFavorite();
            long subscriberId = Long.parseLong(request.getParameter("subscriber_id"));
            long contentId = Long.parseLong(request.getParameter("content_id"));

            responseDto = WATCHLIST_DAO.addContentToWatchlist(subscriberId, contentId, favorite);
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

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        ResponseDto responseDto;
        try {
            long subscriberId = Long.parseLong(request.getParameter("subscriber_id"));
            responseDto = WATCHLIST_DAO.deleteWatchlist(subscriberId);
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
