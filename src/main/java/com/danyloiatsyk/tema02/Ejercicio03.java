package com.danyloiatsyk.tema02;

public class Ejercicio03 {
    public static void main(String[] args) {
        Thread hilo = new Thread(() -> {
            for (int numero = 0; numero < 100; numero++) {
                System.out.println(numero);
            }
        });

        hilo.setDaemon(true);
        hilo.start();
        System.out.println("fin de programa");
    }
}
