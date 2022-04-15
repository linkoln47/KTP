package com.company;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.geom.Rectangle2D;


public class FractalExplorer {
    private int sizeDisp; //размер окна
    private JImageDisplay image; //ссылка обновления отображения
    private FractalGenerator FGen; //отображение различных фракталов
    private Rectangle2D.Double range; //объект, определяющий размеры того что показываетс яв данный момент
    private JComboBox box; //для использования комбобокса
    private int rowsRemaning; //переменная для отслеживания того сколько строк осталось
    JButton resetButton; //кнопка ресета
    JButton saveButton; //кнопка сохранения

    private FractalExplorer (int sizeDisp){ //конструктор, принимающий размер окна, после чего инициализирует объект
        this.sizeDisp=sizeDisp;
        this.FGen = new Mandelbrot();
        this.range = new Rectangle2D.Double(0, 0, 0, 0);
        FGen.getInitialRange(this.range);
    }

    public void createAndShowGUI(){ // инициализация GUI Swing
        JFrame frame = new JFrame("Fractal Explorer"); //запись в заголовке
        JPanel pan1 = new JPanel(); //создание панели
        JPanel pan2 = new JPanel(); //создание панели
        JLabel lbl = new JLabel("Fractal:"); //добавление лейбла

        image = new JImageDisplay(sizeDisp, sizeDisp); //задание окну размеров
        image.addMouseListener(new MouseListener()); //считывание действий мышки

        box = new JComboBox(); //реализация конструктора комбобокса, и добавления туда элементов
        box.addItem(new Mandelbrot()); //вкладывание экземпляров классов
        box.addItem(new Tricorn());
        box.addItem(new BurningShip());
        box.addActionListener(new ActionHandler()); //добавление действия при нажатии

        resetButton = new JButton("Reset"); //добавление надписей на кнопки, отправку команд и добавление действия при нажатии
        resetButton.setActionCommand("Reset");
        resetButton.addActionListener(new ActionHandler());
        saveButton = new JButton("Save Image");
        saveButton.setActionCommand("Save");
        saveButton.addActionListener(new ActionHandler());

        pan1.add(lbl, java.awt.BorderLayout.CENTER); //центрирвания панелей и боксов
        pan1.add(box, java.awt.BorderLayout.CENTER);
        pan2.add(resetButton, java.awt.BorderLayout.CENTER);
        pan2.add(saveButton, java.awt.BorderLayout.CENTER);

        frame.setLayout(new java.awt.BorderLayout()); //установка границ дя окна
        frame.add(image, java.awt.BorderLayout.CENTER); //добавление картинки фрактала
        frame.add(pan1, BorderLayout.NORTH); //централизация панелей
        frame.add(pan2, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//закрытие программы при нажатие на крестик

        frame.pack();//размещение содержимого окна
        frame.setVisible(true);//делает окно видимым
        frame.setResizable(false);//запрет изменения размеров окна
    }

    public class ActionHandler implements ActionListener { //взаимодействие с мышкой
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("Reset")) { //реализация ресета
                FGen.getInitialRange(range);
                drawFractal();
            } else if (e.getActionCommand().equals("Save")) { //реализация сохранения
                JFileChooser chooser = new JFileChooser();
                FileFilter filter = new FileNameExtensionFilter("PNG Images", "png");
                chooser.setFileFilter(filter);
                chooser.setAcceptAllFileFilterUsed(false);
                int t = chooser.showSaveDialog(image);
                if (t == JFileChooser.APPROVE_OPTION) {
                    try {
                        ImageIO.write(image.getBufferedImage(), "png", chooser.getSelectedFile());
                    } catch (NullPointerException | IOException e1) {
                        JOptionPane.showMessageDialog(image, e1.getMessage(), "Cannot Save Image", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                FGen = (FractalGenerator) box.getSelectedItem();
                range = new Rectangle2D.Double(0, 0, 0, 0);
                FGen.getInitialRange(range);
                drawFractal();
            }
        }
    }

    public class MouseListener extends MouseAdapter { //считывание действий мыши
        @Override
        public void mouseClicked(MouseEvent e) {
            if (rowsRemaning==0) { //условие, чтобы мышка работала только когда отрисовка закончена
                double xCoord = FractalGenerator.getCoord(range.x, range.x + range.width, sizeDisp, e.getX());
                double yCoord = FractalGenerator.getCoord(range.y, range.y + range.width, sizeDisp, e.getY());
                FGen.recenterAndZoomRange(range, xCoord, yCoord, 0.5);
                drawFractal();
            }
        }
    }

    private void drawFractal(){ //вызывает метод enableUI, чтобы отклчить элементы
        enableUI(false);
        rowsRemaning = sizeDisp; //устанавливает количество строк, которое осталось
        for (int i = 0; i < sizeDisp; i++)
        {
            FractalWorker rowDrawer = new FractalWorker(i);
            rowDrawer.execute(); //после прохождения строки завершает ее
        }
    }

    private class FractalWorker extends SwingWorker<Object, Object>{ //класс отвечает за вычисление значений цвета для одной строки фрактала
        private int yCoord; //координата вычисляемой строки
        private int[] rgb; // массив для хранения вычесленных значений цвета
        public FractalWorker(int yCoord) //конструктор, получает координату и сохраняет ее
        {
            this.yCoord = yCoord;
        }
        public Object doInBackground()  //вызывается в фоновом потоке
        {
            rgb = new int[sizeDisp]; // сохраняет каждое значение цвета в соот элементе массива
            for (int i = 0; i < sizeDisp; i++) {
                int count = FGen.numIterations(FractalGenerator.getCoord(range.x,
                        range.x + range.width, sizeDisp, i),
                        FractalGenerator.getCoord(range.y, range.y + range.width, sizeDisp, yCoord));
                if (count == -1)
                    rgb[i]=0;
                else
                {
                    double hue = 0.7f + (float) count / 200f;
                    int rgbColor = Color.HSBtoRGB((float) hue, 1f, 1f);
                    rgb[i]= rgbColor;
                }
            }
            return null; //возвращает объект типа object, указанных в объявлении SwingWorker
        }
        public void done() //вызывается из потока обработки событий, когда фоновая задача завершена
        {
            for (int i = 0; i < sizeDisp; i++)
                image.drawPixel(i,yCoord,rgb[i]); //отрисовка построчно
            image.repaint(0, 0, yCoord, sizeDisp, 1); //укзываем область для перерисовки
            rowsRemaning--;
            if (rowsRemaning==0) //включение панелек, при окончании отрисовок
                enableUI(true);
        }
    }
    public void enableUI(boolean var) //включает отключает кнопки через var = 0, 1
    {
        saveButton.setEnabled(var);
        resetButton.setEnabled(var);
        box.setEnabled(var);
    }
    public static void main(String[] args) {
        FractalExplorer FExp = new FractalExplorer(800);
        FExp.createAndShowGUI();
        FExp.drawFractal();
    }
}