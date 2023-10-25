package Trabajos_Finales.Practica5_Final.tests_i_esquelets;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LZW {
    public static void compress(InputStream is, OutputStream os) throws IOException {
        List<TableEntry> table = new ArrayList<>();
        int index = 0;
        int numero;

        while ((numero = is.read()) != -1) {
            char actual = (char) numero;
            int ind = search(actual, table, index);

            if (ind == 0) {
                table.add(new TableEntry(index, (byte) numero));
                index = 0;
            } else {
                index = ind;
            }
        }

        if (index != 0) {
            table.add(new TableEntry(table.get(index-1).index, table.get(index-1).num));
        }

        DataOutputStream dos = new DataOutputStream(os);
        for (TableEntry entry : table){
            os.write(entry.index);
            os.write(entry.num);
        }
        dos.close();
    }

    private static int search(char actual, List<TableEntry> table, int index) {
        int contador = 1;
        for (TableEntry t : table) {
            if (t.index == index && t.num == actual) {
                return contador;
            }
            contador++;
        }
        return 0;
    }

    public static void decompress(InputStream is, OutputStream os) throws IOException {
        DataInputStream dis = new DataInputStream(is);
        List<TableEntry> table = new ArrayList<>();

        while (dis.available() > 0){
            int index = dis.readByte();
            int num = dis.readByte();

            if (index == 0){
                os.write(num);
                table.add(new TableEntry(index,(byte) num));
            }else {
                TableEntry entry = table.get(index-1);
                os.write(table.get(index-1).num);
                os.write(num);
                table.add(new TableEntry(index, entry.num));
            }
        }
        //Se cierra el OutputStream.
        os.close();
    }
}

class TableEntry {
    int index;
    byte num;

    public TableEntry(int index, byte num) {
        this.index = index;
        this.num = num;
    }
}