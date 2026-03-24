package com.pao.laboratory05.playlist;

import java.util.Arrays;
import java.util.Comparator;

public class Playlist {
    private String name;
    private Song[] songs;
    private static int size;

    public Playlist(String name) {
        this.name = name;
        this.songs = new Song[2];
        Playlist.size = 0;
    }

    public void addSong(Song song) {
        if (size == songs.length) {
            Song[] newSongs = new Song[songs.length * 2];
            System.arraycopy(songs, 0, newSongs, 0, songs.length);
            songs = newSongs;
        }
        songs[size++] = song;
    }

    public void printSortedByTitle() {
        Song[] copy = Arrays.copyOf(songs, size);
        Arrays.sort(copy);

        for (Song s : copy) System.out.println(s);
    }

    public void printSortedByDuration() {
        Song[] copy = Arrays.copyOf(songs, size);
        Arrays.sort(copy, (s1, s2) -> s1.durationSeconds() - s2.durationSeconds());

        for (Song s : copy) System.out.println(s);
    }

    public int getTotalDuration() {
        int total = 0;
        for (int i = 0; i < size; i++) {
            total += songs[i].durationSeconds();
        }

        return total;
    }

    public String getName(){
        return this.name;
    }
}