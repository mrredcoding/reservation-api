package cedric.alonso.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a room is not available for reservation.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RoomUnavailableException extends BaseException {

    /**
     * Constructs a new RoomUnavailableException with a default message.
     */
    public RoomUnavailableException() {
        super("Room is not available.");
    }
}