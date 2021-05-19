package room.occupancy.manager.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationResponse implements Serializable {

    private PremiumRoomModel premiumRoomModel;
    private EconomyRoomModel economyRoomModel;

}
