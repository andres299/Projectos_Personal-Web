package Trabajos_Finales.Practica2_Final;

import java.math.BigInteger;

public class BigNumber {
    String valor; // valor es un atribut que necesita el Bignamber per enmagatzenar els valors

    // Constructor 1
    public BigNumber(String s) {
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c != '0') {
                s = s.substring(i);
                break;
            }
        }
        this.valor = s;
    }

    // Constructor 2
    public BigNumber(BigNumber b) {
    }

    // Suma
    BigNumber add(BigNumber other) {
        String a1 = this.valor;
        String a2 = other.valor;
        String res = "";
        int llevo = 0;
        int a = Math.abs(a1.length() - a2.length());
        if (a1.length() < a2.length()) {
            for (int i = 0; i < a; i++) {
                a1 = "0" + a1;
            }
        } else {
            for (int i = 0; i < a; i++) {
                a2 = "0" + a2;
            }
        }

        for (int i = a1.length() - 1; i >= 0; i--) {
            int num1 = Integer.parseInt(String.valueOf(a1.charAt(i)));
            int num2 = Integer.parseInt(String.valueOf(a2.charAt(i)));
            int suma = num1 + num2 + llevo;
            if (suma > 9) {
                res = suma % 10 + res;
                llevo = suma / 10;
            } else {
                res = suma + res;
                llevo = 0;
            }
            if (i == 0 & llevo != 0) {
                res = llevo + res;
            }
        }
        return new BigNumber(res);
    }

    // Resta
    BigNumber sub(BigNumber other) {
        String a1 = this.valor;
        String a2 = other.valor;
        String res = "";
        int llevo = 0;
        int a = Math.abs(a1.length() - a2.length());
        if (a1.length() < a2.length()) {
            for (int i = 0; i < a; i++) {
                a1 = "0" + a1;
            }
        } else {
            for (int i = 0; i < a; i++) {
                a2 = "0" + a2;
            }
        }

        for (int i = a1.length() - 1; i >= 0; i--) {
            int num1 = Integer.parseInt(String.valueOf(a1.charAt(i)));
            int num2 = Integer.parseInt(String.valueOf(a2.charAt(i))) + llevo;
            int resto = 0;
            if (num1 < num2) {
                resto = (num1 + 10) - num2;
                llevo = 1;
            } else {
                resto = num1 - num2;
                llevo = 0;
            }
            res = resto + res;
        }
        return new BigNumber(res);
    }

    // Multiplica
    BigNumber mult(BigNumber other) {
        String a1 = this.valor;
        String a2 = other.valor;
        String res = "";
        int llevo = 0;
        int nceros = 0;
        BigNumber resFinal = new BigNumber("0");

        for (int i = a2.length() - 1; i >= 0; i--) {
            res = "";
            for (int j = a1.length() - 1; j >= 0; j--) {
                int num1 = Integer.parseInt(String.valueOf(a1.charAt(j)));
                int num2 = Integer.parseInt(String.valueOf(a2.charAt(i)));
                int mult = num1 * num2 + llevo;

                if (mult > 9) {
                    res = mult % 10 + res;
                    llevo = mult / 10;
                } else {
                    res = mult + res;
                    llevo = 0;
                }

                if (j == 0 && llevo != 0) {
                    res = llevo + res;
                    llevo = 0;
                }
            }

            for (int j = 0; j < nceros; j++) {
                res = res + "0";
            }
            nceros++;
            resFinal = new BigNumber(res).add(resFinal);
        }
        return resFinal;
    }

    // Divideix
    BigNumber div(BigNumber other) {
        StringBuilder coef = new StringBuilder();
        String selecDeNum = "";
        String mult = "";
        //Miramos cual de los dos numeros es más grande
        int majorMenor = this.compareTo(other);
        //Si this es menor devolvemos 0
        if (majorMenor == -1) {
            return new BigNumber("0");
            //Si son iguales devolvemos 1
        } else if (majorMenor == 0) {
            return new BigNumber("1");
        } else {
            for (int i = 0; i < this.valor.length(); i++) {
                BigNumber c = new BigNumber(String.valueOf(this.valor.charAt(i)));
                selecDeNum += c;
                if (new BigNumber(selecDeNum).compareTo(other) >= 0) {
                    for (int j = 1; j < 11; j++) {
                        mult = String.valueOf(other.mult(new BigNumber(String.valueOf(j))));
                        if (new BigNumber(mult).compareTo(new BigNumber(selecDeNum)) > 0) {
                            coef.append(j - 1);
                            selecDeNum = String.valueOf(new BigNumber(selecDeNum).sub((other.mult(
                                    new BigNumber(String.valueOf(j - 1))))));
                            break;
                        }
                    }
                } else {
                    if (!coef.toString().equals("")) {
                        coef.append("0");
                    }
                }
            }
        }
        return new BigNumber(String.valueOf(coef));
    }

    // Potència
    BigNumber power(int n) {
        BigNumber numPotencia = new BigNumber(this.valor);
        for (int i = 1; i < n; i++) {
            numPotencia = numPotencia.mult(this);
        }
        return numPotencia;
    }

    // Arrel quadrada
    BigNumber sqrt() {
        BigNumber temp;
        //Lo dividimos entre dos ya que no será mayor que la mitad
        BigNumber res = (new BigNumber(this.valor).div(new BigNumber("2")));
        //Hacemos que entre en el bucle
        do {
            //Guardamos el resultado en la variable temporal
            temp = res;
            //Hacemos la división del número con su mitad
            BigNumber a = (new BigNumber(this.valor).div((temp)));
            //Luego sumamos el temporal
            BigNumber b = (temp.add(a));
            res = ((b).div(new BigNumber("2")));
        } while (!String.valueOf(temp.sub(res)).equals("0"));
        return (res);
    }

    // Factorial
    BigNumber factorial() {
        BigNumber resultado = new BigNumber(this.valor);
        BigNumber resta = new BigNumber("1");
        BigNumber factorial = new BigNumber(this.valor).sub(resta);
        while (!factorial.valor.equals("1")){
            resultado = resultado.mult(factorial);
            factorial = factorial.sub(resta);
        }
        return resultado;
    }


    // MCD. Torna el Màxim comú divisor
    BigNumber mcd(BigNumber other) {
        String b1 = this.valor;
        String b2 = other.valor;
        // Mientras que b2 no este vació entramos en el bucle
        while (!String.valueOf(b2).equals("")) {
            //Esta variable temporal es para no perder b2
            String temporal = b2;
            //Calculamos el resto mediante la multiplicación, division i resta
            b2 = String.valueOf(new BigNumber(b1).sub(new BigNumber(b1).div(new BigNumber(b2)).mult(new BigNumber(b2))));
            b1 = temporal;
            String c = String.valueOf(b2.charAt(0));
            if (c.equals("0")) {
                b2 = "";
            }
        }
        return (new BigNumber(b1));
    }

    // Compara dos BigNumber. Torna 0 si són iguals, -1
// si el primer és menor i torna 1 si el segon és menor
    public int compareTo(BigNumber other) {
        String c1 = this.valor;
        String c2 = other.valor;
        if (c1.length() > c2.length()) {
            return 1;
        } else if (c1.length() < c2.length()) {
            return -1;
        } else {
            for (int i = 0; i < c1.length(); i++) {
                int num1 = Integer.parseInt(String.valueOf(c1.charAt(i)));
                int num2 = Integer.parseInt(String.valueOf(c2.charAt(i)));
                if (num1 > num2) {
                    return 1;
                } else if (num1 < num2) {
                    return -1;
                }
            }
        }
        return 0;
    }
    // Torna un String representant el número

    public String toString() {
        return this.valor;
    }

    // Mira si dos objectes BigNumber són iguals
    public boolean equals(Object other) {
        BigNumber b = (BigNumber) other;
        //Si el valor de b1 y b2 es igual entonces retorna true, si no es igual responde false.
        if (b.valor.equals(this.valor))
            return true;
        return false;
    }
}
