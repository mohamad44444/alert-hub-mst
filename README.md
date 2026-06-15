# Alert Hub MST – Microservices Project

Alert Hub is a microservices-based system designed to monitor project management platforms such as Jira, ClickUp, and GitHub, evaluate metrics and conditions, and send alerts to project managers by Email or SMS.

The system was developed as part of the MST course project.

## Project Architecture

The system is built using 10 Spring Boot microservices:

1. **User Service** – manages users and roles.
2. **Security Service** – handles user permissions and access checks.
3. **Action Service** – manages alert actions and conditions.
4. **Metric Service** – manages metric definitions and thresholds.
5. **Loader Service** – stores platform information from external systems.
6. **Evaluation Service** – evaluates platform data and aggregates metrics.
7. **Processor Service** – processes actions, evaluates conditions, and triggers notifications.
8. **Sender Email Service** – sends email notifications.
9. **Sender SMS Service** – sends SMS notifications.
10. **Logger Service** – stores system logs using MongoDB.

## Technologies Used

* Java 17
* Spring Boot
* Spring Web
* Spring Data JPA
* MySQL
* MongoDB
* Swagger / OpenAPI
* JUnit & Mockito
* Docker
* Docker Hub
* Kubernetes
* Minikube

## Main Flow

1. Platform data is stored by the Loader Service.
2. Metrics are defined in the Metric Service.
3. Alert actions are configured in the Action Service.
4. The Processor Service evaluates the action condition.
5. If the condition is satisfied, the Processor triggers Email or SMS notification.
6. The Logger Service stores logs about the process.

## Docker

Each microservice contains its own Dockerfile and was built as a Docker image.

Docker Hub images:

* `mohamad1111/user-service:latest`
* `mohamad1111/metric-service:latest`
* `mohamad1111/action-service:latest`
* `mohamad1111/loader-service:latest`
* `mohamad1111/evaluation-service:latest`
* `mohamad1111/processor-service:latest`
* `mohamad1111/sender-email-service:latest`
* `mohamad1111/sender-sms-service:latest`
* `mohamad1111/security-service:latest`
* `mohamad1111/logger-service:latest`

## Kubernetes / Minikube Deployment

All services were deployed locally using Kubernetes with Minikube.

The Kubernetes YAML files are located in the `k8s` folder.

To apply the services:

```bash
kubectl apply -f k8s/
```

To check running pods:

```bash
kubectl get pods
```

To check services:

```bash
kubectl get services
```

## Testing

The project includes:

* Service layer unit tests
* Exception handling validation
* Swagger API testing
* Docker container testing
* Kubernetes deployment testing
* Integration testing through the Processor Service

## Project Status

Completed:

* 10 microservices implemented
* Swagger documentation added
* Exception handling added
* Service layer tests added
* Dockerfiles created
* Docker images pushed to Docker Hub
* Kubernetes YAML files created
* All services deployed on Minikube
* Integration between services tested successfully

## Author

Mohamad Dar Yahya
Software Engineering Student
