package com.Movie.controller;

import com.Movie.entity.Movie;
import com.Movie.payload.MovieDto;
import com.Movie.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    // http://localhost:8080/api/movie/save
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/save")
    public ResponseEntity<MovieDto> saveMovie(@RequestBody MovieDto movieDto) {
        MovieDto dto = movieService.AddMovieRecord(movieDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    // http://localhost:8080/api/movie/getAll
    // http://localhost:8080/api/movie/getAll?sortBy=duration
    @GetMapping("/getAll")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Movie> getAllMovie(
            @RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = "20", required = false) int pageSize,
            @RequestParam(name = "sortBy", defaultValue = "id", required = false) String sortBy
    ) {
        List<Movie> studentDtos = movieService.GetAllMovie(pageNo, pageSize, sortBy);
        return studentDtos;
    }

    // http://localhost:8080/api/movie/3
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteMovie(@PathVariable long id) {
        movieService.DeleteMovie(id);
        return new ResponseEntity<>("Movie is delete", HttpStatus.OK);

    }


    // http://localhost:8080/api/movie?movieID=1
    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MovieDto> updateMovie(
            @RequestParam("movieID") long movieId,
            @RequestBody MovieDto movieDto
    ) {
        MovieDto dto = movieService.UpdateMovie(movieId, movieDto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    // http://localhost:8080/api/movie/search?name=The Godfather
    // http://localhost:8080/api/movie/search?description=good movie
    @GetMapping("/search")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<Movie>> searchMoviesByNameAndDescription(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "description", required = false) String description, Pageable pageable) {
        Page<Movie> movies = movieService.searchMoviesByNameAndDescription(name, description, pageable);
        return ResponseEntity.ok(movies);
    }

}
