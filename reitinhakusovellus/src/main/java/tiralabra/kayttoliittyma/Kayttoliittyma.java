package tiralabra.kayttoliittyma;

import tiralabra.karttojenpiirto.Kartat;
import tiralabra.karttojenpiirto.RandomWalk;
import tiralabra.vertailu.Vertailu;

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
     * @param io     Luokka joka hoitaa tulostuksen ja käyttäjän syötteiden
     *               lukemisen.
     * @param kartat Luokka karttojen säilöntään ja käsittelyyn session aikana.
     */
    public Kayttoliittyma(RajapintaIO io, Kartat kartat) {
        this.io = io;
        this.kartat = kartat;
    }

    /**
     * Konsolipohjaisen käyttöliittymän käynnistävä metodi.
     */
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

    /** Valikon tulostus. */
    public void naytaValikko() {
        io.tulosta("\nTOIMINNOT: \n");
        io.tulosta("1 -- Luo uusi kartta \n");
        io.tulosta("2 -- Näytä aiemmin luotu kartta \n");
        io.tulosta("3 -- Vertaile reitinhakualgoritmeja \n");
        io.tulosta("0 -- Sulje sovellus\n");
    }

    /**
     * Toimintojen käsittely.
     * Sovellus ei vielä tue virheellisten syöttteiden käsittelyä kovin hyvin.
     * @param toiminto Toiminnon numero, käsitellään stringinä
     */
    public void valitseToiminto(String toiminto) {
        if (toiminto.equalsIgnoreCase("1")) {
            luoKartta();
        }
        if (toiminto.equalsIgnoreCase("2")) {
            listaaKartat();
        }

        if (toiminto.equalsIgnoreCase("3")) {
            vertaileAlgoritmeja();
        }
    }

    /** Uuden kartan luonti ja tulostus. */
    public void luoKartta() {
        io.tulosta("Anna kartan tunniste: \n");
        String nimi = io.lue();
        io.tulosta("Anna kartan sivun leveys (ruutujen lkm), esim. 10: \n");
        int leveys = Integer.valueOf(io.lue());
        io.tulosta("Anna polkujen määrä, esim. 10: \n");
        int polut = Integer.valueOf(io.lue());
        io.tulosta("Anna yksittäisen polun maksimipituus, esim. 10: \n");
        int pituus = Integer.valueOf(io.lue());
        RandomWalk kartta = new RandomWalk(leveys, polut, pituus, nimi);
        kartat.lisaaKartta(kartta.muodostaKartastaVerkko("dijkstra"));
        kartat.lisaaKartta(kartta.muodostaKartastaVerkko("jps"));
        kartat.tulostaKartta(nimi.concat("-dijkstra"));
        kartat.tulostaKartta(nimi.concat("-jps"));
    }

    /** Session aikana luotujen karttojen listaus ja valitun kartan tulostus. */
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
        }
    }

    /** Vertailuajon käynnistys, valittavana suppea ja laaja vertailu. 
     * Suppea aineisto tarkoitettu ruudulta katseltavaksi.
     * Suppea aineisto muodostetaan vertailemalla algoritmeja kolmella eri Random Walk-kartalla. 
     * Laaja aineisto tarkoitettu ruudulta kopioitavaksi esim. exceliin jatkokäsittelyä varten.
     * Laaja aineisto muodostetaan ajamalla luomalla kolmella eri asetuksella 1000 Random Walk -karttaa kullakin.
     * Myöhemmin tarkoitus vaihtaa laajan aineiston tulostus tiedostoon ruudun sijasta.
    */
    public void vertaileAlgoritmeja() {
        io.tulosta("a -- Haluan katsella tuloksia ruudulla (pieni aineisto) \n");
        io.tulosta("b -- Haluan käsitellä dataa muualla (iso aineisto) \n");
        io.tulosta(">> Valitse missä muodossa haluat tulokset: \n");
        String tulostus = io.lue();

        boolean teksteilla = true;
        int i = 1;
        if (tulostus.equals("b")) {
            teksteilla = false;
            i = 1000;
        }
        io.tulosta("Odota hetki - vertailuajo vie hieman aikaa.... ");
        io.tulosta("\n");
        while (i > 0) {
            Vertailu vertailu100 = new Vertailu(teksteilla);
            ArrayList<String> tulokset100 = vertailu100.annaTulokset(1000, 100, 1000, "1000-100-1000");
            for (String tulos : tulokset100) {
                io.tulosta(tulos);
            }
            Vertailu vertailu150 = new Vertailu(teksteilla);
            ArrayList<String> tulokset150 = vertailu150.annaTulokset(1000, 150, 1000, "1000-150-1000");
            for (String tulos : tulokset150) {
                io.tulosta(tulos);
            }

            Vertailu vertailu1000 = new Vertailu(teksteilla);
            ArrayList<String> tulokset200 = vertailu1000.annaTulokset(1000, 200, 1000, "1000-200-1000");
            for (String tulos : tulokset200) {
                io.tulosta(tulos);
            }
            i--;
        }

    }
}
