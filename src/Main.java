import javax.swing.*;
import java.awt.*;


public class Main
{
    public static String name1;
    public static String  name2;
    public static void main(String [] args) {
        name1 = JOptionPane.showInputDialog("Введите имя Первого игрока");//окно с вводом имени Первого игрока
        name2 = JOptionPane.showInputDialog("Введите имя Второго игрока");//окно с вводом имени Второго игрока
        JFrame window = new JFrame("Крестики - Нолики");//Окно игры
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//кнопка "х" для закрытия окна
        window.setSize(300, 300);//размер окна
        window.setLayout(new BorderLayout());//компановка
        window.setLocationRelativeTo(null);//окно по центру экрана
        window.setVisible(true);//видимость окна
        CrossZero game = new CrossZero();//объект класса игры
        window.add(game);//добавление объекта в окно
    }

}