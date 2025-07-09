package cedric.alonso.backend.repositories;

import cedric.alonso.backend.models.Room;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends MongoRepository<Room, String> {
    List<Room> findByIsAvailableTrue();
    List<Room> findByIsAvailableFalse();
}