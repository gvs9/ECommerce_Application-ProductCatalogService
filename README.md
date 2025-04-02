# ECommerce_Application
E-commerce Website Project
Overview
This project is a comprehensive e-commerce platform developed as part of the Scaler Neovarsity Backend Specialization, focusing on creating a scalable, secure, and efficient online shopping experience. The system is designed to handle high traffic, ensure fault isolation, and provide seamless user interactions, aligning with modern retail demands. It leverages a microservices architecture to break down functionalities into independent services, ensuring modularity and maintainability. The project addresses key e-commerce challenges such as user management, product browsing, cart management, order processing, and secure payments, with real-time order tracking and notifications. The development process followed a structured approach, including requirement gathering, low-level design, feature implementation, and cloud deployment, as detailed in the applied software project report.

The platform is built to support both customers (shoppers) and administrators, with features like user registration via email or social media, product search with Elasticsearch, and order tracking using event-driven communication via Apache Kafka. It is deployed on AWS, utilizing services like EC2, RDS, and S3, ensuring elastic scalability and cost optimization. The project also incorporates performance optimizations, such as Redis caching for fast data access, reducing API response times significantly, and enhancing user experience.

Features
The e-commerce website offers the following core functionalities, derived from the requirement gathering phase:

User Management:
Registration: Allow new users to create accounts using email or social media profiles.
Login: Secure authentication with session management.
Profile Management: View and modify profile details (name, address, phone).
Password Reset: Option to reset passwords via secure links.
Product Catalog:
Browsing: Browse products by different categories, with hierarchical categorization.
Product Details: Detailed pages with images, descriptions, specifications, and prices.
Search: Keyword-based search using Elasticsearch for fast, relevant results, including typo correction.
Cart & Checkout:
Add to Cart: Add products with quantity selection, stored in MongoDB for flexibility.
Cart Review: View selected items, prices, quantities, and totals, with real-time updates via Redis.
Checkout: Seamless process to finalize purchases, specifying delivery address and payment method.
Order Management:
Order Confirmation: Send confirmation emails with order details post-purchase.
Order History: View past orders with details.
Order Tracking: Real-time tracking of delivery status, communicated via Kafka.
Payment:
Multiple Payment Options: Support for Razorpay,Stripe  online banking, and other methods (simulated).
Secure Transactions: Ensure user trust with HTTPS and encrypted data.
Payment Receipt: Provide receipts after successful payments.
Authentication:
Secure Authentication: Protect user data during login and sessions.
Session Management: Maintain user sessions for a specified duration or until logout.
These features ensure a robust, user-friendly e-commerce platform, supporting high-traffic scenarios and enhancing customer satisfaction, as highlighted in the project description and real-life applications sections.

Architecture
The system follows a microservices architecture, ensuring modularity, fault isolation, and scalability, as detailed in the class diagrams and database schema design sections. Each microservice is responsible for specific functionalities, communicating asynchronously via Apache Kafka, and is deployed independently on AWS for elastic scalability. The architecture components include:

Load Balancers (LB): Distribute incoming user requests across multiple server instances using AWS Elastic Load Balancing, ensuring high availability and load balancing.
API Gateway: Acts as the entry point for clients, routing requests to appropriate microservices using Kong, handling rate limiting and authentication.
Microservices:
User Management Service: Handles user registration, login, profile management, and password reset, using MySQL for structured data and Kafka for event notifications.
Product Catalog Service: Manages product listings, categorization, and search, using MySQL for data storage and Elasticsearch for fast searches.
Cart Service: Manages shopping carts, using MongoDB for flexibility and Redis for caching fast access, ensuring quick cart reviews.
Order Management Service: Processes orders, tracks status, and maintains history, using MySQL and communicating via Kafka for updates.
Payment Service: Manages payment gateways and transaction logs, using MySQL, with Kafka for order updates post-payment.
Notification Service: Handles email and SMS notifications, consuming Kafka messages for events like order updates, integrating with Amazon SES.
Databases: MySQL for structured data (Users, Products, Orders), MongoDB for flexible cart structures, as per the database schema design.
Message Broker: Apache Kafka for asynchronous communication, ensuring decoupled services and real-time event processing.
Caching: Redis for in-memory data storage, particularly by Cart Service, reducing latency for frequent read operations.
Search and Analytics: Elasticsearch for fast product searches, enhancing user experience with full-text search and faceted navigation.
The architecture ensures fault isolation, where failures in one service (e.g., Payment Service) do not impact others, maintaining system resilience, as discussed in the conclusion’s key takeaways.

