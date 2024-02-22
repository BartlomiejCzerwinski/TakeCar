# TakeCar
Android car rental application. An application connects people that want to earn money on renting their car with one's that looking for car.
Current version of application provides base stack of functionalities (some more functionalities are TODO)
An application is built using java with Android SDK. The database part of application is created using Firebase (Authentication, Realtime Database, Storage) 

## Table of functionalities
|          Functionality          |     Status    |
| ------------------------------- | ------------- |
|     Registration and sign up    |      Done     |
|           Adding cars           |      Done     |
|      Showing available cars     |      Done     |
|           Renting cars          |      Done     |
|     Car incomes calculation     |      Done     |
| Basic sorting of available cars |      Done     |
|         Editing car data        |      TODO     |
|         Car reservation         |      TODO     |
|     List of current rentals     |      TODO     |
|       History of rentals        |      TODO     |

## Registration and sign in
The system collects data of users in sake of cars rentals. Registered user can login into application and start renting cars.
The whole functionality is based on Firebase Authentication.

<p float="left">
<img src="https://private-user-images.githubusercontent.com/84719721/307100574-e0583dfc-c431-4697-9dd4-d4b9c26aeba2.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MDg2MjUzMTAsIm5iZiI6MTcwODYyNTAxMCwicGF0aCI6Ii84NDcxOTcyMS8zMDcxMDA1NzQtZTA1ODNkZmMtYzQzMS00Njk3LTlkZDQtZDRiOWMyNmFlYmEyLnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNDAyMjIlMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjQwMjIyVDE4MDMzMFomWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPWVjY2VhMzczODEyNjZjMGEzODEyMWE5OWZjMmM5YTlhYmM1MGYwNTdjNmFhYzRjZGJjM2NjYWZkNmNhODk1MWQmWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0JmFjdG9yX2lkPTAma2V5X2lkPTAmcmVwb19pZD0wIn0._5qbOLa5AeJwNHSaUhup9ejOzTx7wTTN3pAYoIKmSZs" width="315" height="612" style="margin-right: 20px;"/>
<img src="https://private-user-images.githubusercontent.com/84719721/307100579-458ee30f-f672-4dd6-9878-707b35c9c980.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MDg2MjUzMTAsIm5iZiI6MTcwODYyNTAxMCwicGF0aCI6Ii84NDcxOTcyMS8zMDcxMDA1NzktNDU4ZWUzMGYtZjY3Mi00ZGQ2LTk4NzgtNzA3YjM1YzljOTgwLnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNDAyMjIlMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjQwMjIyVDE4MDMzMFomWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPWJiZjQ0NWVkMDI2MzQzYTQwNzk5MDg0YzkwNGNjNzBlMWU3ZDgyNmUzYjAwZjhiNWFjNDMxMzIxNjBiOGUzOGUmWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0JmFjdG9yX2lkPTAma2V5X2lkPTAmcmVwb19pZD0wIn0.hMsYcQDzDukd2wY8ba2bUbpJLmTztlSG8JcWUBgfM0Q" width="315" height="612" style="margin-right: 20px;"/>
<img src="https://private-user-images.githubusercontent.com/84719721/307100570-311abbba-a8f0-4bf1-8765-816a50e0c39a.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MDg2MjUzMTAsIm5iZiI6MTcwODYyNTAxMCwicGF0aCI6Ii84NDcxOTcyMS8zMDcxMDA1NzAtMzExYWJiYmEtYThmMC00YmYxLTg3NjUtODE2YTUwZTBjMzlhLnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNDAyMjIlMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjQwMjIyVDE4MDMzMFomWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPWM1ZTI1OWJiZDJkZmNiZjliYTg5OTAwYjRjNDdkNjQyZTU3MTAwMTRjYTVhNWVkNzAxZjljNDcwODZjYTA5ZDEmWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0JmFjdG9yX2lkPTAma2V5X2lkPTAmcmVwb19pZD0wIn0.8Ed6uxo3dKz2Z_k9-Gzvkg2Vk6oyrTfAEu6TvpzWeLo" width="315" height="612"/>
</p>
