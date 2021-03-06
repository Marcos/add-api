## add-api
This is the [api](https://radd-api.herokuapp.com) to create characters of the [D&D App](https://add-ui.netlify.app/).
You can check the [openapi](https://radd-api.herokuapp.com/v3/api-docs/) definition for more details. 

## Requirements
To build and run locally you will need to have docker and docker-compose installed.

## Build
```shell script
docker-compose build
```

The application will be running at [http:localhost:8080](http:localhost:8080)

## Run
```shell script
docker-compose up
```

## Deploy
This project is deployed on Heroku. To deploy a new version, you need to push a new commit to master.

## Badges
[![CircleCI](https://circleci.com/gh/Marcos/add-api.svg?style=svg)](https://circleci.com/gh/Marcos/add-api)
