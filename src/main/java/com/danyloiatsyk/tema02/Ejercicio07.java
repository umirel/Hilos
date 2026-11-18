package com.danyloiatsyk.tema02;

public class Ejercicio07 {
    public static boolean contiene(int[] array, int numeroBuscado) throws InterruptedException {
        int numeroHilos = Math.min(Runtime.getRuntime().availableProcessors(), array.length);
        if (numeroHilos == 0) {
            return false;
        }
        Resultado resultado = new Resultado();
        Thread[] hilos = new Thread[numeroHilos];
        int elementosPorHilo = array.length / numeroHilos;
        int resto = array.length % numeroHilos;
        int inicio = 0;

        for (int indice = 0; indice < numeroHilos; indice++) {
            int fin = inicio + elementosPorHilo + (indice < resto ? 1 : 0);
            hilos[indice] = new Thread(new Buscador(array, numeroBuscado, inicio, fin, resultado));
            hilos[indice].start();
            inicio = fin;
        }

        for (Thread hilo : hilos) {
            hilo.join();
        }

        return resultado.encontrado();
    }

    private static class Resultado {
        private boolean encontrado;

        public synchronized void encontrar() {
            encontrado = true;
        }

        public synchronized boolean encontrado() {
            return encontrado;
        }
    }

    private static class Buscador implements Runnable {
        private final int[] array;
        private final int numeroBuscado;
        private final int inicio;
        private final int fin;
        private final Resultado resultado;

        private Buscador(int[] array, int numeroBuscado, int inicio, int fin, Resultado resultado) {
            this.array = array;
            this.numeroBuscado = numeroBuscado;
            this.inicio = inicio;
            this.fin = fin;
            this.resultado = resultado;
        }

        @Override
        public void run() {
            for (int indice = inicio; indice < fin; indice++) {
                if (array[indice] == numeroBuscado) {
                    resultado.encontrar();
                    return;
                }
            }
        }
    }
}
