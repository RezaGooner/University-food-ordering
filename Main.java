/*
The code you provided is the `main` method of a class called "Main." Here is a breakdown of the code:

1. The `main` method is the entry point of the program and is where the execution starts.

2. Within the `main` method, there is a try-catch block to handle any exceptions that might occur.

3. Inside the try block, several `Font` objects are created with different styles and sizes.

4. The UIManager class is used to set the default fonts for various Swing components. The "Label" font is set to `font`, the "Button" font is set to `fontBold`, the "TextField" font is set to `font`, and the "Menu" font is set to `fontSmall`.

5. The `setLookAndFeel` method is called to set the look and feel of the Swing components to the "Nimbus" look and feel.

6. If any exception occurs during the try block, it is caught by the catch block, and no specific action is taken.

7. Finally, a new instance of the `LoginFrame` class is created, which presumably launches the login screen of the application.

Overall, this code sets the default fonts and look and feel for Swing components and then creates an instance of the `LoginFrame` class to start the application.
 */

import Frames.LoginFrame;

import javax.swing.*;
import java.awt.*;

public class  Main {
    public static void main(String[] args)  {
        try {
            Font fontSmall = new Font("Tahoma", Font.PLAIN, 10);
            Font font = new Font("Tahoma", Font.PLAIN, 12);
            Font fontBold = new Font("Tahoma", Font.BOLD, 12);

            UIManager.put("Label.font", font);
            UIManager.put("Button.font", fontBold);
            UIManager.put("TextField.font", font);
            UIManager.put("Menu.font", fontSmall);

            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
        }
        new LoginFrame();
    }
}

/*
    7k line    - 280k Character
    1402/02/30 ~ 1402/04/02
    20 - 5 - 2023 ~  23 - 6 - 2023
    Reza Asadi
*/