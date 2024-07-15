# Visit Products

<br>

## Overview
In this project, we will see how to <mark>return the top 3 products visited by customers and update the list every 3 seconds.</mark> To achieve this, we use <strong>RScoredSortedSetReactive</strong> for efficiently managing and accessing products based on their visit scores. Additionally, we utilize <strong>Sinks.Many</strong> to enable real-time updates, ensuring that the top products are refreshed and delivered to users seamlessly.
 
 <br>
 
## Usages
- Spring WebFlux
- Redisson "Redis"
- Spring Data R2DBC
- Lombok
    
<br> 

## Architecture of the Project

 ### 1-src folder
   - Configration
   - Controller
   - Model
   - Repository
   - Service
   
### 2-Maven pom.xml
<br> 
    
```
<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-r2dbc</artifactId>
		</dependency>
		<dependency>
			<groupId>io.asyncer</groupId>
			<artifactId>r2dbc-mysql</artifactId>
			<scope>runtime</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-webflux</artifactId>
		</dependency>
		<dependency>
			<groupId>org.redisson</groupId>
			<artifactId>redisson-spring-boot-starter</artifactId>
			<version>3.16.6</version>
		</dependency>
		<dependency>
			<groupId>com.mysql</groupId>
			<artifactId>mysql-connector-j</artifactId>
			<scope>runtime</scope>
		</dependency>
		<dependency>
			<groupId>org.projectlombok</groupId>
			<artifactId>lombok</artifactId>
			<optional>true</optional>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>io.projectreactor</groupId>
			<artifactId>reactor-test</artifactId>
			<scope>test</scope>
		</dependency>
	</dependencies>
 ```

<br>

###### Output :star_struck:

https://github.com/SaraKhild/product-visitors/assets/67427643/8720166f-3341-455c-a8f2-a5b3f3bd325c





