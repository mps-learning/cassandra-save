# cassandra-load

A sample project to test the reactive select and inert in cassandra

## Setup Instructions
I'm connecting to cassandra-cluster on my machine in docker 
Feel free to skip following steps, if you have some remote cassandra cluster you can connect to, then feel free to skip following steps)
Install Docker desktop if you do not have  

### Step 1: 
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

