# uber4freefood
## Users Api
###### Postgres set up
Edit the file in `/models/base.go` on line `28` to set local password and user before running.
###### Running
Run `go run main.go`
###### Api Routes
`:*port/api/user/login`
method `post`
body `json`
`{"email": "", "password": ""}`

`:*port/api/user/login`
method `post`
body `json`
`{"email": "", "firstname": "", "lastname": "", "phonenumber": "", "usertype": "", "profilepicname": "","password": ""}`

###### Response
body 
```
{
    "account": {
        "ID": ,
        "CreatedAt": "",
        "UpdatedAt": "",
        "DeletedAt": ,
        "email": "",
        "firstname": "",
        "lastname": "",
        "phonenumber": "",
        "usertype": "",
        "profilepicname": "",
        "password": "",
        "token": ""
    },
    "message": "",
    "status": 
}
```
###### Token
Type : `JWT`

key: in `.env` file

body:
```
{
 alg: "HS256",
 typ: "JWT"
}.
{
 UserId: 
}.
```