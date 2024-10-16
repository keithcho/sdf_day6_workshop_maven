## Compiling

### Server
```
mvn compile exec:java -Dexec.mainClass="day6_workshop.Server" -Dexec.args="3000 cookie_file.txt"
```

### Client
```
mvn compile exec:java -Dexec.mainClass="day6_workshop.Client" -Dexec.args="localhost:3000"
```

## Packaging

```
mvn package
```

### Server
```
java -cp target/day6_workshop-1.0-SNAPSHOT.jar day6_workshop.Server 3000 cookie_file.txt
```

### Client
```
java -cp target/day6_workshop-1.0-SNAPSHOT.jar day6_workshop.Client localhost:3000
```
