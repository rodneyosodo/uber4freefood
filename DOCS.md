# Kafka streaming microservice

## Description
This is the microservice for publishing user location and also retrieving user data

## Documentation
The kafka website is a good starting point.
What is basically does the user post its location to the kafka backend which a certain topic which is its user id and you can retreive the message with the user id as the topic. User post location data every 1 minutes to ensure tracking.

## Requirements
- Docker
- Docker compose
- Python (for testing)


## Setup
```sh
git fetch --all
git checkout kafka-streaming
cd Kafka
docker-compose pull
docker-compose build
docker-compose up -d
```

## Shutdown
```sh
docker-compose down
```

## Testing
I wrote a simple python program to push and pull data to the kafka stream
#### For pushing data to the kafka stream 
```sh
cd Helpers
python3 producer.py
```
#### For pushing data to the kafka stream 
```sh
cd Helpers
python3 consumer.py
```
