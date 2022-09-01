# Hotel-Reservation-Application-Core-Java

This project is a hotel reservation application written in Core Java and built using IntelliJ IDEA that will allow 
customers to find and book a hotel room based on room availability. The main entry point to the application is HotelApplication.
This project was submitted as part of Udacity Java Nanodegree.

## Main Components of the App

The major components of the Hotel Reservation Application will consist of the following:

* **CLI for the User Interface.** We'll use the Command Line Interface (or CLI for the user interface. For this, we'll need to have Java monitor the CLI for user input, so the user can enter commands to search for available rooms, book rooms, and so on.
* **Java code.** The second main component is the Java code itself—this is where we add our business logic for the app.
* **Java collections.** Finally, we'll use Java collections for in-memory storage of the data we need for the app, such as the users' names, room availability, and so on.

## Application Architecture

The app will be separated into the following layers:

* **User interface (UI),** including a main menu for the users who want to book a room, and an admin menu for administrative functions.
* **Resources** will act as our Application Programming Interface (API) to our UI.
* **Services** will communicate with our resources, and each other, to build the business logic necessary to provide feedback to our UI.
* **Data** models will be used to represent the domain that we're using within the system (e.g., rooms, reservations, and customers).

![image](https://user-images.githubusercontent.com/40417570/187844510-5a9fc30f-18c1-4625-971e-7703d990da62.png)

### Advantages of Layering

This architecture uses layers to support modularization and decoupling. For example, If we later decided to change our UI components to a webpage 
instead of a command-line interface, layering would support this. Layering is achieved by ensuring there are no cross-communication calls from one layer to another.
