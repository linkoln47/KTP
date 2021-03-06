package com.company;
import java.awt.geom.Rectangle2D;

public class BurningShip extends FractalGenerator{ //реализация фрактала горящего корабля

    public static final int MAX_ITERATIONS = 2000; //ограничение на 2к

    public void getInitialRange (Rectangle2D.Double range){ //задание изначальных диапазонов
        range.x = -2;
        range.y = -2.5;
        range.height = 4;
        range.width = 4;
    }


    public int numIterations(double x, double y) {
        double Re = x;
        double Im = y;
        int counter = 0;
        while ((counter < MAX_ITERATIONS)) {
            counter++;
            double Re2 = Re * Re - Im * Im + x;
            double Im2 = Math.abs(2 * Re * Im) + y;
            Re = Re2;
            Im = Im2;
            if ((Re * Re + Im * Im) > 4)
                break;
        }
        if (counter == MAX_ITERATIONS)
            return -1;
        return counter;
    }
    public String toString() //возвращение команды какой это фрактал
    {
        return "Burning Ship";
    }
}
