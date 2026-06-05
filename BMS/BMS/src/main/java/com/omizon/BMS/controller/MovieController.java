package com.omizon.BMS.controller;

import com.omizon.BMS.Service.MovieService;
import com.omizon.BMS.dto.MovieRequest;
import com.omizon.BMS.entity.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Movie> addMovie(
            @RequestPart("request") MovieRequest request,
            @RequestPart("poster") MultipartFile poster
    ) {
        return ResponseEntity.ok(movieService.addMovie(request, poster));
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Long id) {
        return ResponseEntity.ok(movieService.getMovieById(id));
    }

    // SEARCH
    @GetMapping("/search")
    public ResponseEntity<List<Movie>> searchMovies(@RequestParam String title) {
        return ResponseEntity.ok(movieService.searchByTitle(title));
    }

    // GET BY GENRE
    @GetMapping("/genre")
    public ResponseEntity<List<Movie>> getByGenre(@RequestParam String genre) {
        return ResponseEntity.ok(movieService.getByGenre(genre));
    }

    // GET BY LANGUAGE
    @GetMapping("/language")
    public ResponseEntity<List<Movie>> getByLanguage(@RequestParam String language) {
        return ResponseEntity.ok(movieService.getByLanguage(language));
    }

    // UPDATE
    @PutMapping
    public ResponseEntity<Movie> updateMovie(@RequestBody Movie movie) {
        return ResponseEntity.ok(movieService.updateMovie(movie));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }


}