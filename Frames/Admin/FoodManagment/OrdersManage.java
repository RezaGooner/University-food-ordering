package Frames.Admin.FoodManagment;

/*
این کد یک پنجره مدیریت سفارشات را ایجاد می‌کند.
 در این پنجره، admin می‌تواند سفارشات را مشاهده، اضافه، ویرایش، و حذف کند.
  برای این منظور، از یک JTable برای نمایش داده‌ها استفاده شده است.
   همچنین، از یک DefaultTableModel برای مدیریت داده‌ها، و از یک TableRowSorter برای مرتب‌سازی و جستجو در داده‌ها استفاده شده است.

در متد سازنده، ابتدا آرایه‌هایی که شامل گزینه‌ها و قیمت‌های ناهار و شام هستند، ساخته می‌شوند.
سپس، داده‌های موجود در فایل سفارشات خوانده می‌شوند و به جدول اضافه می‌شوند.
 در ادامه، پنلی برای جستجو، و سه دکمه برای اضافه کردن، ویرایش و حذف داده‌ها به پنجره اضافه می‌شوند.
 سپس، جدول، پنل جستجو، و دکمه‌ها به پنجره اضافه می‌شوند. در نهایت، تنظیمات جدول و پنجره انجام می‌شود و پنجره نمایش داده می‌شود.

   `````````````````````````````````````````````````````

This code defines an `OrdersManage` class that extends `JFrame` and is used to create a GUI application
for managing food orders. Here's a breakdown of the code:

    - The `OrdersManage` constructor sets up the main components of the GUI.
      It sets the title of the application window and creates the table, table model, and row sorter for managing the orders.
    - The constructor reads the orders from a file and populates the table with the data.
    - It creates a search panel with a text field that allows users to filter the orders based on their input.
    - Buttons for adding, editing, and deleting orders are created and registered with action listeners to
      handle button click events.
    - The constructor also creates a menu bar with a "Refresh" menu item that allows users to refresh the orders.
    - The `saveToFile` method is used to save the orders to a file.
    - The `CreateDinnerArray` and `CreateLunchArray` methods are used to read the dinner and lunch options from
      files and populate the corresponding arrays.

Please note that there are some dependencies in the code, such as `Classes.Pathes.FilesPath.icon`, `Classes.Theme.
SoundEffect.errorSound`, `Frames.Order.UniversitySelfRestaurant.orderFilename`, `Frames.Order.UniversitySelfRestaurant.
dinnerFilePath`, and `Frames.Order.UniversitySelfRestaurant.lunchFilePath`. You need to ensure that these dependencies
are properly resolved and that the necessary files exist for the code to work correctly.

 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import static Classes.Pathes.FilesPath.icon;
import static Classes.Theme.SoundEffect.errorSound;
import static Frames.Order.UniversitySelfRestaurant.*;

public class OrdersManage extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private List<String[]> data;
    private TableRowSorter<DefaultTableModel> sorter;
    private static String[] lunchOptions = new String[7];
    private static int[] lunchPrices = new int[7];
    private static String[] dinnerOptions = new String[7];
    private static int[] dinnerPrices = new int[7];

    public OrdersManage() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        super("سفارشات");

        CreateDinnerArray();
        CreateLunchArray();

        data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(new File(orderFilename)))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                data.add(parts);
            }
        } catch (Exception e) {
            errorSound();
        }

        tableModel = new DefaultTableModel();
        tableModel.addColumn("شناسه");
        tableModel.addColumn("تاریخ");
        tableModel.addColumn("ناهار");
        tableModel.addColumn("شام");

        for (String[] row : data) {
            tableModel.addRow(row);
        }

        table = new JTable(tableModel);

        JPanel searchPanel = new JPanel();
        JTextField searchField = new JTextField(20);
        searchField.addActionListener(e -> {
            String text = searchField.getText();
            if (text.length() == 0) {
                sorter.setRowFilter(null);
            } else {
                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
            }
        });
        searchPanel.add(searchField);

        JButton addButton = new JButton("اضافه کردن");
        addButton.addActionListener(e -> {
            String id = JOptionPane.showInputDialog(this, "شناسه را وارد نمایید :");
            if (id != null) {
                String date = JOptionPane.showInputDialog(this, "تاریخ را وارد نمایید :  YYYY-MM-DD");
                if (date != null) {
                    String lunch = (String) JOptionPane.showInputDialog(this, "ناهار را انتخاب کنید :",
                            "ناهار", JOptionPane.PLAIN_MESSAGE, null, lunchOptions, lunchOptions[0]);
                    if (lunch != null){
                        String dinner = (String) JOptionPane.showInputDialog(this, "شام را انتخاب کنید :",
                                "شام", JOptionPane.PLAIN_MESSAGE, null, dinnerOptions, dinnerOptions[0]);
                        if (dinner != null) {
                            String[] row = { id, date, lunch, dinner };
                            data.add(row);
                            tableModel.addRow(row);
                            try {
                                saveToFile();
                            } catch (Exception ex) {
                            }
                        }
                    }
                }
            }
        });

        JButton editButton = new JButton("ویرایش ");
        editButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String id = JOptionPane.showInputDialog(this, "شناسه را وارد نمایید :",
                        table.getValueAt(selectedRow, 0));
                if (id != null) {
                    String date = JOptionPane.showInputDialog(this, "تاریخ را وارد نمایید :  YYYY-MM-DD\n",
                            table.getValueAt(selectedRow, 1));
                    if (date != null) {
                        String lunch = (String) JOptionPane.showInputDialog(this,  "ناهار جایگزین " + table.getValueAt(selectedRow, 2) + " را انتخاب کنید : ",
                                "ناهار", JOptionPane.PLAIN_MESSAGE, null, lunchOptions, lunchOptions[0]);
                        if (lunch != null) {
                            String dinner = (String) JOptionPane.showInputDialog(this,  "شام جایگزین " + table.getValueAt(selectedRow, 3) + " را انتخاب کنید : ",
                                    "شام", JOptionPane.PLAIN_MESSAGE, null, dinnerOptions, dinnerOptions[0]);
                            if (dinner != null) {
                                String[] row = { id, date, lunch, dinner };
                                data.set(selectedRow, row);
                                tableModel.setValueAt(id, selectedRow, 0);
                                tableModel.setValueAt(date, selectedRow, 1);
                                tableModel.setValueAt(lunch, selectedRow, 2);
                                tableModel.setValueAt(dinner, selectedRow, 3);

                                try {
                                    saveToFile();
                                } catch (Exception ex) {
                                }
                            }
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "لطفا یک فیلد را انتخاب نمایید.");
            }
        });

        JButton deleteButton = new JButton("حذف");
        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                data.remove(selectedRow);
                tableModel.removeRow(selectedRow);
                try {
                    saveToFile();
                } catch (Exception ex) {
                }
            } else {
                JOptionPane.showMessageDialog(this, "لطفا یک فیلد را انتخاب نمایید.");
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        add(new JScrollPane(table));
        add(searchPanel, "North");
        add(buttonPanel, "South");

        sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

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
                    new OrdersManage();
                } catch (Exception ignored) {

                }
            }
        });
        menu.add(refreshButton);


        setIconImage(icon.getImage());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }

    private void saveToFile() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File(orderFilename)))) {
            for (String[] row : data) {
                bw.write(String.join(",", row));
                bw.newLine();
            }
        } catch (Exception e) {
            errorSound();
        }
    }
    public static void CreateDinnerArray() throws FileNotFoundException {
        File file = new File(dinnerFilePath);

        Scanner scanner = new Scanner(file);
        int index = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(",");
            dinnerOptions[index] = parts[0];
            dinnerPrices[index] = Integer.parseInt(parts[1]);
            index++;
        }
        scanner.close();
    }
    public static void CreateLunchArray() throws FileNotFoundException {
        File file = new File(lunchFilePath);


        Scanner scanner = new Scanner(file);
        int index = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(",");
            lunchOptions[index] = parts[0];
            lunchPrices[index] = Integer.parseInt(parts[1]);
            index++;
        }
        scanner.close();
    }
}


//  1402/03/29 05:00 a.m. ~ 19 - 6 - 2023
