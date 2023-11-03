package Frames.Order;

/*

بخش اصلی کد اینجاست!

پس از تایید شدن اطلاعات در پنجره مربوط به کلاس LoginFrame ، پنجره ای از این کلاس ساخته میشود


 این فریم شامل چندین المان  مانند: تاریخ، نوع نهار و شام، قیمت نهار و شام،
  دکمه‌های مختلفی مانند دکمه ثبت سفارش و دکمه تاریخچه، و
   همچنین یک منوی بالای صفحه است که شامل دکمه‌های افزایش موجودی، تازه سازی صفحه و بازگشت می‌شود.

این فریم با متغیرهایی همچون نام کاربر، شناسه، سازمان و درصد تخفیف مربوط به سازمان شروع می‌شود
و با استفاده از المان‌های  مختلف، امکان ثبت سفارش، حذف سفارش، نمایش تاریخچه سفارشات و تازه سازی صفحه را فراهم می‌کند.
همچنین این فریم از کلاس BalanceHandler برای مدیریت موجودی کاربر استفاده می‌کند.


   این کد زمان را برای سفارش غذا در یکی از روزهای هفته مشخص می‌کند و به کاربر اجازه می‌دهد تا غذای ناهار و شام خود را از یک لیست انتخاب کند.
    سپس کد محاسبات لازم برای محاسبه هزینه سفارش را انجام می‌دهد و سفارش را در یک فایل متنی ذخیره می‌کند.
     این کد همچنین امکان حذف سفارشات قبلی را نیز فراهم می‌کند و تاریخ سفارش را به صورت روزانه نمایش می‌دهد.

  اگر روی "prevButton" کلیک شود، 7 روز به عقب باز می گردد یا با کلیک روی  nextButton 7 روز به جلو پیمایش میشود ،
در اخر فیلد متنی "dateField" را به روز می کند و سفارشات تاریخ جدید را نشان می دهد.

 متد setCurrentDay:  روز جاری را بر اساس دکمه روز انتخاب شده تنظیم می کند. دکمه انتخاب شده را غیرفعال می کند و بقیه دکمه ها را فعال می کند.
  همچنین فیلد متنی "dateField" را به روز می کند و جعبه های ترکیبی "lunchCombo" و "dinnerCombo" را بازنشانی می کند.

 متد getDate   تاریخ جاری را بر اساس دکمه روز انتخاب شده دریافت می کند. تعداد روزهایی را محاسبه می کند
  که باید به تاریخ فعلی اضافه شود تا به روز انتخاب شده برسید و تاریخ جدید را برمی گرداند.

 متد saveOrder اطلاعات سفارش را در یک فایل متنی ذخیره می کند. همچنین بررسی می کند که آیا تاریخ، ناهار و شام انتخاب شده است یا خیر،
  و در صورت خالی بودن هر یک از آنها پیغام خطا نشان می دهد.
   همچنین بررسی می کند که آیا کاربر قبلاً برای تاریخ و وعده غذایی انتخاب شده سفارش داده است یا خیر،
   و از کاربر می پرسد که آیا می خواهد سفارش موجود را جایگزین کند یا خیر.
    در نهایت، سفارش جدید را در فایل می نویسد، موجودی را به روز می کند و یک پیام تایید نمایش می دهد.

 متد `showOrderHistory` تاریخچه سفارشات کاربر را نمایش می‌دهد.

  متد `replaceFileContent` با دریافت اطلاعات جدیدی از کاربر، سفارش را به‌روزرسانی می‌کند.


 متد `findIndexCost` با استفاده از تاریخ و شناسه کاربر، شماره خط مربوط به سفارش کاربر در فایل را پیدا می‌کند.
 کاربر ارتباط مستقیم با این متد ندارد.


   متد `reCostCalculate` نیز با استفاده از شماره خط مربوط به سفارش کاربر در فایل، قیمت سفارش را محاسبه می‌کند.

     کلیه این متدها در این کلاس قرار دارند و برای مدیریت سفارشات کاربران استفاده می‌شوند.
 */

import Classes.Theme.CustomRendererDuplicateOrder;
import Classes.Theme.CustomRendererShowOrder;
import Classes.Theme.StyledButtonUI;
import Frames.LoginFrame;

import java.awt.*;
import java.awt.event.*;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Scanner;

import static Classes.Pathes.FilesPath.*;
import static Classes.Theme.SoundEffect.errorSound;
import static Classes.Theme.SoundEffect.successSound;


public class UniversitySelfRestaurant extends JFrame implements ActionListener {
    private double percent;
    private Calendar calendar = Calendar.getInstance();
    private JLabel dateLabel, lunchLabel, dinnerLabel, priceLabel, finalPriceLabel;
    private static JTextField dateField;
    private static JComboBox<String> lunchCombo;
    private static JComboBox<String> dinnerCombo;
    private JButton[] dayButtons;
    private JButton prevButton, nextButton, submitButton, historyButton, removeButton;
    private JPanel dayPanel;

    private int currentDay = 0;
    private String[] days = {"شنبه", "یکشنبه", "دوشنبه", "سه شنبه", "چهارشنبه", "پنجشنبه"};
    private static String[] lunchOptions = new String[7];
    private static int[] lunchPrices = new int[7];
    private static String[] dinnerOptions = new String[7];
    private static int[] dinnerPrices = new int[7];

