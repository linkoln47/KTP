package com.company;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        System.out.print("Input Point A: \n");
        point3d A = new point3d(in.nextDouble(), in.nextDouble(), in.nextDouble());
        System.out.print("Input Point B: \n");
        point3d B = new point3d(in.nextDouble(), in.nextDouble(), in.nextDouble());
        System.out.print("Input Point C: \n");
        point3d C = new point3d(in.nextDouble(), in.nextDouble(), in.nextDouble());
        if (A.equals(B)||B.equals(C)||C.equals(A))
            System.out.println("Ошибка: Есть равные точки");
        else
            System.out.println("Площадь треугольника = " + computeArea(A, B, C));
    }

    public static double computeArea(point3d first, point3d second, point3d third)
    {
        double a;
        double b;
        double c;
        double P;
        double S;
        a = first.distanceTo(second);
        b = second.distanceTo(third);
        c = third.distanceTo(first);
        P = (a+b+c)/2;
        S = Math.sqrt(P*(P-a)*(P-b)*(P-c));
        String number = String.format(java.util.Locale.ROOT,"%.2f", S);
        return Double.parseDouble(number);
    }

}
