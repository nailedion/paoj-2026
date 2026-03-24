package com.pao.laboratory05.audit;

import java.time.LocalDateTime;
import java.util.Arrays;

public class AngajatService {
    private Angajat[] angajati;
    private AuditEntry[] auditLog;

    private AngajatService() {
        this.angajati = new Angajat[0];
        this.auditLog = new AuditEntry[0];
    }

    private static class AngajatHolder {
        private static final AngajatService INSTANCE = new AngajatService();
    }

    public static AngajatService getInstance() {
        return AngajatHolder.INSTANCE;
    }

    private void logAction(String action, String target) {
        AuditEntry entry = new AuditEntry(action, target, LocalDateTime.now().toString());
        auditLog = Arrays.copyOf(auditLog, auditLog.length + 1);
        auditLog[auditLog.length - 1] = entry;
    }

    public void addAngajat(Angajat a) {
        angajati = Arrays.copyOf(angajati, angajati.length + 1);
        angajati[angajati.length - 1] = a;

        logAction("ADD", a.getNume());
    }

    public void printAll() {
        for (Angajat a : angajati) {
            System.out.println(a);
        }
    }

    public void listBySalary() {
        Angajat[] copy = Arrays.copyOf(angajati, angajati.length);
        Arrays.sort(copy);

        for (Angajat a : copy) {
            System.out.println(a);
        }
    }

    public void findByDepartament(String numeDept) {
        logAction("FIND_BY_DEPT", numeDept);

        boolean gasit = false;
        for (Angajat a : angajati) {
            if (a.getDepartament().nume().equalsIgnoreCase(numeDept)) {
                System.out.println(a);
                gasit = true;
            }
        }

        if (!gasit) {
            System.out.println("Niciun angajat in departamentul: " + numeDept);
        }
    }

    public void printAuditLog() {
        if (auditLog.length == 0) {
            System.out.println("Nu sunt actiuni");
        } else {
            for (AuditEntry entry : auditLog) {
                System.out.println(entry);
            }
        }
    }
}