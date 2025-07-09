package cedric.alonso.backend.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Represents a reservation made by a client for a hotel room.
 */
@Data
@Document(collection = "reservations")
public class Reservation {
    @Id
    private String id;
    private String roomId;
    private String clientName;
    private int numberOfGuests;
    private Date createdDate;
}