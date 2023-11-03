package Frames.Admin.Managment;

/*
این کد جاوا یک کلاس است که متد setColor() را دارد.
 این متد یک پنجره رنگ انتخاب کننده باز می کند و رنگ انتخاب شده توسط کاربر را برمی گرداند.

 در ابتدا، یک آرایه رشته ای و سه آرایه از integer تعریف می شود.
  پس از باز شدن پنجره رنگ انتخاب کننده، مقادیر رنگ قرمز، سبز و آبی را از رنگ انتخاب شده به دست می آورد
  و آنها را در آرایه های integer مربوطه ذخیره می کند.

   سپس پیامی به کاربر نمایش داده می شود که تغییرات با موفقیت اعمال شده است
   و رشته ای که شامل مقادیر رنگ است برمی گردانده می شود.

    در صورتی که کاربر هیچ رنگی را انتخاب نکرده باشد، مقدار null برگردانده می شود.

       `````````````````````````````````````````````````````

It extends the `JFrame` class and provides a graphical user interface for changing the colors of different frames in
a Java Swing application.

Here is a breakdown of the code:

    1. The code imports various classes from different packages, including `javax.swing` for GUI components and
       `java.awt` for basic AWT components.

    2. The `ColorChangeMenu` class is defined, which extends the `JFrame` class.

    3. The constructor of the `ColorChangeMenu` class is defined. It sets up the main frame by setting the title,
       making it non-resizable, and positioning it at the center of the screen.

    4. A `JPanel` named `panel` is created with a grid layout of 5 rows and 1 column. This panel will contain other
       panels representing different menus.

    5. Five panels (`panel1` to `panel5`) are created, each representing a different menu. Each panel has a titled border.

    6. Within each panel, there are two buttons and two labels representing the background color and button color of
       the corresponding frame. The buttons are initially set to the current colors of the frames. When clicked,
       these buttons open a color chooser dialog (`JColorChooser`) that allows the user to select a new color.

    7. The `ColorChangeListener` class is defined as an inner class within `ColorChangeMenu`.
       This class implements the `ActionListener` interface and is responsible for handling color change events when
       the buttons are clicked.

    8. The `actionPerformed` method of `ColorChangeListener` is implemented to handle the button click events.
       It opens a color chooser dialog and retrieves the selected color. Then it updates the background color or button color of the corresponding frame, based on which button was clicked.

    9. The `main` method is defined, which creates an instance of `ColorChangeMenu` and sets it visible.

Overall, this code creates a GUI with multiple panels, each allowing the user to change the colors of different frames
in a Java Swing application.
 */


import Frames.LoginFrame;
import Frames.Order.UniversitySelfRestaurant;
import Frames.Profile.ForgotPassword;
import Frames.Profile.NewUserFrame;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Classes.Pathes.ClassesPath.*;
import static Frames.Admin.Managment.ChangeThemeColor.changeColor;
import static Frames.Admin.Managment.ColorChooser.setColor;
import static Frames.Profile.ChangePasswordFrame.colorBackground;
import static Frames.Profile.ChangePasswordFrame.colorButton;

