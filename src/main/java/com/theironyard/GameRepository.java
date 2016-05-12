package com.theironyard;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by johnjastrow on 5/4/16.
 */
public interface GameRepository extends CrudRepository<Game, Integer> {
    List<Game> findByGenre(String genre);

    List<Game> findByReleaseYear(int year);

    List<Game> findByGenreAndReleaseYear(String genre, int releaseYear);

    List<Game> findByReleaseYearIsGreaterThanEqual(int minReleaseYear);

    Game findFirstByGenre(String genre);

    int countByGenre(String genre);

    List<Game> findByGenreOrderByNameAsc(String genre);

    // doesn't seem to work ToDo: (even after adding Spring facet
    @Query("SELECT g FROM Game g WHERE g.name LIKE ?1%")
    List<Game> findByNameStartingWith(String name);

    List<Game> findByUser(User user);

    @Override
    List<Game> findAll();
}

