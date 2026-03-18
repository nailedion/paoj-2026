package com.pao.laboratory03.enums;

/**
 * Exercițiul 2 — Enum-uri
 *
 * Creează în acest pachet (lângă acest Main.java) un enum și apoi folosește-l aici.
 *
 * PASUL 1 — Creează enum-ul Priority.java (fișier separat în același pachet):
 *   - Constante: LOW, MEDIUM, HIGH, CRITICAL
 *   - Câmpuri private: int level, String color
 *   - Constructor privat: Priority(int level, String color)
 *   - Getteri: getLevel(), getColor()
 *   - Metodă abstractă: String getEmoji() — fiecare constantă o implementează diferit
 *     LOW → "🟢", MEDIUM → "🟡", HIGH → "🟠", CRITICAL → "🔴"
 *   - Valorile sugerate:
 *     LOW(1, "green"), MEDIUM(2, "yellow"), HIGH(3, "orange"), CRITICAL(4, "red")
 *
 * Output așteptat:
 *
 * === Toate prioritățile ===
 * 🟢 LOW (level=1, color=green)
 * 🟡 MEDIUM (level=2, color=yellow)
 * 🟠 HIGH (level=3, color=orange)
 * 🔴 CRITICAL (level=4, color=red)
 *
 * === Switch pe prioritate ===
 * ⚠️ Atenție! Prioritate ridicată!
 *
 * === valueOf ===
 * Priority.valueOf("HIGH") = HIGH
 *
 * === Comparare enum ===
 * HIGH == HIGH? true
 * HIGH == LOW? false
 *
 * === name() și ordinal() ===
 * LOW: name=LOW, ordinal=0
 * MEDIUM: name=MEDIUM, ordinal=1
 * HIGH: name=HIGH, ordinal=2
 * CRITICAL: name=CRITICAL, ordinal=3
 *
 *  * PASUL 2 — În acest Main.java:
 *  *   a) Parcurge toate valorile cu Priority.values() și afișează:
 *  *      "emoji name (level=X, color=Y)"
 *  *   b) Folosește switch pe un Priority și afișează un mesaj specific.
 *  *   c) Convertește un String în Priority cu Priority.valueOf("HIGH") — afișează rezultatul.
 *  *   d) Demonstrează compararea: folosește == între două enum-uri (NU .equals()).
 *  *   e) Afișează name() și ordinal() pentru fiecare constantă.
 *  *
 */
public class Main {
    public static void main(String[] args) {
        // TODO: implementează pașii de mai sus
        // Hint: creează mai întâi fișierul Priority.java în acest pachet

        for(Priority.Prioritate prioritate: Priority.Prioritate.values()){
            System.out.println("  " + prioritate.name() + "(level="+ prioritate.getLevel()+", color="+prioritate.getColor() + ")");
        }

        System.out.println();
        Priority.Prioritate current = Priority.Prioritate.MEDIUM;
        switch (current){
            case LOW: System.out.println("Este low"); break;
            case MEDIUM: System.out.println("Este medium"); break;
            case HIGH: System.out.println("Este high"); break;
        }

        Priority.Prioritate high = Priority.Prioritate.valueOf("HIGH");
        System.out.println(high.getEmoji());
        System.out.println(high == Priority.Prioritate.HIGH);
        System.out.println(high == Priority.Prioritate.MEDIUM);

        for(Priority.Prioritate prioritate: Priority.Prioritate.values()){
            System.out.printf("%s: %s, (ordinal=%d)%n", prioritate.name(), prioritate.name(), prioritate.getLevel());
        }
    }
}

