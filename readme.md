
# EzyWallet

Our Digital Wallet Backend Demo Application showcases a simplified yet comprehensive implementation of a digital wallet management system. Developed as a demonstration project for a, this application highlights key features and functionalities relevant to digital wallet backend development.

#### Key Features:

* __User Registration and Authentication:__ Users can register securely and authenticate their identities to access the digital wallet functionalities. This feature demonstrates basic user management and authentication mechanisms.

* __Digital Wallet Creation:__ The application allows users to create digital wallets, generating unique wallet addresses for each user. This feature illustrates the process of creating and managing user accounts within the system.

* __Mock Fund Transfer:__ Users can simulate fund transfers between wallets within the demo environment. While not performing actual transactions, this feature demonstrates the flow of funds between digital wallets.

* __Transaction History:__ Users can view mock transaction histories to understand how transactions are recorded and displayed within the system. This feature showcases basic transaction management and history tracking capabilities.

* __Simple API Integration:__ The application provides simple API endpoints for basic functionalities, enabling developers to understand the principles of API integration in backend development.

* __Documentation and Code Structure:__ The project includes clear documentation and well-structured code, facilitating understanding and future development. This feature demonstrates the importance of clean coding practices and documentation in software development.
## Tech Stack

**Server:** Spring Boot, Spring Security, JWT, Spring Data JPA, MySQL, Redis, Kafka, Microservices


## Internal Flow Diagram

[Diagram](https://github.com/SumitDas2004/EzyWallet/blob/main/EzyWallet.pdf)


## User API Reference

#### Register User

```http
  POST /user/register
```

| Property | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `phone` | `String` | **Required, Unique** Length between 9-12|
| `email` | `String` | **Required, Unique** |
| `password` | `String` | **Required** |
| `name` | `String` | **Required**|
| `authorities` | `[String]` | **Required**. authorities. {USER} |

##### Example request: 
```
{
    "phone":"123456789",
    "email":"sumitdasofficial@gmail.com",
    "name":"sumit das",
    "password":"hello world",
    "authorites":["USER"]
}
```
##### Example response: 
```
{
    "message": "User registration successful.",
    "status": 1,
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzdW1pdGRhc3Vub2ZmaWNpYWxAZ21haWwuY29tIiwiaWF0IjoxNzE1MTUzODU5LCJleHAiOjE3MTUxNTc0NTl9.2c-hDTELxCOO76u8a_hmocEMaHjS3z-ytIuNtJa6FTPJ8NjkU68OJp_QYcU9DTVAAH8zQMAPpJr1Wy30v_MAJg"
}
```

#### Login User

```http
  POST /user/login
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `email`   | `string` | **Required** |
| `password`| `string` | **Required** |

##### Example request: 
```
{
    "email":"sumitdasofficial@gmail.com",
    "password":"hello world"
}
```
##### Example response: 
```
{
    "message": "User login successful.",
    "status": 1,
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzdW1pdGRhc3Vub2ZmaWNpYWxAZ21haWwuY29tIiwiaWF0IjoxNzE1MTU0MDAyLCJleHAiOjE3MTUxNTc2MDJ9.SnH4zCQldf4NXMppclcMkIYv2XZVJcoqCrLPH4HA9ByDjqoVAppsvkfHlE57z7iu7lFn9av1G8XcnALXqz5BEQ"
}
```

#### Get User Details

```http
  GET /user/userDetails
```

| Header |Description                       |
| :-------- |:-------------------------------- |
| `Authorization`   | **Required** Starts with "Bearer ".|

##### Example response: 
```
{
    "data": {
        "id": "U100000",
        "email": "sumitdasofficial@gmail.com",
        "phone": "123456789",
        "name": "sumit das",
        "createdAt": "2024-05-08T07:37:38.712+00:00",
        "updatedAt": "2024-05-08T07:37:38.712+00:00"
    },
    "message": "Success",
    "status": 1
}
```


## Transaction API Reference

#### Initiate Transaction

```http
  POST /transaction/initiate
```

| Header |Description                       |
| :-------- |:-------------------------------- |
| `Authorization`   | **Required** Starts with "Bearer ".|

| Property | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `receiver` | `String` | **Required** Receiver's phone number|
| `amount` | `String` | **Required** |
| `purpose` | `String` | Purpose of the transaction |

##### Example request: 
```
{
    "receiver": "123456789",
    "amount": "100",
    "purpose": "Hello World"
}
```
##### Example response: 
```
{
    "message": "Successfully sent ₹10.0 to 123456789",
    "status": 1
}
```

#### Get User Details

```http
  GET /transaction/getTransactions?offset=0&size=3
```

| Header |Description                       |
| :-------- |:-------------------------------- |
| `Authorization`   | **Required** Starts with "Bearer ".|

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `offset` | `String` | **Required** Page offset|
| `size` | `String` | **Required** Page size|

#### Example response: 
```
{
    "data": [
        {
            "id": 8,
            "sender": "123456789",
            "receiver": "123456789",
            "amount": 10.0,
            "status": "FAILED",
            "createdOn": 1715157287287,
            "updatedOn": 1715157287287,
            "purpose": "Hello World"
        },
        {
            "id": 7,
            "sender": "123456789",
            "receiver": "123456789",
            "amount": 10.0,
            "status": "SUCCESS",
            "createdOn": 1715157269022,
            "updatedOn": 1715157269022,
            "purpose": "Hello World"
        },
        {
            "id": 6,
            "sender": "123456789",
            "receiver": "123456789",
            "amount": 10.0,
            "status": "SUCCESS",
            "createdOn": 1715157267925,
            "updatedOn": 1715157267925,
            "purpose": "Hello World"
        }
    ],
    "message": "Success.",
    "status": 1
}
```


## Wallet API Reference

#### create wallet

```http
  POST /wallet/create
```

| Header |Description                       |
| :-------- |:-------------------------------- |
| `Authorization`   | **Required** Starts with "Bearer ".|

##### Example response: 
```
{
    "message": "Wallet created successfully.",
    "status": 1
}
```

#### Get Wallet Details

```http
  GET /wallet/getWallet
```

| Header |Description                       |
| :-------- |:-------------------------------- |
| `Authorization`   | **Required** Starts with "Bearer ".|

#### Example response: 
```
{
    "data": {
        "id": 1,
        "balance": 450.0,
        "phone": "123456789",
        "createdAt": "2024-05-08T08:00:33.357+00:00",
        "updatedAt": "2024-05-08T08:34:28.950+00:00"
    },
    "message": "Success",
    "status": 1
}
```

#### Recharge Wallet

```http
  PUT /wallet/recharge/{amount}
```

#### Example response: 
```
{
    "message": "Recharge successful.",
    "status": 1
}
```
