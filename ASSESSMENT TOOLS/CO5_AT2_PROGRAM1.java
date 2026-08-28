import java.util.*;
import java.io.*;
public class Main {
    static HashMap<Integer, Employee> map = new HashMap<>();
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            if (!sc.hasNext()) return;
            String op = sc.next().toUpperCase();
            if (op.equals("ADD")) {
                int id = sc.nextInt();
                String name = sc.next();
                String dept = sc.next();
                double salary = sc.nextDouble();
                if (map.containsKey(id)) {
                    System.out.println("Duplicate ID! Employee with ID " + id + " already exists.");
                } else {
                    Employee e = new Employee(id, name, dept, salary);
                    map.put(id, e);
                    try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
                         ObjectOutputStream oos = new ObjectOutputStream(bos)) {
                        oos.writeObject(map);
                    } catch (IOException ex) {
                        System.out.println("Invalid file operation");
                    }
                    
                    System.out.println("Employee added successfully. Hash: " + e.hashCode());
                    System.out.println(e);
                }
            } 
            else if (op.equals("SEARCH")) {
                int id = sc.nextInt();
                if (map.containsKey(id)) {
                    System.out.println(map.get(id));
                } else {
                    System.out.println("Employee with ID " + id + " not found.");
                }
            }
            else if (op.equals("DELETE")) {
                int id = sc.nextInt();
                if (map.remove(id) != null) {
                    System.out.println("Employee deleted successfully.");
                } else {
                    System.out.println("Employee with ID " + id + " not found.");
                }
            }
        } catch (Exception e) {
            System.out.println("Invalid operation: " + e.getMessage());
        } finally {
            sc.close();
        }
    }
    static class Employee implements Serializable {
        private static final long serialVersionUID = 1L;
        int id;
        String name;
        String dept;
        double salary;
        public Employee(int id, String name, String dept, double salary) {
            this.id = id;
            this.name = name;
            this.dept = dept;
            this.salary = salary;
        }
        @Override
        public int hashCode() {
            return id % 100;
        }
        @Override
        public String toString() {
            return "ID: " + id + ", Name: " + name + ", Dept: " + dept + ", Salary: " + salary;
        }
    }
}
