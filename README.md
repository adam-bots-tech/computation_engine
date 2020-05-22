This is a message-based computation engine.

List of commands to get all the docker images running locally

docker network create --driver bridge ce_network

docker run --name ce_cassandra --net ce_network -v /Users/adamlong/eclipse-workspace/data:/var/lib/cassandra -d cassandra:3.11.6

docker run -v /Users/adamlong/eclipse-workspace/rabbitmq:/var/lib/rabbitmq -d --net ce_network --name ce_rabbit rabbitmq:3

docker build -f Dockerfile -t ce_endpoint computation_engine_endpoint

docker run -p 8080:8080 computation_engine_endpoint/ce_endpoint --name ce_endpoint --net ce_network
