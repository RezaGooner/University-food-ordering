package Frames.Admin.ProfileManagment;

/*
این کد یک کلاس جاوا به نام `ShowDeletedUsers` است که جدولی از کاربران حذف شده را نمایش می دهد.
 این کد از فایلی که در متغیر `deletedFilePath` مشخص شده است، داده های لازم را می خواند.

یک شیء `JTable` با ناموجودیت های خاص برای ستون ها و داده ها ایجاد می شود.
 از `DefaultTableModel` برای مدیریت داده ها در جدول استفاده می شود.
  جدول در یک `JScrollPane` درون یک `JFrame` نمایش داده می شود.
   همچنین این کد یک نوار منو با چند مورد منو دارد: "تازه سازی"، "سابقه حذف"، "بازگشت" و "خروج".

دکمه "تازه سازی" جدول را با ایجاد یک نمونه جدید از کلاس `ShowDeletedUsers` بروزرسانی می کند.
 دکمه "بازگشت" در این کد پیاده سازی نشده است و دکمه "خروج" در صورت انتخاب برنامه را خاتمه می دهد.

این کد فرض می کند که فایل مشخص شده توسط `deletedFilePath` شامل داده های کاربران حذف شده است
 که هر خط آن حاوی داده های جداگانه یک کاربر حذف شده است.
 این داده ها شامل هفت فيلد "شناسه"، "نام"، "نام خانوادگی"، "شماره همراه"، "جنسیت"، "سازمان" و "تاریخ" می باشند.

    `````````````````````````````````````````````````````

This code is a Java class named `ShowDeletedUsers` that displays a table of deleted users in a GUI application.

Here is a breakdown of the code:

1. The code imports various classes from different packages, including `javax.sound.sampled` for handling audio-related exceptions, `javax.swing` for GUI components, `javax.swing.table` for table-related components, `java.awt.event` for event-related classes, and `java.io` for file-related operations.

2. The `ShowDeletedUsers` class is defined.

3. The constructor of the `ShowDeletedUsers` class is defined. It sets up the table and other components.

4. An empty `JTable` named `table` is created with column names specified in the `columnNames` array.

5. The number of rows in the table is determined by counting the lines in the file specified by `UserEditor.deletedFilePath`.

6. The `tableData` array is initialized with the appropriate dimensions based on the number of rows and columns.

7. The data is read from the file and populated into the `tableData` array.

8. A `DefaultTableModel` named `model` is created using the `tableData` and `columnNames`.

9. The `model` is set on the `table` using the `setModel()` method.

10. A custom renderer (`CustomRendererShowOrder`) is set on the `table` to customize the rendering of table cells.

11. A new `JFrame` named `frame` is created.

12. A menu bar (`JMenuBar`) is created and added to the `frame`.

13. A menu (`JMenu`) named "بیشتر" (More) is created and added to the menu bar.

14. A menu item (`JMenuItem`) named "تازه سازی" (Refresh) is created. It adds an action listener to refresh the table by disposing the current frame and creating a new instance of `ShowDeletedUsers`.

15. The menu item is added to the menu.

16. A scroll pane (`JScrollPane`) is created and added to the `frame`, wrapping the `table`.

17. The `frame` is packed to adjust its size based on the components.

18. The `frame` is set to be visible.

19. The icon image is set for the `frame` using the `setIconImage()` method.

20. The default close operation is set to dispose the `frame` when it is closed.

21. The size of the `frame` is set to 625 pixels in width and 500 pixels in height.

Overall, this code provides a GUI application for displaying a table of deleted users. It allows users to refresh the table and customize the rendering of table cells using a custom renderer.
 */


import Classes.Theme.CustomRendererShowOrder;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static Classes.Pathes.FilesPath.icon;
import static Classes.Theme.SoundEffect.errorSound;

public class ShowDeletedUsers {
    public ShowDeletedUsers() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        String line;
        String[] data;

        String[] columnNames = {"شناسه", "نام", "نام خانوادگی", "شماره همراه", "جنسیت", "سازمان", "تاریخ"};
        Object[][] tableData = new Object[0][7];
        JTable table = new JTable(tableData, columnNames);

        int rowCount = 0;
        try (
        BufferedReader br = new BufferedReader(new FileReader(UserEditor.deletedFilePath))) {
            while (br.readLine() != null) {
                rowCount++;
            }
        } catch (Exception e) {
            errorSound();
        }

        tableData = new Object[rowCount][7];
            try (BufferedReader br = new BufferedReader(new FileReader(UserEditor.deletedFilePath))) {
            int i = 0;
            while ((line = br.readLine()) != null) {
                data = line.split(",");
                tableData[i][0] = data[0];
                tableData[i][1] = data[1];
                tableData[i][2] = data[2];
                tableData[i][3] = data[3];
                tableData[i][4] = data[4];
                tableData[i][5] = data[5];
                tableData[i][6] = data[6];
                i++;
            }
        }  catch (Exception e) {
                errorSound();
            }

        DefaultTableModel model = new DefaultTableModel(tableData, columnNames);
            table.setModel(model);
            table.setDefaultRenderer(Object.class, new CustomRendererShowOrder());


            JFrame frame = new JFrame();
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        JMenu menu = new JMenu("بیشتر");
        menuBar.add(menu);
        JMenuItem refreshButton = new JMenuItem("تازه سازی");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                try {
                    new ShowDeletedUsers();
                }  catch (Exception exception) {
                }
            }
        });
        menu.add(refreshButton);



            frame.add(new JScrollPane(table));
            frame.pack();
            frame.setVisible(true);
            frame.setIconImage(icon.getImage());
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(625, 500);
    }
}

