package Classes.Theme;

/*
این کد یک کلاس `CustomRendererShowOrder` را برای سفارشی کردن نمایش سلول‌های جدولی  تعریف می‌کند.
 این کلاس از کلاس `DefaultTableCellRenderer` ارث بری می‌کند.

متد `getTableCellRendererComponent` برای سفارشی کردن نمایش هر سلول جدول استفاده می‌شود.
 این متد یک شیء `JTable`، یک شیء `Object` (مقدار سلول) و چند پارامتر دیگر را به عنوان ورودی دریافت می‌کند
  و یک شیء `Component` ( یک `JLabel`) را برمی‌گرداند.

متد `getTableCellRendererComponent` ابتدا شیء `Component` را به عنوان نمایش پیش‌فرض سلول دریافت کرده
و در متغیر `c` ذخیره می‌کند.

سپس با استفاده از `row % 2`، بررسی می‌کند که آیا شماره سطر جاری (row) به صورت فرد یا زوج است.
اگر شماره سطر جاری زوج باشد، رنگ پس‌زمینه `c` به رنگ خاکستری روشن تنظیم می‌شود،
 در غیر این صورت (برای شماره سطر جاری فرد)، رنگ پس‌زمینه `c` به رنگ پیش‌فرض جدول (`table.getBackground()`) تنظیم می‌شود.

در کل، این کلاس برای سفارشی کردن نمایش سلول‌های جدولی استفاده می‌شود،
 به گونه‌ای که سطرهای زوج با رنگ پس‌زمینه خاکستری روشن و سطرهای فرد با رنگ پس‌زمینه پیش‌فرض جدول نمایش داده می‌شوند.

   `````````````````````````````````````````````````````

 This code defines a custom cell renderer class called `CustomRendererShowOrder` that extends `DefaultTableCellRenderer`.
 This renderer is used to customize the appearance of cells in a `JTable` that displays orders.

Here's a breakdown of the code:

    - The `CustomRendererShowOrder` class overrides the `getTableCellRendererComponent` method from
      the `DefaultTableCellRenderer` class.
      This method is called by the `JTable` to obtain the component that will be used to render a cell in the table.
    - The overridden `getTableCellRendererComponent` method takes several parameters:
      the `JTable` instance, the `value` of the cell, a boolean indicating whether the cell is selected,
      a boolean indicating whether the cell has focus, the `row` and `column` indices of the cell.
    - The method first calls the `super.getTableCellRendererComponent` to obtain the default component for rendering the cell.
    - It then checks if the `row` is an even number (row % 2 == 0). If it is,
      the background color of the component is set to `Color.LIGHT_GRAY`.
    - If the `row` is an odd number, the background color of the component is set to the default background color of the table obtained from `table.getBackground()`.
    - Finally, the modified component is returned.

This custom renderer can be used by setting an instance of `CustomRendererShowOrder` as the cell renderer for
specific columns in the `JTable` displaying the orders. This renderer will then be applied to the cells in those columns,
and the appearance will be customized based on the logic in the `getTableCellRendererComponent` method.
Even rows will have a light gray background, while odd rows will have the default background color of the table.
 */

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class CustomRendererShowOrder extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (row % 2 == 0) {
            c.setBackground(Color.LIGHT_GRAY);
        } else {
            c.setBackground(table.getBackground());
        }

        return c;
    }
}

