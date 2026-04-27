package com.pao.laboratory09.exercise2;

import com.pao.laboratory09.exercise1.TipTranzactie;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.*;

public class Main {
    private static final String OUTPUT_FILE = "output/lab09_ex2.bin";
    private static final int RECORD_SIZE = 32;

    public static void main(String[] args) throws Exception {
        // TODO: Implementează conform Readme.md
        //
        // 1. Citește N din stdin, apoi cele N tranzacții (id suma data tip)
        // 2. Scrie toate înregistrările în OUTPUT_FILE cu DataOutputStream (format binar, RECORD_SIZE=32 bytes/înreg.)
        //    - bytes 0-3:   id (int, little-endian via ByteBuffer)
        //    - bytes 4-11:  suma (double, little-endian via ByteBuffer)
        //    - bytes 12-21: data (String, 10 chars ASCII, paddat cu spații la dreapta)
        //    - byte 22:     tip (0=CREDIT, 1=DEBIT)
        //    - byte 23:     status (0=PENDING, 1=PROCESSED, 2=REJECTED)
        //    - bytes 24-31: padding (zerouri)
        // 3. Procesează comenzile din stdin până la EOF cu RandomAccessFile:
        //    - READ idx       → seek(idx * RECORD_SIZE), citește și afișează înregistrarea
        //    - UPDATE idx ST  → seek(idx * RECORD_SIZE + 23), scrie noul status (0/1/2)
        //                       afișează "Updated [idx]: STATUS"
        //    - PRINT_ALL      → citește și afișează toate înregistrările
        //
        // Format linie output:
        //   [idx] id=<id> data=<data> tip=<CREDIT|DEBIT> suma=<suma:.2f> RON status=<STATUS>

//        System.out.println("TODO: implementează exercițiul 2");

        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextInt()) return;

        int n = scanner.nextInt();
        new File("output").mkdirs();

        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(OUTPUT_FILE))) {
            for (int i = 0; i < n; i++) {
                int id = scanner.nextInt();
                double suma = scanner.nextDouble();
                String data = scanner.next();
                TipTranzactie tip = TipTranzactie.valueOf(scanner.next());

                dos.write(serializeTransaction(id, suma, data, tip));
            }
        }

        try (RandomAccessFile raf = new RandomAccessFile(OUTPUT_FILE, "rw")) {
            while (scanner.hasNext()) {
                String comanda = scanner.next();
                if (comanda.equals("READ")) {
                    int idx = scanner.nextInt();
                    printRecord(raf, idx, n);
                } else if (comanda.equals("UPDATE")) {
                    int idx = scanner.nextInt();
                    String statusStr = scanner.next();
                    byte statusByte = getStatusByte(statusStr);

                    raf.seek((long) idx * RECORD_SIZE + 23);
                    raf.write(statusByte);
                    System.out.println("Updated [" + idx + "]: " + statusStr);
                } else if (comanda.equals("PRINT_ALL")) {
                    for (int i = 0; i < n; i++) {
                        printRecord(raf, i, n);
                    }
                }
            }
        }
    }

    private static byte[] serializeTransaction(int id, double suma, String data, TipTranzactie tip) {
        ByteBuffer buffer = ByteBuffer.allocate(RECORD_SIZE).order(ByteOrder.LITTLE_ENDIAN);

        buffer.putInt(id);
        buffer.putDouble(suma);

        byte[] dataBytes = data.getBytes();
        buffer.put(dataBytes);
        for (int i = dataBytes.length; i < 10; i++) buffer.put((byte) ' ');

        buffer.put((byte) (tip == TipTranzactie.CREDIT ? 0 : 1));
        buffer.put((byte) 0);

        return buffer.array();
    }

    private static void printRecord(RandomAccessFile raf, int idx, int totalRecords) throws IOException {
        if (idx < 0 || idx >= totalRecords) return;

        raf.seek((long) idx * RECORD_SIZE);
        byte[] bytes = new byte[RECORD_SIZE];
        raf.read(bytes);

        ByteBuffer buffer = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN);
        int id = buffer.getInt();
        double suma = buffer.getDouble();

        byte[] dataBytes = new byte[10];
        buffer.get(dataBytes);
        String data = new String(dataBytes).trim();

        int tipCode = buffer.get() & 0xFF;
        String tip = (tipCode == 0) ? "CREDIT" : "DEBIT";

        int statusCode = buffer.get() & 0xFF;
        String status = getStatusString(statusCode);

        System.out.printf("[%d] id=%d data=%s tip=%s suma=%.2f RON status=%s\n",
                idx, id, data, tip, suma, status);
    }

    private static byte getStatusByte(String status) {
        switch (status) {
            case "PROCESSED": return 1;
            case "REJECTED": return 2;
            default: return 0;
        }
    }

    private static String getStatusString(int code) {
        if (code == 1) return "PROCESSED";
        if (code == 2) return "REJECTED";
        return "PENDING";
    }
}
