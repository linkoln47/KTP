package com.company;
public class Palindrome {

    public static void main(String[] args)
    {
	for (int i = 0; i < args.length; i++) {
        String s = args[i]; //запись слов в массив
        reverseString(s);
        isPalindrome(s);
    }

    }

    public static String reverseString(String s) //функция вывод перевернутое слово
    {
        int N = s.length(); //считывание длины слова
        for (int i = N-1; i >= 0; i--){
            System.out.print(s.charAt(i)); //выводпобуквенно слова
        }
        System.out.print("\n");
        //System.out.println(i); строка выводит количество букв в слове
        return s;
    }

    public static boolean isPalindrome(String s)
    {
        String r = "";
        int N = s.length();
        for (int i = N-1; i >= 0; i--){
           r += s.charAt(i);
        }
        boolean P = r.equals(s); // сравнение слов
        if (P == true) //проверка что вывод
            System.out.println("Слово полиндром ");
        else System.out.println("Слово не полиндром ");
        return P;
    }
}
