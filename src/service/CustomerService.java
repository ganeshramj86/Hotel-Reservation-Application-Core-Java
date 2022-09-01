package service;

import model.Customer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CustomerService {

    // static reference single_instance of type CustomerService
    private static CustomerService single_instance = null;

    // private collection variables
    //private Map<String, Customer> mapOfCustomer = new HashMap<String, Customer>();
    //private ArrayList<Customer> listOfCustomer = new ArrayList<Customer>();
    private final Map<String, Customer> mapOfCustomer;
    private final ArrayList<Customer> listOfCustomer;

    //private constructor
    private CustomerService()
    {
        mapOfCustomer = new HashMap<>();
        listOfCustomer = new ArrayList<>();
    }

    // static method to create instance of Singleton class
    public static CustomerService getInstance() {
        if (single_instance == null) {
            single_instance = new CustomerService();
        }

        return single_instance;
    }

    public void addCustomer(String email, String firstName, String lastName) {

        if (mapOfCustomer.containsKey(email)) {
            throw new IllegalArgumentException("Account with this email already Exists");
        }
        Customer customer = new Customer(firstName, lastName, email);
        mapOfCustomer.put(customer.getEmail(), customer);
        listOfCustomer.add(customer);

    }

    public Customer getCustomer(String customerEmail) {
        if (!mapOfCustomer.containsKey(customerEmail)) {
            throw new IllegalArgumentException("Account (Email) is not Registered");
        }

        return mapOfCustomer.get(customerEmail);
    }

    public Collection<Customer> getAllCustomers() {

        return listOfCustomer;
    }

    public void clearCustomers() {
        mapOfCustomer.clear();
        listOfCustomer.clear();
    }
}
