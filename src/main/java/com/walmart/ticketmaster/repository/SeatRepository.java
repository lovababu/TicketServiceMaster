package com.walmart.ticketmaster.repository;

import com.walmart.ticketmaster.constant.SeatStatus;
import com.walmart.ticketmaster.constant.VenueLevelEnum;
import com.walmart.ticketmaster.domain.Seat;
import com.walmart.ticketmaster.domain.SeatHold;
import lombok.Synchronized;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Created by mkambam on 12/15/15.
 */
@Repository
public class SeatRepository {

    private static Map<Integer, List<Seat>> SEATS;
    private static Map<String, SeatHold> SEAT_HOLDER;
    private static AtomicInteger holdId = new AtomicInteger(9999);

    @PostConstruct
    public void init() {
        System.out.println("Initializing static ticketData map.");
        SEATS = new HashMap<>(4);
        SEATS.put(VenueLevelEnum.ORCHESTRA.getLevel(), getSeats(VenueLevelEnum.ORCHESTRA));
        SEATS.put(VenueLevelEnum.MAIN.getLevel(), getSeats(VenueLevelEnum.MAIN));
        SEATS.put(VenueLevelEnum.BALCONY2.getLevel(), getSeats(VenueLevelEnum.BALCONY2));
        SEATS.put(VenueLevelEnum.BALCONY1.getLevel(), getSeats(VenueLevelEnum.BALCONY1));

        SEAT_HOLDER = new HashMap<>();
    }

    private List<Seat> getSeats(VenueLevelEnum venueLevel) {
        List<Seat> seats = new ArrayList<>(venueLevel.getTotalSeats());

        for (int i = 1; i <= venueLevel.getTotalSeats(); i++) {
            Seat seat = new Seat();
            seat.setId(i + (1000 * venueLevel.getLevel()));
            seat.setStatus(SeatStatus.AVAILABLE);
            seat.setVenueLevelId(venueLevel.getLevel());
            seats.add(seat);
        }
        return seats;
    }

    public int getAvailableSeatsByVenueLevelId(int venueLevelId) {
        return (int) SEATS.get(venueLevelId).stream()
                .filter(seat -> (seat.isAvailable())).count();
    }


    public int getAllAvailableSeats() {
        int numOfSeats = 0;
        for (VenueLevelEnum e : VenueLevelEnum.values())
            numOfSeats += getAvailableSeatsByVenueLevelId(e.getLevel());
        return numOfSeats;
    }


    @Synchronized
    public List<Seat> findSeatsToHold(int numSeats, int venueLevelId, long durationToHold) {
        /*return SEATS.get(venueLevelId).stream().filter(seat -> {
            int numOfSeats = 0;
            boolean available = seat.isAvailable() && numOfSeats++ < numSeats;
            if (available) {
                seat.setTimer(durationToHold);
            }
            return available;
        }).collect(Collectors.toList());*/

        List<Seat> seatsTohold = new ArrayList<>(numSeats);
        for (Seat seat: SEATS.get(venueLevelId)) {
            if (seat.isAvailable()) {
                seat.setTimer(durationToHold);
                seat.setStatus(SeatStatus.HOLD);
                seatsTohold.add(seat);
            }

            if (seatsTohold.size() == numSeats) {
                break;
            }
        }

        return seatsTohold;
    }

    public SeatHold holdSeats(List<Seat> seats, String customerEmail, long durationToHold) {
            SeatHold seatHold = new SeatHold(holdId.incrementAndGet(), customerEmail, durationToHold);
            seatHold.setSeats(seats);
            SEAT_HOLDER.put(seatHold.getId() + customerEmail, seatHold);
            System.out.println("SeatHoldId for the customer: " + customerEmail + " is: " + seatHold.getId());
            return seatHold;
    }

    public String reserveSeats(Integer seatHoldId, String customerEmail) {
        if(SEAT_HOLDER.containsKey(seatHoldId + customerEmail)) {
            SeatHold seatHold = SEAT_HOLDER.get(seatHoldId + customerEmail);
            if (seatHold.getTimer() < System.currentTimeMillis()) {
                System.out.println("Hold duration has expired, cannot reserve seats for you!!!");
                SEAT_HOLDER.remove(seatHoldId + customerEmail);
                return "Hold duration has expired, cannot reserve seats for you!!!";
            }
            seatHold.getSeats().stream().forEach(seat -> {
                seat.setStatus(SeatStatus.RESERVED);
                for (Seat s: SEATS.get(seat.getVenueLevelId())) {
                    if (s.getId() == seat.getId()) {
                        s.setStatus(SeatStatus.RESERVED);
                        s.setTimer(null);
                    }
                }
            });
            return "Your seats has been reserved.";
        }
        return "Unknown or Invalid combination of seatHoldId & customerMailId!!!";
    }

}


