# How it works

There are 2 endpoints, both endpoints receive the same payload.

The first endpoint retrieves a histogram of the all the surgeries in Manchester by Outward Code

    http://localhost:8080/openPharmacy/
    

example response

    [
        {
            "postcode": "M6",
            "numberOfSurgeries": 16,
            "percentageOfSurgeries": "5.11%"
        },
        ...
    ]

The second endpoint retrieves a histogram of the top surgeries in Manchester by Outward Code.
In the request you define if you want to see top 10, top 5, and so on.

    http://localhost:8080/openPharmacy/3
    
    
example response

    [
        {
            "postcode": "M6",
            "numberOfSurgeries": 16,
            "percentageOfSurgeries": "5.11%"
        },
        {
            "postcode": "M22",
            "numberOfSurgeries": 16,
            "percentageOfSurgeries": "5.11%"
        },
        {
            "postcode": "M40",
            "numberOfSurgeries": 14,
            "percentageOfSurgeries": "4.47%"
        }
    ]
    
This is a postman collection with the request : https://www.getpostman.com/collections/e47acc8d63d0b840e2af

# How to run

The easiest way is to pull a docker image and run a container from that image

###### Pull Docker Image
    docker pull filipecunha720/open_pharmacy

###### Run Docker Image
    docker run -p 8080:8080 filipecunha720/open_pharmacy

##### If you don't have docker installed

    sudo apt install docker.io

Your unprivileged user does not belong to the docker group yet. 
You will need to run the next command.

    sudo usermod -a -G docker $USER

for this action to take effect you need to restart your machine or run this command

    newgrp docker

after this you should be able to do run the docker commands


### If you want to change the code and build a different image

###### How to start the application
    ./gradlew bootRun

###### How to build the JAR
    ./gradlew bootJar

###### How to build the Docker Image
    docker build -t newimagename .




