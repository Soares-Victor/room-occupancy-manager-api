# Room Occupancy Manager API
The proposal about this project is management rooms of a hotel. 
The objective is to inform how many premium and economy rooms are available, we also will inform
how much each customer will pay.

# Tree
```text
└── kubernetes
    └── config-map.yaml
    └── deployment.yaml
    └── ingress.yaml
    └── service.yaml
└── src
    ├── main
    │   ├── java
    │   │   └── room.occupancy.manager.api
    │   │       └── controller
    │   │       └── model
    │   │       └── service
    │   │
    │   └── resources
    │       └── application.yaml
    └── test
        └── java
            └── room.occupancy.manager.api
                └── controller
                └── service
```

# How to run Local
1. Clone repository:
```sh
$ git clone https://github.com/Soares-Victor/room-occupancy-manager-api.git
```

2. Change Directory:
```sh
$ cd room-occupancy-manager-api/
```

3. Build:
```sh
$ ./gradlew build
```

4. Execute Directly:
```sh
$ ./gradlew bootRun
``` 

5. Test your first request:
```sh
$ curl --location --request POST 'http://localhost:8080/room-occupancy-manager-api/reservation' \
--header 'Content-Type: application/json' \
--data-raw '{
  "freePremiumRoom": 3,
  "freeEconomyRoom": 3,
  "priceToPay": [23, 45, 155, 374, 22, 99.99, 100, 101, 115, 209]
}'
``` 

6. Or access the swagger, following the address: [Swagger UI](http://localhost:8080/room-occupancy-manager-api/swagger-ui.html)

# Architecture about this project
* Gradle
* Java 11
* JUnit
* Spring Webflux (Reactive Mode)
* Docker
* Kubernetes
* Rancher (Orchestrator Kubernetes)
* AWS (EC2, Route 53, etc)
* Terraform Infra as Code (Deploy Architecture)
* Pipeline as Code
* Sonar
* Agile

# How To Access this Project in "Production"

1. Test your first request in production:
```sh
$ curl --location --request POST 'http://app.projectsysvictor.com/room-occupancy-manager-api/reservation' \
--header 'Content-Type: application/json' \
--data-raw '{
  "freePremiumRoom": 3,
  "freeEconomyRoom": 3,
  "priceToPay": [23, 45, 155, 374, 22, 99.99, 100, 101, 115, 209]
}'
``` 

2. Or access the swagger, following the address:
[Swagger UI Production](http://app.projectsysvictor.com/room-occupancy-manager-api/swagger-ui.html)

   
3. Check the Quality Gate: 
[Sonar](http://sonar.projectsysvictor.com/dashboard?branch=main&id=Soares-Victor-room-occupancy-manager-api)


4. Also check the agile board [Board Trello](https://trello.com/b/taTfFRZa)


5. Check the Architecture in Rancher [Rancher Production](https://rancher-stg.projectsysvictor.com/login)
```sh
Username: guest
Password: 1234
``` 