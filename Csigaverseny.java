package csigaverseny;

/**
 *
 * @author p086gp
 */
import java.util.Random;
import java.util.Scanner;

class Csiga {
    private String szin;
    private int sebesseg;
    private int messze;
    private boolean kapottGyorsito;
    private String[] szinek;

    public Csiga(String szin, String[] szinek) {
        this.szin = szin;
        this.sebesseg = 0;
        this.messze = 0;
        this.kapottGyorsito = false;
        this.szinek = szinek;
    }

     public String getSzin() {
        return szin;
    }

    public int getSebesseg() {
        return sebesseg;
    }

    public int getMessze() {
        return messze;
    }

    public boolean kapottGyorsito() {
        return kapottGyorsito;
    }

    public void lep(int gyorsitas) {
        Random rand = new Random();
        int lepes = rand.nextInt(4); // Véletlenszerű (0-3)
        if (gyorsitas > 0) {
            lepes *= 2; // Ha van gyorsítás, a lépésszám duplázódik
            kapottGyorsito = true;
        }
        sebesseg = lepes;
        messze += lepes;
    }

    public void adGyorsitoHaKell() {
        Random rand = new Random();
        if (rand.nextInt(100) < 20) { // 20% esély
            kapottGyorsito = true;
        }
    }

    public void nullazGyorsito() {
        kapottGyorsito = false;
    }

    public int getSzinIndex() {
        for (int i = 0; i < szinek.length; i++) {
            if (szinek[i].equals(szin)) {
                return i;
            }
        }
        return -1;
    }
}

public class Csigaverseny {
    private Csiga[] csigak;
    private int korok;
    private String[] szinek;

    public Csigaverseny(String[] szinek, int korok) {
        this.korok = korok;
        this.szinek = szinek;
        this.csigak = new Csiga[szinek.length];
        for (int i = 0; i < szinek.length; i++) {
            csigak[i] = new Csiga(szinek[i], szinek);
        }
    }

    public void inditVerseny() {
        Scanner scanner = new Scanner(System.in);

        // Színek és az azokhoz tartozó számok kiíratása
        System.out.println("Színek:");
        for (int i = 0; i < szinek.length; i++) {
            System.out.println(i + ": " + szinek[i]);
        }

        // Fogadás
        int fogadas;
        while (true) {
            System.out.println("Fogadjon, hogy melyik csiga nyer! (0-" + (szinek.length - 1) + ")");
            fogadas = scanner.nextInt();
            if (fogadas >= 0 && fogadas < szinek.length) {
                break; // Kilép a ciklusból, ha a fogadás érvényes
            } else {
                System.out.println("Érvénytelen szám! Kérjük, adja meg újra.");
            }
        }
        System.out.println("Fogadott csiga: " + szinek[fogadas]);
        System.out.println();

        Random rand = new Random();
        for (int kor = 1; kor <= korok; kor++) {
            System.out.println("Kör " + kor + ":");

            // Gyorsító adása 20% eséllyel minden csigának
            for (Csiga csiga : csigak) {
                csiga.adGyorsitoHaKell();
            }

            // Minden csiga lép egyet, csiga kap gyorsítást
            for (Csiga csiga : csigak) {
                csiga.lep(csiga.kapottGyorsito() ? 1 : 0); // Ha van gyorsítás, 1, egyébként 0
                System.out.println(csiga.getSzin() + " csiga - Sebesség: " + csiga.getSebesseg() + ", Hol tart: " + csiga.getMessze() +
                        ", Gyorsítót kapott-e: " + (csiga.kapottGyorsito() ? "Igen" : "Nem"));
            }
            System.out.println();

            // Gyorsító nullázása minden kör végén
            for (Csiga csiga : csigak) {
                csiga.nullazGyorsito();
            }
        }

        // Eredmény kiíratása
        Csiga nyertes = meghatarozNyertest();
        System.out.println("A " + nyertes.getSzin() + " csiga nyert " + nyertes.getMessze() + " egységgel!");

        // Fogadás eredményének ellenőrzése
        if (fogadas == nyertes.getSzinIndex()) {
            System.out.println("Gratulálunk, nyertél!");
        } else {
            System.out.println("Sajnos nem nyertél, a " + nyertes.getSzin() + " csiga nyert.");
        }
    }

    private Csiga meghatarozNyertest() {
        Csiga nyertes = csigak[0];
        for (Csiga csiga : csigak) {
            if (csiga.getMessze() > nyertes.getMessze()) {
                nyertes = csiga;
            }
        }
        return nyertes;
    }

    public static void main(String[] args) {
        String[] szinek = {"piros", "zöld", "kék"};
        int korok = 5;

        Csigaverseny verseny = new Csigaverseny(szinek, korok);
        verseny.inditVerseny();
    }
}
