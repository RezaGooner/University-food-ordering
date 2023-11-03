package Frames.Admin.Managment;

/*
این کد    به کاربر امکان تغییر رنگ منوهای مختلف برنامه را می دهد.

کلاس ColorChangeMenu از کلاس JFrame گسترش می یابد و یک فریم جدید با عنوان "تغییر رنگ منو ها" ایجاد می کند
 و آن را غیر قابل تغییر اندازه می کند. سپس یک پنل با یک طرح بندی شبکه ای 5 در 3 ایجاد می شود.

پنل شامل چندین JLabel و JButton است، هر کدام نماینده یک منو مختلف و رنگ پس زمینه و دکمه های آن است.
 هر دکمه دارای یک ActionListener است که متد setColor() را فراخوانی می کند
  و با استفاده از مقدار رنگ برگشتی، رنگ مناسب منو را تغییر می دهد.
   متد changeColor() با مقدار جدید رنگ و مسیر به کلاسی که متغیرهای رنگ استاتیک آن باید تغییر کنند، فراخوانی می شود.

در انتهای پنل، یک JMenuBar با یک مورد JMenu "بیشتر" و یک JMenuItem "تنظیمات اولیه" اضافه می شود.
با کلیک بر روی این JMenuItem، تمام رنگ های منو به مقادیر پیش فرض بازنشانی می شوند.

پنل سپس به فریم اضافه می شود، که با اندازه 500 در 500 پیکسل قابل مشاهده است.

   `````````````````````````````````````````````````````

It provides a static method `setColor()` that opens a color chooser dialog (`JColorChooser`) and returns the selected
color as a string in the format "red,green,blue".

Here is a breakdown of the code:

    1. The code imports various classes from different packages, including `java.awt` for basic AWT components,
       `javax.sound.sampled` for audio-related classes, and `javax.swing` for GUI components.

    2. The `ColorChooser` class is defined.

    3. The `setColor()` method is defined as a static method. It is declared to throw `UnsupportedAudioFileException`,
       `LineUnavailableException`, and `IOException`, although it doesn't seem to use or throw these exceptions.

    4. Within the `setColor()` method, several variables are declared:
       - `s` is an array of type String with a length of 1.
       - `red`, `green`, and `blue` are arrays of type int with a length of 1.

    5. The `JColorChooser.showDialog()` method is called to display a color chooser dialog. The dialog is displayed
       with a null parent component, a title of "رنگ انتخاب شده" (which translates to "Selected Color" in Persian),
       and a null initial color.

    6. If the user selects a color and clicks "OK" in the color chooser dialog, the `selectedColor` variable will hold
       the chosen color. Otherwise, it will be null.

    7. If `selectedColor` is not null, the values for red, green, and blue components of the selected color are obtained
       using the `getRed()`, `getGreen()`, and `getBlue()` methods, respectively.

    8. The obtained red, green, and blue values are stored in the `red[0]`, `green[0]`, and `blue[0]` variables, respectively.

    9. A message dialog (`JOptionPane.showMessageDialog()`) is displayed with a null parent component and
       a message of "تغییرات با موفقیت اعمال گردید." (which translates to "Changes applied successfully" in Persian).

    10. Finally, the method returns a string concatenating the red, green, and blue values separated by commas.

    11. If no color is selected (i.e., `selectedColor` is null), the method returns null.

Overall, this code provides a utility method for opening a color chooser dialog and
extracting the selected color's RGB values as a string.

 */

import java.awt.*;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;


public class ColorChooser {
    public static String setColor() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        final String[] s = new String[1];
        final int[] red = new int[1];
        final int[] green = new int[1];
        final int[] blue = new int[1];

        Color selectedColor = JColorChooser.showDialog(null,"رنگ انتخاب شده",null);
        if(selectedColor != null) {
            red[0] = selectedColor.getRed();
            green[0] = selectedColor.getGreen();
            blue[0] = selectedColor.getBlue();
            JOptionPane.showMessageDialog(null , "تغییرات با موفقیت اعمال گردید.");
            return (red[0] + "," + green[0] + "," + blue[0]);
        }
        return null;
    }
}


//   1402/04/01 03:00 a.m. ~ 22 - 6 - 2023