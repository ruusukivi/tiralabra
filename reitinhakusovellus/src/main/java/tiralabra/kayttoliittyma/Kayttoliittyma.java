package tiralabra.kayttoliittyma;
import tiralabra.karttojenpiirto.RandomWalk;

/**
 * Luokka sovelluksen komentorivikäyttöliittymälle
 * 
 * @see IO
 */

public class Kayttoliittyma {
    /** Syotteiden ja tulosteiden käsittely */
    private IO io;

    /**
     * Käyttöliittymän konstruktori
     *
     * @param io tulostus ja käyttäjän syötteiden luku
     */
    public Kayttoliittyma(IO io) {
        this.io = io;
    }

    /** Käyttöliittymän käynnistys */
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
        io.tulosta("1 -- Piirrä uusi kartta \n");
        io.tulosta("0 -- Sulje sovellus\n");
    }

    /** Toimintojen käsittely */
    public void valitseToiminto(String toiminto) {
        if (toiminto.equalsIgnoreCase("1")){
            io.tulosta("Anna kartan nimi: \n");
            String nimi = io.lue();
            io.tulosta("Anna kartan sivun leveys (ruutujen lkm), esim. 100: \n");
            int leveys = Integer.valueOf(io.lue());
            io.tulosta("Anna tunneleiden määrä, esim. 500: \n");
            int tunnelit = Integer.valueOf(io.lue());
            io.tulosta("Anna yksittäisen tunnelin maksimipituus, esim 15: \n");
            int pituus = Integer.valueOf(io.lue());

            RandomWalk kartta = new RandomWalk(leveys, tunnelit, pituus, nimi);
            kartta.piirraKartta();
        }
    }

}