    private static String Id, Name;
    private final String Organization;
    private int cost, reCost, finalPrice;
    private String idArgumentForRefresh, nameArgumentForRefresh, organArgumentForRefresh;

    public static String dinnerFilePath = "E:/Programming/University/Term 2/Food Self/dinner.txt";
    public static String lunchFilePath = "E:/Programming/University/Term 2/Food Self/lunch.txt";

    private boolean percentFlag = false;
    public static Color colorButton = new Color(45,206,152);
    public static Color colorBackground = new Color(207,255,199);
    public static String orderFilename = "E:/Programming/University/Term 2/Food Self/orders.txt";
    private static File orderFile = new File("E:/Programming/University/Term 2/Food Self/orders.txt");


    public UniversitySelfRestaurant(String id, String name, String organ) throws FileNotFoundException {
        super(" " + name + " : " + BalanceHandler.getBalance(id) + " تومان ");
        setIconImage(icon.getImage());

        CreateDinnerArray();
        CreateLunchArray();


        if (BalanceHandler.getBalance(id).equals("خطا")) {
            setTitle(" " + name +  " خطا در خواندن موجودی ");
        }

        idArgumentForRefresh = id;
        nameArgumentForRefresh = name;
        organArgumentForRefresh = organ;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 250);
        setLayout(new BorderLayout());
        setResizable(false);

        Organization = organ;

        setDefaultDiscount();


        Id = id;
        Name = name;

        dateLabel = new JLabel(": تاریخ");
        dateLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lunchLabel = new JLabel(": نهار");
        lunchLabel.setHorizontalAlignment(SwingConstants.CENTER);
        dinnerLabel = new JLabel(": شام");
        dinnerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        priceLabel = new JLabel();
        finalPriceLabel = new JLabel();

        dateField = new JTextField();

        lunchCombo = new JComboBox<>(lunchOptions);

