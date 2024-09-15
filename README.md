<h1 align="center">VOU</h1>

<p align="center">Monorepo, microservices backend for a marketing with real-time games platform.</p>
<p align="center">Java, Spring, Maven, Docker, AWS, MySQL, MongoDB, Redis, Kafka, Resilience4J, Firebase, Grafana stack.</p>

## System Architecture

![_Diagrams-Architecture.drawio.png](images/_Diagrams-Architecture.drawio.png)

## Resources

For local development, we use the `/resources/application.yml` of each service instead of making a separated config
server for simple demo.

For deployment with docker, we use `docker-compose.yml` to run our services as containers that can be used by
front-end team during development and for demo purposes.

## Java development setup

Our service is set up by Spring Boot using [Spring Initializr](https://start.spring.io/)

- Project: Maven
- Language version: Java (JDK 17)
- Spring boot version: 3.3.1

### Dependencies

Leverage a lot of spring framework library such as Lombok, Spring Boot DevTools, Spring Data JPA (Hibernate by default), Spring Web, Spring Websocket, etc...

For more details, can look at `application.yml` of each service

## AWS Services and Third-Party Libraries

- [AWS S3](https://aws.amazon.com/s3/): Utilized for storing static files, including audio and images.

- [AWS Polly](https://aws.amazon.com/polly/):
  Employs advanced deep learning technologies to convert text, such as quiz game questions, into lifelike speech.

- [Firebase Cloud Messaging (FCM)](https://firebase.google.com/products/cloud-messaging): Facilitates the delivery of notifications and messages to users across multiple platforms.

- [SpeedSMS](https://speedsms.vn/): Enables the rapid and efficient dispatch of SMS messages to a large number of recipients.

## Local deployment with IntelliJ

### Setting Up the Project with IntelliJ

1. Open IntelliJ.
2. Click on "Open" and navigate to the folder containing your Spring Boot project.
3. Go to `File > Project Structure > Project`.
4. Ensure the Project SDK is set to your installed JDK (preferably JDK 17).
5. Verify that your project uses the correct build tool (Maven).

### Running Services

1. Each service is a Maven project and needs to be run individually.
2. Open the first service, right-click the main class annotated with `@SpringBootApplication`, and select `Run 'Application'`.
3. Repeat the process for the main class of the second service by right-clicking and selecting `Run 'Application'`.
4. Note: Some services, such as sessions and notifications, rely on third-party integrations. These require specific environment variables and secret files, which are stored in the `.env` file. Please contact `@Fbin` for access to these credentials and configurations.

## Container deployment with Docker

### Prerequisites

- Docker installed on your local machine.
- Docker Compose installed on your local machine.

### Building Docker Images

Our docker images are built using the `Google Jib` plugin, which simplifies the process of building and pushing images to a registry.

1. Navigate to the root directory of each service.
2. Run the following Maven command to build the Docker image:

```sh
mvn compile jib:dockerBuild
```

### Running Services with Docker Compose

We use `docker-compose.yml` to manage and run our services as containers. This setup is used by the front-end team during development and for demo purposes.

1. Navigate to the root directory of the project where the `docker-compose.yml` file is located.
2. Run the following command to start all services:

```sh
docker-compose up
```

### Environment Variables

Some services require environment variables and secret files that are saved in the `.env` file. Ensure you have the correct `.env` file in the root directory before starting the services.

### Stopping Services

To stop the running services, use the following command:

```sh
docker-compose down
```

### Additional Notes

- Ensure that your Docker daemon is running before executing any Docker commands.
- For more details on configuring each service, refer to the `application.yml` file of each service.
