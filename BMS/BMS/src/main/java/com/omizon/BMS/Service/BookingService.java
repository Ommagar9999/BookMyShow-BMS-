package com.omizon.BMS.Service;

import com.omizon.BMS.dto.BookingRequest;
import com.omizon.BMS.dto.BookingResponse;
import com.omizon.BMS.entity.*;
import com.omizon.BMS.enums.BookingStatus;
import com.omizon.BMS.repository.BookingRepository;
import com.omizon.BMS.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final SeatRepository seatRepository;
    private final UserService userService;
    private final ShowService showService;

    @Transactional
    public Booking createBooking(BookingRequest request) {

        // validation
        if (request.getSeatIds() == null || request.getSeatIds().isEmpty()) {
            throw new RuntimeException("Seat list cannot be empty");
        }


        User user = userService.getUserById(request.getUserId());
        Show show = showService.getShowById(request.getShowId());

        // already booked seats
        List<Long> bookedSeatIds =
                bookingRepository.findBookedSeatIdsByShowId(show.getId());

        Set<Long> bookedSet = new HashSet<>(bookedSeatIds);

        // check duplicate booking
        for (Long seatId : request.getSeatIds()) {
            if (bookedSet.contains(seatId)) {
                throw new RuntimeException("Seat already booked: " + seatId);
            }
        }

        //  fetch seats
        List<Seat> seats = seatRepository.findAllById(request.getSeatIds());

        if (seats.size() != request.getSeatIds().size()) {
            throw new RuntimeException("Some seats are invalid");
        }

        // total price
        double totalPrice = seats.size() * show.getTicketPrice();

        // create booking
        Booking booking = Booking.builder()
                .user(user)
                .show(show)
                .seats(seats)
                .totalPrice(totalPrice)
                .status(BookingStatus.CONFIRMED)
                .build();

        return bookingRepository.save(booking);
    }

    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with id " + id));
    }

    public List<BookingResponse> getBookingByUser(Long userId)
    {
        List<Booking> bookings = bookingRepository.findByUserId(userId);

        return bookings.stream()
                .map(booking -> BookingResponse.builder()
                        .id(booking.getId())
                        .userId(booking.getUser().getId())
                        .showId(booking.getShow().getId())
                        .seatIds(
                                booking.getSeats()
                                        .stream()
                                        .map(Seat::getId)
                                        .toList()
                        )
                        .totalPrice(booking.getTotalPrice())
                        .status(booking.getStatus())
                        .build())
                .toList();
    }

    public Booking cancelbooking(Long bookingId) {
        Booking booking = getBookingById(bookingId);
        booking.setStatus(BookingStatus.CANCELLED);
        return bookingRepository.save(booking);
    }


    public List<Seat> getAvailableSeats(Long showId) {

        Show show = showService.getShowById(showId);

        List<Seat> allSeats =
                seatRepository.findByScreenId(show.getScreen().getId());

        List<Long> bookedSeatIds =
                bookingRepository.findBookedSeatIdsByShowId(showId);

        return allSeats.stream()
                .filter(seat -> !bookedSeatIds.contains(seat.getId()))
                .toList();
    }

}