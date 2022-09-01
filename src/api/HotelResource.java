package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.Date;

public class HotelResource {

    // static reference single_instance of type HotelResource
    private static HotelResource single_instance = null;

    // private constructor
    private HotelResource() {}

    // static method to create instance of Singleton class
    public static HotelResource getInstance() {
        if (single_instance == null) {
            single_instance = new HotelResource();
        }

        return single_instance;
    }

    // create instance of Customer and Reservation Service
    CustomerService customerService = CustomerService.getInstance();
    ReservationService reservationService = ReservationService.getInstance();

    public Customer getCustomer(String email) {

        return customerService.getCustomer(email);

    }

    public void createACustomer(String email, String firstName, String lastName) {

        customerService.addCustomer(email, firstName, lastName);

    }

    public IRoom getRoom(String roomNumber) {

        return reservationService.getARoom(roomNumber);

    }

    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date CheckoutDate) {

        Customer customer = getCustomer(customerEmail);

        return reservationService.reserveARoom(customer, room, checkInDate, CheckoutDate);

    }

    public Collection<Reservation> getCustomerReservations(String customerEmail) {

        Customer customer = getCustomer(customerEmail);

        return reservationService.getCustomersReservation(customer);

    }

    public Collection<IRoom> findARoom(Date checkIn, Date checkOut, String choice, int days) {

        return reservationService.findRooms(checkIn, checkOut, choice, days);

    }

}
