package com.omizon.BMS.Service;

import com.omizon.BMS.dto.MovieRequest;
import com.omizon.BMS.entity.Movie;
import com.omizon.BMS.repository.MovieRepository;
import com.omizon.BMS.repository.ShowRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final ShowRepository showRepository;
    private final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/posters/";

    public Movie addMovie(MovieRequest request, MultipartFile poster) {

        try {
            File dir = new File(UPLOAD_DIR);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String fileName = UUID.randomUUID() + "_" + poster.getOriginalFilename();

            File file = new File(UPLOAD_DIR + fileName);
            poster.transferTo(file);

            String posterUrl = "/uploads/posters/" + fileName;

            Movie movie = Movie.builder()
                    .title(request.getTitle())
                    .description(request.getDescription())
                    .genre(request.getGenre())
                    .language(request.getLanguage())
                    .rating(request.getRating())
                    .durationMinutes(request.getDurationMinutes())
                    .posterUrl(posterUrl)
                    .releaseDate(request.getReleaseDate())
                    .build();

            return movieRepository.save(movie);

        } catch (Exception e) {
            throw new RuntimeException("Error uploading poster: " + e.getMessage());
        }
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie getMovieById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found with id: " + id));
    }

    public List<Movie> searchByTitle(String title) {
        return movieRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Movie> getByLanguage(String language) {
        return movieRepository.findByLanguage(language);
    }

    public List<Movie> getByGenre(String genre) {
        return movieRepository.findByGenre(genre);
    }

    public Movie updateMovie(Movie upmovie) {
        Movie movie = movieRepository.findById(upmovie.getId())
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        movie.setTitle(upmovie.getTitle());
        movie.setDescription(upmovie.getDescription());
        movie.setGenre(upmovie.getGenre());
        movie.setRating(upmovie.getRating());
        movie.setLanguage(upmovie.getLanguage());
        movie.setPosterUrl(upmovie.getPosterUrl());
        movie.setReleaseDate(upmovie.getReleaseDate());
        movie.setDurationMinutes(upmovie.getDurationMinutes());

        return movieRepository.save(movie);
    }




    @Transactional
    public void deleteMovie(Long id) {

        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        showRepository.deleteByMovieId(id);
        movieRepository.delete(movie);
    }
}