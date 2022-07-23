package tiralabra.kayttoliittyma;

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
        io.tulosta("\na->>-b->>-a->>-b->>-a->>-b->> REITINHAKU <<-a-<<-b-<<-a-<<-b-<<-a-<<-b");

        while (true) {
            naytaValikko();
            io.tulosta(">> Valitse toiminto: ");
            String syote = io.lue();
            int toiminto = Integer.valueOf(syote);
            if (toiminto == 0) {
                break;
            } 
            valitseToiminto(toiminto);

        }
    }

    /** Valikon tulostus */
    public void naytaValikko() {
        io.tulosta("\nTOIMINNOT: ");
        io.tulosta("1 -- Luo kartat (TODO)");
        io.tulosta("0 -- Sulje sovellus\n");
    }

    /** Toimintojen käsittely */
    public void valitseToiminto(int toiminto) {

    }

}
