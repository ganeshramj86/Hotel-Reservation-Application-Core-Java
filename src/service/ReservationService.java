package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ReservationService {

    // static reference single_instance of type ReservationService
    private static ReservationService single_instance = null;

    // private collection variables
    private final Set<IRoom> setOfRooms;
    private final Set<IRoom> availableRooms;
    private final ArrayList<Reservation> listOfReservations;

    //private constructor
    private ReservationService()
    {
        setOfRooms = new HashSet<>();
        availableRooms = new HashSet<>();
        listOfReservations = new ArrayList<>();

    }

    // static method to create instance of Singleton class
    public static ReservationService getInstance() {
        if (single_instance == null) {
            single_instance = new ReservationService();
        }

        return single_instance;
    }

    public void addRoom(IRoom room) {

        setOfRooms.add(room);
        availableRooms.add(room);

    }

    public IRoom getARoom(String roomId) {

        for (IRoom room : setOfRooms) {
            if (room.getRoomNumber().equals(roomId)) {
                return room;
            }
        }
        throw new IllegalArgumentException("Selected Room Number does not exist");
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);

        listOfReservations.add(reservation);

        // remove the room from available rooms
        availableRooms.remove(room);

        return reservation;

    }

    //public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate, String search_choice, int days) {
        // first find available rooms that are not reserved
        boolean is_paid = search_choice.equalsIgnoreCase("p");
        // create a modified available rooms based on search choice
        Set<IRoom> availableChosenRooms = new HashSet<>();
        ArrayList<Reservation> listOfChosenReservations = new ArrayList<>();
        // reduced available rooms for search
        for (IRoom availRoom : availableRooms) {
            if (is_paid) {
                if (Double.compare(availRoom.getRoomPrices(), 0.0) != 0) {
                    availableChosenRooms.add(availRoom);
                }
            }
            else {
                if (Double.compare(availRoom.getRoomPrices(), 0.0) == 0) {
                    availableChosenRooms.add(availRoom);
                }
            }
        }
        // reduced reservations list for search
        for (Reservation choiceReserve : listOfReservations) {
            if (is_paid) {
                if (Double.compare(choiceReserve.getRoom().getRoomPrices(), 0.0) != 0) {
                    listOfChosenReservations.add(choiceReserve);
                }
            }
            else {
                if (Double.compare(choiceReserve.getRoom().getRoomPrices(), 0.0) == 0) {
                    listOfChosenReservations.add(choiceReserve);
                }
            }
        }
        //Initialize the foundrooms with available rooms based on search choice (paid or free)
        Set<IRoom> foundRooms = new HashSet<>(availableChosenRooms);
        // find available rooms that are reserved but open on the data
        for (Reservation bookedRoom : listOfChosenReservations) {
            if ((checkInDate.compareTo(bookedRoom.getCheckOutDate()) > 0)
            || (checkOutDate.compareTo(bookedRoom.getCheckInDate()) < 0)) {
                foundRooms.add(bookedRoom.getRoom());
            }
        }
        // Now find recommended rooms
        if (foundRooms.isEmpty()) {
            System.out.println("All rooms are Booked for the given dates");
            // add 7 days to check in and checkout date
            Date newCheckInDate = addDaysToDate(checkInDate, days);
            Date newCheckOutDate = addDaysToDate(checkOutDate, days);

            // print new dates
            DateFormat df = new SimpleDateFormat("EEE MMM dd  yyyy");
            System.out.println("Recommended Rooms (+" + days + " days)");
            System.out.println("From: " + df.format(newCheckInDate));
            System.out.println("From: " + df.format(newCheckOutDate));

            // now find rooms based on new check in and checkout dates
            for (Reservation bookedRoom : listOfChosenReservations) {

                if ((newCheckInDate.compareTo(bookedRoom.getCheckOutDate()) > 0)
                        || (newCheckOutDate.compareTo(bookedRoom.getCheckInDate()) < 0)) {
                    foundRooms.add(bookedRoom.getRoom());
                }
            }
        }

        return foundRooms;
    }

    public Collection<Reservation> getCustomersReservation(Customer customer) {
        List<Reservation> reservedRooms = new ArrayList<>();

        for (Reservation customerReservation : listOfReservations) {
            String check_email = customerReservation.getCustomer().getEmail();
            if (check_email.equals(customer.getEmail())) {
                reservedRooms.add(customerReservation);
            }
        }

        return reservedRooms;

    }

    public void printAllReservation() {

        for (Reservation reserve : listOfReservations) {
            System.out.println(reserve);
        }

    }

    Date addDaysToDate(Date inputDate, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(inputDate);
        c.add(Calendar.DATE, days);

        return c.getTime();

    }

    public Collection<IRoom> getAllRooms() {

        return setOfRooms;
    }

    public void clearRooms() {
        setOfRooms.clear();
        availableRooms.clear();
    }

    public void clearReservations() {
        listOfReservations.clear();
    }
}
