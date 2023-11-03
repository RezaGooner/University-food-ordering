package Classes.Roles;
/*
این کد یک کلاس به نام `Person` را شامل می‌شود که ویژگی‌های `firstName`، `lastName` و `gender` را دارد.

در کلاس `Person`، دو `constructor` تعریف شده‌اند، یکی با سه پارامتر `firstName`، `lastName` و `gender` و دیگری بدون پارامتر.

همچنین در این کلاس، سه متد `getter` و `setter` برای ویژگی‌های `firstName`، `lastName` و `gender` ایجاد شده‌اند.

این کلاس به منظور نگهداری و مدیریت اطلاعات شخصی  است.

همچنین کلاس های Student و Employee از این کلاس ارث بری می کنند.

   `````````````````````````````````````````````````````

this code defines a `Person` class with private fields `firstName`, `lastName`, and `gender`.
It also includes a constructor and getter/setter methods for these fields.

Here's a breakdown of the code:

    - The `Person` class has three private fields: `firstName`, `lastName`, and `gender`.
    - The class has a constructor that takes `firstName`, `lastName`, and `gender` as parameters.
      It initializes the corresponding fields with the provided values.
    - There are getter methods (`getFirstName()`, `getLastName()`, and `getGender()`) that return the values of
      the respective fields.
    - There are setter methods (`setFirstName()`, `setLastName()`, and `setGender()`) that set new values for the fields.

This `Person` class serves as a basic representation of a person with their first name, last name, and gender.
The getter and setter methods allow accessing and modifying these attributes.
 */

public class Person {
    private String firstName;
    private String lastName;
    private Gender gender;

    public Person(String firstName, String lastName, Gender gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}

//  1402/03/10 03:00 a.m. ~ 31 - 5 - 2023
