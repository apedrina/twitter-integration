# Kafka and SpringBoot example #

+ Connect to the [Twitter Streaming API](https://dev.twitter.com/streaming/overview) using Java, Kafka, ActiveMQ, Postgres and SpringBoot.
## Pre-Requirements ##

* Java 15
* Docker
* Maven 3.6.3

You need create an App on twitter to use and integrate with twitter's API. See these links:

[Stackoverflow](https://stackoverflow.com/questions/1808855/getting-new-twitter-api-consumer-and-secret-keys)

[Twitter Developer Portal](https://developer.twitter.com/en/portal/projects-and-apps)

When you've created your twitter App get the either "consumerKey" and "consumerSecret" and fill the "twitter-producer/application.yml"
file. See the code snippet:

```yml
twitter:
  lookup:
    string: java
  oauth:
    consumerKey: <put_here_your_key>
    consumerSecret: <put_here_your_secret>
```

## Compile ##

```bash
cd <project_root>
mvn clean install

```
# Run #

## Infra ##

First we need up the infrastructure containers 

### Kafka ###

```bash
cd <project_root>/docker
docker-compose -f docker-compose-kafka.yml up

```
### Postgres ###

```bash
cd <project_root>/docker
docker-compose -f docker-compose-postgres.yml up

```
### ActiveMQ ###

Build the image:

```bash
cd <project_root>/docker/active
docker build .

```

Give a name to new image:

```bash
docker tag <IMAGE_ID> apedrina/activemq

```

Up the container:

```bash
cd <project_root>/docker
docker-compose -f docker-compose-active.yml up

```

## Producer ##

```bash
cd <project_root>/twitter-producer
mvn spring-boot:run

```
After executing the above command go to the authorization url, copy the code
go back to the console and paste the code.

## Consumer ##

```bash
cd <project_root>/twitter-consumer
mvn spring-boot:run

```

# Run with Docker #

Even tough we've either Dockerfile and docker-compose files to both twitter-consumer and twitter-producer 
(eg. twitter-producer/Dockerfile ) this feature isn't work yet, but I'll continue working with it and on the
next commits it will be done.

