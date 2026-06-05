package com.omizon.BMS.Service;

import com.omizon.BMS.dto.ShowRequest;
import com.omizon.BMS.entity.Movie;
import com.omizon.BMS.entity.Screen;
import com.omizon.BMS.entity.Show;
import com.omizon.BMS.repository.ShowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShowService {


    private final ShowRepository showRepository;
    private final ScreenService screenService;
    private final MovieService movieService;


    public Show addShow(ShowRequest request) {
        Movie movie = movieService.getMovieById(request.getMovieId());
        Screen screen = screenService.getScreenById(request.getScreenId());

        Show show = Show.builder()
                .movie(movie)
                .screen(screen)
                .showDate(request.getShowDate())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .ticketPrice(request.getTicketPrice())
                .build();


        return showRepository.save(show);
    }

    public List<Show> getAllShow() {

        return showRepository.findAll();

    }

    public Show getShowById(Long id) {

        return showRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found with id :" + id));
    }

    public List<Show> getShowByMovie(Long movieId) {
        return showRepository.findByMovieId(movieId);
    }


    public List<Show> getShowByMovieAndDate(Long movieId, LocalDate date) {
        return showRepository.findByMovieIdAndShowDate(movieId, date);
    }

    public List<Show> getShowByScreen(Long screenId) {
        return showRepository.findByScreenId(screenId);
    }


}