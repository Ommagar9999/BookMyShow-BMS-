package com.omizon.BMS.repository;

import com.omizon.BMS.entity.Movie;
import com.omizon.BMS.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie,Long>
{


    List<Movie> findByGenre(String genre);
    List<Movie> findByLanguage(String language);
    List<Movie> findByTitleContainingIgnoreCase(String title);


}
