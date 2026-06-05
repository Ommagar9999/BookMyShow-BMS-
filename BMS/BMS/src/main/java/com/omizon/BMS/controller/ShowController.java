package com.omizon.BMS.controller;


import com.omizon.BMS.Service.ShowService;
import com.omizon.BMS.dto.ShowRequest;
import com.omizon.BMS.entity.Show;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/shows")
@RequiredArgsConstructor
public class ShowController {


    private  final ShowService showService;


@PostMapping("addShow")
public ResponseEntity<Show> AddShow(@RequestBody ShowRequest request)
{
    return  ResponseEntity.ok(showService.addShow(request));

}


@GetMapping
public ResponseEntity<List<Show>>  getAllShow()
{

    return ResponseEntity.ok(showService.getAllShow());


}

    @GetMapping("/{id}")
    public ResponseEntity<Show>  getShowById(@PathVariable Long id)
    {

        return ResponseEntity.ok(showService.getShowById(id));


    }



    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<Show>>  getShowByMovie(@PathVariable Long movieId)
    {

        return ResponseEntity.ok(showService.getShowByMovie(movieId));


    }

      @GetMapping("/movie/{movieId}/date")
    public  ResponseEntity<List<Show>> getShowByMovieAndDate(@PathVariable Long movieId , @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date)
      {
          return ResponseEntity.ok(showService.getShowByMovieAndDate(movieId,date));

      }








}
