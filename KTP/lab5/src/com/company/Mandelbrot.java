package com.company;
import java.awt.geom.Rectangle2D;

public class Mandelbrot extends FractalGenerator{

    public static final int MAX_ITERATIONS = 2000; //задания кол-ва итераций

    public void getInitialRange (Rectangle2D.Double range){ //хадание стандартных параметров
        range.x = -2;
        range.y = -1.5;
        range.height = 3;
        range.width = 3;
    }


    public int numIterations(double x, double y) { //расчет по x y, отдельно мнимая и реальная части числа
        double Re = x;
        double Im = y;
        int counter = 0; //установка счетчика
        while ((counter < MAX_ITERATIONS)) {
            counter++;
            double Re2 = Re * Re - Im * Im + x;
            double Im2 = 2 * Re * Im + y;
            Re = Re2;
            Im = Im2;
            if ((Re * Re + Im * Im) > 4)
                break;
        }
        if (counter == MAX_ITERATIONS) //если больше 2к, то будет черный пиксель
            return -1;
        return counter;
    }
    public String toString() //возвращения команды какаой это фрактал
    {
        return "Mandelbrot";
    }
}