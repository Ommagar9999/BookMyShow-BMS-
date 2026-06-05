package com.omizon.BMS.repository;

import com.omizon.BMS.entity.Screen;
import com.omizon.BMS.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScreenRepository extends JpaRepository<Screen,Long>
{


    List<Screen> findByTheaterId(Long theaterId);


}
