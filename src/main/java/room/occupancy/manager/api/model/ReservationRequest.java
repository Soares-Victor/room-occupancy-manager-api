package room.occupancy.manager.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationRequest implements Serializable {

    private Integer freePremiumRoom;
    private Integer freeEconomyRoom;
    private List<BigDecimal> priceToPay;

}
