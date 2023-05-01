### README

The Invoice storage project uses SpringBoot, Postgres and Kafka to perform the CRUD operations on the invoice JSONs.<br>
### Build Instructions

- Update the `spring.datasource.url` in application.properties in `resources` folder with the postgres db url, default is `jdbc:postgresql://127.0.0.1:5432/postgres`
- Update the `spring.datasource.username` and `spring.datasource.password` with postgres username and password, <br> defaults are `test` and `test@1234` respectively
- Create a table in the postgres to store invoices by executing following script
    
        CREATE TABLE invoices (
            id SERIAL PRIMARY KEY NOT NULL,
            invoice_to VARCHAR (124) NOT NULL,
            address VARCHAR (512) NOT NULL,
            product VARCHAR (124) NOT NULL,
            price NUMERIC (10,2) NOT NULL,
            tax NUMERIC (10,2) NOT NULL,
            invoice_date Date  NOT NULL,
            invoice_number VARCHAR(124) NOT NULL UNIQUE
        );

- Update `spring.kafka.bootstrap-servers` with kafka url along with the port, default is `localhost:9092`
- Update `spring.kafka.topic.name` with a topic name to act as a message queue, default is `invoice_topic`
- Download and set JAVA_HOME to the java installation directory and for this project we are using JAVA 17
- Download maven and set `maven bin` path in PATH variable
- CD to project root directory and run the following command
    `mvn clean package spring-boot:repackage`
- The above command will create a folder `target` in project root.
- In the `target` folder **run** the following command to start the project
    `java -jar .\target\Storage-0.0.1-SNAPSHOT.jar`
- The above command will start a Spring Tomcat server on port 8080 and will create connectors to kafka and postgres
- To run application in dev mode execute the following command from the root folder `mvn spring-boot:run`
### Api Spec
Basic Invoice JSON

    {
    
    "to": "Aslam_basha",// String
    
    "address": "AP India",// String
    
    "product": "Books",//String
    
    "price": 157.50, //BIGDECIMAL with two decimal places precesion
    
    "tax": 28.00, //BIGDECIMAL with two decimal places precesion
    
    "invoiceDate": "2023-05-01", //date with YYYY-MM-DD format
    
    "invoiceNumber": "INV-0003" // String unique to invoice
    
    }
Application provides following endpoints

- POST `/invoice`: Will send the data to a kafka topic, asynchronously a consumer will read the data from the topic and write the data to the DB
  
        curl --location 'localhost:8080/invoice' \
        --header 'Content-Type: application/json' \
        --data '{
        "to": "User1",
        "address": "AP India",
        "product": "Books",
        "price": 157.5,
        "tax": 28.0,
        "invoiceDate": "2023-05-01",
        "invoiceNumber": "INV-0003"
        }'
        
        Response
        boolean
- GET `/invoice/{invoice_number}`: Will retrieve the respective invoice with the invoice number

        curl --location 'localhost:8080/invoice/INV-0003'

        Response
        {
        "to": "User1",
        "address": "AP India",
        "product": "Books",
        "price": 157.50,
        "tax": 28.00,
        "invoiceDate": "2023-05-01",
        "invoiceNumber": "INV-0003"
        }
- PUT `/invoice`: Will update the invoice in db

        curl --location --request PUT 'localhost:8080/invoice' \
        --header 'Content-Type: application/json' \
        --data '{
        "to": "User1",
        "address": "AP India",
        "product": "pen",
        "price": 157.5,
        "tax": 20.0,
        "invoiceDate": "2023-05-01",
        "invoiceNumber": "INV-0001"
        }'

        Response
        {
        "to": "User1",
        "address": "AP India",
        "product": "pen",
        "price": 157.5,
        "tax": 20.0,
        "invoiceDate": "2023-05-01",
        "invoiceNumber": "INV-0001"
        }
- DELETE `/invoice/{invoice_number}`: Deletes the Invoice if exists

        curl --location --request DELETE 'localhost:8080/invoice/INV-0001'

        Response
        {
        "to": "USER1",
        "address": "AP India",
        "product": "pen",
        "price": 157.50,
        "tax": 20.00,
        "invoiceDate": "2023-05-01",
        "invoiceNumber": "INV-0001"
        }
- GET `/invoice`: Will retrieve all the invoices

        Response
        [
        {
        "to": "USER2",
        "address": "AP India",
        "product": "pen",
        "price": 157.50,
        "tax": 20.00,
        "invoiceDate": "2023-05-01",
        "invoiceNumber": "INV-0001"
        },
        {
        "to": "USER1",
        "address": "AP India",
        "product": "Books",
        "price": 157.50,
        "tax": 28.00,
        "invoiceDate": "2023-05-01",
        "invoiceNumber": "INV-0003"
        }
        ]
Access the above end points with respective data as mentioned above to test.