package com.pao.laboratory05.biblioteca;

import java.util.Arrays;
import java.util.Comparator;

public class BibliotecaService {
    private Carte[] carti;

    private BibliotecaService() {
        this.carti = new Carte[0];
    }

    private static class BibliotecaHolder {
        private static final BibliotecaService INSTANCE = new BibliotecaService();
    }

    public static BibliotecaService getInstance() {
        return BibliotecaHolder.INSTANCE;
    }

    public void addCarte(Carte carte) {
        this.carti = Arrays.copyOf(this.carti, this.carti.length + 1);
        this.carti[this.carti.length - 1] = carte;
    }

    public void listSortedByRating() {
        Carte[] copy = Arrays.copyOf(carti, carti.length);
        Arrays.sort(copy);

        for (Carte c : copy) System.out.println(c);
    }

    public void listSortedBy(Comparator<Carte> comparator) {
        Carte[] copy = Arrays.copyOf(carti, carti.length);
        Arrays.sort(copy, comparator);

        for (Carte c : copy) System.out.println(c);
    }
}