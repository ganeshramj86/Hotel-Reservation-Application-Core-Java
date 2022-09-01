package api;

import model.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class AdminMenu {

    // create instance of Admin and Hotel Resource
    static AdminResource adminResource = AdminResource.getInstance();

    public static void handleAdminMenu() {
        Scanner scanner = new Scanner(System.in);

        printAdminMenu();

        String userInput;
        while (!(userInput = scanner.nextLine()).equals("7")) {
            switch(userInput) {
                case "1":
                    //System.out.println("See Customers");
                    handleSeeCustomers();
                    break;
                case "2":
                    //System.out.println("See Rooms");
                    handleSeeRooms();
                    break;
                case "3":
                    //System.out.println("See All Reservations");
                    handleSeeAllReservations();
                    break;
                case "4":
                    //System.out.println("Add Room");
                    handleAddRoom();
                    break;
                case "5":
                    System.out.println("Adding Test Data");
                    handleAddTestData();
                    break;
                case "6":
                    System.out.println("Removing Test Data");
                    handleRemoveTestData();
                default:
                    System.out.println("Enter Valid Menu Option [1-5]");
                    break;
            }
            printAdminMenu();
        }

    }

    private static void printAdminMenu() {
        System.out.println();
        System.out.println("Admin Menu");
        System.out.println("--------------------------------------------------");
        System.out.println("1. See all Customers");
        System.out.println("2. See all Rooms");
        System.out.println("3. See all Reservations");
        System.out.println("4. Add a Room");
        System.out.println("5. Add Test Data");
        System.out.println("6. Clear Test Data");
        System.out.println("7. Back to Main Menu");
        System.out.println("--------------------------------------------------");
        System.out.println("Please select a number for the menu option");
    }

    private static void handleSeeCustomers() {
        for (Customer customer : adminResource.getAllCustomers()) {
            System.out.println(customer);
        }
    }

    private static void handleSeeRooms() {
        for (IRoom room : adminResource.getAllRooms()) {
            System.out.println(room);
        }
    }

    private static void handleSeeAllReservations() {
        adminResource.displayAllReservations();
    }

    private static void handleAddRoom() {
        Scanner add_room_scan = new Scanner(System.in);
        boolean add_another_room_flag = true;
        List<IRoom> roomsToAdd = new LinkedList<>();

        while (add_another_room_flag) {
            roomsToAdd.add(checkAndAddRoom());
            System.out.println("Would you like to add another room y/n");
            String room_add_choice;
            boolean room_add_ok = false;
            while (!room_add_ok) {
                room_add_choice = add_room_scan.nextLine();
                if (room_add_choice.equalsIgnoreCase("y")) {
                    room_add_ok = true;
                }
                else if (room_add_choice.equalsIgnoreCase("n")) {
                    // add all rooms through API
                    adminResource.addRoom(roomsToAdd);
                    room_add_ok =true;
                    add_another_room_flag = false;
                }
                else {
                    System.out.println("Please enter Y (Yes) or N (No)");
                }
            }
        }

    }

    private static IRoom checkAndAddRoom() {
        // get room number
        int roomNumber = getRoomNumberInput();
        // get room price
        double roomPrice = getRoomPriceInput();
        // get room type
        int roomType = getRoomTypeInput();
        RoomType newRoomType;
        if (roomType == 1) {
            newRoomType = RoomType.SINGLE;
        }
        else {
            newRoomType = RoomType.DOUBLE;
        }
        // check if it is paid or free room
        IRoom newRoom;
        if (Double.compare(roomPrice, 0.0) == 0) {
            newRoom = new FreeRoom(String.valueOf(roomNumber), roomPrice, newRoomType);
        }
        else {
            newRoom = new Room(String.valueOf(roomNumber), roomPrice, newRoomType);
        }

        return newRoom;
    }

    private static int getRoomNumberInput() {
        Scanner roomNumberScan = new Scanner(System.in);

        /* room number logic */
        int roomNumber = 0;
        boolean roomNumberOk = false;
        System.out.println("Enter room number");
        // check if room number is ok
        while (!roomNumberOk) {
            if (roomNumberScan.hasNextInt()) {
                roomNumber = roomNumberScan.nextInt();
                if (roomNumber < 0) {
                    System.out.println("Enter Non Negative Room Number");
                    //room_scan.next();
                }
                else {
                    roomNumberOk = true;
                }
                //room_scan.next();
            }
            else {
                System.out.println("Enter an Integer for Room Number");
                roomNumberScan.next();
            }
        }

        return roomNumber;
    }

    private static double getRoomPriceInput() {

        Scanner roomPriceScan = new Scanner(System.in);
        double roomPrice = 0.0;
        boolean roomPriceOk = false;
        System.out.println("Enter price per night");

        while (!roomPriceOk) {
            if (roomPriceScan.hasNextDouble()) {
                roomPrice = roomPriceScan.nextDouble();
                if (roomPrice < 0.0) {
                    System.out.println("Enter Non Negative Room Price");
                }
                else {
                    roomPriceOk = true;
                }
            }
            else {
                System.out.println("Enter Non Negative Room Price");
                roomPriceScan.next();
            }
        }
        return roomPrice;
    }

    private static int getRoomTypeInput() {
        Scanner roomTypeScan = new Scanner(System.in);
        int roomType = 0;
        boolean roomTypeOk = false;
        System.out.println("Enter room type: 1 for single bed, 2 for double bed");

        while (!roomTypeOk) {
            if (roomTypeScan.hasNextInt()) {
                roomType = roomTypeScan.nextInt();
                if ((roomType < 0) || (roomType > 2)) {
                    System.out.println("Enter room type: 1 for single bed, 2 for double bed");
                }
                else {
                    roomTypeOk = true;
                }
            }
            else {
                System.out.println("Enter room type: 1 for single bed, 2 for double bed");
                roomTypeScan.next();
            }
        }
        return roomType;
    }

    private static void handleAddTestData() {

        try {
            SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            df.setLenient(false);

            // Add Rooms
            List<IRoom> rooms = new LinkedList<>();
            rooms.add(new Room("100", 135.0, RoomType.SINGLE));
            rooms.add(new Room("200", 240.0, RoomType.DOUBLE));
            rooms.add(new Room("300", 0.0, RoomType.SINGLE));
            rooms.add(new Room("400", 0.0, RoomType.DOUBLE));

            adminResource.addRoom(rooms);

            // Add Customers
            MainMenu.hotelResource.createACustomer("j@gmail.com", "gans", "jay");
            MainMenu.hotelResource.createACustomer("ganesh@gmail.com", "ganesh", "jayaram");
            MainMenu.hotelResource.createACustomer("john@gmail.com", "john", "powell");

            // Add reservations
            MainMenu.hotelResource.bookARoom("j@gmail.com", MainMenu.hotelResource.getRoom("100"),
                    df.parse("07/09/2021"), df.parse("07/12/2021"));

            MainMenu.hotelResource.bookARoom("ganesh@gmail.com", MainMenu.hotelResource.getRoom("200"),
                    df.parse("07/20/2021"), df.parse("07/23/2021"));

            MainMenu.hotelResource.bookARoom("j@gmail.com", MainMenu.hotelResource.getRoom("300"),
                    df.parse("07/13/2021"), df.parse("07/16/2021"));

            MainMenu.hotelResource.bookARoom("ganesh@gmail.com", MainMenu.hotelResource.getRoom("400"),
                    df.parse("07/21/2021"), df.parse("07/25/2021"));
        }
        catch (IllegalArgumentException | ParseException ex) {
            System.out.println("Test Data Already Added. Cannot Add Test Data Again.");
        }

    }

    private static void handleRemoveTestData() {
        adminResource.clearData();
    }

}
