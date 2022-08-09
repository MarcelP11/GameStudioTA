package sk.tacademy.gamestudio.service;

import sk.tacademy.gamestudio.entity.Rating;

public interface RatingService {
    void setRating(Rating rating);
    int getAverageRating(String name);
    int getRating(String name, String username);
    void reset();
}
