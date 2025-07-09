package cedric.alonso.backend.controllers;

import cedric.alonso.backend.exceptions.BaseException;
import cedric.alonso.backend.models.Room;
import cedric.alonso.backend.services.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing hotel rooms.
 */
@RestController
@RequestMapping("/hotel/rooms")
@Tag(name = "Room Management", description = "APIs for managing hotel rooms.")
public class RoomController {

    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @Operation(summary = "Get available rooms", description = "Returns a list of all available hotel rooms.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved available rooms",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Room.class)))
    })
    @GetMapping("/available")
    public ResponseEntity<List<Room>> getAvailableRooms() {
        List<Room> availableRooms = roomService.getAvailableRooms();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(availableRooms);
    }

    @Operation(summary = "Get reserved rooms", description = "Returns a list of all reserved (unavailable) hotel rooms.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved reserved rooms",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Room.class)))
    })
    @GetMapping("/reserved")
    public ResponseEntity<List<Room>> getReservedRooms() {
        List<Room> reservedRooms = roomService.getReservedRooms();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reservedRooms);
    }

    @Operation(summary = "Get room details", description = "Returns details of a room by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved room",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Room.class))),
            @ApiResponse(responseCode = "404", description = "Room not found",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoomDetails(@PathVariable String id) throws BaseException {
        Room room = roomService.getRoomById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(room);
    }
}