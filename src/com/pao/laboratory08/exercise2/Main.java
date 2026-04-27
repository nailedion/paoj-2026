package com.pao.laboratory08.exercise2;

import com.pao.laboratory08.exercise1.Adresa;
import com.pao.laboratory08.exercise1.Student;

import java.io.*;
import java.util.*;

public class Main {
    private static final String FILE_PATH = "paoj-2026/src/com/pao/laboratory08/tests/studenti.txt";

    private static final String INPUT_FILE = "paoj-2026/src/com/pao/laboratory08/tests/studenti.txt";
    private static final String OUTPUT_FILE = "rezultate.txt";

    public static void main(String[] args) throws Exception {
        // 1. Citeste studentii din FILE_PATH cu BufferedReader
        // 2. Citeste pragul de varsta din stdin cu Scanner
        // 3. Filtreaza studentii cu varsta >= prag
        // 4. Scrie filtratii in "rezultate.txt" cu BufferedWriter
        // 5. Afiseaza sumarul la consola


        List<Student> studenti = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE));
        String line;
        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) {
                continue;
            }
            String[] parts = line.split(",");
            String nume = parts[0].trim();
            int varsta = Integer.parseInt(parts[1].trim());
            String oras = parts[2].trim();
            String strada = parts[3].trim();

            Adresa adresa = new Adresa(oras, strada);
            studenti.add(new Student(nume, varsta, adresa));
        }

        Scanner scanner = new Scanner(System.in);
        int pragVarsta = scanner.nextInt();

        List<Student> studentiFiltrati = new ArrayList<>();
        for (Student s : studenti) {
            if (s.getVarsta() >= pragVarsta) {
                studentiFiltrati.add(s);
            }
        }

        System.out.println("Filtru: varsta >= " + pragVarsta);
        System.out.println("Rezultate: " + studentiFiltrati.size() + " studenti\n");

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(OUTPUT_FILE))) {
            for (Student s : studentiFiltrati) {
                bw.write(s.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Eroare la scrierea in fisierul rezultat: " + e.getMessage());
        }
    }
}

