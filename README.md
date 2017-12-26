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

List of commands available for the user:<br>

###Commands

**There are two REST interfaces**

    REST uri     |    HTTP method | Response format  | Accept
    ------------ | -------------- | ---------------  | ---------------
<domain>/rest/tasks/new` | GET | `application/json` | key-value pairs with keys: `domain` and `keyword`
<domain>/rest/tasks/{id}`| GET | `application/json` | path variable, where `{id}` is integer number  

Request example | Response example
--------------- | -----------------
`http://localhost:8080/rest/tasks/new?domain=example.com&keyword=example`  | `{"id":1}`
`http://localhost:8080/rest/tasks/1` | `{"id":1,"domain":"http://example.com", "keyword":"example","title":"Example Domain","amountWordsInBody":30,"density":{"h1":50,"title":50,"body":6}}`
`http://localhost:8080/rest/tasks/42` | `{"status":-1}`
