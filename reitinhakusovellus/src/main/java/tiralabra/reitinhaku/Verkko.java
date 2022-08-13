package tiralabra.reitinhaku;

import java.util.ArrayList;

/**
 * Verkon luova luokka.
 * 
 * Kartasta verkon muodostava luokka.
 */

public class Verkko {
    private String nimi;
    private int koko;
    private Solmu[][] solmut;
    private ArrayList<String> reitti;

    /**
     * Metodi luo tyhjän matriisitaulukon verkolle.
     * 
     * @param koko Kartan sivun pituus, joka tarvitaan matriisitaulukon luontiin.
     * @param nimi Verkon nimi helpottamaan hakua session aikana. Tällä hetkellä ei vielä uniikki.
     */

    public Verkko(int koko, String nimi) {
        this.koko = koko;
        this.nimi = nimi;
        this.solmut = new Solmu[koko][koko];
        this.reitti = new ArrayList<String>();
    }

    /**
     * Metodi luo uuden solmun ja lisää sen verkkoon.
     * 
     * @param x          Solmun x-koordinaatin arvo.
     * @param y          Solmun y-koordinaatin arvo.
     * @param seina      Kartan kohta jota ei voi käyttää osana reittiä.
     * @param kuljettava Kartan kohta joka sopii osaksi reittiä.
     */

    public void lisaaSolmu(int x, int y, boolean seina, boolean kuljettava) {
        this.solmut[x][y] = new Solmu(x, y, seina, kuljettava);
    }

    /**
     * Solmujen määrä.
     * 
     * @return int Palauttaa verkossa olevien solmujen määrän.
     */
    public int solmujenMaara() {
        return this.koko * this.koko;
    }

    /**
     * @return String Palauttaa verkon nimen.
     */
    public String getNimi() {
        return this.nimi;
    }

    /**
     * @return int Palauttaa verkon koon.
     */
    public int getKoko() {
        return this.koko;
    }

    /**
     * @return Solmu[][] Palauttaa verkon solmut matriisitaulukkona.
     */
    public Solmu[][] getSolmut() {
        return this.solmut;
    }

    /**
     * @return ArrayList Palauttaa tekstimuotoisen kuvauksen reitistä tulostusta varten.
     */
    public ArrayList<String> getReitilla() {
        return reitti;
    }

    /**
     * @param reitilla Lisää reittikuvaukseen tiedon solmusta ja sen etäisyydestä reitin alkuun.
     */
    public void lisaaReitti(String reitilla) {
        this.reitti.add(reitilla);
    }

}
