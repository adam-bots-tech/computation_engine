This is a message-based computation engine.

List of commands to get all the docker images running locally

docker network create --driver bridge ce_network

docker run --name ce_cassandra --net ce_network -v /Users/adamlong/eclipse-workspace/data:/var/lib/cassandra -d cassandra:3.11.6

docker run -v /Users/adamlong/eclipse-workspace/rabbitmq:/var/lib/rabbitmq -d --net ce_network --name ce_rabbit rabbitmq:3

docker run --name ce_postgres --net ce_network -e POSTGRES_PASSWORD=root -e PGDATA=/var/lib/postgresql/data/pgdata -v /Users/adamlong/eclipse-workspace/postgres:/var/lib/postgresql/data -d postgres

docker build -f computation_engine_endpoint/Dockerfile -t computation_engine_endpoint/ce_endpoint computation_engine_endpoint

docker run --name ce_endpoint --net ce_network -p 8080:8080 computation_engine_endpoint/ce_endpoint

docker build -f computation_engine_worker/Dockerfile -t computation_engine_worker/ce_worker computation_engine_worker

docker run --name ce_worker --net ce_network computation_engine_worker/ce_worker
