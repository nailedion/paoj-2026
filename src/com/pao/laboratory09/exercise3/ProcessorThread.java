package com.pao.laboratory09.exercise3;

import com.pao.laboratory09.exercise1.Tranzactie;

public class ProcessorThread implements Runnable {
    private final CoadaTranzactii coada;
    public volatile boolean activ = true;

    public ProcessorThread(CoadaTranzactii coada) {
        this.coada = coada;
    }

    @Override
    public void run() {
        try {
            while (activ || !coada.esteGoala()) {
                if (coada.esteGoala() && activ) {
                    Thread.sleep(10);
                    continue;
                }

                if (!coada.esteGoala()) {
                    Tranzactie t = coada.extrage();
                    System.out.println("[Processor] Factura #" + t.getId() + " - procesata.");
                    Thread.sleep(80);
                } else if (!activ) {
                    break;
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}