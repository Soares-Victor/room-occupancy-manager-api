package room.occupancy.manager.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationRequest implements Serializable {

    @Schema(description = "Free premium rooms", example = "3")
    private Integer freePremiumRoom;

    @Schema(description = "Free economy rooms", example = "3")
    private Integer freeEconomyRoom;

    @Schema(description = "List guest to pay", example = "[23, 45, 155, 374, 22, 99.99, 100, 101, 115, 209]")
    private List<Double> priceToPay;

}
