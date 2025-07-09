package cedric.alonso.backend.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents a hotel room.
 */
@Data
@Document(collection = "rooms")
public class Room {
    @Id
    private String id;
    private String name;
    private int maxOccupancy;
    private boolean isAvailable;
}