# Voter
Voting application for AT consulting

How to run 
----------

### Prerequisites

The following software should be installed on your machine:

```
* Java 8 and Maven.
* API testing tool (postman, soapUi, ect...)
```


This app is powered by oauth 2.0, so you will need an access token before accessing the api.<br>
<br>
To get token run the app, set authorization type as basic and add following credentials:<br>
<br>
<b>Username: </b> user<br>
<b>Password: </b> password<br>
<br>
To authorize as<b>user</b>use the following link on your client:


```
http://localhost:8080/oauth/token?grant_type=password&username=user&password=user
```
To authorize as<b>admin</b>use this:
```
http://localhost:8080/oauth/token?grant_type=password&username=admin&password=admin
```


##User commands
List of commands available for the user

###Get all restaurants
Returns all restaurants saved in database
 
 * **URL**
 
   /user/restaurants
 
 * **Method:**
 
   `GET`
   
 * **Success Response:**
 
   * **Code:** 200 <br />
     **Content:** `{ [
                         {
                             "name": "KFC",
                             "score": 0,
                             "id": 1
                         },
                         {
                             "name": "McDonalds",
                             "score": 0,
                             "id": 2
                         }
                     ] }`
  
 * **Error Response:**
 
   * **Code:** 400 BAD REQUEST <br />
     **Content:** `{ error : "Error fetching all restaurants" }`
 

###Get restaurant's menu
Returns menu for the requested restaurant
 
 * **URL**
 
   /user/restaurant/menu?id=1
   
   **Required:**
       
       `id=[integer]
 
 * **Method:**
 
   `GET`
   
   
 * **Success Response:**
 
   * **Code:** 200 <br />
     **Content:** `{ [
                         {
                             "name": "Rice",
                             "id": 1,
                             "price": 200
                         },
                         {
                             "name": "Meat",
                             "id": 2,
                             "price": 300
                         }
                     ] }`
  
 * **Error Response:**
 
   * **Code:** 404 NOT FOUND <br />
     **Content:** `{ error : "Menu for restaurant: ID is not found" }`
     

###Vote for restaurant

 * **URL**
 
   /user/restaurant/vote?id=1

  **Required:**
       
       `id=[integer]`(id of the restaurant)
 
 * **Method:**
 
   POST
   
 * **Success Response:**
 
   * **Code:** 200 <br />
  
 * **Error Response:**
 
   * **Code:** 400 BAD REQUEST <br />
     **Content:** `{ error : "Something went wrong!" }`
     
