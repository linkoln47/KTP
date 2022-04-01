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
    private int sizeDisp; //размеры окна ширина*высота
    private JImageDisplay image; //ссылка обновления отображения
    private FractalGenerator FGen; //отображение различных фракталов
    private Rectangle2D.Double range; //объект, определяющий размеры того что показывается в данный момент
    private JComboBox box; //для использования комбобокса, для выбора фракталов

    private FractalExplorer (int sizeDisp){ //конструктор, принимающий размер окна, после чего инициализирвует размер объекта
        this.sizeDisp=sizeDisp;
        this.FGen = new Mandelbrot();
        this.range = new Rectangle2D.Double(0, 0, 0, 0);
        FGen.getInitialRange(this.range);
    }
    public void createAndShowGUI(){ //инициализация  GUI Swing
        JFrame frame = new JFrame("Fractal Explorer"); //что записанов  заголовке
        JButton resetButton = new JButton("Reset"); // запись для кнопки сброса
        JButton saveButton = new JButton("Save Image"); //запись для кнопки сохранения
        JPanel pan1 = new JPanel(); //создание панели
        JPanel pan2 = new JPanel(); //создание панели
        JLabel lbl = new JLabel("Fractal:"); //добавление лебла

        image = new JImageDisplay(sizeDisp, sizeDisp); //задание картинке размеров
        image.addMouseListener(new MouseListener());//действия с мышкой

        box = new JComboBox(); //реализация конструктора комбобокса и добавления туда элементов
        box.addItem(new Mandelbrot()); //вкалдываем экземпляры классов
        box.addItem(new Tricorn());
        box.addItem(new BurningShip());
        box.addActionListener(new ActionHandler()); //добавление кликабельности

        resetButton.setActionCommand("Reset"); //отправка команды
        resetButton.addActionListener(new ActionHandler()); //действие ресет
        saveButton.setActionCommand("Save"); //отправка команды
        saveButton.addActionListener(new ActionHandler()); //действие сохранение

        pan1.add(lbl, java.awt.BorderLayout.CENTER); //централизация лейблов и боксов
        pan1.add(box, java.awt.BorderLayout.CENTER);
        pan2.add(resetButton, java.awt.BorderLayout.CENTER);
        pan2.add(saveButton, java.awt.BorderLayout.CENTER);

        frame.setLayout(new java.awt.BorderLayout()); //устанавливает содержимое окна
        frame.add(image, java.awt.BorderLayout.CENTER); //картинка будет центрироваться по центру
        frame.add(pan1, BorderLayout.NORTH); //конпка будет находится внизу
        frame.add(pan2, BorderLayout.SOUTH); //конпка будет находится внизу
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //устанавливает флажок завершения при закрытии

        frame.pack(); //размещение содержимого окна
        frame.setVisible(true); //делают окно видимым
        frame.setResizable(false); //запрет изменения окна
    }
    public class ActionHandler implements ActionListener { //взаимодействие с мышкой
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("Reset")) { //реализация ресета
                FGen.getInitialRange(range); //возвращение прежнего размера
                drawFractal(); //отрисовка фрактала заново
            } else if (e.getActionCommand().equals("Save")) { //реализация сохранения
                JFileChooser chooser = new JFileChooser(); //установка выбора формата файла
                FileFilter filter = new FileNameExtensionFilter("PNG Images", "png");
                chooser.setFileFilter(filter);
                chooser.setAcceptAllFileFilterUsed(false); //не разрешает пользователь выбрать другой формат
                int t = chooser.showSaveDialog(image); //вывод окна для сохранения
                if (t == JFileChooser.APPROVE_OPTION) { //подтверждение, что пользователь выбрал сохранение
                    try { //попытка сохранить в пнг
                        ImageIO.write(image.getBufferedImage(), "png", chooser.getSelectedFile());
                    } catch (NullPointerException | IOException e1) { //вывод окна об ошибке
                        JOptionPane.showMessageDialog(image, e1.getMessage(), "Cannot Save Image", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                FGen = (FractalGenerator) box.getSelectedItem();
                range = new Rectangle2D.Double(0, 0, 0, 0); //установка самих размеров
                FGen.getInitialRange(range); //установка размера фрактала
                drawFractal(); //отрисовка фрактала
            }
        }
    }

    public class MouseListener extends MouseAdapter { //считывание действий мыши
        @Override
        public void mouseClicked(MouseEvent e) { //принимает действия мышки
            double xCoord = FractalGenerator.getCoord(range.x, range.x + range.width, sizeDisp, e.getX()); //отслеживание полоэения х
            double yCoord = FractalGenerator.getCoord(range.y, range.y + range.width, sizeDisp, e.getY()); //отслеживание положения у
            FGen.recenterAndZoomRange(range, xCoord, yCoord, 0.5); //приближение на 0.5
            drawFractal(); //отрисовка фраткала
        }
    }
    private void drawFractal(){
        for (int x = 0; x < sizeDisp; x++) { //проход по каждому пикселю
            for (int y = 0; y < sizeDisp; y++) {
                int count = FGen.numIterations(FractalGenerator.getCoord(range.x, range.x + range.width, sizeDisp, x),
                        FractalGenerator.getCoord(range.y, range.y + range.width, sizeDisp, y)); //установка счетчика
                if (count == -1) //пиксель в черный
                    image.drawPixel(x, y, 0);
                else //установка цвета в зависимости от счетчика
                {
                    float hue = 0.7f + (float) count / 200f;
                    int rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
                    image.drawPixel(x, y, rgbColor);
                }
            }
        }
        image.repaint(); //обновление дисплея

    }
    public static void main(String[] args) { //точка входа
        FractalExplorer FExp = new FractalExplorer(800);
        FExp.createAndShowGUI();
        FExp.drawFractal();
    }
}
