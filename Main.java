package mx.edu.utez;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static final char PARED = '█';
    private static final char CAMINO = ' ';
    private static final char JUGADOR = 'P';
    private static final char SALIDA = 'S';
    private static final char VISITADO = '.';

    private static final int FILAS = 15;
    private static final int COLUMNAS = 25;

    private char[][] laberinto;
    private int jugadorFila;
    private int jugadorColumna;
    private int salidaFila;
    private int salidaColumna;
    private int pasos;
    private boolean juegoTerminado;

    public Main() {
        laberinto = new char[FILAS][COLUMNAS];
        pasos = 0;
        juegoTerminado = false;
        inicializarLaberinto();
        colocarJugadorYSalida();
    }

    private void inicializarLaberinto() {
        Random rand = new Random();

        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                laberinto[i][j] = PARED;
            }
        }

        for (int i = 1; i < FILAS - 1; i += 2) {
            for (int j = 1; j < COLUMNAS - 1; j += 2) {
                laberinto[i][j] = CAMINO;

                if (j + 2 < COLUMNAS - 1 && rand.nextBoolean()) {
                    laberinto[i][j + 1] = CAMINO;
                }

                if (i + 2 < FILAS - 1 && rand.nextBoolean()) {
                    laberinto[i + 1][j] = CAMINO;
                }
            }
        }

        for (int i = 0; i < FILAS; i++) {
            laberinto[i][0] = PARED;
            laberinto[i][COLUMNAS - 1] = PARED;
        }
        for (int j = 0; j < COLUMNAS; j++) {
            laberinto[0][j] = PARED;
            laberinto[FILAS - 1][j] = PARED;
        }
    }

    private void colocarJugadorYSalida() {
        jugadorFila = 1;
        jugadorColumna = 1;
        laberinto[jugadorFila][jugadorColumna] = JUGADOR;

        salidaFila = FILAS - 2;
        salidaColumna = COLUMNAS - 2;
        laberinto[salidaFila][salidaColumna] = SALIDA;

        crearCaminoGarantizado();
    }

    private void crearCaminoGarantizado() {
        int filaActual = jugadorFila;
        int colActual = jugadorColumna;

        while (filaActual < salidaFila) {
            laberinto[filaActual][colActual] = CAMINO;
            filaActual++;
        }

        while (colActual < salidaColumna) {
            laberinto[filaActual][colActual] = CAMINO;
            colActual++;
        }

        laberinto[jugadorFila][jugadorColumna] = JUGADOR;
        laberinto[salidaFila][salidaColumna] = SALIDA;
    }

    public void mostrarLaberinto() {
        System.out.println("\n=== LABERINTO ===");
        System.out.println("P: Jugador | S: Salida | █: Pared | .: Visitado");
        System.out.println("Pasos: " + pasos + "\n");

        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                System.out.print(laberinto[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public boolean moverJugador(char direccion) {
        if (juegoTerminado) {
            return false;
        }

        int nuevaFila = jugadorFila;
        int nuevaColumna = jugadorColumna;

        switch (Character.toUpperCase(direccion)) {
            case 'W':
                nuevaFila--;
                break;
            case 'S':
                nuevaFila++;
                break;
            case 'A':
                nuevaColumna--;
                break;
            case 'D':
                nuevaColumna++;
                break;
            default:
                System.out.println("Direccion invalida. Usa W A S D");
                return false;
        }

        if (esMovimientoValido(nuevaFila, nuevaColumna)) {
            laberinto[jugadorFila][jugadorColumna] = VISITADO;

            jugadorFila = nuevaFila;
            jugadorColumna = nuevaColumna;

            if (laberinto[nuevaFila][nuevaColumna] == SALIDA) {
                laberinto[nuevaFila][nuevaColumna] = JUGADOR;
                juegoTerminado = true;
                System.out.println("¡FELICIDADES! Encontraste la salida en " + pasos + " pasos");
            } else {
                laberinto[nuevaFila][nuevaColumna] = JUGADOR;
            }

            pasos++;
            return true;
        } else {
            System.out.println("Movimiento invalido. Hay una pared ahi");
            return false;
        }
    }

    private boolean esMovimientoValido(int fila, int columna) {
        if (fila < 0 || fila >= FILAS || columna < 0 || columna >= COLUMNAS) {
            return false;
        }

        char celda = laberinto[fila][columna];
        return celda == CAMINO || celda == SALIDA || celda == VISITADO;
    }

    public void jugar() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== BIENVENIDO AL LABERINTO ===");
        System.out.println("Instrucciones:");
        System.out.println("Usa W A S D para moverte");
        System.out.println("Llega a la salida S evitando las paredes");
        System.out.println("Los puntos . muestran tu camino");

        while (!juegoTerminado) {
            mostrarLaberinto();

            System.out.print("Ingresa tu movimiento W A S D o Q para salir: ");
            String input = scanner.nextLine().toUpperCase();

            if (input.equals("Q")) {
                System.out.println("Abandonaste el laberinto");
                break;
            }

            if (input.length() > 0) {
                moverJugador(input.charAt(0));
            }
        }

        scanner.close();
    }

    public int getPasos() {
        return pasos;
    }

    public boolean isJuegoTerminado() {
        return juegoTerminado;
    }

    public int getJugadorFila() {
        return jugadorFila;
    }

    public int getJugadorColumna() {
        return jugadorColumna;
    }

    public static void main(String[] args) {
        Main juego = new Main();
        juego.jugar();
    }
}