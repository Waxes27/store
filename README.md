# Store Application
The Store application keeps track of customers and orders in a database.

# Assumptions
This README assumes you're using a posix environment. It's possible to run this on Windows as well:
* Instead of `./gradlew` use `gradlew.bat`
* The syntax for creating the Docker container is different. You could also install PostgreSQL on bare metal if you prefer


# Prerequisites
This service assumes the presence of a postgresql 16.2 database server running on localhost:5433 (note the non-standard port)
It assumes a username and password `admin:admin` can be used.
It assumes there's already a database called `store`

You can start the PostgreSQL instance like this:
```shell
docker run -d \
  --name postgres \
  --restart always \
  -e POSTGRES_USER=admin \
  -e POSTGRES_PASSWORD=admin \
  -e POSTGRES_DB=store \
  -v postgres:/var/lib/postgresql/data \
  -p 5433:5432 \
  postgres:16.2 \
  postgres -c wal_level=logical
```

# Running the application

## Using Gradle
You should be able to run the service using
```shell
./gradlew bootRun
```

## Using Docker Compose
You can also run the application using Docker Compose, which will start both the application and the PostgreSQL database:
```shell
docker-compose up -d
```

The application uses Liquibase to migrate the schema. Some sample data is provided. You can create more data by reading the documentation in utils/README.md

# Data model
An order has an ID, a description, and is associated with the customer which made the order.
A customer has an ID, a name, and 0 or more orders.

# API
Two endpoints are provided:
   * /order
   * /customer

Each of them supports a POST and a GET. The data model is circular - a customer owns a number of orders, and that order necessarily refers back to the customer which owns it.
To avoid loops in the serializer, when writing out a Customer or an Order, they're mapped to CustomerDTO and OrderDTO which contain truncated versions of the dependent object - CustomerOrderDTO and OrderCustomerDTO respectively.

The API is documented in the OpenAPI file OpenAPI.yaml. Note that this spec includes part of one of the tasks below (the new /products endpoint)

# Tasks

1. Extend the order endpoint to find a specific order, by ID
2. Extend the customer endpoint to find customers based on a query string to match a substring of one of the words in their name
3. Users have complained that in production the GET endpoints can get very slow. The database is unfortunately not co-located with the application server, and there's high latency between the two. Identify if there are any optimisations that can improve performance
4. Add a new endpoint /products to model products which appear in an order:
      * A single order contains 1 or more products. 
      * A product has an ID and a description. 
      * Add a POST endpoint to create a product
      * Add a GET endpoint to return all products, and a specific product by ID
        * In both cases, also return a list of the order IDs which contain those products
      * Change the orders endpoint to return a list of products contained in the order

# CI/CD Pipeline

A CI/CD pipeline has been implemented using GitHub Actions. The pipeline:

1. Builds the application
2. Runs tests
3. Generates a test coverage report
4. Checks code style
5. Builds a Docker image
6. Pushes the Docker image to Docker Hub

The pipeline is defined in `.github/workflows/ci.yml`.

## Docker

The application has been containerized using Docker. The Dockerfile uses a multi-stage build approach to optimize the image size and build process:

1. The first stage builds the application using Gradle
2. The second stage creates a runtime image with only the necessary components

The Docker image includes a health check that verifies the application is running correctly.

To build the Docker image locally:
```shell
docker build -t store:latest .
```

To run the Docker image locally:
```shell
docker run -p 8080:8080 --name store-app store:latest
```

# Notes on the tasks
Assume that the project represents a production application.
Think carefully about the impact on performance when implementing your changes
The specifications of the tasks have been left deliberately vague. You will be required to exercise judgement about what to deliver - in a real world environment, you would clarify these points in refinement, but since this is a project to be completed without interaction, feel free to make assumptions - but be prepared to defend them when asked.
Feel free to refactor the codebase if necessary. Bad choices were deliberately made when creating this project.
