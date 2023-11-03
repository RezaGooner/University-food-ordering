package Classes.Roles;
/*
این کد یک کلاس به نام `Employee` را شامل می‌شود که از کلاس `Person` ارث‌ بری شده است و ویژگی دیگری به نام `employeeId` به آن اضافه شده است.

در کلاس `Employee`، سه متد getter و setter برای ویژگی `employeeId` و همچنین متدهای override برای ویژگی‌های ارث ‌بری شده از کلاس `Person` ایجاد شده‌اند.

این کلاس به منظور نمایش و مدیریت اطلاعات کارمندان در برنامه‌های مختلف قابل استفاده است.

   `````````````````````````````````````````````````````

The `Employee` class is a subclass of the `Person` class.
It represents an employee and extends the `Person` class by adding an `employeeId` field specific to employees.

Here's a breakdown of the code:

    - The `Employee` class has a private field `employeeId` to store the employee's ID.
    - The class has a constructor that takes `firstName`, `lastName`, `gender`, and `employeeId` as parameters.
      It calls the superclass constructor (`Person`) using the `super` keyword to initialize the inherited
      fields (`firstName`, `lastName`, and `gender`). It also assigns the `employeeId` parameter to the
      `employeeId` field specific to the `Employee` class.
    - There are getter and setter methods for the `employeeId` field.
    - The class overrides some methods inherited from the `Person` class.
      These methods (`getFirstName()`, `setFirstName()`, `getLastName()`, `setLastName()`, `getGender()`,
      and `setGender()`) simply call the corresponding methods in the superclass (`Person`) using the `super` keyword.

Overall, the `Employee` class extends the `Person` class and adds an `employeeId` field along with its getters,
setters, and a constructor to handle employee-specific data.

 */

public class Employee extends Person {
    private String employeeId;

    public Employee(String firstName, String lastName, Gender gender, String employeeId) {
        super(firstName, lastName, gender);
        this.employeeId = employeeId;
    }



    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public String getFirstName() {
        return super.getFirstName();
    }

    @Override
    public void setFirstName(String firstName) {
        super.setFirstName(firstName);
    }

    @Override
    public String getLastName() {
        return super.getLastName();
    }

    @Override
    public void setLastName(String lastName) {
        super.setLastName(lastName);
    }

    @Override
    public Gender getGender() {
        return super.getGender();
    }

    @Override
    public void setGender(Gender gender) {
        super.setGender(gender);
    }


    }

    //  1402/03/10 03:00 a.m. ~ 31 - 5 - 2023



