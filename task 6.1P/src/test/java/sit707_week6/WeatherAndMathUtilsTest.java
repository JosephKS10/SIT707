package sit707_week6;

import org.junit.Assert;
import org.junit.Test;

public class WeatherAndMathUtilsTest {

    // ============================
    // Student identity tests
    // ============================
    @Test
    public void testStudentIdentity() {
        String studentId = "s225053039";
        Assert.assertNotNull("Student ID is null", studentId);
    }

    @Test
    public void testStudentName() {
        String studentName = "Joseph Kalayathankal Saji";
        Assert.assertNotNull("Student name is null", studentName);
    }

   
    @Test
    public void testCancelByDangerousWindspeed() {
        // windSpeed > 70.0
        Assert.assertEquals("CANCEL", WeatherAndMathUtils.weatherAdvice(70.1, 0.0));
    }

    @Test
    public void testCancelByDangerousRainfall() {
        // precipitation > 6.0
        Assert.assertEquals("CANCEL", WeatherAndMathUtils.weatherAdvice(0.0, 6.1));
    }

    @Test
    public void testCancelByConcerningCombo() {
        // windSpeed > 45.0 AND precipitation > 4.0
        Assert.assertEquals("CANCEL", WeatherAndMathUtils.weatherAdvice(45.1, 4.1));
    }


    @Test
    public void testWarnByConcerningWindspeed() {
        // windSpeed > 45.0 only
        Assert.assertEquals("WARN", WeatherAndMathUtils.weatherAdvice(45.1, 0.0));
    }

    @Test
    public void testWarnByConcerningRainfall() {
        // precipitation > 4.0 only
        Assert.assertEquals("WARN", WeatherAndMathUtils.weatherAdvice(0.0, 4.1));
    }


    @Test
    public void testAllClearNormalConditions() {
        Assert.assertEquals("ALL CLEAR", WeatherAndMathUtils.weatherAdvice(30.0, 2.0));
    }

    @Test
    public void testAllClearAtThresholdBoundary() {
        // Boundary value test improves branch coverage confidence.
        Assert.assertEquals("ALL CLEAR", WeatherAndMathUtils.weatherAdvice(45.0, 4.0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeWindspeedThrows() {
        WeatherAndMathUtils.weatherAdvice(-1.0, 0.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativePrecipitationThrows() {
        WeatherAndMathUtils.weatherAdvice(0.0, -1.0);
    }


    @Test
    public void testIsEvenWithEvenNumber() {
        Assert.assertTrue(WeatherAndMathUtils.isEven(4));
    }

    @Test
    public void testIsEvenWithZero() {
        Assert.assertTrue(WeatherAndMathUtils.isEven(0));
    }

    // Replaces the old failing testFalseNumberIsEven stub
    @Test
    public void testFalseNumberIsEven() {
        Assert.assertFalse(WeatherAndMathUtils.isEven(7));
    }


    @Test
    public void testIsPrimeWithOne() {
        // Covers the (n == 1) branch
        Assert.assertTrue(WeatherAndMathUtils.isPrime(1));
    }

    @Test
    public void testIsPrimeWithTwo_LoopNotEntered() {
        // n=2 → loop condition (i<n) false from start → returns true
        Assert.assertTrue(WeatherAndMathUtils.isPrime(2));
    }

    @Test
    public void testIsPrimeWithEvenComposite() {
        // n=4 → loop entered, isEven(n) true → returns false
        Assert.assertFalse(WeatherAndMathUtils.isPrime(4));
    }

    @Test
    public void testIsPrimeWithOddPrime_LoopCompletes() {
        // n=3 → loop entered, isEven(n) false, loop completes → returns true
        Assert.assertTrue(WeatherAndMathUtils.isPrime(3));
    }
}