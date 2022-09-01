package api;

import model.Customer;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.List;

public class AdminResource {

    // static reference single_instance of type AdminResource
    private static AdminResource single_instance = null;

    // private constructor
    private AdminResource() {}

    // static method to create instance of Singleton class
    public static AdminResource getInstance() {
        if (single_instance == null) {
            single_instance = new AdminResource();
        }

        return single_instance;
    }

    // create instance of Customer and Reservation Service
    CustomerService customerService = CustomerService.getInstance();
    ReservationService reservationService = ReservationService.getInstance();

    public Customer getCustomer(String email) {

        return customerService.getCustomer(email);

    }

    public void addRoom(List<IRoom> rooms) {

        for (IRoom room : rooms) {
            reservationService.addRoom(room);
        }

    }

    public Collection<IRoom> getAllRooms() {

        return reservationService.getAllRooms();
    }

    public Collection<Customer> getAllCustomers() {

        return customerService.getAllCustomers();

    }

    public void displayAllReservations() {

        reservationService.printAllReservation();

    }

    public void clearData() {
        customerService.clearCustomers();
        reservationService.clearRooms();
        reservationService.clearReservations();
    }

}
