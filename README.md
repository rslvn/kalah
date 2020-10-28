# kalah

[![CircleCI](https://circleci.com/gh/rslvn/kalah-game.svg?style=svg)](https://circleci.com/gh/rslvn/kalah-game)
[![Coverage Code](https://sonarcloud.io/api/project_badges/measure?project=rslvn_kalah-game&metric=coverage)](https://sonarcloud.io/dashboard?id=rslvn_kalah-game)
[![Lines Of Code](https://sonarcloud.io/api/project_badges/measure?project=rslvn_kalah-game&metric=ncloc)](https://sonarcloud.io/dashboard?id=rslvn_kalah-game)
[![Technical Debit](https://sonarcloud.io/api/project_badges/measure?project=rslvn_kalah-game&metric=sqale_index)](https://sonarcloud.io/dashboard?id=rslvn_kalah-game)


6 pits 6 stones kalah game implementation.

[What is kalah?](https://en.wikipedia.org/wiki/Kalah)

# implementation
* A spring-boot application
* Gradle
* lombok
* pojo-tester
* [repository](/src/main/java/com/example/kalah/repository) is a dummy repository. It should be replaced with relational a database or nosql solution
* Test coverage `%99` `274/276`
    * Unit and Integration test
    * Many functional test implemented in [KalahBoardServiceTest](/src/test/java/com/example/kalah/service/KalahBoardServiceTest.java)

# build

```
gradle clean build
```

# run

## docker
> `docker` and `docker-compose` installations are necessary
```
docker-compose build
docker-compose up -d
```

## gradle

```
gradle bootRun
```

# test

## /games

```
curl --header "Content-Type: application/json" \
     --request POST \
     http://localhost:8080/games
```

## /games/{gameId}/pits/{pitId}

> use `id` field value from previous request's response as `gameId` 

```
curl --header "Content-Type: application/json" \
     --request PUT \
     http://localhost:8080/games/<id>/pits/5
```
