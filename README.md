# Web Quiz REST API

This engine implements a web quiz REST API service with some available request features. 


The project was build with [Spring boot](https://spring.io/) framework, [Hibernate](https://hibernate.org/) with [H2](https://www.h2database.com/) databases, 
and the [Swagger](https://swagger.io/) framework that will make it easier to understand all available quiz requests.

## Description

The web service engine provides quiz management capabilities by sending GET, POST and DELETE requests to specific web addresses.
 The web service also implements [basic HTTP authentication](https://en.wikipedia.org/wiki/Basic_access_authentication), 
 which requires that each user should be registered. Each request in terms of the specifics of the basic HTTP authentication, 
 and the task conditions must be followed by a successful log on.
 
 To register, the User should go to http://localhost:8080/api/register and send his/her email and password to the server. 
 The information will be validated, and the new User will be added to the database, which means that the registration has been successfully completed.
The User data is kept in an [H2 database](https://www.h2database.com/), the password is encrypted with the [BCrypt](https://en.wikipedia.org/wiki/Bcrypt) function.

Each registered User can add, decide and delete quizzes. All quizzes store information about the user who created them, 
and each User also can get a list of all available quizzes. The database also stores information about each of 
the quizzes solved by a certain user - Quiz id and Timestamp. A User can solve quizzes created by other users, but he can only delete his own quiz.

[Database model](http://d.zaix.ru/nyAA.png)

The service supports the feature of sorting, paging and navigating by requested information, 
so the list of all quizzes is sorted by an id and divided into 10 quizzes per page. 
You can choose the necessary page to view and the method of sorting by adding the query parameters.

Example: 

`http://localhost:8080/api/quizzes?page=10` - *will get you to the 10th page of all available quizzes.*

## Launch Manual.

*To launch the program you should have installed [JRE](https://java.com/ru/download/) on your computer. Please install it if there are no exists.*

* Clone repository to your machine
* Open the root folder of the project
* Run the following command
``` 
$ java -jar build/libs/*.jar
```
* Wait until finished loading

> *The first launch of service creates a database in the <b>/build</b> directory of the project.*

* Then you should go to http://localhost:8080/swagger-ui.html to open a page 
provided by the Swagger framework with all available requests and methods of their processing.

>*Do not close the terminal - it will cause the shutdown of the service.*

## Available requests

### User registration

To register a user you should send a `POST` request to the address http://localhost:8080/api/register with JSON in the request body.
JSON must have the following format :
```json
{
    "email" : "example@gmail.com",
    "password" : "password"
}
```
If the registration was successful, you will get a `200 OK` code, and a notification in the response body.
If a user with such an email already exists, or the input format is not correct, 
you will receive a suitable notification, and an error code.

### Quiz creation

To create a quiz, you should send a `POST` request to http://localgost:8080/api/quizzes
 with JSON in the request body. JSON must have the following format :
```json
{
  "title": "The Java Logo",
  "text": "What is depicted on the Java logo?",
  "options": ["Robot","Tea leaf","Cup of coffee","Bug"],
  "answer": [2]
}
```

Where: 
* "title" - is a quiz title
* "text" - is a quiz text
* "options" - is an answer options
* "answer" - is an index/indexes of correct answer in an array with answer choices


If the registration was successful, you will get a `200 OK` code, and a new quiz in the response body.
>*You may see that the quiz has got an id and now the answers are not available for viewing.*

If the input format is not correct, you will receive a suitable notification, and an error code.

### Solving quiz

To solve the quiz you should to send a `POST` request with JSON to http://localhost:8080/api/quizzes/{id}/solve
 by choosing the id of the necessary quiz.  JSON must have the following format :
 ```json
 {
   "answer": [2]
 }
 ```
Where "answer" is an index/indexes of correct answer in an array with answer choices.

In return, you will get the relevant JSON in the response body, and the status code.

### Quiz Deletion

To delete the quiz you should to send `DELETE` request to http://localhost:8080/api/quizzes/{id}/solve
 by choosing the id of the necessary quiz.
 
 You should be the creator of the selected quiz to be allowed to delete it. 
 If you try to delete a quiz created by another user, you will get an error code `403 FORBIDDEN`
 If the quiz exists, and you are the creator of the quiz, 
 you will get the status code `204 NO CONTENT` and the notification about successful deletion in the response body.
 
### Get all quizzes

If you want to get a list of all available quizzes, 
you should send a GET request to http://localhost:8080/api/quizzes
with the number of the required page as the request parameters.

`http://localhost:8080/api/quizzes?page=1` - for get second page of quizzes list

You can also choose the sorting method and quantity of quizzes on the page by addition of necessary parameters.

`http://localhost:8080/api/quizzes?page=1&size=3&sortedby=id` - 
for sort quizzes by the id and get only three quizzes per page.

In the response body you will get a JSON with Page object 
with various information ( current page, sorting method, etc.) and a list of all available quizzes.

### Get a quiz

You can get a certain quiz by its id by sending a GET request to http://localhost:8080/api/quizzes/{id}.
In the response body you will get a JSON with required quiz.

### Get all solved quizzes

To get a list of all the quizzes solved by the current 
user you should send a GET request to http://localhost:8080/api/quizzes/completed.

In the response body you will get a JSON with Page object with a list of all information of solved quizzes.

>* *It also features sorting and page navigation methods by adding parameters to the current URL.*
>* *The service provides the possibility of solving the same quiz several times and 
>so there may be several quizzes with the same id in the list.*

## Built with

* [Spring boot](https://spring.io/)
* [Hibernate](https://hibernate.org/)
* [H2 databases](https://www.h2database.com/) 
* [Gradle](https://gradle.org/)
* [Swagger](https://swagger.io/)

## Authors

[Nikita-prF](https://github.com/Nikita-prF)
