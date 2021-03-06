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
To authorize as <b>user</b> use the following link on your client:


```
http://localhost:8080/oauth/token?grant_type=password&username=user&password=user
```
To authorize as <b>admin</b> use this:
```
http://localhost:8080/oauth/token?grant_type=password&username=admin&password=admin
```


###Commands

**Examples of api commands**

<b>User commands</b><br><br>

Method | Request example | Response example
------ | --------------- | -----------------
`GET`  |  `http://localhost:8080/user/restaurants`  | `{"name":"McDonalds",   "score": 0,  "id": 1}`
`GET`  | `http://localhost:8080/user/restaurant/menu?id=1` | `{"name": "Rice",   "id": 1,  "price": 200}`
`POST` | `http://localhost:8080/user/restaurant/vote?id=1` | `{"status": OK}`

<br><b>Admin commands</b><br>

Method  | Request example | Request body      | Response example
------  | --------------- | ----------------- | -----------------
`POST`  | `hhttp://localhost:8080/admin/restaurant/update`  | `[{name":"McDonalds",   "score": 0,  "id": 1}]` | `"name": "McDonaldsUpdated",  "id": 1`
`PUT`   | `http://localhost:8080/admin/restaurant/create`   | `[{name": "Burger King", "score": 0,"id": 3}]`  | `"name": "Burger King"`
`PUT`   | `http://localhost:8080/admin/menu/update?id=1`    | `[{ "name": "Cheese",   "price": 200}]`         | `"name": "Cheese",  "id": 4, "price": 200"`

