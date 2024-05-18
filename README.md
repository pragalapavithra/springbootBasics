A spring boot project with basic CRUD operation and postgres as database

QuestionController :
The class is flagged as a @RestController, meaning it is ready for use by Spring MVC to handle web requests

QuestionService : 
Service Provider class which has business logic

QuestionDao :
Interface which extends JpaRepository to provide CRUD operations

Question :
An entity class in java which respresents the table

Postgres Connection:
Configured in application.properties with url , username and password 

Unit tests:
MockMvc is used to perform http connections
MockBean will provide necessary bean for test 

