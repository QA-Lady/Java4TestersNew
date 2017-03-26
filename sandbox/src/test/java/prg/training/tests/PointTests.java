package prg.training.tests;


import org.testng.Assert;
import org.testng.annotations.Test;
import prg.training.Point;

/**
 * Created by QA Lady on 3/26/2017.
 */
public class PointTests {

    @Test
    public void distanceTest1() {
        checkDistance(new Point(-8, 2), new Point(1, 5), 9.487);
    }

    @Test
    public void distanceTest2() {
        checkDistance(new Point(3, 9), new Point(0, 5), 5);
    }

    public void checkDistance(Point point1, Point point2, double expDistance) {
        Point p1 = point1;
        Point p2 = point2;
        double actualDistance = p1.distance(p2);
        System.out.println("checking actual distance rounded to 3 decimal points");
        Assert.assertEquals(Math.round(actualDistance * 1000d) / 1000d, expDistance, "check distance");
    }
}