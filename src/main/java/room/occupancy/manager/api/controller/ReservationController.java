package room.occupancy.manager.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import room.occupancy.manager.api.model.ReservationRequest;
import room.occupancy.manager.api.model.ReservationResponse;

@RestController
@RequestMapping("/reservation")
@Tag(name = "Reservations", description = "Reserve Information")
public class ReservationController {


    
    @PostMapping
    @Operation(summary = "Rooms Information", description = "Details about rooms")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success")
    })
    public Mono<ReservationResponse> calculateOccupancyRooms(@RequestBody  ReservationRequest reservationRequest) {
        return null;
    }
    


}
