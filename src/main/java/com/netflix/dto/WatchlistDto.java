package com.netflix.dto;

import com.netflix.enums.Favorite;

public class WatchlistDto {
    private Favorite isFavorite;

    public Favorite getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(Favorite isFavorite) {
        this.isFavorite = isFavorite;
    }

    @Override
    public String toString() {
        return "WatchlistDto{" +
                "isFavorite=" + isFavorite +
                '}';
    }
}
