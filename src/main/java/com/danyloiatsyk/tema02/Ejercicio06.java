package com.danyloiatsyk.tema02;

public class Ejercicio06 {
    public static void main(String[] args) {
        int numeroHilos = Runtime.getRuntime().availableProcessors();

        for (int identificador = 0; identificador < numeroHilos; identificador++) {
            Thread hilo = new Thread(new HiloNumeros(identificador));
            hilo.start();
        }
    }

    private static class HiloNumeros implements Runnable {
        private final int identificador;

        private HiloNumeros(int identificador) {
            this.identificador = identificador;
        }

        @Override
        public void run() {
            for (int numero = 0; numero < 10000; numero++) {
                System.out.println("Hilo: " + identificador + " (" + numero + ")");
            }
        }
    }
}
