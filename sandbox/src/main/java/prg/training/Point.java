package prg.training;

/**
 * Created by QA Lady on 3/15/2017.
 */
public class Point {
    public double x;
    public double y;


    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }


    public double distance(Point b) {
        return Math.sqrt(Math.pow((b.x - x), 2) + Math.pow((b.y - y), 2));
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
