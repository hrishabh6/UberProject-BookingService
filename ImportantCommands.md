## redis ip check 
```
hostname -I
```

## Redis start command in wsl
```
sudo service redis-server start
```

## Check if the redis is running
```
sudo service redis-server status
```

## Commands to run kafka
- Run the Zookeper first
```
cd \Kafka\kafka_2.12-3.9.1
```
```
.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
```

- Then run kafka in another terminal
```aiignore
cd \Kafka\kafka_2.12-3.9.1
```
```
.\bin\windows\kafka-server-start.bat .\config\server.properties
```