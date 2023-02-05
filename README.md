# cosmart

Library reservation system

Using java spring boot

Req

openjdk version 1.8.0_292
IDE intelij


How to run 

Run project on SpringAppApplication

List api

Bellow is list api. use your local url and place on {base_url}  when run this api

Get books list

curl -L -X GET '{base_url}/api/book?subject=Interpersonal%20relations' \
-H 'Content-Type: application/json' \
--data-raw ''

Subject is mandatory, please insert relevant subject, 
if book with subject is exists, it will return list of books, if not then it return empty list 

Response example

Success

{
    "data": [
        {
            "title": "Wuthering Heights",
            "author": "Emily Brontë",
            "editionNumber": "1746"
        },
        {
            "title": "Ethan Frome",
            "author": "Edith Wharton",
            "editionNumber": "718"
        }
    ],
    "errorMessage": null,
    "success": true
}

data : list of available book
errorMessage : if something happen on run time, error message will print cause of error
Success : Api result state 



Reserve book

curl -L -X POST '{base_url}/api/book' \
-H 'Content-Type: application/json' \
--data-raw '{
    "title" : "The Importance of Being Earnest",
    "author" : "Oscar Wilde",
    "editionNumber" : 389,
    "reservationDate" : "2023-2-6"
}'

All field is mandatory, please insert relevant date.
Reservation only can do minimum 1 day after reservation. 
This api will return reservation id that can help librarian when check reserve request

Response example

Success

{
    "data": {
        "reservationId": "ff5cb3f6-a090-4502-9e57-0d46732d032e",
        "title": "The Importance of Being Earnest",
        "author": "Oscar Wilde",
        "editionNumber": "389",
        "reservationDate": "2023-02-06",
        "complete": false
    },
    "errorMessage": null,
    "success": true
}

Failed
{
    "data": null,
    "errorMessage": "reservation date at least 1 day after now",
    "success": false
}

data : Request detail
errorMessage : if something happen on run time, error message will print cause of error
Success : Api result state 



