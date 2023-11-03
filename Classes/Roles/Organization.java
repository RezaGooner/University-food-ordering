package Classes.Roles;
/*
این کد یک `enum` به نام `Organization` را شامل می‌شود که چهار مقدار `NOT`، `KOMITE`، `BEHZISTI` و `MOMTAZ` دارد.

در این `enum`، هر مقدار یک برچسب را نگهداری می‌کند که با استفاده از `toString`، به صورت رشته بازگشت داده می‌شود.

از `enum` برای تعریف ثابت برای سازمان‌های مختلف استفاده شده است .

 این `enum` می‌تواند در برنامه‌های مدیریت دانشجویان برای نگهداری و مدیریت تخفیف غذای  دانشجو در برنامه استفاده شود.

   `````````````````````````````````````````````````````

 This code instantiates a number called "organization" with four values: "NOT", "KOMITE", "BEHZISTI" and "MOMTAZ".
 Each indicator represents a livelihood support organization or having a high GPA and has a tag associated with Farsi.

The enum declaration includes a constructor that takes the "tag".
The constructor assigns "label" to the "label" field of any enum constant.

The ``Organization'' enum also overrides the ``toString()'' method inherited from
 the ``Enum'' class. Overridden 'toString()' method removes the enum constant 'label' when called.

In the "VipStudent" class, you can save the "organization" as a file and use the "toString()" method
to retrieve the Farsi label of the organization. For example, if you have an instance of
"VipStudent" called "vipStudent", you can call "vipStudent.getOrganizationLabel()" to get
 the Farsi labels of the student organization.

 */

public enum Organization {
    NOT ("سایر"),
    KOMITE("کمیته امام"),
    BEHZISTI("سازمان بهزیستی"),
    MOMTAZ("دانشجوی ممتاز");

    private final String label;

    Organization(String label) {
        this.label = label;
    }

    public String toString() {
        return label;
    }
}

//   1402/03/09 00:00 a.m. ~ 30 - 5 - 2023