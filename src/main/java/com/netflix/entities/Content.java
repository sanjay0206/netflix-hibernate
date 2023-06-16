package com.netflix.entities;

import com.netflix.dto.ContentDto;
import com.netflix.enums.ContentType;
import com.netflix.enums.Genre;

import javax.persistence.*;

@Entity
@Table(name = "content")
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "release_year", nullable = false)
    private Integer releaseYear;

    @Enumerated(EnumType.STRING)
    @Column(name = "content_type", nullable = false)
    private ContentType contentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "genre", nullable = false)
    private Genre genre;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "duration", nullable = false)
    private Integer duration;

    @Column(name = "rating", nullable = false)
    private Integer rating;

    @Column(name = "cast", nullable = false)
    private String cast;

    @Column(name = "director", nullable = false)
    private String director;

    @Column(name = "studio", nullable = false)
    private String studio;

    @Column(name = "trailer_url", nullable = false)
    private String trailerUrl;

    public Content() {
    }

    public Content(String title, Integer releaseYear, ContentType contentType, Genre genre, String description, Integer duration, Integer rating, String cast, String director, String studio, String trailerUrl) {
        this.title = title;
        this.releaseYear = releaseYear;
        this.contentType = contentType;
        this.genre = genre;
        this.description = description;
        this.duration = duration;
        this.rating = rating;
        this.cast = cast;
        this.director = director;
        this.studio = studio;
        this.trailerUrl = trailerUrl;
    }

    public Content(ContentDto dto) {
        this.title = dto.getTitle();
        this.releaseYear = dto.getReleaseYear();
        this.contentType = dto.getContentType();
        this.genre = dto.getGenre();
        this.description = dto.getDescription();
        this.duration = dto.getDuration();
        this.rating = dto.getRating();
        this.cast = dto.getCast();
        this.director = dto.getDirector();
        this.studio = dto.getStudio();
        this.trailerUrl = dto.getTrailerUrl();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
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

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
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
        return "Content{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", releaseYear=" + releaseYear +
                ", contentType=" + contentType +
                ", genre=" + genre +
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
