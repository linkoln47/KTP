package com.company;
import java.awt.geom.Rectangle2D;

//расчет фрактала Мандельброта
public class Mandelbrot extends FractalGenerator{

    public static final int MAX_ITERATIONS = 2000; //установка максимального кол-во итераций

    public void getInitialRange (Rectangle2D.Double range){ //установка изначальных для расчета
        range.x = -2;
        range.y = -1.5;
        range.height = 3;
        range.width = 3;
    }


    public int numIterations(double x, double y) { //расчет итераций
        double Re = x;
        double Im = y;
        int counter = 0; //счетчик
        while ((counter < MAX_ITERATIONS)) { //пока меньше 2к,
            counter++;
            double Re2 = Re * Re - Im * Im + x; //реализация реальной части числа
            double Im2 = 2 * Re * Im + y; //реализация мнимой части числа
            Re = Re2;
            Im = Im2;
            if ((Re * Re + Im * Im) > 4)
                break;
        }
        if (counter == MAX_ITERATIONS) //если достигнут максимум возвращает -1
            return -1;
        return counter;
    }
}