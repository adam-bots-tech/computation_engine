# COMPUTATION ENGINE

This is a simple message-based computation engine. I wrote it to demonstrate my coding abilities and my knowledge of enterprise architecture. I've included some instructions for running the code via Docker

The engine works by receiving HTTP requests containing a JSON request message. The endpoint passes the request to a worker via a message queue and returns a message ID to the caller. The worker will pull the message off the queue at a later time, process the request for data and push the data in a payload back to the message queue. The endpoint will receive the payload and store it for use later. 

Then, at a later data, the user who sent the request to the endpoint can use the message ID to retrieve the payload. If they attempt to retrieve it before processing has finished, they receive a 404 error code and a message instructing them to try again later.

The design of this system contains the following valuable elements. These elements make it desirable to enterprise systems like those used in enterprise business, online services and IoT backends.

* The functionality is divided up between different microservices instead of being contained inside of a single monolith. This sets the footprint for small, scalable, encapsulated units of code which can be worked on by independent teams.
* The technology used to facilitate communication between the microservices and between the system and the consumers/users (JSON messages, HTTP protocol, message queues) are technology agnostic. This allows microservices built in different programming languages to be added into the system, expanding it's functionality in a scalable fashion. Python, for example, has become the dominant language in data science and being able to utilize it for microservices responsible for analytics and data mining is quite valuable! My next project is to begin mastering Python and I look forward to adding a Python microservice to this code example.
* Each microservice makes uses of it's own unique database, chosen for it's ability to optimally handle the given responsibilities of the microservice.
* The system is run using Docker containers, creating the smallest footprint possible on the hardware.

## TECH STACK

* Java and the Spring framework provide the foundation for both microservices.
* Cassandra is the database used by the endpoint to store and retrieve the complete messages. NoSQL databases are great at storing and scaling large volumes of sequential data and provides fast read times and write times, so the endpoint can complete HTTP requests quickly.
* Postgres is the database used by the worker to store key/value pairs and data aggregations for reference when computing requests. Since we do not store the bulk messages in Postgres, scaling the database is less of a concern, allowing us to benefit from a relational database's ability to execute complex data queries.
* RabbitMQ provides the message queues used to facilitate communication between the different microservices.
* Docker is used to create the container images for each service.

## CODE MODULES

### computation_engine_messages

This is a library of all the Java representations of the JSON messages. It is used by both the endpoint and the worker to keep the marshalling and demarshalling of the data consistent.

#### Design Patterns
* Model
* Factory
* Envelope
* Transformer

### computation_engine_endpoint

The endpoint has two responsibilities. One, it receives request messages from the user and schedules them for processing with the worker. Two, when a user requests the completed payload for the request they sent, the endpoint checks it's repository to see if the payload has been delivered by the worker and if so, returns it to the user.

#### Design Patterns
* Service
* Controller
* Model
* Helper
* Listener
* Repository
* Configuration
* Exception

### computation_engine_worker

The worker is responsible for retrieving the request message from the endpoint via RabbitMQ, identifying what type of message it is and then, performing the desired work based on that type. The results are compiled into a payload message and passed back to the endpoint.

#### Design Patterns
* Service
* Transformer
* Listener
* Repository
* Configuration
* Exception

## RUNNING THE SOFTWARE

This code is mostly for review and discussion by future employers, but if you wish to run the system locally, you can with these commands. These are written for a Mac and run from the project root.

```
mvn clean install

docker network create --driver bridge ce_network

docker run --name ce_cassandra --net ce_network -v [PATH_TO_YOUR_DATA_FOLDER]:/var/lib/cassandra -d cassandra:3.11.6

docker run -v [PATH_TO_YOUR_DATA_FOLDER]:/var/lib/rabbitmq -d --net ce_network --name ce_rabbit rabbitmq:3

docker run --name ce_postgres --net ce_network -e POSTGRES_PASSWORD=root -e PGDATA=/var/lib/postgresql/data/pgdata -v [PATH_TO_YOUR_DATA_FOLDER]:/var/lib/postgresql/data -d postgres

docker build -f computation_engine_endpoint/Dockerfile -t computation_engine_endpoint/ce_endpoint computation_engine_endpoint

docker run --name ce_endpoint --net ce_network -p 8080:8080 computation_engine_endpoint/ce_endpoint

docker build -f computation_engine_worker/Dockerfile -t computation_engine_worker/ce_worker computation_engine_worker

docker run --name ce_worker --net ce_network computation_engine_worker/ce_worker
```

## TESTING THE SOFTWARE

The computation engine supports one basic operation at the moment: updating a key/value table with runtime configuration data containing constants useful for doing computations. We just have one configuration at the moment; a data point for default interest rate for use in financial calculations.

To kick off update configuration process, send this POST request to *http://localhost:8080/message* via an application like Postman.

```
{
    "type": "UPDATE_CONFIGURATION",
    "metaData": {
        "source": "USER"
    },
    "message": {
        "className": "org.al.priv.ce.messages.requests.UpdateConfigurationRequest",
        "defaultInterestRate": "4.0"
    }
}
```

This message contains type for identifying what is in the envelope, metaData about the envelope being sent and the message property which contains the data for the message itself and instructions as to what class to demarshall the data into.

After the endpoint receives the request, it will return a response containing the request's message ID.

```
{
    "messageId": 1590693842777
}
```

This message ID may be used in a GET request to *http://localhost:8080/message/1590693842777* to receive the completed payload.

```
{
    "type": "UPDATE_CONFIGURATION",
    "metaData": {
        "originalSource": "source",
        "dateProcessed": "2020-05-28T19:24:03.843Z",
        "dateSent": null,
        "messageId": 1590693842777
    },
    "message": {
        "className": "org.al.priv.ce.messages.payloads.ConfigurationUpdatedPayload",
        "changed": true
    }
}
```


