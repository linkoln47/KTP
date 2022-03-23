package com.company;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

public class FractalExplorer {
    private int sizeDisp; //ширина экрана высота*ширине
    private JImageDisplay image; //ссылка JImageDisplay обновление отображения
    private FractalGenerator FGen; //объект на будущее, для отображения других фракталов
    private Rectangle2D.Double range; //объект определяющий размер того, что показывается в данный момент

    private FractalExplorer (int sizeDisp){ //конструктор, принимающий размер окна, после чего иницилизирует размер объекта и генератора фрактала
        this.sizeDisp = sizeDisp;
        this.FGen = new Mandelbrot();
        this.range = new Rectangle2D.Double(0, 0, 0, 0);
        FGen.getInitialRange(this.range);
    }
    public void createAndShowGUI(){ //инициализация  GUI Swing и кнопку сброса
        JFrame frame = new JFrame("Fractal Explorer"); //что записанов  заголовке
        JButton Button = new JButton("Reset"); //что будет записано в кнопке

        image = new JImageDisplay(sizeDisp, sizeDisp);
        image.addMouseListener(new MouseListener());

        Button.addActionListener(new ActionHandler());

        frame.setLayout(new java.awt.BorderLayout()); //устанавливает содержимое окна
        frame.add(image, java.awt.BorderLayout.CENTER); //картинка будет центрироваться по центру
        frame.add(Button, java.awt.BorderLayout.SOUTH); //конпка будет находится внизу
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //устанавливает флажок завершения при закрытие

        frame.pack(); //размещение содержимого окна
        frame.setVisible(true); //делают окно видимым
        frame.setResizable(false); //запрет изменения окна
    }
    public class ActionHandler implements ActionListener { //реализация кнопки reset
        public void actionPerformed(ActionEvent e) {
            FGen.getInitialRange(range); //установка изначальных координат для расчета
            drawFractal(); //отрисовка фрактала, после reset
        }
    }
    public class MouseListener extends MouseAdapter { //реализация нажатия кнопки мыши
        @Override
        public void mouseClicked(MouseEvent e) { //отслеживает действия с мышкой
            double xCoord = FractalGenerator.getCoord(range.x, //получение х координат
                    range.x + range.width, sizeDisp, e.getX());
            double yCoord = FractalGenerator.getCoord(range.y, //получение у координат
                    range.y + range.width, sizeDisp, e.getY());
            FGen.recenterAndZoomRange(range, xCoord, yCoord, 0.5); //приближение на 0.5
            drawFractal(); //отрисовка фрактала после приближения
        }
    }
    private void drawFractal(){ //вспомогательный метод для отображения фрактала
        for (int x = 0; x < sizeDisp; x++) { //проход по коррдинате х
            for (int y = 0; y < sizeDisp; y++) { //проход по координате у
                //вычесление кол-во итераций для координат во фрактале
                int count = FGen.numIterations(FractalGenerator.getCoord(range.x, range.x + range.width, sizeDisp, x),
                        FractalGenerator.getCoord(range.y, range.y + range.width, sizeDisp, y));
                if (count == -1) //при этом значение, устанавливает черный цвет пиксели
                    image.drawPixel(x, y, 0);
                else
                {
                    //установка цвета в зависисомти от итерации
                    float hue = 0.7f + (float) count / 200f; //вычисление цвета
                    int rgbColor = Color.HSBtoRGB(hue, 1f, 1f); //установка цвета
                    image.drawPixel(x, y, rgbColor); //отрисовка пикселя
                }
            }
        }
        image.repaint(); //обновление дисплея

    }
    public static void main(String[] args) {
        FractalExplorer FExp = new FractalExplorer(800);//создание окна
        FExp.createAndShowGUI();//вызов GUI Swing
        FExp.drawFractal(); //вызов отрисовка фрактала
    }
}
