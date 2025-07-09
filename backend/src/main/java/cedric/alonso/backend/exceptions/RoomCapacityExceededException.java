package cedric.alonso.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when the number of guests exceeds the room's capacity.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RoomCapacityExceededException extends BaseException {

    /**
     * Constructs a new RoomCapacityExceededException with a default message.
     */
    public RoomCapacityExceededException() {
        super("No available room with enough capacity.");
    }
}