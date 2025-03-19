package com.dokko.win4jui.api;

public class Numbers4JUI {
    public static int nearestPerfectSquare(int num, int mode) {
        if (num < 0 || mode < 0 || mode > 2) {
            throw new IllegalArgumentException("Invalid input: number must be non-negative, and mode must be 0 (nearest), 1 (nearest below), or 2 (nearest above).");
        }

        int lower = (int) Math.floor(Math.sqrt(num));
        int upper = lower + 1;

        int lowerSquare = lower * lower;
        int upperSquare = upper * upper;

        if (mode == 0) { // Nearest perfect square
            return (num - lowerSquare <= upperSquare - num) ? lowerSquare : upperSquare;
        } else if (mode == 1) { // Nearest perfect square that does not surpass the number
            return lowerSquare;
        } else { // mode == 2, Nearest perfect square that surpasses the number
            return upperSquare;
        }
    }
}
