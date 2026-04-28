package sit707_week5;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class WeatherControllerTest {


    private static WeatherController wController;
    private static double[] hourlyTemperatures;
    private static int nHours;

 
    @BeforeClass
    public static void setUpBeforeClass() {
        System.out.println("=== @BeforeClass: initialising WeatherController (once) ===");

        
        wController = WeatherController.getInstance();

       
        nHours = wController.getTotalHours();
        hourlyTemperatures = new double[nHours];
        for (int i = 0; i < nHours; i++) {
            hourlyTemperatures[i] = wController.getTemperatureForHour(i + 1);
        }
        System.out.println("=== Pre-fetched " + nHours + " hourly temperatures ===");
    }

  
    @AfterClass
    public static void tearDownAfterClass() {
        System.out.println("=== @AfterClass: closing WeatherController (once) ===");
        wController.close();
    }

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
    public void testTemperatureMin() {
        System.out.println("+++ testTemperatureMin +++");


        double minTemperature = 1000;


        for (int i = 0; i < nHours; i++) {
            if (minTemperature > hourlyTemperatures[i]) {
                minTemperature = hourlyTemperatures[i];
            }
        }


        Assert.assertTrue(wController.getTemperatureMinFromCache() == minTemperature);
    }

    @Test
    public void testTemperatureMax() {
        System.out.println("+++ testTemperatureMax +++");


        double maxTemperature = -1;

        for (int i = 0; i < nHours; i++) {
            if (maxTemperature < hourlyTemperatures[i]) {
                maxTemperature = hourlyTemperatures[i];
            }
        }


        Assert.assertTrue(wController.getTemperatureMaxFromCache() == maxTemperature);
    }

    @Test
    public void testTemperatureAverage() {
        System.out.println("+++ testTemperatureAverage +++");


        double sumTemp = 0;

        for (int i = 0; i < nHours; i++) {
            sumTemp += hourlyTemperatures[i];
        }
        double averageTemp = sumTemp / nHours;

        Assert.assertTrue(wController.getTemperatureAverageFromCache() == averageTemp);
    }

    @Test
    public void testTemperaturePersist() {
       
    }
}