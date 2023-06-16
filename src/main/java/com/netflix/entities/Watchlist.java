package com.netflix.entities;


import com.netflix.dto.WatchlistDto;
import com.netflix.enums.Favorite;

import javax.persistence.*;

@Entity
@Table(name = "watchlist")
public class Watchlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
    In the Watchlist entity class, the Subscriber field is marked with the @ManyToOne annotation because
    a single subscriber can have multiple entries in the watchlist table, but each entry in the
    watchlist table is associated with only one subscriber
    */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /*
    In the Watchlist entity class, the Content field is marked with the @ManyToOne annotation because
    a single piece of content can be on multiple subscribers' watchlists, but each entry in the
    watchlist table is associated with only one piece of content
    */
    @ManyToOne
    @JoinColumn(name = "content_id")
    private Content content;

    @Column(name = "is_favorite", nullable = false)
    @Enumerated(EnumType.STRING)
    private Favorite isFavorite;

    public Watchlist() {
    }

    public Watchlist(User user, Content content, Favorite isFavorite) {
        this.user = user;
        this.content = content;
        this.isFavorite = isFavorite;
    }

    public Watchlist(WatchlistDto watchlistDto) {
       this.isFavorite = watchlistDto.getIsFavorite();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public Favorite getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(Favorite favorite) {
        this.isFavorite = favorite;
    }

    @Override
    public String toString() {
        return "Watchlist{" +
                "id=" + id +
                ", user=" + user +
                ", content=" + content +
                ", isFavorite=" + isFavorite +
                '}';
    }
}
