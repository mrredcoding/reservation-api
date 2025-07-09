package cedric.alonso.backend.services;

import cedric.alonso.backend.exceptions.BaseException;
import cedric.alonso.backend.exceptions.ResourceNotFoundException;
import cedric.alonso.backend.models.Room;
import cedric.alonso.backend.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for room-related operations.
 */
@Service
public class RoomService {
    private final RoomRepository roomRepository;

    @Autowired
    public  RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }
    /**
     * Retrieves all available rooms.
     * @return list of available rooms
     */
    public List<Room> getAvailableRooms() {
        return roomRepository.findByIsAvailableTrue();
    }

    /**
     * Retrieves all reserved rooms.
     * @return list of reserved rooms
     */
    public List<Room> getReservedRooms() {
        return roomRepository.findByIsAvailableFalse();
    }

    /**
     * Retrieves room details by ID.
     * @param id the room ID
     * @return the Room object
     */
    public Room getRoomById(String id) throws BaseException {
        return roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + id));
    }
}