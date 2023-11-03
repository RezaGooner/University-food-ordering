package Frames.Admin.ProfileManagment;

/*
این کد یک پنجره جدول برای نمایش تاریخچه ورود کاربران به سیستم ایجاد می‌کند.
در این پنجره، داده‌های مربوط به فایل log.txt خوانده شده و در جدول نمایش داده می‌شوند.
همچنین، امکان جستجو در داده‌های جدول با استفاده از یک JTextField نیز فراهم شده است.

در متد سازنده، یک DefaultTableModel ایجاد و ستون‌های مربوط به شناسه، زمان و وضعیت ورود به آن اضافه می‌شوند.
 سپس، با استفاده از FileInputStream و BufferedReader، داده‌های مربوط به فایل log.txt خوانده شده
 و به صورت ردیف به جدول اضافه می‌شوند.

در پایان، جستجو به پنجره اضافه شده و با استفاده از TableRowSorter، توانایی مرتب‌سازی ردیف‌ها به صورت صعودی و نزولی فراهم شده است.
 سپس، جدول به پنجره اضافه شده و پنجره نمایش داده می‌شود.

    `````````````````````````````````````````````````````
This code is a Java class named `LogHistory` that extends the `JFrame` class. It represents a GUI application for displaying and searching log history.

Here is a breakdown of the code:

1. The code imports various classes from different packages, including `javax.sound.sampled` for handling audio-related exceptions, `javax.swing` for GUI components, `javax.swing.table` for table-related components, `java.awt.event` for event-related classes, and `java.io` for file-related operations.

2. The `LogHistory` class is defined, which extends the `JFrame` class.

3. The constructor of the `LogHistory` class is defined. It sets up the main frame by setting the title.

4. A `DefaultTableModel` named `tableModel` is created to hold the data for the table. Columns with the headers "شناسه" (ID), "زمان" (Time), and "وضعیت ورود" (Login Status) are added to the table model.

5. The log data is read from a file specified by the `LogPath` and added to the table model. Each line of the file represents a row in the table.

6. A `JTable` named `table` is created using the table model.

7. A search panel (`JPanel`) is created to hold the search field. The search field (`JTextField`) allows users to search for specific entries in the table. When the Enter key is pressed in the search field, an action listener is triggered to filter the table rows based on the entered text.

8. The search panel is added to the north (top) of the frame.

9. A `TableRowSorter` named `sorter` is created using the table model. It allows sorting and filtering of the table rows.

10. The sorter is set on the table using the `setRowSorter()` method.

11. The table is wrapped inside a scroll pane (`JScrollPane`) and added to the frame.

12. A menu bar (`JMenuBar`) is created and added to the frame.

13. A menu (`JMenu`) named "بیشتر" (More) is created and added to the menu bar.

14. A menu item (`JMenuItem`) named "تازه سازی" (Refresh) is created. It adds an action listener to refresh the log history by disposing the current frame and creating a new instance of `LogHistory`.

15. The menu item is added to the menu.

16. The icon image is set for the frame using the `setIconImage()` method.

17. The default close operation is set to dispose the frame when it is closed.

18. The frame is positioned at the center of the screen using the `setLocationRelativeTo()` method.

19. The frame is packed to adjust its size based on the components.

20. The frame is set to be visible.

Overall, this code provides a GUI application for displaying log history in a table format. It allows users to search for specific entries and refresh the log history.

 */

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static Classes.Pathes.FilesPath.icon;
import static Classes.Theme.SoundEffect.errorSound;
import static Classes.Pathes.FilesPath.LogPath;


public class LogHistory  extends JFrame {
    private JTable table;
    private TableRowSorter<DefaultTableModel> sorter;


    public LogHistory() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        super("سابقه ورود");

        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("شناسه");
        tableModel.addColumn("زمان");
        tableModel.addColumn("وضعیت ورود");

        try (BufferedReader br = new BufferedReader(new FileReader(new File(LogPath)))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" , ");
                tableModel.addRow(parts);
            }
        } catch (Exception e) {
            errorSound();
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

        add(searchPanel, "North");

        sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);


        add(new JScrollPane(table));

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
                    new LogHistory();
                }  catch (Exception exception) {
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
}
