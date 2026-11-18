package com.danyloiatsyk.tema02;

import java.util.Random;

public class Lib {
    private final static Random random = new Random();

    /**
     * Determina si un número es primo
     * @param num Número que queremos determinar si es primo.
     * @return true si es primo, false en caso contrario
     */
    public static boolean esPrimo(long num) {
        /** Si el número es mayor que 2 y divisible entre 2, no es primo */
        /**
         * Es redundante ya que a esta función sólo le vamos a pasar números impares pero se deja
         * por si quermos hacer uso de esta función en otros proyectos
         */
        if(num > 2 && num % 2 == 0) {
            return false;
        }
        /**
         * Si llegamos hasta la raíz cuadrada del número (redondeada) podemos asegurar que es primo,
         * ya que a partir de la raíz cuadrada del número son los mismos factores pero invertidos.
         * Ej: 100 -> 1x100, 2x50, 4x25, 5x20, 10x10, 20x5, 25x4, 50x2 y  100x1
         * Podemos observar que a partir de 10x10, que es la raíz cuadrada de 100, los factores se
         * repiten pero invertidos.
         */
        long top = (long) Math.sqrt(num) + 1;
        for(long i = 3; i < top; i += 2) {
            if(num % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static void setRandomSeed(long seed) {
        random.setSeed(seed);
    }

    public static int random(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    public static float random(float min, float max) {
        return random.nextFloat() * (max - min) + min;
    }

    public static double random(double min, double max) {
        return random.nextDouble() * (max - min) + min;
    }

    public static void wait(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException ignored) {
        }
    }
}
