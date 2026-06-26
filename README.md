# Alert Hub MST – Microservices Project

Alert Hub is a microservices-based system designed to monitor project management platforms such as Jira, ClickUp, and GitHub, evaluate metrics and conditions, and send alerts to project managers by Email or SMS.

The system was developed as part of the MST course project.

## Project Architecture

The system is built using 10 Spring Boot microservices:

1. **User Service** – manages users and roles.
2. **Security Service** – handles user permissions and access checks.
3. **Action Service** – manages alert actions, scheduling rules, and notification jobs.
4. **Metric Service** – manages metric definitions and thresholds.
5. **Loader Service** – stores platform information from external systems.
6. **Evaluation Service** – evaluates platform data and aggregates metrics.
7. **Processor Service** – consumes action jobs, evaluates conditions, and publishes notification messages.
8. **Sender Email Service** – consumes email messages and sends email notifications.
9. **Sender SMS Service** – consumes SMS messages and sends SMS notifications.
10. **Logger Service** – stores system logs using MongoDB.

## Technologies Used

* Java 17
* Spring Boot
* Spring Web
* Spring Data JPA
* MySQL
* MongoDB
* Apache Kafka
* Swagger / OpenAPI
* JUnit & Mockito
* Docker
* Docker Hub
* Kubernetes
* Minikube

## Kafka Integration

Apache Kafka was added as the messaging layer between the microservices.

Kafka is used to decouple the services and allow asynchronous communication between them.
Instead of calling each other directly using REST, services publish and consume messages through Kafka topics.

Kafka improves the system by:

* Reducing direct dependency between microservices
* Supporting asynchronous processing
* Improving reliability when a service is temporarily unavailable
* Allowing notification jobs to be processed through message queues
* Making the system easier to extend with more consumers in the future

### Kafka Topics

The system uses Kafka topics such as:

* `action-jobs` – used by Action Service to publish action IDs for processing.
* `email-topic` – used by Processor Service to publish email notification messages.
* `sms-topic` – used by Processor Service to publish SMS notification messages.

## Main Flow

1. Platform data is stored by the Loader Service.
2. Metrics are defined in the Metric Service.
3. Alert actions are configured in the Action Service.
4. The Action Service sends action jobs to Kafka topic `action-jobs`.
5. The Processor Service consumes action jobs from Kafka.
6. The Processor Service evaluates the action condition.
7. If the condition is satisfied, the Processor Service publishes a notification message to Kafka:

   * Email notifications are published to `email-topic`.
   * SMS notifications are published to `sms-topic`.
8. Sender Email Service consumes messages from `email-topic` and sends emails.
9. Sender SMS Service consumes messages from `sms-topic` and sends SMS messages.
10. Logger Service stores logs about the process.

## Kafka Flow Tested Successfully

The following end-to-end Kafka flow was tested successfully inside Kubernetes:

```text
action-service
   ↓
Kafka topic: action-jobs
   ↓
processor-service
   ↓
Kafka topic: email-topic
   ↓
sender-email-service
```

The test confirmed that:

* Action Service successfully published an action job to Kafka.
* Processor Service successfully consumed the action job.
* Processor Service processed the action and published an email notification to Kafka.
* Sender Email Service successfully consumed the email message and handled the notification.

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

Kafka was also deployed inside Kubernetes and exposed internally using:

```text
kafka-service:9092
```

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

To check logs for a service:

```bash
kubectl logs deployment/<service-name> --tail=120
```

Example:

```bash
kubectl logs deployment/processor-service --tail=120
```

## Important Kubernetes Notes

Inside Kubernetes, services communicate with Kafka using:

```text
kafka-service:9092
```

For local Minikube deployment, services connect to the local MySQL database using the Minikube host address configuration.

The system time inside Kubernetes pods uses UTC time, so scheduling tests must consider the time difference from the local machine time.

## Testing

The project includes:

* Service layer unit tests
* Exception handling validation
* Swagger API testing
* Docker container testing
* Kubernetes deployment testing
* Kafka producer and consumer testing
* End-to-end Kafka flow testing
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
* Kafka deployed on Kubernetes
* Kafka producer added to Action Service
* Kafka consumer and producer added to Processor Service
* Kafka consumer added to Sender Email Service
* All services deployed on Minikube
* End-to-end Kafka integration tested successfully
* Full notification flow tested successfully through Kubernetes

## Author

Mohamad Dar Yahya
Software Engineering
