# uber4freefood
## Food Api

###### Running
Clone files into go src directory
run `docker-compose up`

###### port `*:8080`

###### Api Routes
`GET    /food`
This return all the records from the table
```
{
    "data": [
        {
            "id": 1,
            "foodName": "",
            "dietType": "",
            "description": "",
            "specialIngridients": "",
            "serving": "",
            "SpecialNote": "",
            "foodImageId": "",
            "locationLat": "",
            "locationLong": "",
            "angelUserID": "",
	        "humanUserID": "",
            "status": "",
	        "CreatedAt": "",
	        "UpdatedAt": "",
	        "DeletedAt": ""
        }
    ]
}
```

`POST   /food`
This is used to create a record
```
{
    "data": [
        {
            "foodName": "",
            "dietType": "",
            "description": "",
            "specialIngridients": "",
            "serving": "",
            "SpecialNote": "",
            "foodImageId": "",
            "locationLat": "",
            "locationLong": "",
            "angelUserID": "",
	        "humanUserID": "",
            "status": ""
        }
    ]
}
```
`GET    /food/:id`
return specific record from given id
```
{
    "data": [
        {
            "foodName": "",
            "dietType": "",
            "description": "",
            "specialIngridients": "",
            "serving": "",
            "SpecialNote": "",
            "foodImageId": "",
            "locationLat": "",
            "locationLong": "",
            "angelUserID": "",
	        "humanUserID": "",
            "status": ""
        }
    ]
}
```
PATCH  /food/:id
Edit staus for food
`{"Status": ""}`
returns
```
{
    "data": [
        {
            "id": 1,
            "foodName": "",
            "dietType": "",
            "description": "",
            "specialIngridients": "",
            "serving": "",
            "SpecialNote": "",
            "foodImageId": "",
            "locationLat": "",
            "locationLong": "",
            "angelUserID": "",
	        "humanUserID": "",
            "status": ""
        }
    ]
}
```
`DELETE /food/:id`
delete specified record
```
{
    "data": true
}
```