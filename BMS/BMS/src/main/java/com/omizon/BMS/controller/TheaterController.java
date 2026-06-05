package com.omizon.BMS.controller;


import com.omizon.BMS.Service.TheaterService;
import com.omizon.BMS.dto.TheaterRequest;
import com.omizon.BMS.dto.UserRequest;
import com.omizon.BMS.entity.Theater;
import com.omizon.BMS.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/theaters")
@RequiredArgsConstructor
public class TheaterController
{

  private final TheaterService theaterService;


    @PostMapping("/addTheater")
   public ResponseEntity<Theater> AddTheater (@RequestBody TheaterRequest request)
    {
        return ResponseEntity.ok(theaterService.addTheater(request));

    }


    @GetMapping
    public  ResponseEntity<List<Theater>> getAllTheaters()
    {
        return ResponseEntity.ok(theaterService.getAllTheaters());


    }



    @GetMapping("/{id}")
    public  ResponseEntity<Theater> getTheatersById(@PathVariable Long id)
    {
        return ResponseEntity.ok(theaterService.getTheaterById(id));


    }


    @GetMapping("/city/{cityId}")
    public  ResponseEntity< List<Theater>> getTheatersByCityId(@PathVariable Long cityId)
    {
        return ResponseEntity.ok(theaterService.getTheaterByCity(cityId));


    }










}
