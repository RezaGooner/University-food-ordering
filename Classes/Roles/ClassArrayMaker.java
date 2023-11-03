package Classes.Roles;

/*
این کد کلاس برای خواندن داده‌ها از یک فایل و تبدیل آن‌ها به شیء با ویژگی‌های مشخص شده در کلاس‌های `Student`، `Employee` و `VipStudent` ساخته شده است.

در این کلاس، سه آرایه از شیء‌های `Student`، `Employee` و `VipStudent` تعریف شده است که به ترتیب در متغیر‌های `students`، `employees` و `vipStudents` ذخیره می‌شوند.

تابع `readDataFromFile` فایل مربوطه را می‌خواند و بر اساس شناسه هر داده، آن را به کلاس مربوطه تبدیل می‌کند و سپس به آرایه مربوطه آن را اضافه می‌کند.

توابع `StudentDataFields`، `EmployeeDataFields` و `VipStudentDataFields` برای بازگرداندن آرایه‌های دانشجویان، کارمندان و دانشجویان VIP  ایجاد شده‌اند.

توابع `studentDisplay`، `VIPStudentDisplay` و `employeeDisplay` برای چاپ اطلاعات دانشجویان، دانشجویان VIP و کارمندان به ترتیب ایجاد شده‌اند.

در طراحی کلاس، از اصول مفهومی انتزاع و ارث بری استفاده شده است.


   `````````````````````````````````````````````````````

The code you provided defines a Java class named "ClassArrayMaker".
This class contains several static methods and variables for reading data from a file and displaying the data
in different formats.

The class has three static arrays: "students", "employees", and "vipStudents", which are used to store objects
of the classes "Student", "Employee", and "VipStudent" respectively.

The class provides the following methods:

1. `studentDisplay(Student[] students)`:
    This method takes an array of Student objects as input and displays the first name,
    last name, student ID, and gender of each student.

2. `VIPStudentDisplay(VipStudent[] students)`:
    This method takes an array of VipStudent objects as input and displays the first name,
    last name, student ID, gender, and organization of each VIP student.

3. `employeeDisplay(Employee[] employees)`:
    This method takes an array of Employee objects as input and displays the first name,
    last name, employee ID, and gender of each employee.

4. `StudentDataFields()`:
    This method reads data from a file (specified by the "UserPassPath" variable) and populates the "students" array
    with Student objects. It then returns the populated array.

5. `EmployeeDataFields()`:
    This method reads data from the file and populates the "employees" array with Employee objects.
    It then returns the populated array.

6. `VipStudentDataFields()`:
    This method reads data from the file and populates the "vipStudents" array with VipStudent objects.
    It then returns the populated array.

7. `readDataFromFile()`:
    This method reads data from a file (specified by the "UserPassPath" variable) and performs the following tasks:
       - It initializes three variables (`numStudents`, `numEmployees`, `numVipStudents`) to keep track of
         the number of students, employees, and VIP students read from the file.
       - It loops through each line of the file, splits the line by commas, and determines the type of
         the object (student, employee, or VIP student) based on the ID prefix.
       - It increments the respective counter variables depending on the type of the object read.
       - It initializes the arrays (`students`, `employees`, `vipStudents`) with the appropriate lengths based on
         the counter variables.
       - It closes the first scanner.
       - It creates a second scanner to read the file again and populates the arrays with objects based on
         the ID prefix and other data read from the file.
       - Finally, it closes the second scanner.

The code appears to be reading data from a file containing information about students, employees, and VIP students,
and populating arrays with objects of the respective classes.
The display methods can be used to print the data in a formatted manner.

 */


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static Classes.Pathes.FilesPath.UserPassPath;

public class ClassArrayMaker {
    private static Student[] students;
    private static Employee[] employees;
    private static VipStudent[] vipStudents;

