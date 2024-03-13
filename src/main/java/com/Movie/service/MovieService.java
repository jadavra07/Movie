package com.Movie.service;

import com.Movie.entity.Movie;
import com.Movie.exception.ResourceNotFound;
import com.Movie.payload.MovieDto;
import com.Movie.repository.MovieRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ModelMapper modelMapper;

    public MovieDto AddMovieRecord(MovieDto movieDto) {
        Movie movie = MapToEntity(movieDto);
        Movie saveMovie = movieRepository.save(movie);
        return MapTODto(saveMovie);
    }


    public List<Movie> GetAllMovie(int pageNo, int pageSize, String sortBy) {
        Pageable pageRequest = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Movie> all = movieRepository.findAll(pageRequest);
        List<Movie> content = all.getContent();
        return content;
    }

    public void DeleteMovie(long id) {
        movieRepository.findById(id).orElseThrow(
                () -> new ResourceNotFound("Id Is not Fount " + id)
        );
        movieRepository.deleteById(id);
    }

    public MovieDto UpdateMovie(long movieId, MovieDto movieDto) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(
                () -> new ResourceNotFound("Post not Found with id:" + movieId)
        );
        return movieDto;
    }

    Movie MapToEntity(MovieDto movieDto) {
        Movie movie = modelMapper.map(movieDto, Movie.class);
        return movie;
    }

    MovieDto MapTODto(Movie movie) {
        MovieDto dto = modelMapper.map(movie, MovieDto.class);
        return dto;

    }

    public Page<Movie> searchMoviesByNameAndDescription(String name, String description, Pageable pageable) {
        if (name != null && description != null) {
            return movieRepository.findByNameContainingAndDescriptionContaining(name, description, pageable);
        } else if (name != null) {
            return movieRepository.findByNameContaining(name, pageable);
        } else if (description != null) {
            return movieRepository.findByDescriptionContaining(description, pageable);
        } else {
            return movieRepository.findAll(pageable);
        }
    }


}


