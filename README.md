# TakeCar
Android car rental application. An application connects people who want to earn money on renting their car with one's that looking for car.
The current version of the application provides a base stack of functionalities (some more functionalities are TODO).
An application is built using java with Android SDK. The database part of application is created using [Firebase](https://firebase.google.com/) (Authentication, Realtime Database, Storage) 

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
The system collects data of users for the sake of cars rentals. Registered users can log in to the application and start renting cars. 
The whole functionality is based on Firebase Authentication. 

<p float="left">
<img src="https://private-user-images.githubusercontent.com/84719721/307100574-e0583dfc-c431-4697-9dd4-d4b9c26aeba2.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MDk2MjMwNDMsIm5iZiI6MTcwOTYyMjc0MywicGF0aCI6Ii84NDcxOTcyMS8zMDcxMDA1NzQtZTA1ODNkZmMtYzQzMS00Njk3LTlkZDQtZDRiOWMyNmFlYmEyLnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNDAzMDUlMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjQwMzA1VDA3MTIyM1omWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPTc3ZGFmZjY4ZmEwMmYyMzRlMjA4ZTU1NjY4YjllNDIzMWY0MmNlNWZlOTRmMDg1YWZlYjdkNTNjN2RmMmRkMjgmWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0JmFjdG9yX2lkPTAma2V5X2lkPTAmcmVwb19pZD0wIn0.hiKpg4HO8QYrCYvHZQa_W-0nlCgf26-bo-z0j2TMSC8" width="231" height="500" style="margin-right: 20px;"/>
<img src="https://private-user-images.githubusercontent.com/84719721/307100570-311abbba-a8f0-4bf1-8765-816a50e0c39a.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MDk2MjMwNDMsIm5iZiI6MTcwOTYyMjc0MywicGF0aCI6Ii84NDcxOTcyMS8zMDcxMDA1NzAtMzExYWJiYmEtYThmMC00YmYxLTg3NjUtODE2YTUwZTBjMzlhLnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNDAzMDUlMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjQwMzA1VDA3MTIyM1omWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPTI0YTg1NjA0Mjk4OTY3ODE5NjU1MDMwNzViODBiYTMxMWEwNDNiMDMyOGVhYWViMDJjYjI0YmNjNzViOWI0MjMmWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0JmFjdG9yX2lkPTAma2V5X2lkPTAmcmVwb19pZD0wIn0.YClIdN3f6PNByuKe1OEhGtYwAMp5hRJDxSSzaaqW9Z4" width="231" height="500" style="margin-right: 20px;"/>
<img src="https://private-user-images.githubusercontent.com/84719721/307100579-458ee30f-f672-4dd6-9878-707b35c9c980.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MDk2MjMwNDMsIm5iZiI6MTcwOTYyMjc0MywicGF0aCI6Ii84NDcxOTcyMS8zMDcxMDA1NzktNDU4ZWUzMGYtZjY3Mi00ZGQ2LTk4NzgtNzA3YjM1YzljOTgwLnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNDAzMDUlMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjQwMzA1VDA3MTIyM1omWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPWVmNmY3YzRmMjQwNjFhNTQyZThiZjAxNzZkMDBlMTMzMjc5NjMzMGRmNmM1MWRhNTE1ZGE2ODRkNmViY2VmYjUmWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0JmFjdG9yX2lkPTAma2V5X2lkPTAmcmVwb19pZD0wIn0.RU850RBPWbix0I8CSvcceKWu073j47i_bcjzZtWzveU" width="231" height="500"/>
</p>

## Adding cars
After switching to RENT mode we can see preview of cars that we own. Click **ADD NEW CAR** button to load form for addnig a new car.

<p float="left">
<img src="https://private-user-images.githubusercontent.com/84719721/307106349-944fde3b-4dc2-4fa4-8112-b295396def50.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MDk2MjMwNDMsIm5iZiI6MTcwOTYyMjc0MywicGF0aCI6Ii84NDcxOTcyMS8zMDcxMDYzNDktOTQ0ZmRlM2ItNGRjMi00ZmE0LTgxMTItYjI5NTM5NmRlZjUwLnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNDAzMDUlMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjQwMzA1VDA3MTIyM1omWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPWVkOGQ5NjlkNTcwNmJjNTYzNzY5MWUxODEyZGE3ZTMzNWM3MjQxMTUyZDE5ZjgxOTc4NmE5YzBhYmQzYjMwY2YmWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0JmFjdG9yX2lkPTAma2V5X2lkPTAmcmVwb19pZD0wIn0.o6Km-N4-4eUeoDatjexq-F3bLn9auebccRJcRIaK1Po" width="231" height="500" style="margin-right: 20px;"/>
<img src="https://private-user-images.githubusercontent.com/84719721/307106386-6d8221c9-0daa-4cac-a363-da6021f0e1bb.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MDk2MjMwNDMsIm5iZiI6MTcwOTYyMjc0MywicGF0aCI6Ii84NDcxOTcyMS8zMDcxMDYzODYtNmQ4MjIxYzktMGRhYS00Y2FjLWEzNjMtZGE2MDIxZjBlMWJiLnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNDAzMDUlMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjQwMzA1VDA3MTIyM1omWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPWZlZWUzNzI0YTFjMzI3OWI5NDg1ZmY5ODNiZjMxOWNlMTE2NTU5NDc3NGVmNWEwMjExNGU4MThiYjVhNmRkNDUmWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0JmFjdG9yX2lkPTAma2V5X2lkPTAmcmVwb19pZD0wIn0.wfqPqZxvtkEe2b2RtHgu3qx9OxcfgXwhXyia8qlwsq4" width="231" height="500"/>
</p>

## Renting cars
To rent car switch to RENT mode. The list of available cars will be generated (to sort the list click **SORT CARS** button and select propper option). If you want to rent a car click on it's tile - the car page will be loaded. After that press **RENT NOW** button and configure parameters of rental. Confirm by clicking **CONFIRM** button.

<p float="left">
<img src="https://private-user-images.githubusercontent.com/84719721/307305020-a44a4c71-c473-498d-98fb-2d341ae5e912.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MDk2MjMwNDMsIm5iZiI6MTcwOTYyMjc0MywicGF0aCI6Ii84NDcxOTcyMS8zMDczMDUwMjAtYTQ0YTRjNzEtYzQ3My00OThkLTk4ZmItMmQzNDFhZTVlOTEyLnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNDAzMDUlMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjQwMzA1VDA3MTIyM1omWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPWYwMTEzMmFiNDQ5MTUzMTIwMjVjMWM3YWQyODk4ZDZiMWZmNDkwMGQ2OWY5MzUwZmViMTViYWMxMWZhNTJmNGYmWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0JmFjdG9yX2lkPTAma2V5X2lkPTAmcmVwb19pZD0wIn0.HjgbSC2GaBge-POGrtytmhmMaV3PKDSC58Z3lBjoDs4" width="231" height="500" style="margin-right: 20px;"/>
<img src="https://private-user-images.githubusercontent.com/84719721/307305027-230cfe05-e749-43aa-a8d4-62433fe3af61.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MDk2MjMwNDMsIm5iZiI6MTcwOTYyMjc0MywicGF0aCI6Ii84NDcxOTcyMS8zMDczMDUwMjctMjMwY2ZlMDUtZTc0OS00M2FhLWE4ZDQtNjI0MzNmZTNhZjYxLnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNDAzMDUlMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjQwMzA1VDA3MTIyM1omWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPWJiNDExZDQwNjI0Y2QzYWJjMDhkMzA2MmUzZTVhMjFkMTY3MGUyMGNjY2I2ODlkYmExNDlmMzJjNzQzMGZkY2MmWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0JmFjdG9yX2lkPTAma2V5X2lkPTAmcmVwb19pZD0wIn0.3rY_lPd0NVzWNdgvZhMG_GpQHREY74TJXt16PGUwKYQ" width="231" height="500" style="margin-right: 20px;"/>
<img src="https://private-user-images.githubusercontent.com/84719721/307305040-45dfc86e-5614-4f14-9130-a49056217da0.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MDk2MjMwNDMsIm5iZiI6MTcwOTYyMjc0MywicGF0aCI6Ii84NDcxOTcyMS8zMDczMDUwNDAtNDVkZmM4NmUtNTYxNC00ZjE0LTkxMzAtYTQ5MDU2MjE3ZGEwLnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNDAzMDUlMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjQwMzA1VDA3MTIyM1omWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPThkNThhMGViY2I2NWQzOTcxNjNmNWRjNzk1NDE1NDA5OWI2Mjk0MTllN2I0NzY5ZDZjNTJkYTRhZTc4ZGQxMDImWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0JmFjdG9yX2lkPTAma2V5X2lkPTAmcmVwb19pZD0wIn0.tDtm9txqyIeXNi-L9J4ryWCrt5Tceeir1EUzjOdFFh8" width="231" height="500"/>
</p>