    public static void studentDisplay(Student[] students){
        readDataFromFile();
        for (Student student : students) {
            System.out.println(student.getFirstName() + " , " + student.getLastName() + " , " + student.getStudentId() + " , " + student.getGender() + " , ");
        }
    }

    public static void VIPStudentDisplay(VipStudent[] students){
        readDataFromFile();
        for (VipStudent student : students) {
            System.out.println(student.getFirstName() + " , " + student.getLastName() + " , " + student.getStudentId() + " , " + student.getGender() + " , " + student.getOrganization());
        }
    }

    public static void employeeDisplay(Employee[] employees){
        readDataFromFile();
        for (Employee employee : employees) {
            System.out.println(employee.getFirstName() + " , " + employee.getLastName() + " , " + employee.getEmployeeId() + " , " + employee.getGender() );
        }
    }
    public static Student[] StudentDataFields() {
        readDataFromFile();
        return students;
    }
    public static Employee[] EmployeeDataFields() {
        readDataFromFile();
        return employees;
    }
    public static VipStudent[] VipStudentDataFields() {
        readDataFromFile();
        return vipStudents;
    }
    public static void readDataFromFile() {
        // filePath = "userpass.txt"
        File file = new File(UserPassPath);
        try (Scanner scanner = new Scanner(file)) {
            // تعریف سه متغیر برای شمارش دانشحو و دانشجوی VIP و کارمند به هنگام خواندن فایل
            int numStudents = 0;
            int numEmployees = 0;
            int numVipStudents = 0;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");

                String id = parts[0];
                /*
                 شناسه دانشجو با عدد 4 و شناسه کارمند با عدد 0 و شناسه دانشجوی ویژه با عدد 1 شروع میشود
                 هر خط از فایل خوانده میشود و بسته به شناسه با عددی که شروع میشود
                  متغیر هایی که در آغاز متد تعریف شده اند ، یک مقدار اضافه می گردند
                 */
                if (id.startsWith("0")) {
                    numEmployees++;
                } else if (id.startsWith("1")) {
                    numVipStudents++;
                } else if (id.startsWith("4")) {
                    numStudents++;
                }
            }
            /* سه آرایه از نوع کلاس های Employee و Student و VIPStudent با طول هایی با مقدار متغیر هایی که به هنگام خواندن فایل مقداردهی شد
            ایجاد میشوند
             */
            employees = new Employee[numEmployees];
            vipStudents = new VipStudent[numVipStudents];
            students = new Student[numStudents];

            scanner.close();

            Scanner secondScanner = new Scanner(file);

            /*
            در این قسمت فایل مجدد خوانده میشود و با متغیر هایی که در زیر تعریف شده داده به کمک ساختار شناسه ای که گفته شد پیدا شده
            و در آرایه های ساخته شده قرار میگیرند. همچنین با کمک متد های setter که در هر کلاس تعریف شده مقداردهی های لازم صورت میگیرد
             */
            int employeeIndex = 0;
            int vipStudentIndex = 0;
            int studentIndex = 0;

            while (secondScanner.hasNextLine()) {
                String line = secondScanner.nextLine();
                String[] parts = line.split(",");

                String id = parts[0];
                String name = parts[2];
                String lastName = parts[3];
                Gender gender = Gender.valueOf(parts[4]);
                Organization organization = Organization.valueOf(parts[6]);

                if (id.startsWith("0")) {
                    Employee employee = new Employee(name, lastName , gender , id);
                    employees[employeeIndex] = employee;
                    employeeIndex++;
                } else if (id.startsWith("1")) {
                    VipStudent vipStudent = new VipStudent(name, lastName, gender, id , organization);
                    vipStudents[vipStudentIndex] = vipStudent;
                    vipStudentIndex++;
                } else if (id.startsWith("4")) {
                    Student student = new Student(name, lastName, gender, id);
                    students[studentIndex] = student;
                    studentIndex++;
                }

            }



        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }



    }
}

//  1402/03/11 14:00 p.m. ~ 1 - 6 - 2023
