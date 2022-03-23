package com.company;
import javax.swing.JComponent;
import java.awt.*;
import java.awt.image.BufferedImage;

public class JImageDisplay extends JComponent { //класс изображения, выводящий наш фрактал
    //экземпляр буфереизованного изображения
    private BufferedImage image;
    public JImageDisplay(int width,int height) { //конструктор принимающий высоту и ширину
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); //создания объекта изображения
        Dimension dim = new Dimension(width, height);
        super.setPreferredSize(dim); //производится масштабирование изображения
    }
    @Override
    public void paintComponent(Graphics g){ //отрисовка изображения
        g.drawImage(image,0,0, image.getWidth(), image.getHeight(), null);
    }
    public void clearImage (){ //установка всем пикселям ченого цвета
        for(int i = 0; i<image.getWidth(); i++)
            for(int j = 0; j<image.getHeight(); j++)
                image.setRGB(i,j,0);
    }
    public void drawPixel(int x, int y, int rgbColor){ //изменение цвета пикселя
        image.setRGB(x,y,rgbColor);
    }
}




