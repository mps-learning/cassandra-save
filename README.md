# cassandra-load

A sample project with reactive cassandra code

## Setup Instructions
I'm connecting to cassandra-cluster on my machine in docker, if you have some remote cassandra cluster you can connect to, feel free to skip following steps. 

### Step 1: 
Install Docker desktop if you do not have  

### Step 2: 
Run following commands to set up a cassandra docker cluster 

```shell
    docker run  --name cassandra-node-1 -d \
    -e CASSANDRA_CLUSTER_NAME="cluster_cassandra_docker"  \
    -e CASSANDRA_START_RPC=true \
    -e CASSANDRA_NUM_TOKENS="8"  \
    -e CASSANDRA_DC="dc1"  \
    -e CASSANDRA_RACK="rack1"  \
    -e CASSANDRA_ENDPOINT_SNITCH="GossipingPropertyFileSnitch" \
    -p 9042:9042  \
    -v /Users/manvendrasingh/Development/Volumes/Cassandra/data/node1:/var/lib/cassandra/data \
    cassandra:latest


    docker run --name cassandra-node-2 -d \
    -e CASSANDRA_CLUSTER_NAME="cluster_cassandra_docker" \
    -e CASSANDRA_START_RPC=true \
    -e CASSANDRA_NUM_TOKENS="8"  \
    -e CASSANDRA_DC="dc1"  \
    -e CASSANDRA_RACK="rack2”  \
    -e CASSANDRA_ENDPOINT_SNITCH="GossipingPropertyFileSnitch"  \
    -e CASSANDRA_SEEDS="$(docker inspect --format='{{ .NetworkSettings.IPAddress }}' cassandra-node-1)”  \
    -p 9043:9042  \
    -v /Users/manvendrasingh/Development/Volumes/Volumes/Cassandra/data/node2:/var/lib/cassandra/data \
    cassandra:latest
```

### Step 3
Build this maven application and run the `App.java` containing main method

### Step 4
If you are running casandra into docker as I am, 
you can verify the keyspace and table generation and data inserts with below commands

```shell
docker exec -it cassandra-node-1  /bin/bash
```

```sql
select keyspace_name,table_name 
from system_schema.tables 
where keyspace_name='mps_keyspace'; 

select * from mps_keyspace.mps_table ;
```