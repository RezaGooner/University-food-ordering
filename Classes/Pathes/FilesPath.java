package Classes.Pathes;

/*
این کد یک کلاس استاتیک به نام FilesPath ایجاد می کند که مسیرهای مختلف فایل ها را به صورت ثابت در خود ذخیره می کند.
این مسیرها شامل مسیرهای فایل های مربوط به اطلاعات کاربران، تراکنش ها، تخفیف ها، صداها و ... است.

این مسیرهای ثابت به صورت public و static تعریف شده اند و می توانند به راحتی در سایر قسمت های برنامه در دسترس باشند.
 به عنوان مثال، در کد دیگری می توان از مسیر فایل help.txt با استفاده از FilesPath.HelpPath استفاده کرد.

   `````````````````````````````````````````````````````

This code defines a Java class named "FilesPath" that contains several static variables representing file paths
in the file system. These paths are specified for various files in the project.

For example, the variable "icon" is an instance of the ImageIcon class and represents the file path for
an image file named "icon.png" in the specified location.
Similarly, the other variables specify file paths for different files such as
"userpass.txt", "log.txt", "balances.txt", and so on.

These file paths are set based on the file locations in your file system and may be different for you.
The class also includes paths for sound files such as "WellcomeSoundPath", "ErrorSoundPath", and "SuccessSoundPath".

The purpose of this class is to provide easy access to these file paths in other parts of the code,
allowing you to load or manipulate the corresponding files as needed.
 */

import javax.swing.*;

public class FilesPath {

    public static ImageIcon icon = new ImageIcon("Source/icon.png");
    public static String iconPath = "Source/icon.png";

    public static String UserPassPath = "userpass.txt";
    public static  String LogPath = "log.txt";
    public static String BalancesPath = "balances.txt";
    public static String ChargeHistoryPath = "charge_history.txt";
    public static String DiscountsPath = "discounts.txt";
    public static String WellcomeSoundPath = "Source/Windows User Account Control.wav";
    public static String ErrorSoundPath = "Source/Windows Exclamation.wav";
    public static String SuccessSoundPath = "Source/Windows Unlock.wav";
    public static String AdminsPath = "admin.txt";
    public static String HelpPath = "help.txt";
    public static String OrdersPath = "orders.txt";



}

//  1402/04/01 03:00 p.m. ~ 22 - 6 - 2023
