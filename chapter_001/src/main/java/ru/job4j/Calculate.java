package ru.job4j;
/**
* Class Класс для выводы строки на консоль.
* @author Michael Hodkov
*/
public class Calculate{
	/**
	* Method echo.
	* @param name Your name.
	* @return Echo plus your name.
	*/
	public String echo(String name) {
		return "Echo, echo, echo : " + name;
	}
	
	/**
	* Конструктор, вывод строки в консоль.
	* @param args - args
	*/
	public static void main(String[] args){
		System.out.println("Hello world.");
	}
}