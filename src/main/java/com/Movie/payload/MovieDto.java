package com.Movie.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieDto {
    private Long id;

    private String name;
    private double rating;
    private int releaseDate ;
    private int duration;
    private String description;

}
