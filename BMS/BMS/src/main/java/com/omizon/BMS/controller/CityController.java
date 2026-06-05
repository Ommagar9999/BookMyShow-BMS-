package com.omizon.BMS.controller;


import com.omizon.BMS.Service.CityService;
import com.omizon.BMS.dto.CityRequest;
import com.omizon.BMS.entity.City;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cities")
@RequiredArgsConstructor
public class CityController {

private final CityService cityService;

    @PostMapping("/addCity")
    public ResponseEntity<City> addCity(@RequestBody CityRequest request) {
        return ResponseEntity.ok(cityService.addCity(request));
    }

    @GetMapping
  public ResponseEntity<List<City>> getAllCities()
  {

      return  ResponseEntity.ok(cityService.getAllCities());
  }


    @GetMapping("/{id}")
    public ResponseEntity<City> getCitiesById(@PathVariable Long id)
    {
        return  ResponseEntity.ok(cityService.getCityById(id));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCity(@PathVariable Long id) {
        System.out.println("Deleting city id = " + id);
        cityService.deleteCity(id);
        return ResponseEntity.ok("City deleted successfully");
    }




}
