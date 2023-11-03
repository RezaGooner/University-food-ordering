package Classes.Theme;

/*
این کد یک رابط کاربری سفارشی برای دکمه ها در یک برنامه جاوا بر پایه Swing تعریف می‌کند.
این رابط کاربری به نام `StyledButtonUI` است و از کلاس `BasicButtonUI` ارائه شده توسط Swing به ارث می‌برد.

متد `installUI` برای تنظیم برخی از ویژگی‌های اولیه دکمه، از جمله شفاف کردن آن (`button.setOpaque(false)`)
و تنظیم یک حاشیه خالی با چند پیکسل پدینگ (`button.setBorder(new EmptyBorder(5, 15, 5, 15))`)، بازنویسی شده است.

متد `paint` نیز برای رندر کردن پس‌زمینه دکمه با گوشه‌های گرد، بازنویسی شده است.
متد `paintBackground` برای انجام رندرینگ واقعی استفاده می‌شود.
 این متد یک شیء `Graphics` و یک `JComponent` را به عنوان ورودی دریافت می‌کند،
 همچنین عدد صحیحی به عنوان `yOffset` که مشخص می‌کند چقدر از بالای پس‌زمینه دکمه باید به پایین انتقال داده شود.
  این بر اساس این است که دکمه فشرده شده است یا نه و برای به دست آوردن این ارتفاع از یک انتخاب 3D استفاده می‌شود.

متد `paintBackground` ابتدا اندازه دکمه را می‌گیرد و یک شیء `Graphics2D` را از شیء `Graphics` ورودی
 با روش ضد تاری می‌سازد تا لبه‌های مستطیل گرد را صیقل دهد.

سپس نیمه بالای پس‌زمینه دکمه با رنگ تیره‌تر (`c.getBackground().darker()`)
و نیمه پایین با رنگ پس‌زمینه دکمه (`c.getBackground()`) را رندر می‌کند
و با استفاده از `fillRoundRect` مستطیل گرد را رسم می‌کند.

در کل، این رابط کاربری به دکمه‌ها یک ظاهر شیک با گوشه‌های گرد و اثر 3D می‌دهد
 که با رندر کردن پس‌زمینه با دو رنگ متفاوت و انتقال دادن بالای پس‌زمینه به شکلی که 3D به نظر بیاید، ایجاد شده است.

   `````````````````````````````````````````````````````

This code defines a custom UI class called `StyledButtonUI` that extends `BasicButtonUI`.
This UI class is used to style buttons in a Swing application.

Here's a breakdown of the code:

    - The `installUI` method is overridden to customize the button's appearance when the UI is installed on a component.
      The method sets the button to be opaque (transparent) and applies an empty border with specific insets.
    - The `paint` method is overridden to customize the button's painting behavior.
      The method first checks if the button is pressed (`b.getModel().isPressed()`) and adjusts
      the background painting accordingly. Then, it calls `super.paint(g, c)` to perform the default button painting.
    - The `paintBackground` method is a helper method used by the `paint` method to paint the button's background.
      It receives the `Graphics` object, the component, and a `yOffset` value.
      It uses a `Graphics2D` object to apply rendering hints for antialiasing. It then paints the button's
      background using two rounded rectangles. The first rectangle is filled with a darker color,
      and the second rectangle is filled with the button's background color.



By setting the `StyledButtonUI` as the UI for a `JButton`, the button will be styled according to the customization
defined in the `StyledButtonUI` class.
 */

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;

public class StyledButtonUI extends BasicButtonUI {

    @Override
    public void installUI (JComponent c) {
        super.installUI(c);
        AbstractButton button = (AbstractButton) c;
        button.setOpaque(false);
        button.setBorder(new EmptyBorder(5, 15, 5, 15));
    }

    @Override
    public void paint (Graphics g, JComponent c) {
        AbstractButton b = (AbstractButton) c;
        paintBackground(g, b, b.getModel().isPressed() ? 2 : 0);
        super.paint(g, c);
    }

    private void paintBackground (Graphics g, JComponent c, int yOffset) {
        Dimension size = c.getSize();
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(c.getBackground().darker());
        g.fillRoundRect(0, yOffset, size.width, size.height - yOffset, 10, 10);
        g.setColor(c.getBackground());
        g.fillRoundRect(0, yOffset, size.width, size.height + yOffset - 5, 10, 10);
    }
}