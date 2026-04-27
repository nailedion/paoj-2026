package com.pao.laboratory09.exercise3;

import com.pao.laboratory09.exercise1.TipTranzactie;
import com.pao.laboratory09.exercise1.Tranzactie;

public class ATMThread extends Thread {
    private final int idATM;
    private final CoadaTranzactii coada;

    public ATMThread(int idATM, CoadaTranzactii coada) {
        this.idATM = idATM;
        this.coada = coada;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i <= 4; i++) {
                int transId = idATM * 100 + i;
                double suma = 100 + Math.random() * 900;
                Tranzactie t = new Tranzactie(transId, suma, "2024-05-20", "SRC", "DST", TipTranzactie.CREDIT);

                System.out.printf("[ATM-%d] trimite: Tranzactie #%d %.2f RON\n", idATM, transId, suma);
                coada.adauga(t, idATM);

                Thread.sleep(50);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}