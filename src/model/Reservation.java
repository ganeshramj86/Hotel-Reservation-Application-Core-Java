package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Reservation {
    private Customer customer;
    private IRoom room;
    private Date checkInDate;
    private Date checkOutDate;

    public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;

    }

    public Customer getCustomer() {
        return customer;
    }

    public IRoom getRoom() {
        return room;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    @Override
    public String toString() {
        DateFormat df = new SimpleDateFormat("EEE MMM dd  yyyy");
        String checkIn = df.format(checkInDate);
        String checkOut = df.format(checkOutDate);
        return "Reservation" + "\n" +
                customer.getFirstName() + " " + customer.getLastName() + "\n" +
                "Room: " + room.getRoomNumber() + " - " +
                room.getRoomType() + " bed"  + "\n" +
                "Price: $" + room.getRoomPrices() + " price per night" + "\n" +
                "Checkin Date: " + checkIn + "\n" +
                "Checkout Date: " + checkOut + "\n";
    }
}