public class ColorChangeMenu extends JFrame {
    public  ColorChangeMenu() {
        setTitle("تغییر رنگ منو ها");
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 1));


        JPanel panel1 =  new JPanel ();
        panel1.setBorder(new TitledBorder(null, "منوی ورود", TitledBorder.LEFT, TitledBorder.TOP));

        JButton button12 = new JButton("");
        button12.setBackground(LoginFrame.colorBackground);
        button12.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String Color = setColor();
                    changeColor(Color , LoginFramePath , "public static Color colorButton" , "colorButton" );
                    button12.setBackground(new Color(Integer.parseInt(Color.split(",")[0]),
                            Integer.parseInt(Color.split(",")[1]),
                            Integer.parseInt(Color.split(",")[2])));
                } catch (Exception ex) {}
            }
        });
        JLabel label12 = new JLabel("پس زمینه");
        panel1.add(button12);
        panel1.add(label12);
        panel.add(panel1);

        JButton button13 = new JButton("");
        button13.setBackground(LoginFrame.colorButton);
        button13.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String Color = setColor();
                    button13.setBackground(new Color(Integer.parseInt(Color.split(",")[0]),
                            Integer.parseInt(Color.split(",")[1]),
                            Integer.parseInt(Color.split(",")[2])));
                    changeColor(setColor() , LoginFramePath ,
                            "public static Color colorBackground" , "colorBackground" );
                } catch (Exception ex) {}
            }
        });
        JLabel label13 = new JLabel("دکمه ها");
        panel1.add(button13);
        panel1.add(label13);
        panel.add(panel1);

        JPanel panel2 =  new JPanel ();
        panel2.setBorder(new TitledBorder(null, "منوی تغییر رمز", TitledBorder.LEFT, TitledBorder.TOP));
        JButton button22 = new JButton("");
        button22.setBackground(colorBackground);
        button22.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String Color = setColor();
                    button22.setBackground(new Color(Integer.parseInt(Color.split(",")[0]),
                            Integer.parseInt(Color.split(",")[1]),
                            Integer.parseInt(Color.split(",")[2])));
                    changeColor(setColor() , ChangePasswordFramePath,
                            "public static Color colorBackground" , "colorBackground" );
                } catch (Exception ex) {}
            }
        });
        JLabel label22 = new JLabel("پس زمینه");
        panel2.add(button22);
        panel2.add(label22);

        JButton button23 = new JButton("");
        JLabel label23 = new JLabel("دکمه ها");
        button23.setBackground(colorButton);
        button23.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String Color = setColor();
                    button23.setBackground(new Color(Integer.parseInt(Color.split(",")[0]),
                            Integer.parseInt(Color.split(",")[1]),
                            Integer.parseInt(Color.split(",")[2])));
                    changeColor(setColor() , ChangePasswordFramePath ,
                            "public static Color colorButton" , "colorButton" );
                } catch (Exception ex) {}
            }
        });
        panel2.add(button23);
        panel2.add(label23);
        panel.add(panel2);


        JPanel panel3 =  new JPanel ();
        panel3.setBorder(new TitledBorder(null, "منوی فراموشی رمز", TitledBorder.LEFT, TitledBorder.TOP));
        JButton button32 = new JButton("");
        JLabel label32 = new JLabel("پس زمینه");
        button32.setBackground(ForgotPassword.colorBackground);
        button32.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String Color = setColor();
                    button32.setBackground(new Color(Integer.parseInt(Color.split(",")[0]),
                            Integer.parseInt(Color.split(",")[1]),
                            Integer.parseInt(Color.split(",")[2])));
                    changeColor(setColor() , ForgotPasswordPath ,
                            "public static Color colorBackground" , "colorBackground" );
                } catch (Exception ex) {}
            }
        });
        panel3.add(button32);
        panel3.add(label32);

        JButton button33 = new JButton("");
        JLabel label33 = new JLabel("دکمه ها");
        button33.setBackground(ForgotPassword.colorButton);
        button33.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String Color = setColor();
                    button33.setBackground(new Color(Integer.parseInt(Color.split(",")[0]),
                            Integer.parseInt(Color.split(",")[1]),
                            Integer.parseInt(Color.split(",")[2])));
                    changeColor(setColor() , ForgotPasswordPath ,
                            "public static Color colorButton" , "colorButton" );
                } catch (Exception ex) {}
            }
        });
        panel3.add(button33);
        panel3.add(label33);
        panel.add(panel3);

        JPanel panel4 =  new JPanel ();
        panel4.setBorder(new TitledBorder(null, "منوی کاربر جدید", TitledBorder.LEFT, TitledBorder.TOP));

        JButton button42 = new JButton("");
        button42.setBackground(NewUserFrame.colorBackground);
        button42.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String Color = setColor();
                    button42.setBackground(new Color(Integer.parseInt(Color.split(",")[0]),
                            Integer.parseInt(Color.split(",")[1]),
                            Integer.parseInt(Color.split(",")[2])));
                    changeColor(setColor() , NewUserFramePath ,
                            "public static Color colorBackground" , "colorBackground" );
                } catch (Exception ex) {}
            }
        });
        JLabel label42 = new JLabel("پس زمینه");
        panel4.add(button42);
        panel4.add(label42);

        JButton button43 = new JButton("");
        JLabel label43 = new JLabel("دکمه ها");
        button43.setBackground(NewUserFrame.colorButton);
        button43.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String Color = setColor();
                    button43.setBackground(new Color(Integer.parseInt(Color.split(",")[0]),
                            Integer.parseInt(Color.split(",")[1]),
                            Integer.parseInt(Color.split(",")[2])));
                    changeColor(setColor() , NewUserFramePath ,
                            "public static Color colorButton" , "colorButton" );
                } catch (Exception ex) {}
            }
        });
        panel4.add(button43);
        panel4.add(label43);
        panel.add(panel4);


        JPanel panel5 =  new JPanel ();
        panel5.setBorder(new TitledBorder(null, "منوی سفارش", TitledBorder.LEFT, TitledBorder.TOP));
        JButton button52 = new JButton("");
        button52.setBackground(UniversitySelfRestaurant.colorBackground);
        JLabel label52 = new JLabel("پس زمینه");
        button52.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String Color = setColor();
                    button52.setBackground(new Color(Integer.parseInt(Color.split(",")[0]),
                            Integer.parseInt(Color.split(",")[1]),
                            Integer.parseInt(Color.split(",")[2])));
                    changeColor(setColor() , UniversitySelfRestaurantPath ,
                            "public static Color colorBackground" , "colorBackground" );
                } catch (Exception ex) {}
            }
        });
        panel5.add(button52);
        panel5.add(label52);

        JButton button53 = new JButton("");
        button53.setBackground(UniversitySelfRestaurant.colorButton);
        JLabel label53 = new JLabel("دکمه ها");
        button53.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String Color = setColor();
                    button53.setBackground(new Color(Integer.parseInt(Color.split(",")[0]),
                            Integer.parseInt(Color.split(",")[1]),
                            Integer.parseInt(Color.split(",")[2])));
                    changeColor(setColor() , UniversitySelfRestaurantPath ,
                            "public static Color colorButton" , "colorButton" );
                } catch (Exception ex) {}
            }
        });
        panel5.add(button53);
        panel5.add(label53);
        panel.add(panel5);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu menu = new JMenu("بیشتر");
        menuBar.add(menu);

        JMenuItem renewButton = new JMenuItem("تنظیمات اولیه");
        renewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    changeColor("255,255,51" , LoginFramePath ,
                            "public static Color colorButton" , "colorButton" );

                    changeColor("241,255,85" , LoginFramePath ,
                            "public static Color colorBackground" , "colorBackground" );

                    changeColor("211,77,210" , ChangePasswordFramePath ,
                            "public static Color colorBackground" , "colorBackground" );

                    changeColor("165,11,94" , ChangePasswordFramePath ,
                            "public static Color colorButton" , "colorButton" );

                    changeColor( "255,214,153", ForgotPasswordPath ,
                            "public static Color colorBackground" , "colorBackground" );

                    changeColor("252,174,30" , ForgotPasswordPath ,
                            "public static Color colorButton" , "colorButton" );

                    changeColor("136,226,242" , NewUserFramePath ,
                            "public static Color colorBackground" , "colorBackground" );

                    changeColor("19,56,190" , NewUserFramePath ,
                            "public static Color colorButton" , "colorButton" );

                    changeColor("207,255,199" , UniversitySelfRestaurantPath ,
                            "public static Color colorBackground" , "colorBackground" );

                    changeColor("45,206,152" , UniversitySelfRestaurantPath ,
                            "public static Color colorButton" , "colorButton" );

                    JOptionPane.showMessageDialog(null , "تنظیمات رنگ صفحات با موفقیت بازنشانی شد.");
                    dispose();

                } catch (Exception exception){

                }
            }
        });
        menu.add(renewButton);
        add(panel);
        setVisible(true);
        setSize(500,500);
    }
}


//   1402/04/01 03:00 a.m. ~ 22 - 6 - 2023