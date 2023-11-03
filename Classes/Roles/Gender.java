package Classes.Roles;

/*
این کد یک `enum` به نام `Gender` را شامل می‌شود که دو مقدار `MALE` و `FEMALE` دارد.

 از  این `enum` برای تعریف دو مقدار ثابت برای جنسیت استفاده شده است.

علاوه بر این، در این کد، یک `constructor` برای `Gender` تعریف شده است که به منظور نگهداری برچسب مربوط به هر مقدار `enum` استفاده می‌شود.

   `````````````````````````````````````````````````````

The provided code defines an enum called `Gender` with two constant values: `MALE` and `FEMALE`.
Each constant has an associated label in Persian ("مرد" for male and "زن" for female).

The enum declaration includes a constructor that takes a `label` parameter. However,
the constructor implementation is empty, meaning it does not perform any specific actions.

Enums are used to represent a fixed set of constants. In this case, the `Gender` enum represents
the genders "male" and "female" with their corresponding Persian labels.


In the `Person` class, you can store the `Gender` as a field and utilize the `getLabel()` method to
retrieve the Persian label corresponding to the gender.
For example, if you have an instance of `Person` called `person`, you can call `person.getGenderLabel()`
to get the Persian label for the person's gender.

 */

public enum Gender {
    MALE("مرد"),
    FEMALE("زن");

    Gender(String label) {
    }
}

//   1402/03/09 00:00 a.m. ~ 30 - 5 - 2023