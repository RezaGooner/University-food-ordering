package Frames.Admin.FoodManagment;

/*
این کد یک پنجره برای ویرایش لیست غذاهای ارائه شده در سیستم غذایی ایجاد می‌کند.
 در این پنجره، مدیران می‌توانند محتویات فایل‌های مربوط به ناهار و شام را مشاهده کرده و تغییرات مورد نظر را اعمال کنند.

در متد سازنده، یک JTabbedPane ایجاد و دو تب ناهار و شام به آن اضافه می‌شوند.
 برای هر یک از این تب‌ها، یک JTextArea برای نمایش محتویات فایل‌های مربوطه و یک JButton برای ذخیره تغییرات اضافه می‌شود.
  سپس، محتویات فایل‌های ناهار و شام خوانده و در JTextArea مربوطه نمایش داده می‌شوند.

در متد `actionPerformed`، که از رابط ActionListener پیاده‌سازی شده است، برای ذخیره تغییرات، متد `saveChanges` فراخوانی می‌شود.
 در این متد، محتویات هر یک از JTextAreaها خوانده شده و در فایل مربوطه ذخیره می‌شوند.

   `````````````````````````````````````````````````````

This code defines an `EditFood` class that extends `JFrame` and is used to create a GUI application for
editing food items. Here's a breakdown of the code:

    - The `EditFood` constructor sets up the main components of the GUI, such as the tabbed pane,
      text areas for lunch and dinner, and buttons for saving changes.
    - The lunch and dinner text areas are embedded in separate panels and added to the tabbed pane.
    - The `removeLunchButton` and `removeDinnerButton` buttons are created and registered with the `actionPerformed`
      method to handle button click events.
    - The `readLunchFile` and `readDinnerFile` methods are responsible for reading the content of the lunch and dinner files,
      respectively, and displaying them in the corresponding text areas.
    - The `saveChanges` method is called when the user clicks on one of the save buttons.
      It writes the modified content of the lunch and dinner text areas back to the respective files.
    - The GUI is set up with a menu bar that includes a "Refresh" button to reload the content of the text areas.
    - The `setIconImage` method sets the icon for the application window, and the window is displayed with a specific size,
      location, and close operation.

Please note that there are some dependencies in the code, such as `Classes.Pathes.FilesPath.icon`, `Classes.Theme.
SoundEffect.errorSound`, `Frames.Order.UniversitySelfRestaurant.dinnerFilePath`, and `Frames.Order.UniversitySelfRestaurant.
lunchFilePath`. You need to ensure that these dependencies are properly resolved and that the necessary files exist for
the code to work correctly.

 */

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

import static Classes.Pathes.FilesPath.icon;
import static Classes.Theme.SoundEffect.errorSound;
import static Frames.Order.UniversitySelfRestaurant.dinnerFilePath;
import static Frames.Order.UniversitySelfRestaurant.lunchFilePath;

public class EditFood extends JFrame implements ActionListener {
    private JTabbedPane tabbedPane;
    private JTextArea lunchTextArea;
    private JTextArea dinnerTextArea;
    private JButton removeLunchButton;
    private JButton removeDinnerButton;

    public EditFood() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        super(" ویرایش غذاها ");

        tabbedPane = new JTabbedPane();

        lunchTextArea = new JTextArea();
        JScrollPane lunchScrollPane = new JScrollPane(lunchTextArea);
        JPanel lunchPanel = new JPanel(new BorderLayout());
        lunchPanel.add(lunchScrollPane, BorderLayout.CENTER);
        removeLunchButton = new JButton(" ذخیره تغییرات ");
        removeLunchButton.addActionListener(this);
        JPanel lunchButtonPanel = new JPanel(new FlowLayout());
        lunchButtonPanel.add(removeLunchButton);
        lunchPanel.add(lunchButtonPanel, BorderLayout.SOUTH);
        tabbedPane.addTab(" ناهار ", lunchPanel);

        dinnerTextArea = new JTextArea();
        JScrollPane dinnerScrollPane = new JScrollPane(dinnerTextArea);
        JPanel dinnerPanel = new JPanel(new BorderLayout());
        dinnerPanel.add(dinnerScrollPane, BorderLayout.CENTER);
        removeDinnerButton = new JButton(" ذخیره تغییرات ");
        removeDinnerButton.addActionListener(this);
        JPanel dinnerButtonPanel = new JPanel(new FlowLayout());
        dinnerButtonPanel.add(removeDinnerButton);
        dinnerPanel.add(dinnerButtonPanel, BorderLayout.SOUTH);
        tabbedPane.addTab(" شام ", dinnerPanel);

        add(tabbedPane);

        JFrame frame = new JFrame();

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu menu = new JMenu("بیشتر");
        menuBar.add(menu);
        JMenuItem refreshButton = new JMenuItem("تازه سازی");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                try {
                    new EditFood();
                } catch (Exception ex) {
                }
            }
        });
        menu.add(refreshButton);


        readLunchFile();
        readDinnerFile();

        setIconImage(icon.getImage());
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            saveChanges();
        } catch (Exception ex) {
        }
    }

    private void readLunchFile() {
        try {
            FileReader lunchFileReader = new FileReader(lunchFilePath);
            BufferedReader lunchBufferedReader = new BufferedReader(lunchFileReader);
            String lunchLine;
            while ((lunchLine = lunchBufferedReader.readLine()) != null) {
                lunchTextArea.append(lunchLine + "\n");
            }
            lunchBufferedReader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void readDinnerFile() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        try {
            FileReader dinnerFileReader = new FileReader(dinnerFilePath);
            BufferedReader dinnerBufferedReader = new BufferedReader(dinnerFileReader);
            String dinnerLine;
            while ((dinnerLine = dinnerBufferedReader.readLine()) != null) {
                dinnerTextArea.append(dinnerLine + "\n");
            }
            dinnerBufferedReader.close();
        } catch (Exception ex) {
            errorSound();
        }
    }

    private void saveChanges() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        try {
            FileWriter lunchFileWriter = new FileWriter(lunchFilePath);
            BufferedWriter lunchBufferedWriter = new BufferedWriter(lunchFileWriter);
            String[] lunchLines = lunchTextArea.getText().split("\n");
            for (String line : lunchLines) {
                lunchBufferedWriter.write(line + "\n");
            }
            lunchBufferedWriter.close();

            FileWriter dinnerFileWriter = new FileWriter(dinnerFilePath);
            BufferedWriter dinnerBufferedWriter = new BufferedWriter(dinnerFileWriter);
            String[] dinnerLines = dinnerTextArea.getText().split("\n");
            for (String line : dinnerLines) {
                dinnerBufferedWriter.write(line + "\n");
            }
            dinnerBufferedWriter.close();

            JOptionPane.showMessageDialog(this, "تغییرات انجام شد.");
        } catch (Exception ex) {
            errorSound();
            JOptionPane.showMessageDialog(this, "خطا.");
        }
    }


}


//  1402/03/29 05:00 a.m. ~ 19 - 6 - 2023
