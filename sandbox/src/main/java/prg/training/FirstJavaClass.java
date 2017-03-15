package prg.training;

public class FirstJavaClass {


    public static void main(String[] args) {
        printSomething("Learning: Selenium with Java");

        Square s = new Square(5);
        printSomething("Area of a Square with a side of " + s.l + " is equal to " + s.area());

        Rectangle r = new Rectangle(4, 6);
        printSomething("Area of a Rectangle with a short side of " + r.a + " and a longer side of " + r.b + " is equal to " + r.area());
    }

    public static void printSomething(String s) {
        System.out.println(s);
    }

}



	