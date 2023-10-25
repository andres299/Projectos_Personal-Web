package Trabajos_Finales.Practica5_Final.tests_i_esquelets;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class RLE {
    public static void compress(InputStream is, OutputStream os) throws IOException {
        int anterior = is.read();
        int count = 1;
        while (anterior != -1){
            int actual = is.read();
            if (anterior != actual && count == 1){
                os.write(anterior);
            }else if (anterior == actual){
                count++;
                if (count > 257){
                    count = 1;
                    os.write(anterior);
                    os.write(anterior);
                    os.write(count-2);
                }
            }else {
                os.write(anterior);
                os.write(anterior);
                os.write(count-2);
                count = 1;
            }
            anterior = actual;
        }
        os.flush();
    }

    public static void decompress(InputStream is, OutputStream os) throws IOException {
        int anterior = is.read();
        while (anterior != -1) {
            int actual = is.read();
            if (anterior != actual) {
                os.write(anterior);
            } else {
                os.write(anterior);
                os.write(anterior);
                actual = is.read();
                for (int i = 0; i < actual; i++) {
                    os.write(anterior);
                }
                actual = is.read();
            }
            anterior = actual;
        }
        os.flush();
    }
}