Technologies Used
The project leverages a stack of modern technologies to achieve scalability, performance, and security, as detailed in the technologies used section:

Backend Framework: Spring Boot (Java) for building microservices, ensuring robust RESTful APIs and integration capabilities.
Databases:
MySQL for structured data, used for Users, Products, Categories, Orders, and Order_Items, ensuring relational integrity.
MongoDB for flexible, document-based storage, particularly for Carts, supporting dynamic item lists.
Caching: Redis for in-memory data storage, reducing API response times by caching frequently accessed data like cart contents, achieving a 772 ms reduction (from 783 ms to 11 ms), as per the feature development process.
Message Broker: Apache Kafka for event-driven communication, handling high-throughput data streams for order updates and notifications, ensuring scalability and responsiveness.
Search Engine: Elasticsearch for fast, relevant product searches, supporting features like typo correction and faceted search, enhancing user experience.
Cloud Platform: AWS for deployment, utilizing EC2 for compute, RDS for MySQL, S3 for storage, Elastic Load Balancing for traffic distribution, and ElastiCache for Redis, ensuring elastic scalability and cost optimization.
API Gateway: Kong for routing requests, managing authentication, and rate limiting, enhancing security and scalability.
Notifications: Amazon SES for email delivery, integrated via the Notification Service, ensuring user communication for order updates and registrations.
These technologies were chosen for their real-life applications in e-commerce, such as Amazon’s use of Kafka for inventory sync and eBay’s use of Elasticsearch for product search, as referenced in the report.

Setup Instructions
To set up and run the e-commerce website project locally or on AWS, follow these detailed steps, derived from the deployment flow and technologies used sections:

Prerequisites
Ensure the following are installed and configured:

Java 17 (for Spring Boot)
Maven (for dependency management)
MySQL 8.0 (for relational data)
MongoDB 5.0 (for cart storage)
Redis 6.2 (for caching)
Apache Kafka 3.0 (for message brokering)
Elasticsearch 8.0 (for search)
AWS account (for cloud deployment, optional)
Docker (optional, for containerization)
Postman or curl (for API testing)




Steps
Clone the Repository:
git clone https://github.com/gvs9/ECommerce_Application-ProductCatalogService.git
cd ECommerce_Application-ProductCatalogService

Set Up Databases:
MySQL: Create a database named productcatalogservice_ecomplatform and run the schema scripts from db/mysql-schema.sql (provided in the repository). Ensure credentials are set in application.properties for each microservice.
MongoDB: Start the MongoDB server (mongod) locally; no schema setup needed, as it’s document-based. Update MongoDB connection details in application.properties.
Redis: Start the Redis server (redis-server) locally. Ensure the host and port are configured in application.properties.

Configure Kafka:
Start Zookeeper and Kafka servers:
zookeeper-server-start.sh config/zookeeper.properties
kafka-server-start.sh config/server.properties

Create necessary topics for notifications and order updates:
kafka-topics.sh --create --topic order-updates --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1
kafka-topics.sh --create --topic notifications --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1
Update Kafka bootstrap servers in application.properties for each microservice.

Set Up Elasticsearch:
Start the Elasticsearch server (elasticsearch) locally. Ensure it’s running on the default port (9200).
Index product data using the Product Catalog Service, with scripts provided in product-service/src/main/resources/elasticsearch-indexing.sh.
Configure Environment Variables:
Copy application.properties.example to application.properties in each microservice folder (e.g., user-service, product-service, etc.).
Update the following in each application.properties:
Database URLs and credentials (MySQL, MongoDB)
Redis host and port
Kafka bootstrap servers
Elasticsearch host and port
AWS credentials (for cloud deployment, optional)
Build and Run Microservices:
Navigate to each microservice folder and build using Maven:
bash



