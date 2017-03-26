package prg.training.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import prg.training.Square;

/**
 * Created by QA Lady on 3/26/2017.
 */
@Test
public class SquareTests {
    public void testArea() {
        Square s = new Square(5);
//        Assert.assertTrue(s.area() == 25, "check that area euqals to 25");
//        or in case of using assertEquals the type should match too
        Assert.assertEquals(s.area(), 25.0, "check that area euqals to 25");

    }
}
