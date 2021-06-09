# Microservices : 
	Style of developing application as a multiple small deployable units 
each unit have well thought boundaries and is cloud enabled. There will be central managment modules and these units can communicate with each other.
	
# Advantges : 
	- we can write each microservices in different language or framework as per suitable and still can combine those to form one application.
	- Dynamic scaling for each service as per load
	- easy to release new features 
	- Failure of single process doesn't affect whole system
	
# spring microservices different modules (or spring cloud projects) used and usage
	- spring boot : write api and business logic, security, logging
		- spring web 
		- spring data jpa
		- spring security 
		- spring Actuator 
		- Unit testing 
		- Log4j
		- spring mail
		- spring boot dev tools
		- swagger UI 
		
	
	- Spring cloud configuration server : manages configurations for all microservices
		can fetch config from git or local file system.
	- Eureka naming server : let microservices register themselves
	- Ribbon : for load balancing 
	- RestTemplate/Feign : to call another service 
	- Spring cloud Sleuth & Zipkin : slueth is used to assign id to service req and zipkin server is used to trace request across multiple components
	- Spring Clous API Gateway / Netflix zuul API gateway : provides routing 
	- Spring cloud stream : to connect to kafka/rabbit MQ 
	
