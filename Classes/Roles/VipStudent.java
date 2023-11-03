package Classes.Roles;

/*
این کد یک کلاس به نام `VipStudent` را شامل می‌شود که از کلاس `Student` ارث‌بری شده است و ویژگی دیگری به نام `organization` به آن اضافه شده است.

در کلاس `VipStudent`، یک `constructor` با پنج پارامتر تعریف شده است
که به جز پارامترهای ارث‌بری شده از کلاس `Student`، یک پارامتر به نام `organization` نیز دریافت می‌کند.

همچنین یک `getter` و `setter` برای ویژگی `organization` ایجاد شده است.

متدهای `override` برای ویژگی‌های ارث‌بری شده از کلاس `Student` نیز در این کلاس تعریف شده‌اند.

این کلاس به منظور نمایش و مدیریت اطلاعات دانشجویان ویژه است.

   `````````````````````````````````````````````````````
This code defines a `VipStudent` class that extends the `Student` class.
It represents a VIP student and adds an `organization` field specific to VIP students.

Here's a breakdown of the code:

    - The `VipStudent` class has a private field `organization` to store the organization the VIP student belongs to.
    - The class has a constructor that takes `firstName`, `lastName`, `gender`, `studentId`,
      and `organization` as parameters. It calls the superclass constructor (`Student`) using the `super` keyword to
      initialize the inherited fields (`firstName`, `lastName`, `gender`, and `studentId`).
      It also assigns the `organization` parameter to the `organization` field specific to the `VipStudent` class.
    - There are getter and setter methods for the `organization` field.
    - The class overrides some methods inherited from the `Student` class.
      These methods (`getFirstName()`, `setFirstName()`, `getLastName()`, `setLastName()`, `getGender()`,
      `setStudentId()`, and `getStudentId()`) simply call the corresponding methods in the superclass (`Student`)
       using the `super` keyword.

Overall, the `VipStudent` class extends the `Student` class and adds an `organization` field along with its getters,
setters, and a constructor to handle VIP student-specific data.

 */

public class VipStudent extends Student {
    private Organization organization;

    public VipStudent(String firstName, String lastName, Gender gender, String studentId, Organization organization) {
        super(firstName, lastName, gender, studentId);
        this.organization = organization;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
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
    public void setStudentId(String studentId) {
        super.setStudentId(studentId);
    }

    @Override
    public String getStudentId() {
        return super.getStudentId();
    }

    @Override
    public void setGender(Gender gender) {
        super.setGender(gender);
    }


}


//  1402/03/10 03:00 a.m. ~ 31 - 5 - 2023
