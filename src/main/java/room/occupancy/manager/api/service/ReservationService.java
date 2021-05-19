package room.occupancy.manager.api.service;

import reactor.core.publisher.Mono;
import room.occupancy.manager.api.model.ReservationRequest;
import room.occupancy.manager.api.model.ReservationResponse;

public interface ReservationService {

    Mono<ReservationResponse> checkRooms(ReservationRequest reservationRequest);

}
