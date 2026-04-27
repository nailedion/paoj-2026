package com.pao.laboratory08.exercise1;

import java.io.*;
import java.util.*;

public class Main {
    // Calea către fișierul cu date — relativă la rădăcina proiectului
    private static final String FILE_PATH = "paoj-2026/src/com/pao/laboratory08/tests/studenti.txt";

    public static void main(String[] args) throws Exception {
        // TODO: Implementează conform Readme.md
        //
        // 1. Citește studenții din FILE_PATH cu BufferedReader
        // 2. Citește comanda din stdin: PRINT, SHALLOW <nume> sau DEEP <nume>
        // 3. Execută comanda:
        //    - PRINT → afișează toți studenții
        //    - SHALLOW <nume> → shallow clone + modifică orașul clonei la "MODIFICAT" + afișează
        //    - DEEP <nume> → deep clone + modifică orașul clonei la "MODIFICAT" + afișează

        List<Student> studenti = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
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
        } catch (FileNotFoundException e) {
            System.out.println("Fisierul nu a putut fi gasit");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextLine()) {
            return;
        }

        String comanda = scanner.nextLine().trim();

        if (comanda.equals("PRINT")) {
            for (Student s : studenti) {
                System.out.println(s);
            }
        } else if (comanda.startsWith("SHALLOW")) {
            String numeCautat = comanda.split(" ", 2)[1].trim();
            Student original = gasesteStudent(studenti, numeCautat);

            if (original != null) {
                Student clona = original.shallowClone();
                clona.getAdresa().setOras("MODIFICAT");

                System.out.println("Original: " + original);
                System.out.println("Clona: " + clona);
            }
        } else if (comanda.startsWith("DEEP ")) {
            String numeCautat = comanda.split(" ", 2)[1].trim();
            Student original = gasesteStudent(studenti, numeCautat);

            if (original != null) {
                Student clona = original.deepClone();
                clona.getAdresa().setOras("MODIFICAT");

                System.out.println("Original: " + original);
                System.out.println("Clona: " + clona);
            }
        }
    }

    private static Student gasesteStudent(List<Student> studenti, String nume) {
        for (Student s : studenti) {
            if (s.getNume().equals(nume)) {
                return s;
            }
        }
        return null;
    }
}

