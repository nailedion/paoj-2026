package com.pao.laboratory05.audit;

import java.util.Scanner;

/**
 * Exercise 4 (Bonus) — Audit Log
 *
 * Cerințele complete se află în:
 *   src/com/pao/laboratory05/Readme.md  →  secțiunea "Exercise 4 (Bonus) — Audit"
 *
 * Extinde soluția de la Exercise 3 cu un sistem de audit bazat pe record.
 * Creează fișierele de la zero în acest pachet, apoi rulează Main.java
 * pentru a verifica output-ul așteptat din Readme.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Cerințele se află în Readme.md — secțiunea Exercise 4 (Bonus).");

        AngajatService service = AngajatService.getInstance();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Adauga ang" +
                    "\n2. Afiseaza toti angajatii" +
                    "\n3. Cauta după dep" +
                    "\n4. Afiseaza audit log" +
                    "\n0. Iesire");
            System.out.print("Optiune: ");

            if (!scanner.hasNextInt()) break;
            int optiune = scanner.nextInt();
            scanner.nextLine();

            switch (optiune) {
                case 1 -> {
                    System.out.print("Nume: "); String nume = scanner.nextLine();
                    System.out.print("Departament: "); String dept = scanner.nextLine();
                    System.out.print("Locatie: "); String loc = scanner.nextLine();
                    System.out.print("Salariu: "); double sal = scanner.nextDouble();
                    service.addAngajat(new Angajat(nume, new Departament(dept, loc), sal));
                }
                case 2 -> service.printAll();
                case 3 -> {
                    System.out.print("Nume departament: ");
                    String deptCautat = scanner.nextLine();
                    service.findByDepartament(deptCautat);
                }
                case 4 -> service.printAuditLog();
                case 0 -> System.exit(0);
                default -> System.out.println("Invalid");
            }
        }
    }
}
