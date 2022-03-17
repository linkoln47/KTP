package com.company;


//Двумерный класс точки
public class point2d
{
        //координата Х
        protected double xCoord;
        // Координата y
        protected double yCoord;

        //Конструктор инициализации
        public point2d(double x, double y) {
            xCoord = x;
            yCoord = y;
        }

        //Конструктор по умолчанию
        public point2d() {
            //Вызовите конструктор с двумя параметрами и определите источник
            this(0, 0);
        }

        //Возвращение координаты Х
        public double getX() {
            return xCoord;
        }

        //Возвращение координаты Y
        public double getY() {
            return yCoord;
        }

        //Установка значения координаты Х
        public void setX(double val) {
            xCoord = val;
        }

        //Установка значения координаты Y
        public void setY(double val) {
            yCoord = val;
        }

        //проверка равности значений
        public boolean equals(point2d another) {
            return xCoord == another.xCoord && yCoord == another.yCoord;
        }
}
