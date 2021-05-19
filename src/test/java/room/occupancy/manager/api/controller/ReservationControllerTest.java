package room.occupancy.manager.api.controller;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;
import room.occupancy.manager.api.model.EconomyRoomModel;
import room.occupancy.manager.api.model.PremiumRoomModel;
import room.occupancy.manager.api.model.ReservationRequest;
import room.occupancy.manager.api.model.ReservationResponse;
import room.occupancy.manager.api.service.ReservationService;

import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = ReservationController.class)
@Import(ReservationService.class)
public class ReservationControllerTest {

    @MockBean
    ReservationService reservationService;

    @Autowired
    WebTestClient webTestClient;

    @Test
    void testCalculateOccupancyRooms() {

        int freeEconomyRoom = 3;
        int freePremiumRoom = 3;
        double profitPremium = 738;
        double profitEconomy = 167.99;


        List<Double> priceToPay =
                Arrays.asList(23.0, 45.0, 155.0, 374.0, 22.0, 99.99, 100.0, 101.0, 115.0, 209.0);
        ReservationRequest reservationRequest = ReservationRequest.builder()
                .freeEconomyRoom(freeEconomyRoom)
                .freePremiumRoom(freePremiumRoom)
                .priceToPay(priceToPay)
                .build();

        EconomyRoomModel economyRoomModel = EconomyRoomModel.builder()
                .usage(freeEconomyRoom)
                .profit(profitEconomy)
                .build();
        PremiumRoomModel premiumRoomModel = PremiumRoomModel.builder()
                .usage(freePremiumRoom)
                .profit(profitPremium)
                .build();
        ReservationResponse reservationResponse = ReservationResponse.builder()
                .economyRoomModel(economyRoomModel)
                .premiumRoomModel(premiumRoomModel)
                .build();

        Mockito.when(reservationService.checkRooms(reservationRequest)).thenReturn(Mono.just(reservationResponse));

        webTestClient.post()
                .uri("/reservation")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(reservationRequest))
                .exchange()
                .expectStatus().isOk();

        Mockito.verify(reservationService, Mockito.times(1)).checkRooms(reservationRequest);


    }

}
