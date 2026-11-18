package com.danyloiatsyk.tema02;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Ejercicio09 carrera = new Ejercicio09(100, 1, 10);
        carrera.iniciarCarrera();
    }
}
//./gradlew classes
//java -cp build/classes/java/main com.danyloiatsyk.tema02.Main
