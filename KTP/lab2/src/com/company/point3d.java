package com.company;
//Трехмерный класс точки
public class point3d extends  point2d
{
    // Координата z
    private double zCoord;
    //Конструктор инициализации
    public point3d(double x, double y, double z)
    {
        xCoord = x;
        yCoord = y;
        zCoord = z;
    }
    //Конструктор по умолчанию
    public point3d()
    {
        //Вызовите конструктор с двумя параметрами и определите источник
        this(0,0,0);
    }
    //Возвращение координаты Z
    public double getZ()
    {
        return zCoord;
    }
    //Установка значения координаты Z
    public void setZ(double val)
    {
        zCoord = val;
    }
    //проверка равности значений
    public boolean equals(point3d another)
    {
        return xCoord == another.xCoord && yCoord == another.yCoord && zCoord == another.zCoord;
    }
    //вычисление расстояния между двумя точками
    public double distanceTo(point3d another)
    {
        double l = Math.sqrt(Math.pow(xCoord-another.xCoord,2)+Math.pow(yCoord-another.yCoord,2)+
                Math.pow(zCoord-another.zCoord,2));
        String p = Double.toString(l);
        String number = String.format(java.util.Locale.ROOT,"%.2f", l);
        return Double.parseDouble(number);
    }
}
