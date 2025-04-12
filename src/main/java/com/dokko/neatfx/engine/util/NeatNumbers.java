package com.dokko.neatfx.engine.util;

/**
 * Utility class that operates with numbers
 */
public class NeatNumbers {
    /**
     * {@link NeatNumbers#nearestPerfectSquare(int, int)} mode that returns the nearest number: equal, above or below
     */
    public static final int NPS_MODE_NEAREST = 0;
    /**
     * {@link NeatNumbers#nearestPerfectSquare(int, int)} mode that returns the nearest number: equal or below
     */
    public static final int NPS_MODE_BELOW = 1;
    /**
     * {@link NeatNumbers#nearestPerfectSquare(int, int)} mode that returns the nearest number: equal or above
     */
    public static final int NPS_MODE_ABOVE = 2;
    /**
     * Returns the number N that, when squared, is closest to num
     * @param num the number to compare
     * @param mode the mode of the operation. Use NPS_MODE_NEAREST for the nearest, NPS_MODE_BELOW for the nearest below and NPS_MODE_ABOVE for the nearest above
     * @return the closest number, <b>NOT</b> squared
     * @see NeatNumbers#NPS_MODE_NEAREST
     * @see NeatNumbers#NPS_MODE_BELOW
     * @see NeatNumbers#NPS_MODE_ABOVE
     */
    public static int nearestPerfectSquare(int num, int mode) {
        if (num < 0 || (mode != NPS_MODE_ABOVE && mode != NPS_MODE_BELOW && mode != NPS_MODE_NEAREST)) {
            throw new IllegalArgumentException("Invalid input: number must be non-negative, and mode must be equal to" +
                    "NPS_MODE_NEAREST, NPS_MODE_ABOVE or NPS_MODE_BELOW.");
        }

        double sqrt = Math.sqrt(num);

        switch (mode) {
            case NPS_MODE_NEAREST: // Nearest perfect square
                int floor = (int) Math.floor(sqrt);
                int ceil = (int) Math.ceil(sqrt);

                int floorSq = floor * floor;
                int ceilSq = ceil * ceil;

                return (num - floorSq <= ceilSq - num) ? floor : ceil;

            case NPS_MODE_BELOW: // Nearest perfect square below
                return (int) Math.floor(sqrt);

            case NPS_MODE_ABOVE: // Nearest perfect square above
                return (int) Math.ceil(sqrt);

            default:
                throw new IllegalStateException("Unexpected mode: " + mode); // Should never reach here
        }
    }


}
