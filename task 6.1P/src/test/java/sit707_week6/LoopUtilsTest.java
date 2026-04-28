package sit707_week6;

import org.junit.Assert;
import org.junit.Test;

public class LoopUtilsTest {



    @Test
    public void testSumUpToZero_LoopNotEntered() {
        // Path 1: loop condition false at entry
        Assert.assertEquals(0, LoopUtils.sumUpTo(0));
    }

    @Test
    public void testSumUpToOne_LoopExecutesOnce() {
        // Boundary: loop body executes exactly once
        Assert.assertEquals(1, LoopUtils.sumUpTo(1));
    }

    @Test
    public void testSumUpToFive_LoopExecutesMultipleTimes() {
        // 1+2+3+4+5 = 15
        Assert.assertEquals(15, LoopUtils.sumUpTo(5));
    }

    @Test
    public void testSumUpToNegative_LoopNotEntered() {
        // Defensive: negative n behaves the same as 0
        Assert.assertEquals(0, LoopUtils.sumUpTo(-3));
    }


    @Test
    public void testCountEvensEmptyArray_LoopNotEntered() {
        // Path 1: loop condition false at entry
        Assert.assertEquals(0, LoopUtils.countEvens(new int[]{}));
    }

    @Test
    public void testCountEvensAllOdd_InnerIfAlwaysFalse() {
        // Path 2: forces only the if-false branch
        Assert.assertEquals(0, LoopUtils.countEvens(new int[]{1, 3, 5, 7}));
    }

    @Test
    public void testCountEvensAllEven_InnerIfAlwaysTrue() {
        // Path 3: forces only the if-true branch
        Assert.assertEquals(4, LoopUtils.countEvens(new int[]{2, 4, 6, 8}));
    }

    @Test
    public void testCountEvensMixed_BothBranchesOfInnerIf() {
        // Path 4: hits both if-true (2,4,6) and if-false (1,3,5)
        Assert.assertEquals(3, LoopUtils.countEvens(new int[]{1, 2, 3, 4, 5, 6}));
    }
}