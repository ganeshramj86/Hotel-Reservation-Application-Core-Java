//package api;

import model.IRoom;
import model.Reservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class MainMenu {

    // create instance of Admin and Hotel Resource
    static HotelResource hotelResource = HotelResource.getInstance();

    public void start() {
        Scanner scanner = new Scanner(System.in);

        printMainMenu();
        //String userInput = scanner.nextLine();
        String userInput;
        //System.out.println(userInput);

        while (!(userInput = scanner.nextLine()).equals("5")) {
            switch (userInput) {
                case "1":
                    //System.out.println("Find Room");
                    handleFindReserveRoom();
                    break;
                case "2":
                    //System.out.println("See Reservation");
                    handleSeeReservation();
                    break;
                case "3":
                    //System.out.println("Create Account");
                    try {
                        handleCreateAccount();
                    }
                    catch (IllegalArgumentException ex) {
                        System.out.println(ex.getLocalizedMessage());
                    }

                    break;
                case "4":
                    //System.out.println("Admin Menu");
                    AdminMenu.handleAdminMenu();
                    break;
                default:
                    System.out.println("Enter Valid Menu Option [1-5]");
                    break;
            }
            printMainMenu();
        }
    }

    private static void handleSeeReservation() {

        System.out.println("Enter Email format: name@domain.com");
        String customer_email = handleEmailInput();

        try {
            for (Reservation reservation : hotelResource.getCustomerReservations(customer_email)) {
                System.out.println(reservation);
            }
        }
        catch (IllegalArgumentException ex) {
            System.out.println(ex.getLocalizedMessage());
        }

    }

    private static void handleFindReserveRoom() {
        Date currentDate = new Date();
        // get check-in date
        System.out.println("Enter CheckIn Date MM/dd/yyyy example 02/01/2020");
        Date checkinDate = getVerifyDate(currentDate, "CheckIn");
        // get check-out date
        System.out.println("Enter CheckOut Date MM/dd/yyyy example 02/01/2020");
        Date checkOutDate = getVerifyDate(checkinDate, "CheckOut");
        // get search choice
        System.out.println("Choice of Room Search: Please enter P (Paid-Room) or F (Free-Room)");
        String searchChoice = getSearchChoice();
        // get the number of days to search for recommendation
        System.out.println("In case of no available rooms: Please Enter Number of Days to Search for Recommendation");
        int daysToSearchRoom = Integer.parseInt(getNumberInput());

        // Find rooms that match checkin and checkout
        /*
        List<IRoom> available_rooms;
        available_rooms = new LinkedList<>(hotelResource.findARoom(checkinDate, checkOutDate, searchChoice, daysToSearchRoom));
        for (IRoom room : available_rooms) {
            System.out.println(room);
        }
        */

        Set<IRoom> available_rooms = new HashSet<>();
        System.out.println("Available Rooms:");
        for (IRoom searched_room : hotelResource.findARoom(checkinDate, checkOutDate, searchChoice, daysToSearchRoom)) {
            available_rooms.add(searched_room);
            System.out.println(searched_room);
        }
        // if no available rooms, then exit to main menu
        if (available_rooms.isEmpty()) {
            System.out.println("Sorry No Rooms are available to book.");
        }
        // else if rooms are available then book
        else {
            //check if want to book a room
            System.out.println("Would you like to book a room? y/n");
            //String book_room = reserve_room_scan.nextLine();
            String book_room = handleYesNoInput();
            // if want to book check if the user has a account
            if (book_room.equalsIgnoreCase("y")) {
                String customer_email;
                System.out.println("Dp you have an account with us? y/n");
                //String hasAccount = reserve_room_scan.nextLine();4
                String hasAccount = handleYesNoInput();
                // customer has account - get his email
                if (hasAccount.equalsIgnoreCase("y")) {
                    System.out.println("Enter Email format: name@domain.com");
                    customer_email = handleEmailInput();
                    bookRoom(customer_email, checkinDate, checkOutDate, available_rooms);
                }
                // customer does not have account - ask him to create one
                else {
                    try {
                        customer_email = handleCreateAccount();
                        bookRoom(customer_email, checkinDate, checkOutDate, available_rooms);
                    }
                    catch (IllegalArgumentException ex) {
                        System.out.println(ex.getLocalizedMessage());
                    }
                }
            }
        }

    }

    private static void bookRoom(String email, Date in_date, Date out_date, Collection<IRoom> found_rooms) {
        // ask and book a room
        System.out.println("What room number would you like to reserve?");
        String room_number = getNumberInput();
        boolean canBookRoom = false;

        // check if the room number is in available rooms and not booked
        for (IRoom room : found_rooms) {
            if (room_number.matches(room.getRoomNumber())) {
                canBookRoom = true;
                break;
            }
        }

        if (canBookRoom) {
            try {
                // get a room
                IRoom room = hotelResource.getRoom(room_number);
                // now book a room and print the reservation
                System.out.println(hotelResource.bookARoom(email, room, in_date, out_date));
            }
            catch (IllegalArgumentException ex) {
                System.out.println(ex.getLocalizedMessage());
            }
        }
        else {
            System.out.println("Room already booked for the chosen dates.");
        }
    }

    private static String getNumberInput() {
        Scanner numberScan = new Scanner(System.in);
        int number = 0;
        boolean number_ok = false;

        while (!number_ok) {
            if (numberScan.hasNextInt()) {
                number = numberScan.nextInt();
                if (number < 0) {
                    System.out.println("Enter Non Negative Number");
                    //room_scan.next();
                }
                else {
                    number_ok = true;
                }
                //room_scan.next();
            }
            else {
                System.out.println("Enter an Integer for Number");
                numberScan.next();
            }
        }

        return String.valueOf(number);
    }

    private static String handleEmailInput() {
        Scanner emailScan = new Scanner(System.in);
        String emailRegex = "^(.+)@(.+).(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        String email = emailScan.nextLine();

        while (!pattern.matcher(email).matches()) {
            System.out.println("Invalid Email Format: (Correct Usage: name@domain.com)");
            email = emailScan.nextLine();
        }
        return email;
    }

    private static String handleYesNoInput() {
        Scanner yesNoScan = new Scanner(System.in);
        String yes_no = yesNoScan.nextLine();
        while ((!yes_no.equalsIgnoreCase("y")) &&
                (!yes_no.equalsIgnoreCase("n"))) {
            System.out.println("Please enter Y (Yes) or N (No)");
            yes_no = yesNoScan.nextLine();
        }

        return yes_no;
    }

    private static String getSearchChoice() {
        Scanner choiceScan = new Scanner(System.in);
        String searchChoice = choiceScan.nextLine();
        while ((!searchChoice.equalsIgnoreCase("p")) &&
                (!searchChoice.equalsIgnoreCase("f"))) {
            System.out.println("Please enter P (Paid Room) or F (Free Room)");
            searchChoice = choiceScan.nextLine();
        }

        return searchChoice;
    }

    private static Date getVerifyDate(Date check_date, String print_msg) {
        Scanner checkDateScan = new Scanner(System.in);

        String dateRegexFormat = "^(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/((19|20)\\d\\d)$";
        Pattern pattern = Pattern.compile(dateRegexFormat);

        String checkDate = checkDateScan.nextLine();
        boolean checkDate_ok = false;
        Date enteredDate = null;
        //Date currentDate = new Date();

        while (!checkDate_ok) {
            if (!pattern.matcher(checkDate).matches()) {
                System.out.println("Wrong Date Format. Enter " + print_msg + " Date MM/dd/yyyy example 02/01/2020");
                checkDate = checkDateScan.nextLine();
            }
            else {
                enteredDate = null;
                SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                df.setLenient(false);
                try {
                    enteredDate = df.parse(checkDate);
                }
                catch (ParseException ex) {
                    System.out.println("Invalid Date. Enter " + print_msg + " Date MM/dd/yyyy example 02/01/2020");
                    checkDate = checkDateScan.nextLine();
                    continue;
                }
                // check if date is not  before today's date
                if (enteredDate.before(check_date)) {
                    if (print_msg.equals("CheckIn")) {
                        System.out.println("CheckIn date cannot be earlier than today:" + df.format(check_date) + ". Enter CheckIn Date MM/dd/yyyy example 02/01/2020");
                    }
                    else {
                        System.out.println("CheckOut date cannot be earlier than CheckIn Date:" + df.format(check_date) + ". Enter CheckOut Date MM/dd/yyyy example 02/01/2020");
                    }
                    checkDate = checkDateScan.nextLine();
                }
                else {
                    checkDate_ok = true;
                }
            }
        }
        return enteredDate;
    }

    private static String handleCreateAccount() {
        Scanner add_cust_scan = new Scanner(System.in);

        //get and check customer email
        System.out.println("Enter Email Format: name@domain.com");

        // check customer email is ok
        String customer_email = handleEmailInput();
        // get first name
        System.out.println("First Name");
        String first_name = add_cust_scan.nextLine();
        while (first_name.isEmpty()) {
            System.out.println("First Name cannot be empty. Enter First Name:");
            first_name = add_cust_scan.nextLine();
        }
        // get last name
        System.out.println("Last Name");
        String last_name = add_cust_scan.nextLine();
        while (last_name.isEmpty()) {
            System.out.println("Last Name cannot be empty. Enter Last Name:");
            last_name = add_cust_scan.nextLine();
        }

        // add the customer
        hotelResource.createACustomer(customer_email, first_name, last_name);

        return customer_email;

    }

    private static void printMainMenu() {
        System.out.println();
        System.out.println("Welcome to Hotel Reservation Application");
        System.out.println();
        System.out.println("--------------------------------------------------");
        System.out.println("1. Find and reserve a room");
        System.out.println("2. See my reservations");
        System.out.println("3. Create an account");
        System.out.println("4. Admin");
        System.out.println("5. Exit");
        System.out.println("--------------------------------------------------");
        System.out.println("Please select a number for the menu option");
    }
}