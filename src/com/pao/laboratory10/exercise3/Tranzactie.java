package com.pao.laboratory10.exercise3;

import com.pao.laboratory10.exercise1.TipTranzactie;
import java.util.Locale;

public class Tranzactie extends com.pao.laboratory10.exercise1.Tranzactie {
    private String contSursa;

    public Tranzactie(int id, double suma, String data, TipTranzactie tip, String contSursa) {
        super(id, suma, data, tip);
        this.contSursa = contSursa;
    }

    public String getContSursa() {
        return contSursa;
    }

    public String getLuna() {
        return getData().substring(0, 7);
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "%s (Cont: %s)", super.toString(), contSursa);
    }
}