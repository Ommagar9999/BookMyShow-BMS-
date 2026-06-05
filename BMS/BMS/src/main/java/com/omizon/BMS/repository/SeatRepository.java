package com.omizon.BMS.repository;

import com.omizon.BMS.entity.Seat;
import com.omizon.BMS.entity.Theater;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat,Long>
{


    List<Seat> findByScreenId(Long screenId);


}
