package Classes.Roles;
/*
این کد یک کلاس به نام `Student` را شامل می‌شود که از کلاس `Person` ارث‌ بری شده است و ویژگی دیگری به نام `studentId` به آن اضافه شده است.

در کلاس `Student`، سه متد `getter` و `setter` برای ویژگی `studentId` و همچنین متدهای `override` برای ویژگی‌های ارث‌ بری شده از کلاس `Person` ایجاد شده‌اند.

این کلاس به منظور نمایش و مدیریت اطلاعات  است.

همچنین کلاس VIPStudent از این کلاس ارث بری کرده است.

   `````````````````````````````````````````````````````
This code defines a `Student` class that extends the `Person` class.
It represents a student and adds a `studentId` field specific to students.

Here's a breakdown of the code:

    - The `Student` class has a private field `studentId` to store the student's ID.
    - The class has a constructor that takes `firstName`, `lastName`, `gender`, and `studentId` as parameters.
      It calls the superclass constructor (`Person`) using the `super` keyword to initialize the inherited fields
      (`firstName`, `lastName`, and `gender`). It also assigns the `studentId` parameter to
      the `studentId` field specific to the `Student` class.
    - There are getter and setter methods for the `studentId` field.
    - The class overrides some methods inherited from the `Person` class.
      These methods (`getFirstName()`, `setFirstName()`, `getLastName()`, `setLastName()`, `getGender()`, and
      `setGender()`) simply call the corresponding methods in the superclass (`Person`) using the `super` keyword.

Overall, the `Student` class extends the `Person` class and adds a `studentId` field along with its getters,
setters, and a constructor to handle student-specific data.

 */


public class Student extends Person {
    private String studentId;

    public Student(String firstName, String lastName, Gender gender, String studentId) {
        super(firstName, lastName, gender);
        this.studentId = studentId;
    }



    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
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
