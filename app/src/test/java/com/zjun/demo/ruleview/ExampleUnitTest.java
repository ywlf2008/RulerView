package com.zjun.demo.ruleview;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testIt() {
        float[] nums = {
                6,
                3.005821f,
                0.022f,
                0.0188f,
                0.56f,
                1f,
                0.3f,
                0.9f,
                1.5f,
                1.7f,
                2.0f,
                5f,
                0.0001f,
        };

        for (float num : nums) {
            println("%f =%d", num, getScaleIndex(num));
        }
    }

    private float[] mPerCountScaleThresholds = {
            6f,     3.6f,   1.8f,   1.5f, // 10s/unit: 最大值, 1min, 2min, 4min
            0.8f,     0.4f,   // 1min/unit: 5min, 10min
            0.25f,  0.125f, // 5min/unit: 20min, 30min
            0.07f,  0.04f,  0.03f,  0.025f, 0.02f,  0.015f // 15min/unit: 1h, 2h, 3h, 4h, 5h, 6h
    };

    private int getScaleIndex(float scale) {
        final int size = mPerCountScaleThresholds.length;
        int min = 0;
        int max = size - 1;
        int mid = (min + max) >> 1;
//        println("min=%d, max=%d, mid=%d", min, max, mid);
        while (!(scale >= mPerCountScaleThresholds[mid] && scale < mPerCountScaleThresholds[mid - 1])) {
            if (scale >= mPerCountScaleThresholds[mid - 1]) {
                // 因为往大取，所以不修改为mid -1
                max = mid;
            } else {
                min = mid + 1;
            }
//            println("min=%d, max=%d, mid=%d", min, max, mid);
            mid = (min + max) >> 1;
            if (min == max) {
                break;
            }
            if (mid == 0) {
                break;
            }
        }
        return mid;
    }

    private void println(String format, Object... args) {
        System.out.println(String.format(format, args));
    }
}