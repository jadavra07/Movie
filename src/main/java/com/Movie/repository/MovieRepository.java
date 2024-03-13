package com.Movie.repository;

import com.Movie.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    Page<Movie> searchMoviesByNameAndDescription(String name, String description, Pageable pageable);
    Page<Movie> findByNameContainingAndDescriptionContaining(String name, String description, Pageable pageable);
    Page<Movie> findByNameContaining(String name, Pageable pageable);
    Page<Movie> findByDescriptionContaining(String description, Pageable pageable);
}
