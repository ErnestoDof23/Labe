public class Bot {
    Laberinto laberinto;
    public Bot(Laberinto laberinto) {
        this.laberinto = laberinto;
    }

    static class Resultado {
        int pasosMinimos = Integer.MAX_VALUE;
    }

    public void recorrerLaberinto() {
        boolean[][] visitado = new boolean[laberinto.getFilas()][laberinto.getColumnas()];
        Resultado resultado = new Resultado();

        boolean[][] mejorCamino = new boolean[laberinto.getFilas()][laberinto.getColumnas()];

        buscarCamino(laberinto.getJugadorFila(), laberinto.getJugadorColumna(), 0, visitado, resultado, mejorCamino);

        if (resultado.pasosMinimos == Integer.MAX_VALUE) {
            System.out.println("No existe camino hacia la salida.");
        } else {
            System.out.println("El camino más corto hasta la salida tiene " + resultado.pasosMinimos + " pasos.");

            char[][] lab = laberinto.getLaberinto();
            for (int i = 0; i < laberinto.getFilas(); i++) {
                for (int j = 0; j < laberinto.getColumnas(); j++) {
                    if (mejorCamino[i][j] && lab[i][j] == Laberinto.getCAMINO()) {
                        lab[i][j] = laberinto.getVISITADO();
                    }
                }
            }
            laberinto.mostrarLaberinto();
        }
    }

    private void buscarCamino(int fila, int col, int pasos, boolean[][] visitado, Resultado resultado, boolean[][] mejorCamino) {
        char[][] lab = laberinto.getLaberinto();

        if (fila < 0 || fila >= laberinto.getFilas() || col < 0 || col >= laberinto.getColumnas()) return;
        if (lab[fila][col] == Laberinto.getPARED() || visitado[fila][col]) return;

        if (fila == laberinto.getSalidaFila() && col == laberinto.getSalidaColumna()) {
            if (pasos < resultado.pasosMinimos) {
                resultado.pasosMinimos = pasos;

                for (int i = 0; i < laberinto.getFilas(); i++) {
                    for (int j = 0; j < laberinto.getColumnas(); j++) {
                        mejorCamino[i][j] = visitado[i][j];
                    }
                }
            }
            return;
        }

        visitado[fila][col] = true;

        buscarCamino(fila - 1, col, pasos + 1, visitado, resultado, mejorCamino);
        buscarCamino(fila + 1, col, pasos + 1, visitado, resultado, mejorCamino);
        buscarCamino(fila, col - 1, pasos + 1, visitado, resultado, mejorCamino);
        buscarCamino(fila, col + 1, pasos + 1, visitado, resultado, mejorCamino);

        visitado[fila][col] = false;
    }


}
