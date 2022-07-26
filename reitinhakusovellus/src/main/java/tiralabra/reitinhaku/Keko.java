package tiralabra.reitinhaku;

/**
 * Algoritmien hyödyntämästä minimikeosta vastaava luokka.
 * 
 * @see Dijkstra
 * @see JumpPointSearch
 */
public class Keko {

    private Solmu[] keko;
    private int nykyinenKoko;
    private int maxKoko;
    private int lisattyja;

    /**
     * Keon konstruktori.
     * 
     * @param maxKoko Keon maksimikoko, joka määritelty verkon solmujen määrän
     *                mukaan.
     */

    public Keko(int maxKoko) {
        this.maxKoko = maxKoko;
        this.keko = new Solmu[maxKoko + 1];
        this.nykyinenKoko = 0;
        this.lisattyja = 0;
    }

    /**
     * Solmun lisääminen kekoon.
     * 
     * @param solmu Kekoon lisättävä solmu.
     * @throws Error Antaa virheen, jos koitetaan lisätä solmua, jota ei ole tai jos
     *               keko on täynnä.
     * @see Solmu
     */
    public void lisaaKekoon(Solmu solmu) throws Error {
        if (solmu == null) {
            return;
        }
        if (nykyinenKoko >= maxKoko) {
            throw new Error("Ups! Keko täynnä.");
        }
        nykyinenKoko++;
        lisattyja++;
        int indeksi = nykyinenKoko;
        keko[indeksi] = solmu;
        if (nykyinenKoko > 0) {
            nostaKeossa(indeksi);
        }
    }

    /**
     * Keon järjestäminen solmun lisäyksen jälkeen.
     * Järjestäminen huomioi todellisen etäisyyden lisäksi mahdollisen prioriteetin.
     * JPS-algoritmi hyödyntää prioriteettiä.
     * 
     * @param indeksi Keon kohta, johon uusi solmu on lisätty.
     * 
     * @see JumpPointSearch Metodia "laskeDiagonaalinenEtaisyys" hyödynnetään
     *      prioriteetin määrittelyssä.
     */
    public void nostaKeossa(int indeksi) {
        if (nykyinenKoko == 1) {
            swap(indeksi, 1);
            return;
        }
        int vanhemmanIndeksi = indeksi / 2;
        int nykyinenIndeksi = indeksi;
        while (nykyinenIndeksi > 0 && keko[vanhemmanIndeksi].getEtaisyys() * keko[vanhemmanIndeksi]
                .getPrioriteetti() > keko[nykyinenIndeksi].getEtaisyys() * keko[nykyinenIndeksi].getPrioriteetti()) {
            swap(nykyinenIndeksi, vanhemmanIndeksi);
            if (vanhemmanIndeksi > 1) {
                nykyinenIndeksi = vanhemmanIndeksi;
                vanhemmanIndeksi = vanhemmanIndeksi / 2;
            }
        }
    }

    /**
     * Keon järjestäminen solmun poistamisen jälkeen.
     * Järjestäminen huomioi todellisen etäisyyden lisäksi mahdollisen prioriteetin.
     * JPS-algoritmi hyödyntää prioriteettiä.
     * 
     * @param indeksi Keon kohta johon uusi solmu on lisätty.
     * 
     * @see JumpPointSearch Metodia "laskeDiagonaalinenEtaisyys" hyödynnetään
     *      prioriteetin määrittelyssä.
     */
    public void laskeKeossa(int indeksi) {
        int pienimmanIndeksi = indeksi;
        int vasenLapsiIndeksi = 2 * indeksi;
        int oikeaLapsiIndeksi = 2 * indeksi + 1;
        if (vasenLapsiIndeksi < nykyinenKoko
                && keko[pienimmanIndeksi].getEtaisyys()
                        * keko[pienimmanIndeksi].getPrioriteetti() > keko[vasenLapsiIndeksi].getEtaisyys()
                                * keko[vasenLapsiIndeksi].getPrioriteetti()) {
            pienimmanIndeksi = vasenLapsiIndeksi;
        }
        if (oikeaLapsiIndeksi < nykyinenKoko
                && keko[pienimmanIndeksi].getEtaisyys()
                        * keko[pienimmanIndeksi].getPrioriteetti() > keko[oikeaLapsiIndeksi].getEtaisyys()
                                * keko[oikeaLapsiIndeksi].getPrioriteetti()) {
            pienimmanIndeksi = oikeaLapsiIndeksi;
        }
        if (pienimmanIndeksi != indeksi) {
            swap(indeksi, pienimmanIndeksi);
            laskeKeossa(pienimmanIndeksi);
        }
    }

    /**
     * Solmujen paikan vaihto keossa.
     * 
     * @param nykyinenIndeksi  Keon kohta, jossa solmu on tällä hetkellä.
     * @param vanhemmanIndeksi Solmun vanhemman sijainti keossa.
     */
    private void swap(int nykyinenIndeksi, int vanhemmanIndeksi) {
        Solmu temp = keko[nykyinenIndeksi];
        keko[nykyinenIndeksi] = keko[vanhemmanIndeksi];
        keko[vanhemmanIndeksi] = temp;
    }

    /**
     * Metodi palauttaa keossa päällimmäisenä olevan solmun.
     * 
     * @return Solmu Palauttaa keosta solmun, jonka etäisyys alkuun pienin.
     * @throws Error Antaa virheen, jossa pienintä arvoa ei ole eli indeksi 1 on
     *               tyhjä.
     */
    public Solmu pienin() throws Error {
        if (this.keko[1] == null) {
            throw new Error("Ups! Keko on tyhjä.");
        }
        return this.keko[1];
    }

    /**
     * Metodi palauttaa algoritmeille tiedon siitä, onko keossa käsiteltäviä
     * solmuja.
     * 
     * @return boolean Palauttaa tiedon siitä, onko kekossa solmuja.
     */
    public boolean onTyhja() {
        if (nykyinenKoko == 0) {
            return true;
        }
        return false;
    }

    /**
     * Metodi palauttaa ja sitten poistaa keossa päällimmäisenä olevan solmun.
     * 
     * @return Solmu Palauttaa keon solmun, jonka etäisyys alkuun on pienin.
     */
    public Solmu poistaPienin() {
        if (nykyinenKoko == 0) {
            return null;
        }
        Solmu poistettava = pienin();
        keko[1] = keko[nykyinenKoko];
        laskeKeossa(1);
        nykyinenKoko--;
        return poistettava;
    }

    /**
     * Mittaa kekoon reitinhaun aikana lisättyjen solmujen määrää, jotta voidaan
     * verrata algoritmien eroja keon hyödyntämisessä.
     * 
     * @return int Kekoon käytön aikana lisättyjen solmujen määrä.
     */
    public int getLisattyja() {
        return lisattyja;
    }

}
