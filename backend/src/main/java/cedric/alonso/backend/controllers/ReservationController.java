package cedric.alonso.backend.controllers;

import cedric.alonso.backend.exceptions.BaseException;
import cedric.alonso.backend.models.Reservation;
import cedric.alonso.backend.services.ReservationService;
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
 * REST controller for managing room reservations.
 */
@RestController
@RequestMapping("/hotel/reservations")
@Tag(name = "Reservation Management", description = "APIs for managing reservations.")
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @Operation(summary = "Get all reservations", description = "Returns a list of all reservations.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved reservations",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Reservation.class)))
    })
    @GetMapping("/all")
    public ResponseEntity<List<Reservation>> getAllReservations() {
        List<Reservation> reservations = reservationService.getAllReservations();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reservations);
    }

    @Operation(summary = "Get reservation by ID", description = "Returns a reservation by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved reservation",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Reservation.class))),
            @ApiResponse(responseCode = "404", description = "Reservation not found",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/{id}")
    public ResponseEntity<Reservation> get(@PathVariable String id) throws BaseException {
        Reservation reservation = reservationService.getReservation(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reservation);
    }

    @Operation(summary = "Create reservation", description = "Creates a new reservation.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created reservation",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Reservation.class)))
    })
    @PostMapping("/reserve")
    public ResponseEntity<Reservation> reserve(
            @RequestParam String roomId,
            @RequestParam String name,
            @RequestParam int guests) throws BaseException {
        Reservation createdReservation = reservationService.createReservation(roomId, name, guests);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdReservation);
    }

    @Operation(summary = "Update reservation", description = "Updates an existing reservation.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated reservation",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Reservation.class)))
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<Reservation> updateReservation(
            @PathVariable String id,
            @RequestParam String newRoomId,
            @RequestParam int guests) throws BaseException {
        Reservation updatedReservation = reservationService.updateReservation(id, newRoomId, guests);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedReservation);
    }

    @Operation(summary = "Cancel reservation", description = "Cancels a reservation by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully canceled reservation",
                    content = @Content)
    })
    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<String> cancelReservation(@PathVariable String id) throws BaseException {
        reservationService.cancelReservation(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Your reservation has been cancelled successfully.");
    }
}