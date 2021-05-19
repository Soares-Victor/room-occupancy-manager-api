package room.occupancy.manager.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import room.occupancy.manager.api.model.ReservationRequest;
import room.occupancy.manager.api.model.ReservationResponse;
import room.occupancy.manager.api.service.ReservationService;

@RestController
@RequestMapping("/reservation")
@Tag(name = "Reservations", description = "Reserve Information")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    
    @PostMapping
    @Operation(summary = "Rooms Information", description = "Details about rooms")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success")
    })
    public Mono<ReservationResponse> calculateOccupancyRooms(@RequestBody ReservationRequest reservationRequest) {
        return reservationService.checkRooms(reservationRequest);
    }

}
