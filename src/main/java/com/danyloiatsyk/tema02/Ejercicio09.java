package com.danyloiatsyk.tema02;

public class Ejercicio09 {
    private static final int NUMERO_COHETES = 4;
    private static final int FILA_INICIAL = 2;
    private static final int SEPARACION_FILAS = 2;
    private static final int COLUMNA_PISTA = 20;
    private static final int LONGITUD_PISTA = 70;
    private static final int COLUMNA_FIN = COLUMNA_PISTA + LONGITUD_PISTA + 15;
    private final Cohete[] cohetes;
    private final Ganador ganador = new Ganador();

    public Ejercicio09(int distancia, int avanceMinimo, int avanceMaximo) {
        cohetes = new Cohete[NUMERO_COHETES];

        cohetes[0] = new Cohete("Falcon 9", AnsiColor.CYAN, 0, distancia, avanceMinimo, avanceMaximo, ganador);
        cohetes[1] = new Cohete("Soyuz 2", AnsiColor.MAGENTA, 1, distancia, avanceMinimo, avanceMaximo, ganador);
        cohetes[2] = new Cohete("Ariane 5", AnsiColor.YELLOW, 2, distancia, avanceMinimo, avanceMaximo, ganador);
        cohetes[3] = new Cohete("Miura", AnsiColor.RED, 3, distancia, avanceMinimo, avanceMaximo, ganador);
    }

    public void iniciarCarrera() throws InterruptedException {
        Ansi.clearScreen();
        Ansi.hideCursor();
        dibujarEscenario();

        for (Cohete cohete : cohetes) {
            cohete.start();
        }

        for (Cohete cohete : cohetes) {
            cohete.join();
        }

        mostrarResumen();
        Ansi.showCursor();
    }

    private void mostrarResumen() {
        int filaResumen = FILA_INICIAL + NUMERO_COHETES * SEPARACION_FILAS + 1;
        Cohete[] clasificacion = cohetes.clone();

        for (int indice = 0; indice < clasificacion.length - 1; indice++) {
            for (int siguiente = indice + 1; siguiente < clasificacion.length; siguiente++) {
                if (clasificacion[siguiente].getTiempo() < clasificacion[indice].getTiempo()) {
                    Cohete auxiliar = clasificacion[indice];
                    clasificacion[indice] = clasificacion[siguiente];
                    clasificacion[siguiente] = auxiliar;
                }
            }
        }

        Ansi.setCursorTo(filaResumen, 1);
        System.out.println("Resultado de la carrera:");
        System.out.println("Ganador: " + ganador.get().getNombre());
        for (int indice = 0; indice < clasificacion.length; indice++) {
            Cohete cohete = clasificacion[indice];
            System.out.printf("%d. %s (%.3f s)%n", indice + 1, cohete.getNombre(), cohete.getTiempo() / 1000.0);
        }
    }

    private void dibujarEscenario() {
        dibujarPlataformaSalida();
        dibujarPlataformaLlegada();

        for (int indice = 0; indice < cohetes.length; indice++) {
            int fila = FILA_INICIAL + indice * SEPARACION_FILAS;
            Cohete cohete = cohetes[indice];

            Ansi.printTo(cohete.getNombre(), fila, 1, true, cohete.getColor(), AnsiColor.NONE);
        }
    }

    private void dibujarPlataformaSalida() {
        dibujarRectangulo(1, 10, 10, 10, AnsiColor.BLUE);
        dibujarRectangulo(1, 15, 2, 4, AnsiColor.GRAY);
        dibujarRectangulo(9, 15, 2, 4, AnsiColor.GRAY);
        dibujarRectangulo(3, 13, 2, 2, AnsiColor.YELLOW);
        dibujarRectangulo(5, 15, 2, 2, AnsiColor.YELLOW);
        dibujarRectangulo(7, 13, 2, 2, AnsiColor.YELLOW);
    }

    private void dibujarPlataformaLlegada() {
        dibujarRectangulo(1, COLUMNA_FIN, 10, 10, AnsiColor.RED);
        dibujarRectangulo(1, COLUMNA_FIN + 3, 2, 4, AnsiColor.GRAY);
        dibujarRectangulo(9, COLUMNA_FIN + 3, 2, 4, AnsiColor.GRAY);
    }

    private static void dibujarRectangulo(int fila, int columna, int alto, int ancho, AnsiColor color) {
        for (int filaActual = fila; filaActual < fila + alto; filaActual++) {
            for (int columnaActual = columna; columnaActual < columna + ancho; columnaActual++) {
                Ansi.printTo(' ', filaActual, columnaActual, false, AnsiColor.NONE, color);
            }
        }
    }

    private static class Cohete extends Thread {
        private final String nombre;
        private final AnsiColor color;
        private final int posicion;
        private final int distancia;
        private final int avanceMinimo;
        private final int avanceMaximo;
        private final Ganador ganador;
        private int recorrido;
        private long tiempo;

        private Cohete(String nombre, AnsiColor color, int posicion, int distancia, int avanceMinimo,
                       int avanceMaximo, Ganador ganador) {
            this.nombre = nombre;
            this.color = color;
            this.posicion = posicion;
            this.distancia = distancia;
            this.avanceMinimo = avanceMinimo;
            this.avanceMaximo = avanceMaximo;
            this.ganador = ganador;
        }

        @Override
        public void run() {
            long inicio = System.currentTimeMillis();

            while (recorrido < distancia) {
                recorrido += Lib.random(avanceMinimo, avanceMaximo);
                if (recorrido > distancia) {
                    recorrido = distancia;
                }
                synchronized (Ansi.class) {
                    int fila = FILA_INICIAL + posicion * SEPARACION_FILAS;
                    int longitud = recorrido * LONGITUD_PISTA / distancia;
                    dibujarRectangulo(fila, COLUMNA_PISTA, 1, longitud, color);
                    if (recorrido == distancia) {
                        Ansi.printTo("Aterrizado!", fila, COLUMNA_PISTA + LONGITUD_PISTA + 2,
                                true, AnsiColor.GRAY, AnsiColor.NONE);
                    }
                }
                Lib.wait(100);
            }

            tiempo = System.currentTimeMillis() - inicio;
            ganador.asignar(this);
        }

        public String getNombre() {
            return nombre;
        }

        public AnsiColor getColor() {
            return color;
        }

        public long getTiempo() {
            return tiempo;
        }
    }

    private static class Ganador {
        private Cohete cohete;

        public synchronized void asignar(Cohete cohete) {
            if (this.cohete == null) {
                this.cohete = cohete;
            }
        }

        public synchronized Cohete get() {
            return cohete;
        }
    }
}
