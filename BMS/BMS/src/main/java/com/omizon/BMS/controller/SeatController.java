package com.omizon.BMS.controller;


import com.omizon.BMS.Service.SeatService;
import com.omizon.BMS.dto.SeatRequest;
import com.omizon.BMS.entity.Seat;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
@RequiredArgsConstructor
public class SeatController {



    private  final SeatService seatService;

     @PostMapping("/addSeat")
    public ResponseEntity<Seat> addSeat( @RequestBody SeatRequest request)
    {
        return  ResponseEntity.ok(seatService.addSeat(request));

    }

  @GetMapping("/screen/{screenId}")
    public  ResponseEntity<List<Seat>> getSeatByScreen(@PathVariable Long screenId)
    {

        return  ResponseEntity.ok(seatService.getSeatByScreen(screenId));


    }

    @GetMapping("{id}")
    public  ResponseEntity<Seat> getSeatById(@PathVariable Long id)
    {

        return  ResponseEntity.ok(seatService.getSeatById(id));


    }








}
