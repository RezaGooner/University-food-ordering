package Frames.Order;

/*
این کد یک کلاس به نام `BalanceHandler` را شامل می‌شود که متدهای مربوط به مدیریت موجودی حساب کاربران را پیاده‌سازی می‌کند.

در این کلاس، یک فیلد به نام `filePath` تعریف شده است که مسیر فایل حاوی موجودی حساب کاربران را نگهداری می‌کند.

متد `getBalance` با دریافت `id` کاربر، موجودی حساب آن کاربر را با استفاده از خواندن از فایل مشخص شده در فیلد `filePath`، پیدا کرده و آن را به صورت رشته برمی‌گرداند.

متد `decreaseBalance` با دریافت `id` کاربر و مبلغ کاهش موجودی، موجودی حساب کاربر را کاهش داده و نتیجه را در فایل مشخص شده در فیلد `filePath` ذخیره می‌کند.

متد `increaseBalance` با دریافت `id` کاربر، از کاربر می‌خواهد که مبلغی را که می‌خواهد به حسابش اضافه کند، وارد کند و سپس
موجودی حساب کاربر را با این مبلغ افزایش داده و نتیجه را در فایل مشخص شده در فیلد `filePath` ذخیره می‌کند.

متد `addNewUser` با دریافت `id` کاربر، یک ردیف جدید برای آن کاربر با موجودی صفر را در فایل مشخص شده در فیلد `filePath` ایجاد می‌کند.
این متد هنگامی به کار میرود که کاربر جدیدی قصد ثبت نام داشته باشد.

متد `returningOrderCost` با دریافت `id` کاربر و مبلغ سفارش، موجودی حساب کاربر را با این مبلغ افزایش داده و نتیجه را در فایل مشخص شده در فیلد `filePath` ذخیره می‌کند.
این متد هنگامی به کار میرود که کاربر قصد بازگرداندن سفارشات خود را داشته باشد تا پول سفارشات به حساب وی اضافه شود

   `````````````````````````````````````````````````````

It contains several methods related to handling balances and transactions. Here is a breakdown of the code:

1. The class imports various classes and libraries for input/output operations, date and time handling, and graphical user interface components.

2. The `getBalance` method takes an `id` as input and reads a file (`BalancesPath`) to retrieve the balance associated with that ID. It returns the balance as a string.

3. The `decreaseBalance` method takes an `id` and a `price` as input. It reads the `BalancesPath` file, reduces the balance associated with the ID by the given price, and updates the file accordingly.

4. The `increaseBalance` method prompts the user to enter an amount, adds it to the balance associated with the ID, and updates the `BalancesPath` file. It also plays a success sound when the operation is completed.

5. The `addNewUser` method appends a new user with a balance of 0 to the `BalancesPath` file.

6. The `writeChargeBalance` method writes the details of a charge transaction, including the user ID, date, time, and cash amount, to the `ChargeHistoryPath` file.

7. The `returningOrderCost` method increases the balance associated with the ID by a given cost. It is used when returning an order.

8. The `checkBalance` method checks if the balance associated with the ID is sufficient to cover a given cost. It returns `true` if the balance is sufficient and `false` otherwise.

Overall, this code provides functionality for managing balances, performing transactions, and recording transaction history.
 */

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;


import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;

import static Classes.Pathes.FilesPath.BalancesPath;
import static Classes.Pathes.FilesPath.ChargeHistoryPath;
import static Classes.Theme.SoundEffect.errorSound;
import static Classes.Theme.SoundEffect.successSound;


public class BalanceHandler {




    public static String getBalance(String id) {

        try (BufferedReader br = new BufferedReader(new FileReader(BalancesPath))) {
            String line;
            /*
            در هر خط از فایل اطلاعات بدین صورت ذخیره شده است :
            ID : Balance
             */
            while ((line = br.readLine()) != null) {
                if (id.equals(line.split(" : ")[0])) {
                    return line.split(" : ")[1];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "خطا";
    }

    public static void decreaseBalance(String id, int price) {
        try (BufferedReader br = new BufferedReader(new FileReader(BalancesPath))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(id + " : ")) {
                    try {
                        int oldValue = Integer.parseInt(line.split(" : ")[1].trim());
                        int newValue = oldValue - price;
                        sb.append(id).append(" : ").append(newValue).append("\n");
                    } catch (NumberFormatException e) {
                        sb.append(line).append("\n");
                    }
                } else {
                    sb.append(line).append("\n");
                }
            }
            try (FileWriter writer = new FileWriter(BalancesPath)) {
                writer.write(sb.toString());
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void increaseBalance(String id) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        String input = JOptionPane.showInputDialog("میزان مبلغ مورد نظر را وارد نمایید : ");
        if ((!input.equals(null)) && (!input.isEmpty())) {
            if (!(Integer.parseInt(input) <= 0)) {
                int value = Integer.parseInt(input);
                try (BufferedReader br = new BufferedReader(new FileReader(BalancesPath))) {
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        if (line.startsWith(id + " : ")) {
                            try {
                                int oldValue = Integer.parseInt(line.split(" : ")[1].trim());
                                int newValue = Integer.parseInt(input) + oldValue;
                                sb.append(id).append(" : ").append(newValue).append("\n");
                            } catch (NumberFormatException e) {
                                sb.append(line).append("\n");
                            }
                        } else {
                            sb.append(line).append("\n");
                        }
                    }
                    try (FileWriter writer = new FileWriter(BalancesPath)) {
                        writer.write(sb.toString());
                        try {
                            successSound();
                        } catch (Exception ex) {

                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                writeChargeBalance(input, id);
                JOptionPane.showMessageDialog(null, "حساب شما به اندازه " + input + " تومان شارژ شذ!");
            } else {
                errorSound();
                JOptionPane.showMessageDialog(null , "مقدار نامعتبر !");
            }
        }
    }

    public static void addNewUser(String id) {
        try (FileWriter writer = new FileWriter(BalancesPath, true)) {
            writer.write(id + " : 0\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeChargeBalance(String cash , String id) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        String timeString = time.toString().substring(0 , 7);
        String dateTimeString = date.toString();

        try {
            FileWriter fileWriter = new FileWriter(ChargeHistoryPath, true);
            fileWriter.write("\n" + id + " , " + dateTimeString + " , " + timeString + " , " + cash);
            fileWriter.close();
        } catch (Exception e) {
            errorSound();
            JOptionPane.showMessageDialog(null , e.getMessage());
        }
    }

    public static void returningOrderCost(String id, int cost) {
        try (BufferedReader br = new BufferedReader(new FileReader(BalancesPath))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(id + " : ")) {
                    try {
                        int oldValue = Integer.parseInt(line.split(" : ")[1].trim());
                        int newValue = cost + oldValue;
                        sb.append(id).append(" : ").append(newValue).append("\n");
                    } catch (NumberFormatException e) {
                        sb.append(line).append("\n");
                    }
                } else {
                    sb.append(line).append("\n");
                }
            }
            try (FileWriter writer = new FileWriter(BalancesPath)) {
                writer.write(sb.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static boolean checkBalance(String id , int cost){
        if (getBalance(id).equals("0")){
            return false;
        } else if (Integer.parseInt(getBalance(id)) < cost) {
            return false;
        }
        return true;
    }
}



//   1402/03/11 14:00 p.m. ~ 1 - 6 - 2023