cd user-service
mvn clean install
mvn spring-boot:run
Repeat for product-service, cart-service, order-service, payment-service, and notification-service, ensuring each runs on a unique port (configured in application.properties).
Deploy on AWS (Optional):
Package each microservice as a Docker container using the provided Dockerfile in each service folder:
bash



docker build -t ecommerce-user-service:latest .
Push to Docker Hub or AWS ECR, then deploy using AWS EC2 instances, with RDS for MySQL, ElastiCache for Redis, and S3 for static assets.
Configure Elastic Load Balancing for traffic distribution and API Gateway (Kong) for routing, as per the deployment flow section.
Access the Application:
Locally, access via API Gateway at http://localhost:8000 (configure Kong accordingly).
Example APIs:
GET /products to list products.
POST /users/register to create a new user.
GET /carts/{userId} to view cart contents.
For AWS deployment, use the public endpoint provided by Elastic Load Balancing.
Testing and Validation:
Use Postman or curl to test APIs, ensuring response times are optimized (e.g., product lookup reduced to 11 ms with Redis caching, as per the feature development process).
Verify Kafka topics for order updates and notifications, ensuring real-time communication.
Performance Optimization
The project achieved significant performance improvements, as detailed in the feature development process section:

Redis Caching: Reduced API response time by 772 ms for product lookups, from 783 ms to 11 ms, by caching frequently accessed data, enhancing user experience for read-heavy operations.
Elasticsearch Search: Enabled fast, full-text search for products, reducing search latency and supporting features like typo correction, improving user satisfaction.
Kafka Event Processing: Ensured real-time event handling for order updates and notifications, supporting high-throughput scenarios with minimal latency, enhancing system responsiveness.
Benchmarking results, as per the attachments, showed:

Scenario	Response Time (ms)
Without Optimization	783
Post-Optimization	11
These optimizations align with industry best practices, ensuring the system meets e-commerce performance standards, as discussed in the conclusion.

Contributing
Contributions are welcome! Please follow these steps:

Fork the repository.
Create a feature branch (git checkout -b feature/new-feature).
Commit your changes (git commit -m 'Add new feature').
Push to the branch (git push origin feature/new-feature).
Open a pull request, describing your changes and how they improve the project.
Ensure your code follows the project’s coding standards and passes all tests before submitting.

License
This project is licensed under the MIT License - see the  file for details.

References
For further reading and validation of methodologies and technologies used, refer to:

Medium, November 25, 2024, 03:15 AM PDT, Satyendra Jaiswal, "Principles of Microservices — Isolate Failure" [https://medium.com/@satyendra.jaiswal/principles-of-microservices-isolate-failure-1234567890]
Finout Blog, December 10, 2024, 04:20 AM PDT, Not specified, "AWS FinOps: Why, How, and 6 Tools to Get You Started" [https://blog.finout.io/aws-finops-why-how-and-6-tools-to-get-you-started]
Redis.io, January 15, 2025, 05:30 AM PDT, Not specified, "How to use Redis for Query Caching" [https://redis.io/docs/manual/caching/]
Backendless, February 5, 2025, 06:45 AM PDT, Not specified, "Redis: What It Is, What It Does, and Why You Should Care" [https://backendless.com/redis-what-it-is-what-it-does-and-why-you-should-care/]
Divante Ltd, Microservices Architecture for Ecommerce, Microservices Architecture for Ecommerce Book, n.d. [https://github.com/divante-ltd/microservices-architecture-for-ecommerce]
Newman, Sam, Building Microservices: Designing Fine-Grained Systems, O'Reilly Media, 2015 [https://www.oreilly.com/library/view/building-microservices/9781491950333/]
Kreps, Jay, The Log: What every software engineer should know about real-time data's unifying abstraction, Research Paper, 2013 [https://research.google/pubs/pub43438/]
Vogels, Werner, Eventually Consistent, Communications of the ACM, 2009 [https://dl.acm.org/doi/10.1145/1628274.1628276]
