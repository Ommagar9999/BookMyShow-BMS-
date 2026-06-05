package com.omizon.BMS.controller;


import com.omizon.BMS.Service.ScreenService;
import com.omizon.BMS.dto.ScreenRequest;
import com.omizon.BMS.entity.Screen;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/screens")
@RequiredArgsConstructor
public class ScreenController {

private final ScreenService screenService;


    @PostMapping("addScreen")
    public ResponseEntity<Screen> addScreen(@RequestBody ScreenRequest request)
     {
       return  ResponseEntity.ok(screenService.AddScreen(request));
      }

    @GetMapping("/{id}")
    public  ResponseEntity<Screen> getScreenById(@PathVariable Long id)
    {
             return  ResponseEntity.ok(screenService.getScreenById(id));
    }

    @GetMapping
    public  ResponseEntity<List<Screen>> getAllScreens()
    {
        return  ResponseEntity.ok(screenService.getAllScreen());

    }

    @GetMapping("/theater/{theaterId}")
    public  ResponseEntity<List<Screen>>  getScreenByTheater(@PathVariable Long theaterId)
    {
        return ResponseEntity.ok(screenService.getScreenByTheater(theaterId));

    }








}
