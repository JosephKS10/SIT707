package sit707_week5;

import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
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

//    @Test
//    public void testTemperatureMin() {
//        System.out.println("+++ testTemperatureMin +++");
//
//
//        double minTemperature = 1000;
//
//
//        for (int i = 0; i < nHours; i++) {
//            if (minTemperature > hourlyTemperatures[i]) {
//                minTemperature = hourlyTemperatures[i];
//            }
//        }
//
//
//        Assert.assertTrue(wController.getTemperatureMinFromCache() == minTemperature);
//    }
//
//    @Test
//    public void testTemperatureMax() {
//        System.out.println("+++ testTemperatureMax +++");
//
//
//        double maxTemperature = -1;
//
//        for (int i = 0; i < nHours; i++) {
//            if (maxTemperature < hourlyTemperatures[i]) {
//                maxTemperature = hourlyTemperatures[i];
//            }
//        }
//
//
//        Assert.assertTrue(wController.getTemperatureMaxFromCache() == maxTemperature);
//    }
//
//    @Test
//    public void testTemperatureAverage() {
//        System.out.println("+++ testTemperatureAverage +++");
//
//
//        double sumTemp = 0;
//
//        for (int i = 0; i < nHours; i++) {
//            sumTemp += hourlyTemperatures[i];
//        }
//        double averageTemp = sumTemp / nHours;
//
//        Assert.assertTrue(wController.getTemperatureAverageFromCache() == averageTemp);
//    }

    @Test
    public void testTemperaturePersist() {
    	 System.out.println("+++ testTemperaturePersist +++");

         
         Instant frozenInstant = Instant.parse("2026-04-28T10:30:45.00Z");
         Clock fixedClock = Clock.fixed(frozenInstant, ZoneId.systemDefault());
         wController.setClock(fixedClock);

         SimpleDateFormat sdf = new SimpleDateFormat("H:m:s");
         String expectedTime = sdf.format(Date.from(fixedClock.instant()));

         // === ACT ===
         String persistTime = wController.persistTemperature(10, 19.5);
         String now = sdf.format(Date.from(fixedClock.instant()));
         System.out.println("Persist time: " + persistTime + ", now: " + now);

         // === ASSERT ===
         // With a frozen clock, persistTime equals now regardless of the
         // simulated delay inside persistTemperature().
         Assert.assertEquals(expectedTime, persistTime);
         Assert.assertTrue(persistTime.equals(now));

         // === AFTER ===
         // Restore the real clock so we don't leak fixture state to other tests.
         wController.setClock(Clock.systemDefaultZone());
       
    }
}