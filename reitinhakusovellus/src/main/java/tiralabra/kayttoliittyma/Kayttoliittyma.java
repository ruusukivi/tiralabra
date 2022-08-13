package tiralabra.kayttoliittyma;

import tiralabra.karttojenpiirto.Kartat;
import tiralabra.karttojenpiirto.RandomWalk;

import java.util.ArrayList;

/**
 * Luokka reitihakusovelluksen komentorivikäyttöliittymälle.
 * 
 * @see RajapintaIO
 * @see IO
 * @see Kartat
 */

public class Kayttoliittyma {
    private RajapintaIO io;
    private Kartat kartat;

    /**
     * Käyttöliittymän konstruktori.
     *
     * @param io     Luokka joka hoitaa tulostuksen ja käyttäjän syötteiden lukemisen.
     * @param kartat Luokka karttojen säilöntään ja käsittelyyn session aikana.
     */
    public Kayttoliittyma(RajapintaIO io, Kartat kartat) {
        this.io = io;
        this.kartat = kartat;
    }

    public void kaynnista() {
        io.tulosta("\na->>-b->>-a->>-b->>-a->>-b->> REITINHAKU <<-a-<<-b-<<-a-<<-b-<<-a-<<-b\n");

        while (true) {
            naytaValikko();
            io.tulosta(">> Valitse toiminto: \n");
            String syote = io.lue();
            if (syote.equalsIgnoreCase("0")) {
                break;
            } 
            valitseToiminto(syote);
        }
    }

    /** Valikon tulostus */
    public void naytaValikko() {
        io.tulosta("\nTOIMINNOT: \n");
        io.tulosta("1 -- Luo uusi kartta \n"); 
        io.tulosta("2 -- Näytä aiemmin luotu kartta \n");
        io.tulosta("0 -- Sulje sovellus\n");
    }

    /**
     * Toimintojen käsittely
     * 
     * @param toiminto toiminnon numero, käsitellään stringinä
     */
    public void valitseToiminto(String toiminto) {
        if (toiminto.equalsIgnoreCase("1")) {
            luoKartta();
        }
        if (toiminto.equalsIgnoreCase("2")) {
            listaaKartat();
        }
    }

    /* Uuden kartan luonti ja tulostus */
    public void luoKartta() {
        io.tulosta("Anna kartan tunniste: \n");
        String nimi = io.lue();
        io.tulosta("Anna kartan sivun leveys (ruutujen lkm), esim. 100: \n");
        int leveys = Integer.valueOf(io.lue());
        io.tulosta("Anna tunneleiden määrä, esim. 500: \n");
        int tunnelit = Integer.valueOf(io.lue());
        io.tulosta("Anna yksittäisen tunnelin maksimipituus, esim. 100: \n");
        int pituus = Integer.valueOf(io.lue());
        RandomWalk kartta = new RandomWalk(leveys, tunnelit, pituus, nimi);
        kartat.lisaaKartta(kartta.muodostaKartastaVerkko("dijkstra"));;
        kartat.lisaaKartta(kartta.muodostaKartastaVerkko("jps"));
        kartat.tulostaKartta(nimi.concat("-dijkstra"));
        kartat.tulostaKartta(nimi.concat("-jps"));
    }

    /* Session aikana luotujen karttojen listaus ja valitun kartan tulostus */
    public void listaaKartat() {
        if (kartat.karttojenMaara() < 1) {
            io.tulosta("Yhtään karttaa ei ole vielä luotu. \n");
        } else {
            io.tulosta("Valitse tulostettava kartta: \n");
            ArrayList<String> nimet = kartat.karttojenNimet();
            for (String nimi : nimet) {
                io.tulosta(nimi + "\n");
            }
            io.tulosta("Anna tulostettavan kartan nimi: ");
            String syote = io.lue();
            kartat.tulostaKartta(syote);
            //kartat.tulostaKartta(syote, "jps");
        }

    }

}
