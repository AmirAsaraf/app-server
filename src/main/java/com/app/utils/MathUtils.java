package com.app.utils;

import java.util.Random;


public class MathUtils {
	
	public static int getPercentage(long amount, long outOf)
	{		
        return Math.round ((float)(amount * 100) / outOf);
	}

    public static int getValue(long precentage, long outOf) {
        return Math.round (((float)precentage / 100) * outOf);
    }

    public static int getRandom(int min, int max) {
        if (min==max) {
            return min;
        }
        Random r = new Random();
        return r.nextInt(max-min) + min;
    }
}
