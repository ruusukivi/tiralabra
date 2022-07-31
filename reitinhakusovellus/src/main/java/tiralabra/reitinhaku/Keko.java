package tiralabra.reitinhaku;

/**
 * Keon luova luokka.
 * 
 * Algoritmien tarvitseman minimikeon luova luokka.
 */
public class Keko {

    private Solmu[] keko;
    private int nykyinenKoko;
    private int maxKoko;

    /**
     * Keon konstruktori.
     * 
     * @param maxKoko keon maksimikoko, joka määräytyy verkon solmujen määrän mukaan
     */

    public Keko(int maxKoko) {
        this.maxKoko = maxKoko;
        this.keko = new Solmu[maxKoko + 1];
        this.nykyinenKoko = 0;
    }

    /**
     * Solmun lisääminen kekoon.
     * 
     * @param solmu kekoon lisättävä solmu.
     */
    public void lisaaKekoon(Solmu solmu) throws Error {
        if (nykyinenKoko >= maxKoko) {
            throw new Error("Ups! Keko täynnä.");
        }
        nykyinenKoko++;
        int indeksi = nykyinenKoko;
        keko[indeksi] = solmu;
        //System.out.print("Lisää kekoon - solmu sijoitettiin indeksiin: " + indeksi + ", etaisyys " + keko[indeksi].getEtaisyys() + "\n");
        if (nykyinenKoko > 1) {
            nostaKeossa(indeksi);
        }
    }

    /**
     * Keon järjestäminen solmun lisäyksen jälkeem
     * 
     * @param indeksi keon kohta johon uusi solmu on lisätty
     */
    public void nostaKeossa(int indeksi) {
        int vanhemmanIndeksi = indeksi / 2;
        int nykyinenIndeksi = indeksi;
        while (nykyinenIndeksi > 0 && keko[vanhemmanIndeksi].getEtaisyys() > keko[nykyinenIndeksi].getEtaisyys()) {
            swap(nykyinenIndeksi, vanhemmanIndeksi);
            if (vanhemmanIndeksi > 1) {
                nykyinenIndeksi = vanhemmanIndeksi;
                vanhemmanIndeksi = vanhemmanIndeksi / 2;
            }
        }
    }

    /**
     * Solmujen paikan vaihto keossa
     * 
     * @param nykyinenIndeksi  kohta jossa solmu on tällä hetkellä
     * @param vanhemmanIndeksi solmun vanhemman sijainti keossa
     */
    private void swap(int nykyinenIndeksi, int vanhemmanIndeksi) {
        //System.out.print("Tehdään swap " + nykyinenIndeksi + "<>" + vanhemmanIndeksi + ", etäisyydet " + keko[nykyinenIndeksi].getEtaisyys() + "><" + keko[vanhemmanIndeksi].getEtaisyys()+ "\n");
        Solmu temp = keko[nykyinenIndeksi];
        keko[nykyinenIndeksi] = keko[vanhemmanIndeksi];
        keko[vanhemmanIndeksi] = temp;
    }

    /**
     * Keon pienin solmu
     * 
     * @return palauttaa keosta solmun jonka etäisyys pienin
     */
    public Solmu pienin() throws Error {
        if (this.keko[1] == null) {
            throw new Error("Ups! Keko on tyhjä.");
        }
        System.out.print("Pienin etäisyys " + keko[1].getEtaisyys() + "\n");
        return this.keko[1];
    }

}
