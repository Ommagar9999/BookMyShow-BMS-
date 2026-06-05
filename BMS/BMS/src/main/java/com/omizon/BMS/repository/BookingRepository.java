package com.omizon.BMS.repository;

import com.omizon.BMS.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByUserId(Long userId);

    List<Booking> findByShowId(Long showId);

    @Query("""
        SELECT s.id
        FROM Booking b
        JOIN b.seats s
        WHERE b.show.id = :showId
        AND b.status = com.omizon.BMS.enums.BookingStatus.CONFIRMED
    """)
    List<Long> findBookedSeatIdsByShowId(@Param("showId") Long showId);
}
