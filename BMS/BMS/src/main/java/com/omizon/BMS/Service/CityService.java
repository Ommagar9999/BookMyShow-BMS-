package com.omizon.BMS.Service;

import com.omizon.BMS.dto.CityRequest;
import com.omizon.BMS.entity.City;
import com.omizon.BMS.repository.CityRepository;
import com.omizon.BMS.repository.TheaterRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityService {


    private  final CityRepository cityRepository;
    private  final TheaterRepository theaterRepository;

    public City addCity(CityRequest request) {

        City city = City.builder()
                .name(request.getName())
                .state(request.getState())
                .build();

        return cityRepository.save(city);
    }
   public List<City> getAllCities()
   {

       return cityRepository.findAll();

   }


    public City getCityById(Long id) {
        return cityRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("City not found with id: " + id));
    }


    @Transactional
    public void deleteCity(Long id) {

        City city = cityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("City not found"));

        if (!city.getTheaters().isEmpty()) {
            city.getTheaters().clear();
        }

        cityRepository.delete(city);
    }


}
