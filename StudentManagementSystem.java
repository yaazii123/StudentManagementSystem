import java.io.*;
import java.util.*;

// Base class: Person
class Person {
    protected String name;
    protected int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
}

// Derived class: Student
class Student extends Person implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String course;

    public Student(String id, String name, int age, String course) {
        super(name, age);
        this.id = id;
        this.course = course;
    }

    public String getId() {
        return id;
    }

    public String getCourse() {
        return course;
    }

    @Override
    public String toString() {
        return "ID: " + id + " | Name: " + name + " | Age: " + age + " | Course: " + course;
    }
}

// Main class: Student Management System
public class StudentManagementSystem {
    private static final String FILE_NAME = "students.txt";
    private static HashMap<String, Student> studentMap = new HashMap<>();

    public static void main(String[] args) {
        loadFromFile();

        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- Student Record Management ---");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Search Student by ID");
            System.out.println("4. Delete Student");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addStudent(sc);
                    break;
                case 2:
                    viewAllStudents();
                    break;
                case 3:
                    searchStudent(sc);
                    break;
                case 4:
                    deleteStudent(sc);
                    break;
                case 5:
                    saveToFile();
                    System.out.println("Exiting... Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
                    break;
            }
        } while (choice != 5);

        sc.close();
    }

    // Add student
    private static void addStudent(Scanner sc) {
        System.out.print("Enter Student ID: ");
        String id = sc.nextLine();
        if (studentMap.containsKey(id)) {
            System.out.println("Student with this ID already exists.");
            return;
        }

        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Age: ");
        int age = sc.nextInt();
        sc.nextLine(); // consume newline
        System.out.print("Enter Course: ");
        String course = sc.nextLine();

        Student student = new Student(id, name, age, course);
        studentMap.put(id, student);
        System.out.println("Student added successfully.");
    }

    // View all students
    private static void viewAllStudents() {
        if (studentMap.isEmpty()) {
            System.out.println("No student records found.");
            return;
        }

        System.out.println("\n--- Student Records ---");
        for (Student s : studentMap.values()) {
            System.out.println(s);
        }
    }

    // Search student
    private static void searchStudent(Scanner sc) {
        System.out.print("Enter Student ID to search: ");
        String id = sc.nextLine();
        Student s = studentMap.get(id);
        if (s != null) {
            System.out.println("Student Found: " + s);
        } else {
            System.out.println("Student not found.");
        }
    }

    // Delete student
    private static void deleteStudent(Scanner sc) {
        System.out.print("Enter Student ID to delete: ");
        String id = sc.nextLine();
        if (studentMap.remove(id) != null) {
            System.out.println("Student deleted.");
        } else {
            System.out.println("Student not found.");
        }
    }

    // Save to file
    private static void saveToFile() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(studentMap);
            System.out.println("Records saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    // Load from file
    @SuppressWarnings("unchecked")
    private static void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            studentMap = (HashMap<String, Student>) in.readObject();
            System.out.println("Loaded student records from file.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
    }
}

