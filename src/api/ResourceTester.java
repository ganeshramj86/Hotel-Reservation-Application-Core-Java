package api;

import model.Customer;
import model.IRoom;
import model.Room;
import model.RoomType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;


public class ResourceTester {
    public static void main(String[] args) throws ParseException {
        // create instance of Admin and Hotel Resource
        HotelResource hotelResource = HotelResource.getInstance();
        AdminResource adminResource = AdminResource.getInstance();

        // add rooms
        List<IRoom> rooms = new LinkedList<>();
        rooms.add(new Room("100", 135.0, RoomType.SINGLE));
        rooms.add(new Room("200", 240.0, RoomType.DOUBLE));
        rooms.add(new Room("300", 0.0, RoomType.SINGLE));
        rooms.add(new Room("400", 0.0, RoomType.DOUBLE));

        adminResource.addRoom(rooms);
        for (IRoom room : adminResource.getAllRooms()) {
            System.out.println(room);
        }

        // add customers
        hotelResource.createACustomer("j@gmail.com", "gans", "jay");
        hotelResource.createACustomer("ganesh@gmail.com", "ganesh", "jayaram");
        hotelResource.createACustomer("john@gmail.com", "john", "powell");

        for (Customer customer : adminResource.getAllCustomers()) {
            System.out.println(customer);
        }

        // find rooms
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        df.setLenient(false);

        List<IRoom> available_rooms = new LinkedList<>(hotelResource.findARoom(df.parse("07/07/2021"), df.parse("07/10/2021"), "f", 7));
        System.out.println("Found Rooms");
        for (IRoom room : available_rooms) {
            System.out.println(room);
        }

        // reserve a room
        // Add reservations
        /*
        hotelResource.bookARoom("j@gmail.com", hotelResource.getRoom("100"),
                df.parse("07/09/2021"), df.parse("07/12/2021"));

        hotelResource.bookARoom("ganesh@gmail.com", hotelResource.getRoom("200"),
                df.parse("07/20/2021"), df.parse("07/23/2021"));

        hotelResource.bookARoom("j@gmail.com", hotelResource.getRoom("300"),
                df.parse("07/13/2021"), df.parse("07/16/2021"));

        hotelResource.bookARoom("ganesh@gmail.com", hotelResource.getRoom("400"),
                df.parse("07/21/2021"), df.parse("07/25/2021"));

         */

        hotelResource.bookARoom("j@gmail.com", hotelResource.getRoom("100"),
                df.parse("07/09/2021"), df.parse("07/15/2021"));

        hotelResource.bookARoom("ganesh@gmail.com", hotelResource.getRoom("200"),
                df.parse("07/18/2021"), df.parse("07/24/2021"));

        hotelResource.bookARoom("j@gmail.com", hotelResource.getRoom("300"),
                df.parse("07/13/2021"), df.parse("07/19/2021"));

        hotelResource.bookARoom("ganesh@gmail.com", hotelResource.getRoom("400"),
                df.parse("07/22/2021"), df.parse("07/28/2021"));

        adminResource.displayAllReservations();

        System.out.println("Found Rooms");
        for (IRoom room : hotelResource.findARoom(df.parse("07/10/2021"), df.parse("07/20/2021"), "p", 5)) {
            System.out.println(room);
        }

        System.out.println("Found Rooms");
        for (IRoom room : hotelResource.findARoom(df.parse("07/14/2021"), df.parse("07/24/2021"), "f", 5)) {
            System.out.println(room);
        }

        /*
        //no found or recommended rooms
        System.out.println("Found Rooms");
        for (IRoom room : hotelResource.findARoom(df.parse("07/11/2021"), df.parse("07/20/2021"), "p", 7)) {
            System.out.println(room);
        }

         */

    }
}
