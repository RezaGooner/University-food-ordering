package Classes.Pathes;

/*
این کد یک کلاس است که مسیرهای کلاس ها را در قالب رشته هایی از آدرس فایل مشخص می کند.
از این کلاس برای دسترسی به فایل های مختلف در برنامه استفاده می شود.
 این کلاس شامل چندین ثابت استاتیک است که هر یک یک مسیر فایل را نشان می دهد.

  برای مثال، LoginFramePath نشان می دهد که
   فایل LoginFrame.java در آدرس E:/Programming/University/Term 2/Food Self/Food Self/src/Frames وجود دارد

   `````````````````````````````````````````````````````

This code defines a Java class named "ClassesPath" that contains several static string variables.
These variables represent file paths in the file system.
The paths are specified for Java files related to various frames and classes in a project named "Food Self".

For example, the variable "LoginFramePath" specifies the file path for "LoginFrame.
java" in the file system. Similarly, the other variables specify file paths for different files.
These paths are set based on the file locations in your file system and may be different for you.

This class and its static variables can be used in other code to easily access the file paths of Java files
in the project and make use of them.

 */

public class ClassesPath {
     public static String LoginFramePath = "LoginFrame.java" ;
     public static String ChangePasswordFramePath = "ChangePasswordFrame.java";
     public static String ForgotPasswordPath = "ForgotPassword.java";
     public static String NewUserFramePath = "NewUserFrame.java";
     public static String UniversitySelfRestaurantPath = "UniversitySelfRestaurant.java";
}
