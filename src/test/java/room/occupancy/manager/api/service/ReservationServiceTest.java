package room.occupancy.manager.api.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import room.occupancy.manager.api.model.EconomyRoomModel;
import room.occupancy.manager.api.model.PremiumRoomModel;
import room.occupancy.manager.api.model.ReservationRequest;
import room.occupancy.manager.api.model.ReservationResponse;
import room.occupancy.manager.api.service.impl.ReservationServiceImpl;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = ReservationService.class)
@Import(ReservationServiceImpl.class)
public class ReservationServiceTest {


    @InjectMocks
    ReservationServiceImpl reservationService;

    @Autowired
    WebTestClient webTestClient;

    @Test
    void testCalculateOccupancyRooms() {
        int freeEconomyRoom = 3;
        int freePremiumRoom = 3;
        List<Double> priceToPay =
                Arrays.asList(23.0, 45.0, 155.0, 374.0, 22.0, 99.99, 100.0, 101.0, 115.0, 209.0);
        ReservationRequest reservationRequest = ReservationRequest.builder()
                .freeEconomyRoom(freeEconomyRoom)
                .freePremiumRoom(freePremiumRoom)
                .priceToPay(priceToPay)
                .build();

        List<Double> premiumList = reservationRequest.getPriceToPay()
                .stream()
                .filter(v -> v >= 100)
                .sorted(Comparator.reverseOrder())
                .limit(reservationRequest.getFreePremiumRoom())
                .collect(Collectors.toList());

        List<Double> economyLIst = reservationRequest.getPriceToPay()
                .stream()
                .filter(v -> v < 100)
                .sorted(Comparator.reverseOrder())
                .limit(reservationRequest.getFreeEconomyRoom())
                .collect(Collectors.toList());

        assertFalse(reservationRequest.getFreePremiumRoom() > premiumList.size());
        assertTrue(economyLIst.size() >= reservationRequest.getFreeEconomyRoom());

        EconomyRoomModel economyRoomModel = EconomyRoomModel.builder()
                .usage(economyLIst.size())
                .profit(economyLIst.stream().mapToDouble(Double::doubleValue).sum())
                .build();
        PremiumRoomModel premiumRoomModel = PremiumRoomModel.builder()
                .usage(premiumList.size())
                .profit(premiumList.stream().mapToDouble(Double::doubleValue).sum())
                .build();
        ReservationResponse reservationResponse = ReservationResponse.builder()
                .economyRoomModel(economyRoomModel)
                .premiumRoomModel(premiumRoomModel)
                .build();



        Mono<ReservationResponse> reservationResponseMono = reservationService.checkRooms(reservationRequest);

        StepVerifier.create(reservationResponseMono)
                .expectNext(reservationResponse)
                .verifyComplete();

    }

    @Test
    void testIsFreeRoomCalculateOccupancyRooms() {
        int freeEconomyRoom = 1;
        int freePremiumRoom = 7;
        List<Double> priceToPay =
                Arrays.asList(23.0, 45.0, 155.0, 374.0, 22.0, 99.99, 100.0, 101.0, 115.0, 209.0);
        ReservationRequest reservationRequest = ReservationRequest.builder()
                .freeEconomyRoom(freeEconomyRoom)
                .freePremiumRoom(freePremiumRoom)
                .priceToPay(priceToPay)
                .build();

        List<Double> premiumList = reservationRequest.getPriceToPay()
                .stream()
                .filter(v -> v >= 100)
                .sorted(Comparator.reverseOrder())
                .limit(reservationRequest.getFreePremiumRoom())
                .collect(Collectors.toList());

        int upgradeFree = reservationRequest.getFreePremiumRoom() - premiumList.size();

        premiumList = reservationRequest.getPriceToPay()
                .stream()
                .sorted(Comparator.reverseOrder())
                .limit(reservationRequest.getFreePremiumRoom())
                .collect(Collectors.toList());

        List<Double> economyLIst = reservationRequest.getPriceToPay()
                .stream()
                .filter(v -> v < 100)
                .sorted(Comparator.reverseOrder())
                .filter(Predicate.not(premiumList::contains))
                .limit(upgradeFree)
                .collect(Collectors.toList());

        assertFalse(reservationRequest.getFreePremiumRoom() > premiumList.size());
        assertTrue(economyLIst.size() >= reservationRequest.getFreeEconomyRoom());


        EconomyRoomModel economyRoomModel = EconomyRoomModel.builder()
                .usage(economyLIst.size())
                .profit(economyLIst.stream().mapToDouble(Double::doubleValue).sum())
                .build();
        PremiumRoomModel premiumRoomModel = PremiumRoomModel.builder()
                .usage(premiumList.size())
                .profit(premiumList.stream().mapToDouble(Double::doubleValue).sum())
                .build();
        ReservationResponse reservationResponse = ReservationResponse.builder()
                .economyRoomModel(economyRoomModel)
                .premiumRoomModel(premiumRoomModel)
                .build();



        Mono<ReservationResponse> reservationResponseMono = reservationService.checkRooms(reservationRequest);

        StepVerifier.create(reservationResponseMono)
                .expectNext(reservationResponse)
                .verifyComplete();
    }


}
