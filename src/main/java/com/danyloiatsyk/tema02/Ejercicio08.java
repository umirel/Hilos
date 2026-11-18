package com.danyloiatsyk.tema02;

public class Ejercicio08 {
    public static int contarPrimos(int[] array) throws InterruptedException {
        int numeroHilos = Math.min(Runtime.getRuntime().availableProcessors(), array.length);
        if (numeroHilos == 0) {
            return 0;
        }
        Contador contador = new Contador();
        Thread[] hilos = new Thread[numeroHilos];
        int elementosPorHilo = array.length / numeroHilos;
        int resto = array.length % numeroHilos;
        int inicio = 0;

        for (int indice = 0; indice < numeroHilos; indice++) {
            int fin = inicio + elementosPorHilo + (indice < resto ? 1 : 0);
            hilos[indice] = new Thread(new ContadorPrimos(array, inicio, fin, contador));
            hilos[indice].start();
            inicio = fin;
        }

        for (Thread hilo : hilos) {
            hilo.join();
        }

        return contador.valor();
    }

    private static class Contador {
        private int cantidad;

        public synchronized void sumar(int cantidad) {
            this.cantidad += cantidad;
        }

        public synchronized int valor() {
            return cantidad;
        }
    }

    private static class ContadorPrimos implements Runnable {
        private final int[] array;
        private final int inicio;
        private final int fin;
        private final Contador contador;

        private ContadorPrimos(int[] array, int inicio, int fin, Contador contador) {
            this.array = array;
            this.inicio = inicio;
            this.fin = fin;
            this.contador = contador;
        }

        @Override
        public void run() {
            int cantidad = 0;

            for (int indice = inicio; indice < fin; indice++) {
                if (esPrimo(array[indice])) {
                    cantidad++;
                }
            }

            contador.sumar(cantidad);
        }
    }

    private static boolean esPrimo(int numero) {
        if (numero < 2) {
            return false;
        }

        for (int divisor = 2; divisor * divisor <= numero; divisor++) {
            if (numero % divisor == 0) {
                return false;
            }
        }

        return true;
    }
}
