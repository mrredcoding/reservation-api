package cedric.alonso.backend.services;

import cedric.alonso.backend.exceptions.BaseException;
import cedric.alonso.backend.exceptions.ResourceNotFoundException;
import cedric.alonso.backend.exceptions.RoomCapacityExceededException;
import cedric.alonso.backend.exceptions.RoomUnavailableException;
import cedric.alonso.backend.models.Reservation;
import cedric.alonso.backend.models.Room;
import cedric.alonso.backend.repositories.ReservationRepository;
import cedric.alonso.backend.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Service class for reservation-related operations.
 */
@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, RoomRepository roomRepository) {
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
    }

    /**
     * Creates a reservation.
     * @param roomId ID of the room to reserve
     * @param clientName name of the client
     * @param guests number of guests
     * @return the created reservation
     */
    public Reservation createReservation(String roomId, String clientName, int guests) throws BaseException {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + roomId));

        if (!room.isAvailable()) {
            throw new RoomUnavailableException();
        }

        if (guests > room.getMaxOccupancy()) {
            throw new RoomCapacityExceededException();
        }

        Reservation reservation = new Reservation();
        reservation.setRoomId(roomId);
        reservation.setClientName(clientName);
        reservation.setNumberOfGuests(guests);
        reservation.setCreatedDate(new Date());

        room.setAvailable(false);
        roomRepository.save(room);

        return reservationRepository.save(reservation);
    }

    /**
     * Retrieves all reservations.
     * @return list of all reservations
     */
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    /**
     * Retrieves reservation details by ID.
     * @param id the reservation ID
     * @return the Reservation object
     */
    public Reservation getReservation(String id) throws BaseException {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with id: " + id));
    }

    /**
     * Updates an existing reservation.
     * @param id reservation ID
     * @param newRoomId new room ID
     * @param guests updated number of guests
     * @return updated Reservation
     */
    public Reservation updateReservation(String id, String newRoomId, int guests) throws BaseException {
        Reservation reservation = getReservation(id);
        reservation.setRoomId(newRoomId);
        reservation.setNumberOfGuests(guests);
        return reservationRepository.save(reservation);
    }

    /**
     * Cancels a reservation by ID.
     * @param id the reservation ID
     */
    public void cancelReservation(String id) throws BaseException {
        Reservation reservation = getReservation(id);
        String roomId = reservation.getRoomId();
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + roomId));

        room.setAvailable(true);
        roomRepository.save(room);

        reservationRepository.delete(reservation);
    }
}