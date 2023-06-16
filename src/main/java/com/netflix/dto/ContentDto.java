package com.netflix.dto;

import com.netflix.enums.ContentType;
import com.netflix.enums.Genre;

public class ContentDto {
    private String title;
    private int releaseYear;
    private ContentType contentType;
    private Genre genre;
    private String description;
    private int duration;
    private int rating;
    private String cast;
    private String director;
    private String studio;
    private String trailerUrl;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getCast() {
        return cast;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getStudio() {
        return studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public String getTrailerUrl() {
        return trailerUrl;
    }

    public void setTrailerUrl(String trailerUrl) {
        this.trailerUrl = trailerUrl;
    }

    @Override
    public String toString() {
        return "ContentDto{" +
                "title='" + title + '\'' +
                ", releaseYear=" + releaseYear +
                ", contentType='" + contentType + '\'' +
                ", genre='" + genre + '\'' +
                ", description='" + description + '\'' +
                ", duration=" + duration +
                ", rating=" + rating +
                ", cast='" + cast + '\'' +
                ", director='" + director + '\'' +
                ", studio='" + studio + '\'' +
                ", trailerUrl='" + trailerUrl + '\'' +
                '}';
    }
}
