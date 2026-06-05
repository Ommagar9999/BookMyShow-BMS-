package com.omizon.BMS.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieRequest {



    private String title;

    private String description;

    private String genre;

    private String language;

    private Double rating;

    private Integer durationMinutes;

    private String posterUrl;

    private LocalDate releaseDate;

    }


