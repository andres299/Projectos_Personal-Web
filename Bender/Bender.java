package Trabajos_Finales.Practica3_Final;

class Bender {
    // Constructor: ens passen el mapa en forma d'String
    char[][] mapaChars;

    public Bender(String mapa) {
        String[] lineas = mapa.split("\n");
        int nlineas = lineas.length;
        int columnas = lineas[0].length();
        mapaChars = new char[nlineas][columnas];
        for (int i = 0; i < lineas.length; i++) {
            for (int j = 0; j < lineas[0].length(); j++) {
                mapaChars[i][j] = lineas[i].charAt(j);
                System.out.print(mapaChars[i][j]);
            }
            System.out.println();
        }
    }
    // Navegar fins a l'objectiu («$»).
// El valor retornat pel mètode consisteix en una cadena de
// caràcters on cada lletra pot tenir
// els valors «S», «N», «W» o «E»,
// segons la posició del robot a cada moment.

    public String run() {
        int poXX = 0;
        int poXY = 0;
        int po$X = 0;
        int po$Y = 0;
        for (int i = 0; i < mapaChars.length; i++) {
            for (int j = 0; j < mapaChars[0].length; j++) {
                if (mapaChars[i][j] == 'X') {
                    poXX = j;
                    poXY = i;
                    System.out.println("poXX = " + poXX + " ,poXY = " + poXY);
                }
                if (mapaChars[i][j] == '$') {
                    po$X = j;
                    po$Y = i;
                    System.out.println("po$X = " + po$X + " ,po$Y = " + po$Y);
                }
            }
        }

        String res = "";
        String direccion = "S";
        int contador = 0;
        boolean invertido = false;
        while (!destinoFinal(poXX, poXY, po$X, po$Y)) {
            contador++;
            if (contador == 1000) return null;
            if (siguienteCasilla(poXX, poXY, direccion)) {
                if (direccion.equals("S")) {
                    poXY += 1;
                } else if (direccion.equals("N")) {
                    poXY -= 1;
                } else if (direccion.equals("E")) {
                    poXX += 1;
                } else if (direccion.equals("W")) {
                    poXX -= 1;
                }
                if (mapaChars[poXY][poXX] == 'T'){
                    int [] nearesT = encontrarTMasCercana(poXX,poXY);
                    if (nearesT != null){
                        poXX = nearesT[0];
                        poXY = nearesT[1];
                    }
                }
                res += direccion;
            } else {
                if (!invertido) {
                    if (siguienteCasilla(poXX, poXY, "S")) {
                        direccion = "S";
                    } else if (siguienteCasilla(poXX, poXY, "E")) {
                        direccion = "E";
                    } else if (siguienteCasilla(poXX, poXY, "N")) {
                        direccion = "N";
                    } else if (siguienteCasilla(poXX, poXY, "W")) {
                        direccion = "W";
                    }
                } else {
                    if (siguienteCasilla(poXX, poXY, "N")) {
                        direccion = "N";
                    } else if (siguienteCasilla(poXX, poXY, "W")) {
                        direccion = "W";
                    } else if (siguienteCasilla(poXX, poXY, "S")) {
                        direccion = "S";
                    } else if (siguienteCasilla(poXX, poXY, "E")) {
                        direccion = "E";
                    }
                }
            }
            if (mapaChars[poXY][poXX] == 'I') {
                invertido = true;
            }
        }
        return res;
    }

    private int[] encontrarTMasCercana(int poXX, int poXY) {
        int mindist = Integer.MAX_VALUE;
        int[] nearesT = null;

        for (int i = 0; i < mapaChars.length; i++) {
            for (int j = 0; j < mapaChars[0].length; j++) {
                if (mapaChars[i][j] == 'T' && (i != poXY || j != poXX)){
                    int dist = Math.abs(i - poXY) + Math.abs(j -poXX);
                    if (dist < mindist){
                        mindist = dist;
                        nearesT = new int[]{j,i};
                    }
                }
            }
        }
        return nearesT;
    }

    private boolean siguienteCasilla(int poXX, int poXY, String direccion) {
        if (direccion.equals("S")) {
            if (mapaChars[poXY + 1][poXX] == '#') return false;
        }
        if (direccion.equals("N")) {
            if (mapaChars[poXY - 1][poXX] == '#') return false;
        }
        if (direccion.equals("E")) {
            if (mapaChars[poXY][poXX + 1] == '#') return false;
        }
        if (direccion.equals("W")) {
            if (mapaChars[poXY][poXX - 1] == '#') return false;
        }
        return true;
    }

    private boolean destinoFinal(int poXX, int poXY, int po$X, int po$Y) {
        if (poXX == po$X && poXY == po$Y) return true;
        return false;
    }

}