        DefaultListCellRenderer lunchRender = new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                                                          boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value != null) {
                    switch (value.toString()) {
                        case "قرمه سبزی":
                            setToolTipText("3000 تومان ");
                            break;
                        case "چلوکباب کوبیده":
                            setToolTipText("5000 تومان");
                            break;
                        case "قیمه":
                        case "خورش بادمجان":
                            setToolTipText("3000 تومان");
                            break;
                        case "خورش کرفس":
                            setToolTipText("2500 تومان");
                            break;
                        case "زرشک پلو":
                            setToolTipText("4000 تومان");
                            break;
                    }
                }
                return this;
            }
        };

        lunchCombo.setRenderer(lunchRender);
        lunchCombo.setBackground(colorBackground);



        dinnerCombo = new JComboBox<>(dinnerOptions);


        DefaultListCellRenderer dinnerRender = new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                                                          boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value != null) {
                    switch (value.toString()) {
                        case "الویه":
                            setToolTipText("2500 تومان ");
                            break;
                        case "دمپخت عدسی":
                            setToolTipText("3000 تومان");
                            break;
                        case "شنیسل":
                            setToolTipText("3500 تومان");
                            break;
                        case "عدس پلو":
                            setToolTipText("3500 تومان");
                            break;
                        case "کنسرو لوبیا":
                            setToolTipText("3000 تومان");
                            break;
                        case "کنسرو بادمجان":
                            setToolTipText("3000 تومان");
                            break;

                    }
                }
                return this;
            }
        };

        dinnerCombo.setRenderer(dinnerRender);
        dinnerCombo.setBackground(colorBackground);


        prevButton = new JButton("هفته قبل");
        prevButton.setUI(new StyledButtonUI());
        prevButton.setBackground(colorButton);
        nextButton = new JButton("هفته بعد");
        nextButton.setUI(new StyledButtonUI());
        nextButton.setBackground(colorButton);
        submitButton = new JButton("ثبت سفارش");
        submitButton.setUI(new StyledButtonUI());
        submitButton.setBackground(colorButton);
        historyButton = new JButton("تاریخچه");
        historyButton.setUI(new StyledButtonUI());
        historyButton.setBackground(colorButton);
        removeButton = new JButton("لغو سفارشات امروز");
        removeButton.setUI(new StyledButtonUI());
        removeButton.setBackground(colorButton);


        prevButton.addActionListener(this);
        nextButton.addActionListener(this);
        submitButton.addActionListener(this);
        historyButton.addActionListener(this);
        removeButton.addActionListener(this);

        dayPanel = new JPanel(new GridLayout(1, 4));
        dayButtons = new JButton[6];
        for (int i = 0; i < 6; i++) {
            dayButtons[i] = new JButton(days[i]);
            dayButtons[i].setUI(new StyledButtonUI());
            dayButtons[i].setBackground(colorButton);
            dayButtons[i].addActionListener(this);
            dayPanel.add(dayButtons[i]);
        }

        JPanel inputPanel = new JPanel(new GridLayout(6, 2));
        inputPanel.setBackground(colorBackground);
        inputPanel.add(dateLabel);
        inputPanel.add(dateField);
        dateField.setBackground(colorBackground);
        inputPanel.add(lunchLabel);
        inputPanel.add(lunchCombo);
        inputPanel.add(dinnerLabel);
        inputPanel.add(dinnerCombo);
        inputPanel.add(priceLabel);
        inputPanel.add(finalPriceLabel);
        inputPanel.add(submitButton);
        inputPanel.add(removeButton);


        add(dayPanel, BorderLayout.NORTH);
        add(inputPanel, BorderLayout.CENTER);
        add(prevButton, BorderLayout.WEST);
        add(nextButton, BorderLayout.EAST);
        add(historyButton, BorderLayout.SOUTH);


        setCurrentDay(0);
        setOrderField();

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(colorButton);
        setJMenuBar(menuBar);
        JMenu menu = new JMenu("بیشتر");
        menu.setBackground(colorButton);
        menuBar.add(menu);
        JMenu menu2 = new JMenu(" موجودی ");
        menu2.setBackground(colorButton);
        menu.add(menu2);
        JMenuItem increaseBalanceButton = new JMenuItem("افزایش موجودی");
        increaseBalanceButton.setBackground(colorButton);
        menu2.add(increaseBalanceButton);
        JMenuItem chargeHistoryButton = new JMenuItem("سابقه شارژ");
        chargeHistoryButton.setBackground(colorButton);
        menu2.add(chargeHistoryButton);
        JMenuItem discountButton = new JMenuItem("اعمال کد تخفیف");
        discountButton.setBackground(colorButton);
        menu2.add(discountButton);
        JMenuItem refreshButton = new JMenuItem("تازه سازی صفحه");
        refreshButton.setBackground(colorButton);
        menu.add(refreshButton);
        JMenuItem backButton = new JMenuItem("بازگشت");
        backButton.setBackground(colorButton);
        menu.add(backButton);
        JMenuItem exitItem = new JMenuItem("خروج");
        exitItem.setBackground(colorButton);
        menuBar.add(exitItem);


        setVisible(true);


        discountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTempDiscount();
            }
        });

        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Object[] options = {"خیر", "بله"};

                int exitResult = JOptionPane.showOptionDialog(null, "آیا خارج می شوید؟", "خروج",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                        null, options, options[0]);
                if (exitResult == JOptionPane.NO_OPTION) dispose();
            }
        });


        increaseBalanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    BalanceHandler.increaseBalance(Id);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null ,  " خطا " + ex.getMessage());
                }
                setTitle(" " + Name + " : " + BalanceHandler.getBalance(Id) + " تومان ");

            }
        });

        chargeHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    showChargeHistory();
                } catch (Exception ex) {
                }
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new UniversitySelfRestaurant(idArgumentForRefresh, nameArgumentForRefresh, organArgumentForRefresh);
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                dispose();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[] options = {"خیر", "بله"};

                int exitResult = JOptionPane.showOptionDialog(null, "آیا از حساب خود خارج می شوید؟", "بازگشت",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                        null, options, options[0]);
                if (exitResult == JOptionPane.NO_OPTION) {
                    dispose();
                    new LoginFrame();
                }
            }
        });
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == prevButton) {
            calendar.add(Calendar.DAY_OF_MONTH, -7);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = formatter.format(calendar.getTime());
            dateField.setText(formattedDate);
        } else if (e.getSource() == removeButton) {
            Object[] options = {"خیر", "بله"};
            try {
                errorSound();
            } catch (Exception ex) {

            }


            JFrame frame = new JFrame();
            frame.setBackground(colorBackground);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            String tempLunch = " ";
            String tempDinner = " ";
            try {
                if (returnOrder().split(",").length == 2) {
                    if (Arrays.asList(lunchOptions).contains(returnOrder().split(",")[0])) {
                        if (Arrays.asList(dinnerOptions).contains(returnOrder().split(",")[1])) {
                            tempLunch = returnOrder().split(",")[0];
                            tempDinner = returnOrder().split(",")[1];
                        } else {
                            tempDinner = returnOrder().split(",")[1];
                        }
                    } else if (Arrays.asList(dinnerOptions).contains(returnOrder().split(",")[0])) {
                        tempDinner = returnOrder();
                    }
                } else {
                    if (Arrays.asList(lunchOptions).contains(returnOrder())) {
                        if (!returnOrder().split(",")[0].equals(" ")) {
                            tempLunch = returnOrder();
                        }
                    } else if (Arrays.asList(dinnerOptions).contains(returnOrder())) {
                        if (!returnOrder().split(",")[0].equals(" ")) {
                            tempLunch = returnOrder();
                        }
                    }
                }
            } catch (Exception exception) {

            }

            Object[][] data = {
                    {tempLunch, tempDinner}
            };

            if (!( tempLunch.equals("xxx") && tempDinner.equals("xxx") || tempLunch.equals(" ") && tempDinner.equals(" ") )) {

                String[] columnNames = {" ناهار ", " شام "};

                DefaultTableModel model = new DefaultTableModel(data, columnNames);

                JTable table = new JTable(model);
                table.setBackground(colorBackground);


                JPanel panel = new JPanel(new BorderLayout());
                panel.setBackground(colorBackground);

                panel.add(new JScrollPane(table), BorderLayout.CENTER);
                panel.setPreferredSize(new Dimension(table.getPreferredSize().width, table.getPreferredSize().height + 25));


                int result = JOptionPane.showOptionDialog(frame, panel, "حذف سفارشات " + dateField.getText(),
                        JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

                if (result == JOptionPane.NO_OPTION) {
                    frame.setVisible(false);
                    try (Scanner scanner = new Scanner(new File(orderFilename))) {
                        StringBuilder sb = new StringBuilder();

                        while (scanner.hasNextLine()) {
                            String line = scanner.nextLine();
                            String[] parts = line.split(",");

                            if (!parts[1].equals(dateField.getText())) {
                                sb.append(line);
                                sb.append(System.lineSeparator());
                            } else {
                                reCostCalculate();
                                BalanceHandler.returningOrderCost(Id, reCost);
                            }
                        }

                        try (PrintWriter writer = new PrintWriter(new FileWriter(orderFilename))) {
                            writer.print(sb.toString());
                            new UniversitySelfRestaurant(idArgumentForRefresh, nameArgumentForRefresh, organArgumentForRefresh);
                            dispose();
                        } catch (Exception ex) {
                            try {
                                errorSound();
                            } catch (Exception exception) {

                            }
                            JOptionPane.showMessageDialog(null, "خطا در اجرای برنامه: \n" + ex.getMessage(), "خطا", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (Exception ex) {
                        try {
                            errorSound();
                        } catch (Exception exception) {

                        }
                        JOptionPane.showMessageDialog(null, "خطا در اجرای برنامه: \n" + ex.getMessage(), "خطا", JOptionPane.ERROR_MESSAGE);
                    }
                }

                frame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        frame.setVisible(false);
                        System.exit(0);
                    }
                });


                frame.pack();
                frame.setVisible(false);


                try {
                    successSound();
                } catch (Exception ex) {
                }
            } else {
                JOptionPane.showMessageDialog(null, " شما هیچ سفارشی در این تاریخ ندارید!");
            }





        } else if (e.getSource() == nextButton) {
            calendar.add(Calendar.DAY_OF_MONTH, +7);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = formatter.format(calendar.getTime());
            dateField.setText(formattedDate);
        } else if (e.getSource() == submitButton) {
            int lunchPrice = lunchPrices[lunchCombo.getSelectedIndex()];
            int dinnerPrice = dinnerPrices[dinnerCombo.getSelectedIndex()];
            int totalPrice = lunchPrice + dinnerPrice;
            int finalPrice = (int) (totalPrice * percent);

            reCostCalculate();
            if (finalPrice > reCost) {
                cost = (finalPrice - reCost);
            } else {
                cost = reCost - finalPrice;
            }






            saveOrder();


        } else if (e.getSource() == historyButton) {
            try {
                showOrderHistory();
            } catch (Exception ex) {
                try {
                    errorSound();
                } catch (Exception exception) {

                }
                JOptionPane.showMessageDialog(null, "خطا در اجرای برنامه: \n" + ex.getMessage(), "خطا", JOptionPane.ERROR_MESSAGE);
            }

        } else {
            for (int i = 0; i < 6; i++) {
                if (e.getSource() == dayButtons[i]) {
                    setCurrentDay(i);
                    setOrderField();
                    break;
                }
            }
        }
    }


    private void setCurrentDay(int day) {
        if (day < 0 || day > 6) {
            return;
        }
        currentDay = day;
        dayButtons[day].setEnabled(false);
        for (int i = 0; i < 6; i++) {
            if (i != day) {
                dayButtons[i].setEnabled(true);
            }
        }
        dateField.setText(getDate());
        priceLabel.setText("");
        finalPriceLabel.setText("");
    }


    private String getDate() {
        java.time.LocalDate now = java.time.LocalDate.now();
        int dayOfWeek = now.getDayOfWeek().getValue();
        int daysToAdd = currentDay - dayOfWeek;
        if (daysToAdd < 0) {
            daysToAdd += 7;
        }
        java.time.LocalDate date = now.plusDays(daysToAdd);
        return date.toString();
    }


    private void saveOrder() {

        String date = dateField.getText();
        String lunch = (String) lunchCombo.getSelectedItem();
        String dinner = (String) dinnerCombo.getSelectedItem();

        if (date.isEmpty()) {
            try {
                errorSound();
            } catch (Exception ex) {

            }
            JOptionPane.showMessageDialog(this, "لطفا تاریخ سفارش را مشخص نمایید");
            return;
        }
        if (lunch.equals(" ") && dinner.equals(" ")) {
            try {
                errorSound();
            } catch (Exception ex) {
            }
            JOptionPane.showMessageDialog(this, "لطفا سفارش ناهار و یا شام را کامل نمایید");
            return;
        }

        try {

            String[] lunches = {"قرمه سبزی", "چلوکباب کوبیده", "قیمه", "خورش کرفس", "زرشک پلو", "خورش بادمجان"};
            String[] dinners = {"الویه", "دمپخت عدسی", "شنیسل", "عدس پلو", "کنسرو لوبیا", "کنسرو بادمجان"};

            try {
                Scanner scanner = new Scanner(orderFile);

                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    if (line.contains((dateField.getText()))) {


                        if (line.split(",").length == 3) {
                            if (Arrays.asList(lunches).contains(line.split(",")[2])) {
                                if (dinnerCombo.getSelectedItem() == " ") {
                                    if (!(line.split(",")[2].equals(lunchCombo.getSelectedItem()))) {
                                        // اگر ناهار سفارش داده بود و قصد تغییر سفارش داشت :
                                        if (duplicateOrderError() == JOptionPane.NO_OPTION) {
                                            submitOrderIfCheckBalance();
                                            setPricesLabel();
                                        }

                                        setTitle(" " + Name + " : " + BalanceHandler.getBalance(Id) + " تومان ");

                                        return;
                                    } else {
                                        errorSound();
                                        JOptionPane.showMessageDialog(null , " این ناهار سفارش داده شده است!");
                                        return;
                                    }
                                } else {
                                    if (BalanceHandler.checkBalance(Id, cost)) {
                                        submitOrderIfCheckBalance();
                                        setPricesLabel();
                                        setTitle(" " + Name + " : " + BalanceHandler.getBalance(Id) + " تومان ");
                                        return;
                                    } else {
                                        balanceNotEnoughError();
                                    }
                                }
                            } else if (Arrays.asList(dinners).contains(line.split(",")[2])) {
                                if (lunchCombo.getSelectedItem() == " ") {
                                    if (!(dinnerCombo.getSelectedItem().equals(line.split(",")[2]))){
                                    // اگر شام سفارش داده بود و قصد تغییر سفارش داشت :
                                    if (duplicateOrderError() == JOptionPane.NO_OPTION) {
                                        if (BalanceHandler.checkBalance(Id, cost)) {
                                            submitOrderIfCheckBalance();

                                        } else {
                                            balanceNotEnoughError();
                                        }
                                    }


                                    setTitle(" " + Name + " : " + BalanceHandler.getBalance(Id) + " تومان ");
                                    return;
                                } else {
                                        errorSound();
                                        JOptionPane.showMessageDialog(null , " این شام سفارش داده شده است!");
                                        return;
                                    }
                                }
                            }
                        } else if (line.split(",").length == 4) {
                            if (! ((line.split(",")[2].equals(lunchCombo.getSelectedItem())) && ((line.split(",")[3].equals(dinnerCombo.getSelectedItem())))))
                            {
                                if (duplicateOrderError() == JOptionPane.NO_OPTION) {
                                    if (BalanceHandler.checkBalance(Id, cost)) {
                                        submitOrderIfCheckBalance();
                                        setPricesLabel();
                                    } else {
                                        balanceNotEnoughError();
                                    }
                                }

                                setTitle(" " + Name + " : " + BalanceHandler.getBalance(Id) + " تومان ");
                                return;
                            } else {

                                errorSound();
                                JOptionPane.showMessageDialog(null , " این ناهار و شام سفارش داده شده است!");
                                return;
                            }
                        }
                    }
                }

                scanner.close();
            } catch (FileNotFoundException e) {
                try {
                    errorSound();
                } catch (Exception ex) {

                }
                JOptionPane.showMessageDialog(this, "خطا در ذخیره سازی");

            } catch (Exception e) {
                try {
                    errorSound();
                } catch (Exception ex) {

                }
                JOptionPane.showMessageDialog(this, " خطا " + e.getMessage());
            }

            if (BalanceHandler.checkBalance(Id, cost)) {

                FileWriter fw = new FileWriter(orderFilename, true);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(Id + "," + date + "," + lunch + "," + dinner + "\n");
                bw.close();
                try {
                    successSound();
                } catch (Exception ex) {

                }
                setPricesLabel();
                JOptionPane.showMessageDialog(this, "سفارش ثبت شد\n" + priceLabel.getText() + "\n" + finalPriceLabel.getText());
                reCostCalculate();
                BalanceHandler.decreaseBalance(Id, cost);

            } else {
                balanceNotEnoughError();
            }
            setTitle(" " + Name + " : " + BalanceHandler.getBalance(Id) + " تومان ");
        } catch (IOException e) {
            try {
                errorSound();
            } catch (Exception ex) {

            }
            JOptionPane.showMessageDialog(null, "خطا در ذخیره سازی : \n" + e.getMessage(), "خطا", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            try {
                errorSound();
            } catch (Exception ex) {

            }
            JOptionPane.showMessageDialog(null, "خطا در اجرای برنامه: \n" + e.getMessage(), "خطا", JOptionPane.ERROR_MESSAGE);
        }
    }


    /*
    اگر کاربر در تاریخی غذا سفارش داده باشد و بخواهد برای همان روز و برای همان وعده غذایی سفارش دهد با خطا روبرو می شود
    اگر با تغییر غذا موافقت کند این تابع فراخوانی میشود.

   درون تابع توضیحاتی در رابطه با الگوریتم آن درج شده است.
     */
    public static void replaceFileContent() throws IOException {
        String[] lunches = {"قرمه سبزی", "چلوکباب کوبیده", "قیمه", "خورش کرفس", "زرشک پلو", "خورش بادمجان"};
        String[] dinners = {"الویه", "دمپخت عدسی", "شنیسل", "عدس پلو", "کنسرو لوبیا", "کنسرو بادمجان"};

        Scanner scanner;
        StringBuilder stringBuilder;
        try {
            scanner = new Scanner(orderFile);

            stringBuilder = new StringBuilder();

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (Id.equals(parts[0])) {
                    if (dateField.getText().equals(parts[1])) {
                        if (parts.length == 3) {

                            String thirdPart = parts[2];
                            if (Arrays.asList(lunches).contains(thirdPart)) {
                                if (!(lunchCombo.getSelectedItem().equals(" "))) {
                                    /*
                                    فایل خوانده میشود و با توجه به تاریخ و شناسه ، خط مربوطه پیدا میشود
                                    سپس بررسی میشود که طول آن 3 باشد یعنی فقط ناهار یا فقط شام سفارش داده باشد
                                    بعد از آن بررسی میشود که آیا ناهار بوده یا نه و اگر فیلد انتخاب ناهار خالی باشد به ناهار فایل دست نزده و شام را اضافه میکند
                                     */
                                    line = Id + "," + dateField.getText() + "," + lunchCombo.getSelectedItem() + "," + dinnerCombo.getSelectedItem();

                                } else {

                                    line += dinnerCombo.getSelectedItem();
                                }
                            } else if (Arrays.asList(dinners).contains(thirdPart)) {
                                /*
                                اگر غذای سفارش داده شده شام باشد جای عناصر درون فایل عوض میشود
                                 */

                                line = parts[0] + "," + parts[1] + "," + lunchCombo.getSelectedItem() + "," + parts[2];
                            }
                        } else if (parts.length == 4) {

                            line = Id + "," + dateField.getText() + "," + lunchCombo.getSelectedItem() + "," + dinnerCombo.getSelectedItem();
                        }
                    }


                    stringBuilder.append(line + "\n");
                }
            }
            scanner.close();

            FileWriter fileWriter = new FileWriter(orderFilename);
            fileWriter.write(stringBuilder.toString());
            fileWriter.close();

        } catch (Exception e) {
            try {
                errorSound();
            } catch (Exception ex) {

            }
            JOptionPane.showMessageDialog(null, "خطا در اجرای برنامه: \n" + e.getMessage(), "خطا", JOptionPane.ERROR_MESSAGE);
        }




    }

    public int findIndexCost() {
        try (BufferedReader reader = new BufferedReader(new FileReader(orderFilename))) {
            String[] parts;
            int index = 0;
            while (reader.ready()) {
                String line = reader.readLine();
                parts = line.split(",");
                if (parts[1].equals(dateField.getText()) && parts[0].equals(Id)) {
                    if (parts.length == 3) {
                        String element = parts[2];
                        if (lunchContains(element)) {
                            return index;
                        } else {
                            return index + lunchOptions.length;
                        }
                    } else {
                        String Element = parts[2];
                        if (lunchContains(Element)) {
                            return index + 100;
                        } else {
                            return index + lunchOptions.length + 100;
                        }
                    }
                }
                index++;
            }
        } catch (Exception e) {
            try {
                errorSound();
            } catch (Exception ex) {

            }
            JOptionPane.showMessageDialog(null, "خطا در اجرای برنامه: \n" + e.getMessage(), "خطا", JOptionPane.ERROR_MESSAGE);
        }
        return -1;

    }


    private static boolean lunchContains(String element) {
        return Arrays.asList(lunchOptions).contains(element);
    }

    private void reCostCalculate() {
        reCost = 0;
        int index = findIndexCost();
        if (index >= 0) {
            if (index < 7) {
                reCost = lunchPrices[index + 1];
            } else if (index < 14) {
                reCost = dinnerPrices[index - 6];
            } else if (index < 107) {
                reCost = lunchPrices[index - 99];
            } else if (index < 114) {
                reCost = dinnerPrices[index - 106];
            }
        }
    }

    public void balanceNotEnoughError() {
        try {
            errorSound();
        } catch (Exception ex) {

        }

        String[][] data = {{ " : موجودی               . " , ".                    "  + BalanceHandler.getBalance(Id)}};
        Object[] columnNames = { " : هزینه ",  String.valueOf(cost)};
        JTable table = new JTable(data, columnNames);
        table.setBackground(colorBackground);


        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(table.getPreferredSize().width + 150, table.getPreferredSize().height + 25 ));

        JOptionPane.showMessageDialog(null, scrollPane, "موجودی حساب شما کافی نمی باشد!", JOptionPane.PLAIN_MESSAGE);
    }



    public static String returnOrder() throws IOException {

        Scanner scanner;
        try {
            File file = new File(orderFilename);
            scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (Id.equals(parts[0])) {
                    if (dateField.getText().equals(parts[1])) {
                        if (parts.length == 3) {

                            return parts[2];

                        } else  {
                            return parts[2] + "," + parts[3];
                        }
                    }

                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "خطا در اجرای برنامه: \n" + e.getMessage(), "خطا", JOptionPane.ERROR_MESSAGE);
        }
        return "xxx";
    }

    public int duplicateOrderError() throws IOException {
        Object[] options = {"خیر", "بله"};
        try {
            errorSound();
        } catch (Exception ex) {

        }


        String[][] preData = {{ returnOrder().split(",")[0] , returnOrder().split(",")[1] } ,
                {String.valueOf(lunchCombo.getSelectedItem()), String.valueOf(dinnerCombo.getSelectedItem()) }};

        String[] columnNames = { " ناهار ", " شام "};
        JTable preTable = new JTable(preData, columnNames);
        preTable.setBackground(colorBackground);
        preTable.setDefaultRenderer(Object.class, new CustomRendererDuplicateOrder());


        JScrollPane scrollPane = new JScrollPane(preTable);

        JLabel topLabel = new JLabel("شما ناهار و شام این تاریخ را سفارش داده اید.\n");
        JLabel endLabel = new JLabel("\nآیا میخواهید سفارشات جدید را جایگزین کنید؟");


        JPanel panel = new JPanel();
        panel.setBackground(colorBackground);
        panel.setLayout(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(topLabel, BorderLayout.PAGE_START);
        panel.add(endLabel, BorderLayout.PAGE_END);
        scrollPane.setPreferredSize(new Dimension(preTable.getPreferredSize().width + 150 , preTable.getPreferredSize().height + 25 ));


        return JOptionPane.showOptionDialog(null, panel, "اخطار", JOptionPane.YES_NO_OPTION ,
                JOptionPane.QUESTION_MESSAGE , null , options , options[0]);

    }

    public void submitOrderIfCheckBalance() {
        if (BalanceHandler.checkBalance(Id, cost)) {
            try {
                try {
                    successSound();
                } catch (Exception ex) {

                }
                replaceFileContent();
                JOptionPane.showMessageDialog(this, "سفارش ثبت شد\n" + priceLabel.getText() + "\n" + finalPriceLabel.getText());
                reCostCalculate();
                BalanceHandler.decreaseBalance(Id, cost);
                setDefaultDiscount();

            } catch(Exception e){
                try {
                    errorSound();
                } catch (Exception ex) {

                }
                JOptionPane.showMessageDialog(null, "خطا در اجرای برنامه: \n" + e.getMessage(), "خطا", JOptionPane.ERROR_MESSAGE);
            }
        } else{
            balanceNotEnoughError();
        }
    }
    public void setTempDiscount() {
        try {
            String discountCode = JOptionPane.showInputDialog("لطفا کد تخفیف را وارد نمایید : ");

            boolean flag = false;
            String discountPercent = "";

            try (BufferedReader br = new BufferedReader(new FileReader(DiscountsPath))) {
                String line;
                /*
                در هر خط از فایل اطلاعات بدین صورت ذخیره شده است :
                Discount_Code : Percent
                 */
                while ((line = br.readLine()) != null) {
                    if (discountCode.trim().equals(line.split(" : ")[0])) {
                        discountPercent = (line.split(" : ")[1]) + "%";
                        percent -= ((Double.parseDouble(line.split(" : ")[1])) / 100);

                        flag = true;
                    }
                }
                if (!flag) {
                    try {
                        errorSound();
                    } catch (Exception ex) {

                    }
                    JOptionPane.showMessageDialog(null, "کد تخفیف پیدا نشد!");
                } else {
                    try {
                        successSound();
                    } catch (Exception ex) {

                    }
                    JOptionPane.showMessageDialog(null, "کد تخفیف با موفقیت اعمال گردید!\n درصد تخفیف : " + discountPercent);
                    percentFlag = true;
                }
            } catch (IOException e) {
                try {
                    errorSound();
                } catch (Exception ex) {

                }
                JOptionPane.showMessageDialog(null, "خطا در اجرای برنامه: \n" + e.getMessage(), "خطا", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                try {
                    errorSound();
                } catch (Exception ex) {

                }
                JOptionPane.showMessageDialog(null, "خطا در اجرای برنامه: \n" + e.getMessage(), "خطا", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e){
            try {
                errorSound();
            } catch (Exception ex) {

            }
        }
    }
    public void setDefaultDiscount(){
        percentFlag = false ;
        switch (organArgumentForRefresh) {
            // دانشجویان و کارمندان تخفیفی ندارند
            case "NOT":
                percent = 1.0;
                break;
            case "KOMITE":
                // دانشجویان ویژه تحت پوشش کمیته امداد از تخفیف 10 درصدی برخوردار میشوند
                percent = 0.9;
                break;
            case "BEHZISTI":
                // تخفیف برای دانشجویان ویژه تحت پوشش سازمان بهزیستی 15 درصد است
                percent = 0.85;
                break;
            case "MOMTAZ":
                // سفارشات دانشجویان ممتاز شامل 20 درصد تخفیف است
                percent = 0.8;
                break;
        }
    }
    public void setOrderField(){
        try {
            if (returnOrder().split(",").length == 1){
                if (Arrays.asList(lunchOptions).contains(returnOrder())){
                    lunchCombo.setSelectedItem(returnOrder());
                } else {
                    dinnerCombo.setSelectedItem(returnOrder());
                }
            } else if (! returnOrder().equals("xxx")) {
                lunchCombo.setSelectedItem(returnOrder().split(",")[0]);
                dinnerCombo.setSelectedItem(returnOrder().split(",")[1]);
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    public void showOrderHistory() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        boolean isFind = false;

        String[] columnNames = {" تاریخ ", " ناهار ", " شام "};
        ArrayList<Object[]> dataList = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(new File(orderFilename));

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] fields = line.split(",");

                if (fields.length == 4) {
                    if (fields[0].equals(Id)) {
                        String date = fields[1];
                        String lunch = fields[2];
                        String dinner = fields[3];
                        dataList.add(new Object[]{date, lunch, dinner});
                        isFind = true;

                    }
                } else if (fields.length == 3) {
                    if (fields[0].equals(Id)) {
                        if (Arrays.asList(lunchOptions).contains(fields[2])) {
                            if (!fields[2].equals(" ")) {
                                String date = fields[1];
                                String lunch = fields[2];
                                dataList.add(new Object[]{date, lunch, " "});
                                isFind = true;

                            }
                        } else if (Arrays.asList(dinnerOptions).contains(fields[2])) {
                            if (!fields[2].equals(" ")) {
                                String date = fields[1];
                                String dinner = fields[2];
                                dataList.add(new Object[]{date, " ", dinner});
                                isFind = true;

                            }
                        }
                    }
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            errorSound();
            JOptionPane.showMessageDialog(null , " خطا در خواندن فایل " + e.getMessage() , "خطا !" , JOptionPane.ERROR_MESSAGE);
        }

        if (isFind) {
            Object[][] data = new Object[dataList.size()][];
            for (int i = 0; i < dataList.size(); i++) {
                data[i] = dataList.get(i);
            }

            JTable table = new JTable(data, columnNames);
            table.setBackground(colorBackground);
            table.setDefaultRenderer(Object.class, new CustomRendererShowOrder());
            table.getTableHeader().setReorderingAllowed(false);
            JScrollPane scrollPane = new JScrollPane(table);

            JFrame frame = new JFrame();
            frame.setBackground(colorBackground);
            frame.setTitle(" تاریخچه سفارشات کاربر " + Id);
            frame.add(scrollPane);
            scrollPane.setPreferredSize(new Dimension(500, table.getPreferredSize().height + 50));
            frame.setResizable(false);
            frame.pack();
            frame.setVisible(true);
        } else {
            errorSound();
            JOptionPane.showMessageDialog(null ,  "شما سفارشی ندارید!" , "توجه!" , JOptionPane.ERROR_MESSAGE);
        }
    }

    public void showChargeHistory() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        boolean isFind = false;

        String[] columnNames = {" تاریخ ", " ساعت ", " مبلغ "};
        ArrayList<Object[]> dataList = new ArrayList<>();

        Scanner scanner;
        try {
            scanner = new Scanner(new File(ChargeHistoryPath));

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] fields = line.split(" , ");

                if (fields[0].equals(Id)) {
                     String date = fields[1];
                     String time = fields[2];
                     String cash = fields[3];
                     dataList.add(new Object[]{date, time, cash});
                     isFind = true;

                }
            }

        scanner.close();
    } catch (FileNotFoundException e) {
            errorSound();
            JOptionPane.showMessageDialog(null , " خطا در خواندن فایل " + e.getMessage() , "خطا !" , JOptionPane.ERROR_MESSAGE);
        }

        if (isFind) {
            Object[][] data = new Object[dataList.size()][];
            for (int i = 0; i < dataList.size(); i++) {
                data[i] = dataList.get(i);
            }

            JTable table = new JTable(data, columnNames);
            table.setBackground(colorBackground);
            table.setDefaultRenderer(Object.class, new CustomRendererShowOrder());
            table.getTableHeader().setReorderingAllowed(false);
            JScrollPane scrollPane = new JScrollPane(table);

            JFrame frame = new JFrame();
            frame.setBackground(colorBackground);
            frame.setTitle(" تاریخچه شارژ کاربر " + Id);
            frame.add(scrollPane);
            scrollPane.setPreferredSize(new Dimension(500, table.getPreferredSize().height + 50));
            frame.setResizable(false);
            frame.pack();
            frame.setVisible(true);
        } else {
            errorSound();
            JOptionPane.showMessageDialog(null ,  "شما سابقه شارژ ندارید!" , "توجه!" , JOptionPane.ERROR_MESSAGE);
        }
    }
    public void setPricesLabel(){
        if ((!(Organization.equals("NOT")))|| percentFlag) {
            priceLabel.setText(" مجموع هزینه ها : " + (int) (finalPrice/percent));
            finalPriceLabel.setText(" هزینه نهایی بعد از تخفیف : " + cost);
            priceLabel.setHorizontalAlignment(SwingConstants.CENTER);
            finalPriceLabel.setHorizontalAlignment(SwingConstants.CENTER);

        } else {
            priceLabel.setText(" مجموع هزینه ها : " + cost);
            priceLabel.setHorizontalAlignment(SwingConstants.CENTER);
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



//  1402/03/28 15:00 p.m. ~ 18 - 6 - 2023
