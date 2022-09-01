package service;

import model.IRoom;
import model.Room;
import model.RoomType;

public class ServiceTester {
    public static void main(String[] args) {

        // create instance of Customer and Reservation Resource
        CustomerService customerService = CustomerService.getInstance();
        ReservationService reservationService = ReservationService.getInstance();

        IRoom room1 = new Room("100", 135.0, RoomType.SINGLE);
        IRoom room2 = new Room("200", 240.0, RoomType.DOUBLE);

        reservationService.addRoom(room1);
        reservationService.addRoom(room2);



    }
}
