package Frames.Admin.FoodManagment;

/*
این کد یک پنجره برای نمایش آمار سفارشات غذا به صورت روزانه دارد.
 در این پنجره، کاربر می‌تواند برای هر روز مجموع تعداد سفارشات ناهار و شام را مشاهده کند.
  این پنجره شامل دو تب "ناهار" و "شام" است که هر تب یک جدول دارد که شامل نام غذا و تعداد سفارشات آن است.
   در پایین هر جدول، یک JComboBox برای انتخاب تاریخ مورد نظر کاربر قرار دارد.
    همچنین، برای هر جدول یک دکمه "شمارش" وجود دارد که با فشردن آن،
     تعداد سفارشات آن روز محاسبه می‌شود و در جدول نمایش داده می‌شود.

      `````````````````````````````````````````````````````

This code defines an `OrderCounter` class that extends `JFrame` and is used to create a GUI application for
counting food orders. Here's a breakdown of the code:

    - The `OrderCounter` constructor sets up the main components of the GUI. It sets the title and icon of the application
      window, sets the size and location, and makes the window non-resizable.
    - Two tabbed panels, one for lunch orders and one for dinner orders, are created using `JPanel` with a `BorderLayout`.
    - Buttons for counting lunch and dinner orders are created and registered with action listeners to handle button click
      events. When clicked, these buttons invoke the `countLunch` and `countDinner` methods, respectively.
    - The lunch and dinner tables are created using `JTable` and `DefaultTableModel`.
      The table models are used to manage the data in the tables.
    - Two combo boxes, `lunchComboBox` and `dinnerComboBox`, are created to display the available dates for lunch and
      dinner orders.
    - The `populateComboBoxes` method reads the orders file and populates the combo boxes with unique dates.
    - The `countLunch` and `countDinner` methods read the orders file, count the number of orders for each food item on
      the selected date, and update the lunch and dinner tables accordingly. They also calculate the total number of
      orders and display it in a separate row at the bottom of each table.
    - The `highlightSum` method is used to highlight the total row in the tables by setting a different background color.
    - The `setVisible(true)` method is called to display the GUI.

note that there are some dependencies in the code, such as `Classes.Theme.CustomRendererOrderCount`, `Classes.Pathes.
FilesPath.OrdersPath`, and `Classes.Pathes.FilesPath.icon`.
You need to ensure that these dependencies are properly resolved and that the necessary files exist for the code to work
correctly.

 */

import Classes.Theme.CustomRendererOrderCount;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import static Classes.Pathes.FilesPath.OrdersPath;
import static Classes.Pathes.FilesPath.icon;

public class OrderCounter extends JFrame {

    private HashMap<String, Integer> lunchCounts = new HashMap<>();
    private HashMap<String, Integer> dinnerCounts = new HashMap<>();
    private DefaultTableModel lunchTableModel = new DefaultTableModel();
    private DefaultTableModel dinnerTableModel = new DefaultTableModel();
    private JTable lunchTable = new JTable(lunchTableModel);
    private JTable dinnerTable = new JTable(dinnerTableModel);
    private JComboBox<String> lunchComboBox = new JComboBox<>();
    private JComboBox<String> dinnerComboBox = new JComboBox<>();
    private DefaultTableCellRenderer totalRenderer = new DefaultTableCellRenderer();

    public OrderCounter() {
        setTitle("آمار سفارشات");
        setIconImage(icon.getImage());
        setSize(600, 400);
        setLocationRelativeTo(null);
        setResizable(false);

        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel lunchPanel = new JPanel(new BorderLayout());
        JPanel dinnerPanel = new JPanel(new BorderLayout());

        JButton lunchButton = new JButton("شمارش ناهار");
        lunchButton.addActionListener(e -> {
            try {
                countLunch();
            } catch (FileNotFoundException ex) {
            }
        });
        lunchPanel.add(lunchButton, BorderLayout.NORTH);

        lunchTableModel.addColumn("غذا");
        lunchTableModel.addColumn("تعداد");
        lunchPanel.add(new JScrollPane(lunchTable), BorderLayout.CENTER);

        JButton dinnerButton = new JButton("شمارش شام");
        dinnerButton.addActionListener(e -> {
            try {
                countDinner();
            } catch (FileNotFoundException ex) {
            }
        });
        dinnerPanel.add(dinnerButton, BorderLayout.NORTH);

        dinnerTableModel.addColumn("غذا");
        dinnerTableModel.addColumn("تعداد");
        dinnerPanel.add(new JScrollPane(dinnerTable), BorderLayout.CENTER);

        try {
            populateComboBoxes();
        } catch (FileNotFoundException ex) {
        }

        // اضافه کردن دو JComboBox به UI
        JPanel lunchDatePanel = new JPanel(new GridLayout(2, 2));
        lunchDatePanel.add(new JLabel("تاریخ"));
        lunchDatePanel.add(lunchComboBox);
        lunchPanel.add(lunchDatePanel, BorderLayout.SOUTH);

        JPanel dinnerDatePanel = new JPanel(new GridLayout(2, 2));
        dinnerDatePanel.add(new JLabel("تاریخ"));
        dinnerDatePanel.add(dinnerComboBox);
        dinnerPanel.add(dinnerDatePanel, BorderLayout.SOUTH);

        tabbedPane.addTab("ناهار", lunchPanel);
        tabbedPane.addTab("شام", dinnerPanel);

        add(tabbedPane);

        setVisible(true);
    }

