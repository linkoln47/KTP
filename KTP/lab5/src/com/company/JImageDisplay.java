package com.company;
import javax.swing.JComponent;
import java.awt.*;
import java.awt.image.BufferedImage;


public class JImageDisplay extends JComponent { //класс изображения, выводящий наш фрактал
    private BufferedImage image; //экземпляр буфферизированного изображения
    public JImageDisplay(int width,int height) { //получение длины и ширины
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); //создание объекта изображения
        Dimension dim = new Dimension(width, height); //переменная для данных масштабирования
        super.setPreferredSize(dim); //само масштабирования
    }
    @Override
    public void paintComponent(Graphics g){ //отрисовка изображения
        g.drawImage(image,0,0, image.getWidth(), image.getHeight(), null);
    }
    public void clearImage (){ //все в черное
        for(int i = 0; i < image.getWidth(); i++)
            for(int j = 0; j < image.getHeight(); j++)
                image.setRGB(i,j,0);
    }
    public void drawPixel(int x, int y, int rgbColor){ //метод для окраса пикселя в цвет
        image.setRGB(x,y,rgbColor);
    }
    public BufferedImage getBufferedImage() { //возращение картинки
        return image;
    }
}
