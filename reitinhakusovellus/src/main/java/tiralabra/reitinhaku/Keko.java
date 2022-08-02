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
        if(solmu == null){
            return;
        }
        if (nykyinenKoko >= maxKoko) {
            throw new Error("Ups! Keko täynnä.");
        }
        System.out.print("Lisätään kekoon " + solmu.getX() + "," + solmu.getY() + "\n");
        nykyinenKoko++;
        int indeksi = nykyinenKoko;
        keko[indeksi] = solmu;
        // System.out.print("Lisää kekoon - solmu sijoitettiin indeksiin: " + indeksi +
        // ", etaisyys " + keko[indeksi].getEtaisyys() + "\n");
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
        System.out.print("Nostetaan kekossa indeksi " + indeksi + "\n");
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
     * Keon järjestäminen solmun poistamisen jälkeen
     * 
     * @param indeksi keon kohta johon uusi solmu on lisätty
     */
    public void laskeKeossa(int indeksi) {
        System.out.print("Lasketaan keossa indeksiä " + indeksi + "\n");
        int pienimmanIndeksi = indeksi;
        int vasenLapsiIndeksi = 2 * indeksi;
        int oikeaLapsiIndeksi = 2 * indeksi + 1;
        if(vasenLapsiIndeksi < nykyinenKoko && keko[pienimmanIndeksi].getEtaisyys() > keko[vasenLapsiIndeksi].getEtaisyys()) {
            pienimmanIndeksi = vasenLapsiIndeksi;
        }
        if(oikeaLapsiIndeksi < nykyinenKoko && keko[pienimmanIndeksi].getEtaisyys() > keko[oikeaLapsiIndeksi].getEtaisyys()) {
            pienimmanIndeksi = oikeaLapsiIndeksi;
        }
        if(pienimmanIndeksi != indeksi) {
            swap(indeksi, pienimmanIndeksi);
            laskeKeossa(pienimmanIndeksi);
        }
    }

    /**
     * Solmujen paikan vaihto keossa
     * 
     * @param nykyinenIndeksi  kohta jossa solmu on tällä hetkellä
     * @param vanhemmanIndeksi solmun vanhemman sijainti keossa
     */
    private void swap(int nykyinenIndeksi, int vanhemmanIndeksi) {
        System.out.print("Tehdään swap " + nykyinenIndeksi + "<>" + vanhemmanIndeksi+ ", etäisyydet " + keko[nykyinenIndeksi].getEtaisyys() + "><" +keko[vanhemmanIndeksi].getEtaisyys()+ "\n");
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
        System.out.print("Kysyttiin pienin etäisyys " + keko[1].getEtaisyys() + "\n");
        return this.keko[1];
    }

    
    /** 
     * @return boolean
     */
    public boolean onTyhja(){
        if(nykyinenKoko == 0){
            return true;
        } 
        return false;
    }

    public Solmu poistaPienin() {
        if(nykyinenKoko == 0) {
            return null;
        }
        Solmu poistettava = pienin();
        nykyinenKoko--;
        keko[1] = keko[nykyinenKoko];
        return poistettava;
    }

}