    private void countLunch() throws FileNotFoundException {
        lunchTable.setDefaultRenderer(Object.class, new CustomRendererOrderCount());

        String selectedDate = (String) lunchComboBox.getSelectedItem();
        lunchCounts.clear();
        File ordersFile = new File(OrdersPath);
        Scanner scanner = new Scanner(ordersFile);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(",");
            String date = parts[1];
            if (date.equals(selectedDate) ) {
                String food = parts[2];
                if (lunchCounts.containsKey(food)) {
                    if (! food.equals(" ")) {
                        lunchCounts.put(food, lunchCounts.get(food) + 1);
                    }
                } else {
                    if (! food.equals(" ")) {
                        lunchCounts.put(food, 1);
                    }
                }
            }
        }
        scanner.close();

        lunchTableModel.setRowCount(0);
        for (String dish : lunchCounts.keySet()) {
            Object[] row = {dish, lunchCounts.get(dish)};
            lunchTableModel.addRow(row);
        }

        int total = 0;
        for (int i = 0; i < lunchTableModel.getRowCount(); i++) {
            total += (int) lunchTableModel.getValueAt(i, 1);
        }
        Object[] totalRow = {"مجموع", total};
        if (total != 0) {
            lunchTableModel.addRow(totalRow);
        } else {
            JOptionPane.showMessageDialog(null , "شما سفارش ناهار در این تاریخ ندارید.");
        }

        lunchTable.getColumnModel().getColumn(1).setCellRenderer(totalRenderer);
        lunchTable.getColumnModel().getColumn(1).setCellRenderer(totalRenderer);
        highlightSum(lunchTable);

    }

    private void countDinner() throws FileNotFoundException {
        String selectedDate = (String) dinnerComboBox.getSelectedItem();
        dinnerCounts.clear();
        File ordersFile = new File(OrdersPath);
        Scanner scanner = new Scanner(ordersFile);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(",");
            String date = parts[1];
            if (date.equals(selectedDate) ) {
                String food = parts[3];
                if (dinnerCounts.containsKey(food)) {
                    if (! food.equals(" ")) {
                        dinnerCounts.put(food, dinnerCounts.get(food) + 1);
                    }
                } else {
                    if (! food.equals(" ")) {
                        dinnerCounts.put(food, 1);
                    }
                }
            }
        }
        scanner.close();

        dinnerTableModel.setRowCount(0);
        for (String dish : dinnerCounts.keySet()) {
            Object[] row = {dish, dinnerCounts.get(dish)};
            dinnerTableModel.addRow(row);
        }

        int total = 0;
        for (int i = 0; i < dinnerTableModel.getRowCount(); i++) {
            total += (int) dinnerTableModel.getValueAt(i, 1);
        }
        Object[] totalRow = {"مجموع", total};
        if (total != 0) {
            dinnerTableModel.addRow(totalRow);
        } else {
            JOptionPane.showMessageDialog(null , "شما سفارش شام در این تاریخ ندارید.");
        }

        dinnerTable.getColumnModel().getColumn(1).setCellRenderer(totalRenderer);
        dinnerTable.getColumnModel().getColumn(1).setCellRenderer(totalRenderer);
        highlightSum(dinnerTable);

    }

    private void populateComboBoxes() throws FileNotFoundException {
        ArrayList<String> dates = new ArrayList<>();
        File ordersFile = new File(OrdersPath);
        Scanner scanner = new Scanner(ordersFile);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(",");
            String date = parts[1];
            if (!dates.contains(date)) {
                dates.add(date);
            }
        }
        scanner.close();

        for (String date : dates) {
            lunchComboBox.addItem(date);
            dinnerComboBox.addItem(date);
        }
    }
    public static void highlightSum(JTable table) {
            DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
            renderer.setBackground(Color.GRAY);

            for (int row = 0; row < table.getRowCount(); row++) {
                for (int col = 0; col < table.getColumnCount(); col++) {
                    Object value = table.getValueAt(row, col);
                    if (value != null && value.toString().equals("مجموع")) {
                        table.getCellRenderer(row, col).getTableCellRendererComponent(table, value, false, false, row, col).setBackground(Color.PINK);
                    }
                }
            }
        }
}


//   1402/04/02 03:00 a.m. ~ 23 - 6 - 2023