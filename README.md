# TakeCar
Android car rental application. An application connects people who want to earn money on renting their car with one's that looking for car.
The current version of the application provides a base stack of functionalities (some more functionalities are TODO).
An application is built using java with Android SDK. The database part of application is created using [Firebase](https://firebase.google.com/) (Authentication, Realtime Database, Storage) 

## Table of functionalities
|          Functionality          |     Status    |
| ------------------------------- | ------------- |
|     Registration and sign in    |      Done     |
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
<img src="https://github.com/BartlomiejCzerwinski/TakeCar/assets/84719721/e0583dfc-c431-4697-9dd4-d4b9c26aeba2" width="231" height="500" style="margin-right: 20px;"/>
<img src="https://github.com/BartlomiejCzerwinski/TakeCar/assets/84719721/311abbba-a8f0-4bf1-8765-816a50e0c39a" width="231" height="500" style="margin-right: 20px;"/>
<img src="https://github.com/BartlomiejCzerwinski/TakeCar/assets/84719721/458ee30f-f672-4dd6-9878-707b35c9c980" width="231" height="500"/>
</p>

## Adding cars
After switching to RENT mode we can see preview of cars that we own. Click **ADD NEW CAR** button to load form for addnig a new car.

<p float="left">
<img src="https://github.com/BartlomiejCzerwinski/TakeCar/assets/84719721/944fde3b-4dc2-4fa4-8112-b295396def50" width="231" height="500" style="margin-right: 20px;"/>
<img src="https://github.com/BartlomiejCzerwinski/TakeCar/assets/84719721/6d8221c9-0daa-4cac-a363-da6021f0e1bb" width="231" height="500"/>
</p>

## Renting cars
To rent car switch to RENT mode. The list of available cars will be generated (to sort the list click **SORT CARS** button and select propper option). If you want to rent a car click on it's tile - the car page will be loaded. After that press **RENT NOW** button and configure parameters of rental. Confirm by clicking **CONFIRM** button.

<p float="left">
<img src="https://github.com/BartlomiejCzerwinski/TakeCar/assets/84719721/a44a4c71-c473-498d-98fb-2d341ae5e912" width="231" height="500" style="margin-right: 20px;"/>
<img src="https://github.com/BartlomiejCzerwinski/TakeCar/assets/84719721/230cfe05-e749-43aa-a8d4-62433fe3af61" width="231" height="500" style="margin-right: 20px;"/>
<img src="https://github.com/BartlomiejCzerwinski/TakeCar/assets/84719721/45dfc86e-5614-4f14-9130-a49056217da0" width="231" height="500"/>
</p>
