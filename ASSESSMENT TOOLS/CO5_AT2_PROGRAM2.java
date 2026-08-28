import java.util.*;
import java.io.*;
import java.sql.*;
public class Main {
    static HashMap<Integer, Customer> database = new HashMap<>();
    static ByteArrayOutputStream transactionLogStream = new ByteArrayOutputStream();
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.println("JDBC Connectivity Established (Simulated)");
        } catch (Exception e) {
            System.out.println("Database connection exception handled: " + e.getMessage());
        }
        try {
            while (sc.hasNext()) {
                String operation = sc.next().toUpperCase();
                if (operation.equals("INSERT")) {
                    int id = sc.nextInt();
                    String name = sc.next();
                    double balance = sc.nextDouble();
                    insertCustomer(id, name, balance);
                } else if (operation.equals("SELECT")) {
                    int id = sc.nextInt();
                    selectCustomer(id);
                } else if (operation.equals("UPDATE")) {
                    int id = sc.nextInt();
                    double amount = sc.nextDouble();
                    updateCustomer(id, amount);
                } else if (operation.equals("DELETE")) {
                    int id = sc.nextInt();
                    deleteCustomer(id);
                } else if (operation.equals("BACKUP")) {
                    backupCustomers();
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid transaction input: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("I/O and Database exception handled: " + e.getMessage());
        } finally {
            sc.close();
        }
    }
    public static void insertCustomer(int id, String name, double balance) {
        try {
            if (balance < 0) {
                throw new IllegalArgumentException("Balance cannot be negative");
            }
            if (database.containsKey(id)) {
                throw new SQLException("Duplicate customer ID");
            }
            Customer c = new Customer(id, name, balance);
            database.put(id, c);
            String log = "INSERT: " + id + " " + name + " " + balance + "\n";
            transactionLogStream.write(log.getBytes());
            System.out.println("Customer inserted: " + c);
        } catch (SQLException e) {
            System.out.println("Database Exception: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("I/O Exception during log: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Validation Failed: " + e.getMessage());
        }
    }
    public static void selectCustomer(int id) {
        try {
            if (!database.containsKey(id)) {
                throw new SQLException("Customer ID " + id + " not found");
            }
            System.out.println(database.get(id));
        } catch (SQLException e) {
            System.out.println("Database Exception: " + e.getMessage());
        }
    }
    public static void updateCustomer(int id, double amount) {
        try {
            if (!database.containsKey(id)) {
                throw new SQLException("Customer ID " + id + " not found");
            }
            Customer c = database.get(id);
            double newBalance = c.balance + amount;
            if (newBalance < 0) {
                throw new IllegalArgumentException("Insufficient balance for transaction");
            }
            c.balance = newBalance;
            String log = "UPDATE: " + id + " New Balance: " + newBalance + "\n";
            transactionLogStream.write(log.getBytes());
            System.out.println("Customer updated: " + c);
        } catch (SQLException e) {
            System.out.println("Database Exception: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("I/O Exception: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Transaction Validation Failed: " + e.getMessage());
        }
    }
    public static void deleteCustomer(int id) {
        try {
            if (database.remove(id) == null) {
                throw new SQLException("Customer ID " + id + " not found");
            }
            System.out.println("Customer with ID " + id + " deleted successfully");
        } catch (SQLException e) {
            System.out.println("Database Exception: " + e.getMessage());
        }
    }
    public static void backupCustomers() {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(database);
            System.out.println("Customer backup serialized successfully. Size: " + bos.size() + " bytes");
        } catch (IOException e) {
            System.out.println("I/O Exception during backup: " + e.getMessage());
        }
    }
    static class Customer implements Serializable {
        private static final long serialVersionUID = 1L;
        int id;
        String name;
        double balance;
        public Customer(int id, String name, double balance) {
            this.id = id;
            this.name = name;
            this.balance = balance;
        }
        @Override
        public String toString() {
            return "Customer [ID=" + id + ", Name=" + name + ", Balance=" + balance + "]";
        }
    }
}
