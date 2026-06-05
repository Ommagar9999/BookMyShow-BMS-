package com.omizon.BMS.dto;


import com.omizon.BMS.enums.BookingStatus;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingResponse {

    private Long id;
    private Long userId;
    private Long showId;
    private List<Long> seatIds;
    private Double totalPrice;
    private BookingStatus status;
}