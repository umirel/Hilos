package com.danyloiatsyk.tema02;

public class Ejercicio02 {
    public static void main(String[] args) throws InterruptedException {
        Thread hilo = new Thread(() -> {
            for (int numero = 0; numero < 100; numero++) {
                System.out.println(numero);
            }
        });

        hilo.start();
        hilo.join();
        System.out.println("fin de programa");
    }
}
