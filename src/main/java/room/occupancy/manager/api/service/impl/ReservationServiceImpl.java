package room.occupancy.manager.api.service.impl;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import room.occupancy.manager.api.model.EconomyRoomModel;
import room.occupancy.manager.api.model.PremiumRoomModel;
import room.occupancy.manager.api.model.ReservationRequest;
import room.occupancy.manager.api.model.ReservationResponse;
import room.occupancy.manager.api.service.ReservationService;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Override
    public Mono<ReservationResponse> checkRooms(ReservationRequest reservationRequest) {
        return Mono.create(sink -> sink.success(preparationCheckRooms(reservationRequest)));
    }

    private ReservationResponse preparationCheckRooms(ReservationRequest reservationRequest) {
        List<Double> premiumList = getListPremiumRooms(reservationRequest);

        List<Double> economyLIst = getListEconomyRooms(reservationRequest);

        if (isFreeRooms(reservationRequest, premiumList, economyLIst)) {
            int upgradeFree = reservationRequest.getFreePremiumRoom() - premiumList.size();
            premiumList = reservationRequest.getPriceToPay()
                    .stream()
                    .sorted(Comparator.reverseOrder())
                    .limit(reservationRequest.getFreePremiumRoom())
                    .collect(Collectors.toList());

            economyLIst = reservationRequest.getPriceToPay()
                    .stream()
                    .filter(v -> v < 100)
                    .sorted(Comparator.reverseOrder())
                    .filter(Predicate.not(premiumList::contains))
                    .limit(upgradeFree)
                    .collect(Collectors.toList());
        }

        return getReservationResponse(
                economyLIst.size(),
                premiumList.size(),
                economyLIst.stream().mapToDouble(Double::doubleValue).sum(),
                premiumList.stream().mapToDouble(Double::doubleValue).sum());
    }

    private boolean isFreeRooms(ReservationRequest reservationRequest, List<Double> premium, List<Double> economy) {
        return reservationRequest.getFreePremiumRoom() > premium.size() && economy.size() >= reservationRequest.getFreeEconomyRoom();
    }

    private ReservationResponse getReservationResponse(int economyUsage, int premiumUsage, Double economyProfit, Double premiumProfit) {
        PremiumRoomModel premiumRoomModel = PremiumRoomModel.builder()
                .usage(premiumUsage)
                .profit(premiumProfit)
                .build();
        EconomyRoomModel economyRoomModel = EconomyRoomModel.builder()
                .usage(economyUsage)
                .profit(economyProfit)
                .build();

        return ReservationResponse.builder()
                .economyRoomModel(economyRoomModel)
                .premiumRoomModel(premiumRoomModel)
                .build();
    }

    private List<Double> getListEconomyRooms(ReservationRequest reservationRequest) {
        return reservationRequest.getPriceToPay()
                .stream()
                .filter(v -> v < 100)
                .sorted(Comparator.reverseOrder())
                .limit(reservationRequest.getFreeEconomyRoom())
                .collect(Collectors.toList());
    }

    private List<Double> getListPremiumRooms(ReservationRequest reservationRequest) {
        return reservationRequest.getPriceToPay()
                .stream()
                .filter(v -> v >= 100)
                .sorted(Comparator.reverseOrder())
                .limit(reservationRequest.getFreePremiumRoom())
                .collect(Collectors.toList());
    }

}
