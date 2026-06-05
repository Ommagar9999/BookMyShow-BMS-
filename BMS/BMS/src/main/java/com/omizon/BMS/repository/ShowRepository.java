package com.omizon.BMS.repository;

import com.omizon.BMS.entity.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ShowRepository extends JpaRepository<Show, Long> {

    List<Show> findByMovieId(Long movieId);

    List<Show> findByScreenId(Long screenId);

    List<Show> findByMovieIdAndShowDate(Long movieId, LocalDate showDate);

    List<Show> findByScreenIdAndShowDate(Long screenId, LocalDate showDate);


    @Modifying
    @Query("DELETE FROM Show s WHERE s.movie.id = :movieId")
    void deleteByMovieId(@Param("movieId") Long movieId);
}