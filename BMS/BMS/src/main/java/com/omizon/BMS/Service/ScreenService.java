package com.omizon.BMS.Service;

import com.omizon.BMS.dto.ScreenRequest;
import com.omizon.BMS.entity.Screen;
import com.omizon.BMS.entity.Theater;
import com.omizon.BMS.repository.ScreenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScreenService {



private  final ScreenRepository  screenRepository;
private  final  TheaterService theaterService;


 public Screen AddScreen(ScreenRequest request)
 {
     Theater theater = theaterService.getTheaterById(request.getTheaterId());
     Screen screen = Screen.builder()
             .name(request.getName())
             .totalSeats(request.getTotalSeats())
             .theater(theater)
             .build();

     return screenRepository.save(screen);

 }

public List<Screen> getAllScreen()
{

    return screenRepository.findAll();
}

public  Screen getScreenById(Long id)
{
     return screenRepository.findById(id)
             .orElseThrow(()-> new RuntimeException(" Screen not found with id "+id));

}

public List<Screen> getScreenByTheater( Long theaterId)
{

    return  screenRepository.findByTheaterId(theaterId);


}



}
