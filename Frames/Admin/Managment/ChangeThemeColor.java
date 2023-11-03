package Frames.Admin.Managment;

/*
این کد یک کلاس به نام ChangeThemeColor ایجاد می کند که یک متد به نام changeColor در آن تعریف شده است.
این متد برای تغییر رنگ تم در برنامه استفاده می شود.
 ورودی های این متد شامل کد رنگ جدید، مسیر فایلی که باید تغییر کند، کد رنگ قدیمی و کد رنگ جدید می باشد.

در ابتدای متد، رنگ جدید از طریق جدا کردن مقادیر RGB از رشته کد رنگ به دست می آید.
 سپس با استفاده از FileReader و BufferedReader، محتوای فایل مورد نظر خوانده می شود.
  در این مرحله، خط به خط فایل چک می شود و اگر کد رنگ قدیمی در آن یافت شد،
   با استفاده از StringBuilder، یک خط جدید با کد رنگ جدید ساخته می شود.
   در غیر این صورت، خط اصلی فایل به صورت عادی به StringBuilder اضافه می شود.

در نهایت، با استفاده از FileWriter و BufferedWriter، محتوای StringBuilder در فایل جایگزین شده و تغییرات اعمال می شود.
در صورت بروز خطا، صدایی با استفاده از متد errorSound پخش می شود.

همچنین لازم به ذکر است که تغییرات رنگ ایجاد شده در فایل جاوا در هنگام کامپایل شدن فایل امکان پذیر نخواهد بود.
 راه حل این است که تنظیمات رنگ را از یک فایل دیگر بخوانید و آن را فراخوانی و ویرایش کنید.

   `````````````````````````````````````````````````````
This code defines a class called "ChangeThemeColor" that contains a static "changeColor" method. This method is used to
change the theme color in a specified file.

Here is a code breakdown:

    - The 'changeColor' method has four parameters: 'color', 'path', 'oldCode' and 'newCode'.
        - "color" is a string representing RGB color values separated by commas (eg "255,0,0" for red).
        - "path" is a string that indicates the path of the file where the color change should be done.
        - "oldCode" is a string representing the code or text to be replaced.
        - "newCode" is a string that represents the new code or text that replaces the old code.

    - Inside the method, the RGB color values are extracted from the 'color' string using the split method and
      converted into integers.

    - This method then tries to read the contents of the file specified by the path parameter.
        - Uses "BufferedReader" to read the file line by line.

    - When reading each line, the method checks if the line contains "oldCode".
        - If it does, it adds a new line to the 'content' string that defines a new color using 'newCode' and
          the previously extracted RGB values.
        - If the line does not contain "oldCode", it adds the line as is to the "content" string.

    - After reading all lines, "BufferedReader" is closed.

    - This method then creates a 'FileWriter' and a 'BufferedWriter' to return the modified 'content' string to the file.
        - Overwrites the content of the original file with the modified content.

    - Finally "BufferedWriter" is closed.

    - If an exception occurs during the process of reading or writing the file, the "errorSound" method is called.
      However, the implementation of the 'errorSound' method is not provided in the code snippet.

To use this code, you can call the 'changeColor' method with appropriate parameters to change the theme color in a specific file.

Please note that this code does not handle all possible error scenarios such as file not found or invalid color format.
You may need to tailor those to your specific needs. It should also be mentioned that the color changes created in
the Java file will not be possible when the file is compiled; The solution is to read the color settings from
another file and call and edit it.
 */

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.io.*;

import static Classes.Theme.SoundEffect.errorSound;

public class ChangeThemeColor extends JFrame {
    public static void changeColor(String color , String path , String oldCode , String newCode) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        int red = Integer.parseInt(color.split(",")[0]);
        int green = Integer.parseInt(color.split(",")[1]);
        int blue = Integer.parseInt(color.split(",")[2]);

        try {

            File file = new File(path);
            BufferedReader reader = new BufferedReader(new FileReader(file));

            String line;
            StringBuilder content = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                if (line.contains(oldCode)) {
                    content.append("    public static Color " + newCode + " = new Color(" + red + "," + green + "," + blue + ");" ).append(System.lineSeparator());
                } else {
                    content.append(line).append(System.lineSeparator());
                }
            }
            reader.close();

            FileWriter writer = new FileWriter(file);
            BufferedWriter bufferWriter = new BufferedWriter(writer);
            bufferWriter.write(content.toString());
            bufferWriter.close();

        } catch (Exception e) {
            errorSound();
        }
    }
}


//   1402/04/01 03:00 a.m. ~ 22 - 6 - 2023