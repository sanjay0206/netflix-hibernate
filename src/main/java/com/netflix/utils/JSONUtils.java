package com.netflix.utils;

import com.netflix.entities.Content;
import com.netflix.entities.User;
import com.netflix.enums.*;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONUtils {
    public static User parseSubscriber(String payload) {
        User subscriber = null;
        try {
            JSONObject jsonObject = new JSONObject(payload);
            String name = jsonObject.isNull("username") ? "" : jsonObject.get("username").toString();
            String email = jsonObject.isNull("email") ? "" : jsonObject.get("email").toString();
            String password = jsonObject.isNull("password") ? "" : jsonObject.get("password").toString();

            SubscriptionPlan subscriptionPlan = jsonObject.isNull("subscription_plan") ? null :
                    jsonObject.getEnum(SubscriptionPlan.class, "subscription_plan");
            AccountStatus accountStatus = jsonObject.isNull("account_status") ? null :
                    jsonObject.getEnum(AccountStatus.class, "account_status");
            Region region = jsonObject.isNull("region") ? null :
                    jsonObject.getEnum(Region.class, "region");
            subscriber = new User(name, email, password, subscriptionPlan, accountStatus, region);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return subscriber;
    }

    public static Content parseContent(String payload) {
        Content content = null;
        try {
            JSONObject jsonObject = new JSONObject(payload);
            String title = jsonObject.isNull("title") ? "" : jsonObject.get("title").toString();
            int releaseYear = jsonObject.isNull("release_year") ? 0 : jsonObject.getInt("release_year");
            ContentType contentType = jsonObject.isNull("content_type") ? null : jsonObject.getEnum(ContentType.class, "content_type");
            Genre genre = jsonObject.isNull("genre") ? null : jsonObject.getEnum(Genre.class, "genre");
            String description = jsonObject.isNull("description") ? "" : jsonObject.get("description").toString();
            int duration = jsonObject.isNull("duration") ? 0 : jsonObject.getInt("duration");
            int rating = jsonObject.isNull("rating") ? 0 : jsonObject.getInt("rating");
            String cast = jsonObject.isNull("cast") ? "" : jsonObject.get("cast").toString();
            String director = jsonObject.isNull("director") ? "" : jsonObject.get("director").toString();
            String studio = jsonObject.isNull("studio") ? "" : jsonObject.get("studio").toString();
            String trailerUrl = jsonObject.isNull("trailer_url") ? "" : jsonObject.get("trailer_url").toString();
            content = new Content(title, releaseYear, contentType, genre, description,
                    duration, rating, cast, director, studio, trailerUrl);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return content;
    }

}
