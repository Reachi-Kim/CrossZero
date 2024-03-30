import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class CrossZero extends JComponent//класс-наследник
{
    Main namep = new Main();
    //Для хранения состояния ячейки создаём три константы
    public static final int FIELD_EMPTY=0;//пустое поле
    public static final int FIELD_X=10;//поле с крестиком
    public static final int FIELD_0=200;//поле с ноликом
    int [][] field;//объявляем массив игрового поля
    boolean isXturn;//логическая переменная, показывающая, чей ход
    public CrossZero()
    {

        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        field = new int[3][3];//выделяем память под массив при создании компонента
        initGame();
    }
    public void initGame()//сброс игры к начальному состоянию
    {
        for (int i=0; i<3; ++i)
        {
            for (int j=0; j<3; ++j)
            {
                field[i][j] = FIELD_EMPTY;//очищаем массив, заполняя его 0 (пустая ячейка)
            }
        }
    }
    @Override
    protected void processMouseEvent(MouseEvent mouseEvent)//метод, отвечающий за клики мыши на игровом поле
    {
        super.processMouseEvent(mouseEvent);
        if (mouseEvent.getButton()==MouseEvent.BUTTON1)//проверка нажатия левой клавиши
        {
            int x = mouseEvent.getX();//координаты х клика
            int y = mouseEvent.getY();//координаты y клика
            //перевод координат в индексы ячейки в массиве (field)
            int i = (int) ((float) x / getWidth()*3);
            int j = (int) ((float) y / getHeight()*3);
            //проверка возможности сходить в пустую ячейку
            if (field[i][j] == FIELD_EMPTY)
            {
                //проверка, чей ход, если х - ставим крестик, если 0 - ставим нолик
                field[i][j] = isXturn?FIELD_X:FIELD_0;
                isXturn = ! isXturn;//меняем очерёдность хода
                repaint();//перерисовка, вызовет метод paintComponent()
                int res = checkState();
                if (res!=0)
                {
                    if (res==FIELD_0*3)
                    {
                        //победили нолики
                        JOptionPane.showMessageDialog(this, namep.name1 + " победил(а)", "Победа!", JOptionPane.INFORMATION_MESSAGE);
                        //добавляем окно - меню, для простого управления игрой
                        Object[] options = {"Сыграть снова!", "Выход!"};
                        int n = JOptionPane.showOptionDialog(this, "Желаете ли сыграть ещё?", "Меню", JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE,null, options, options[0]);
                        if (n == JOptionPane.YES_OPTION)//если выбрано играть снова - игра запуститься заново
                        {}
                        else if (n == JOptionPane.NO_OPTION)//если выбрано выход - программа завершится
                        {
                            System.exit(0);
                        }
                    }
                    else if (res==FIELD_X*3)
                    {
                        //победили крестики
                        JOptionPane.showMessageDialog(this,namep.name2+ " победил(а)","Победа!",JOptionPane.INFORMATION_MESSAGE);
                        //добавляем окно - меню, для простого управления игрой
                        Object[] options = {"Сыграть снова!", "Выход!"};
                        int n = JOptionPane.showOptionDialog(this, "Желаете ли сыграть ещё?", "Меню", JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE,null, options, options[0]);
                        if (n == JOptionPane.YES_OPTION)//если выбрано играть снова - игра запуститься заново
                        {}
                        else if (n == JOptionPane.NO_OPTION)//если выбрано выход - программа завершится
                        {
                            System.exit(0);
                        }
                    }
                    else
                    {
                        //ничья
                        JOptionPane.showMessageDialog(this,"Ничья!","Ничья!",JOptionPane.INFORMATION_MESSAGE);
                        Object[] options = {"Сыграть снова!", "Выход!"};
                        int n = JOptionPane.showOptionDialog(this, "Желаете ли сыграть ещё?", "Меню", JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE,null, options, options[0]);
                        if (n == JOptionPane.YES_OPTION) {
                        } else if (n == JOptionPane.NO_OPTION) {
                            System.exit(0);
                        }
                    }
                    initGame();//перезапускаем игру
                    repaint();//перерисовываем поля
                }
            }
        }
    }
    void drawX(int i, int j, Graphics graphics)//рисуем крестик
    {
        int w = getWidth();
        int h = getHeight();
        int dw = w / 3;
        int dh = h / 3;
        graphics.setColor(Color.black);//сделаем крестики черными
        int x = i * dw;
        int y = j * dh;
        //линия от верхнего левого угла в правый нижний
        graphics.drawLine(x,y,x+dw,y+dh);
        //линия от нижнего левого угла в правый верхний
        graphics.drawLine(x,y+dh,x+dw,y);
    }
    void draw0(int i, int j, Graphics graphics)//рисуем нолик
    {
        int w = getWidth();
        int h = getHeight();
        int dw = w / 3;
        int dh = h / 3;
        graphics.setColor(Color.red);//сделаем круги красными
        int x = i * dw;
        int y = j * dh;
        graphics.drawOval(x+5*dw/100,y,dw*9/10,dh);//подгоняем нолик по размеру, чтобы не касался стенок ячейки
    }
    void drawX0(Graphics graphics)
    /*
    в массиве field содержатся актуальные данные о поставленных х и 0,
    проходим циклами по всему массиву и если встречается крестик или нолик - рисуем их
     */
    {
        for (int i=0; i<3; ++i)
        {
            for (int j=0; j<3; ++j)
            {
                //если в данной ячейке крестик - рисуем его
                if (field[i][j] == FIELD_X)
                {
                    drawX(i,j,graphics);
                }
                //если в данной ячейке нолик - рисуем его
                else if (field[i][j] == FIELD_0)
                {
                    draw0(i,j,graphics);
                }
            }
        }
    }
    @Override
    protected void paintComponent (Graphics graphics)
    {
        super.paintComponent(graphics);
        //очищаем холст
        graphics.clearRect(0,0,getWidth(),getHeight());
        //рисуем сетку из линий
        drawGrid(graphics);
        //рисуем крестики и нолики
        drawX0(graphics);
    }
    void drawGrid(Graphics graphics)//рисуем сетку из линий
    {
        int w = getWidth();
        int h = getHeight();
        int dw = w / 3;
        int dh = h / 3;
        graphics.setColor(Color.blue);//пусть наши линии будут синие
        for (int i = 1; i < 3; i++)
        {
            graphics.drawLine(0,dh*i,w,dh*i);
            graphics.drawLine(dw*i,0,dw*i,h);
        }
    }
    int checkState()
    /*
    метод каждый ход проверяет сложившуюся ситуацию на "поле боя" и возвращает -1 если ходов не осталось,
    3*FIELD_X если выиграли крестики,
    3*FIELD_0 если выиграли нолики
     */
    {
        //проверка диагоналей
        int diag = 0;
        int diag2 = 0;
        for (int i=0; i<3; i++)
        {
            diag += field[i][i];//сумма значений по диагонали от левого угла
            diag2 += field[i][2-i];//сумма значений по диагонали от правого угла
        }
        if (diag == FIELD_0*3 || diag == FIELD_X*3)//если по одной диагонали стоят крестики или нолики - выход из метода
        {
            return diag;
        }
        if (diag2 == FIELD_0*3 || diag2 == FIELD_X*3)////если по второй диагонали стоят крестики или нолики - выход из метода
        {
            return diag2;
        }
        int check_i, check_j;
        boolean hasEmpty = false;
        //проход по рядам
        for (int i=0; i<3; i++)
        {
            check_i = 0;
            check_j = 0;
            for (int j=0; j<3; j++)
            {
                //суммируем знаки в текущем ряду
                if (field[i][j] == 0)
                {
                    hasEmpty = true;
                }
                check_i += field[i][j];
                check_j += field[j][i];
            }
            if (check_i == FIELD_0*3 || check_i == FIELD_X*3)//если выигрыш крестика или нолика - выход
            {
                return check_i;
            }
            if (check_j == FIELD_0*3 || check_j == FIELD_X*3)//если выигрыш крестика или нолика - выход
            {
                return check_j;
            }
        }
        if (hasEmpty) return 0;//есть пустые ячейки
        else return -1;//ничья
    }
}
