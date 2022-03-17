package com.company;

public class Primes
{
    public static void IsPrime()
    {
    System.out.print("1 2 3 5 7 "); //вывод базовых простых чисел
    for (int i = 8; i < 100; i++) //перебор чисел от 8 до 99 на проверку того простые они или нет
        if (i%2!=0 && i%3!=0 && i%5!=0 && 1%7!=0) //само условие провекри через базовые простые числа
            System.out.print(i + " "); //вывод простых чисел
    }
    public static void main(String[] args)
    {
        IsPrime();
    } //вызов функции
}
