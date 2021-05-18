package room.occupancy.manager.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RoomOccupancyManager {

    public static void main(String[] args) {
        SpringApplication.run(RoomOccupancyManager.class, args);
    }

}
