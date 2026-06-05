package com.omizon.BMS.Service;

import com.omizon.BMS.dto.SeatRequest;
import com.omizon.BMS.entity.Screen;
import com.omizon.BMS.entity.Seat;
import com.omizon.BMS.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatService {


    private final SeatRepository seatRepository;
    private  final  ScreenService screenService;


    public Seat addSeat(SeatRequest request) {

        System.out.println("REQUEST: " + request);

        Screen screen = screenService.getScreenById(request.getScreenId());

        Seat seat = Seat.builder()
                .seatNumber(request.getSeatNumber())
                .row(request.getRow())
                .col(request.getCol())
                .seatType(request.getSeatType())
                .screen(screen)
                .build();

        return seatRepository.save(seat);
    }
 public List<Seat>  getSeatByScreen(Long screenId)
  {
      return seatRepository.findByScreenId(screenId);

  }

  public  Seat getSeatById(Long id)
  {
      return seatRepository.findById(id)
              .orElseThrow(()-> new RuntimeException(" Seat not found with id "+id));
  }




}
