/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pomoc;

/**
 *
 * @author Marcin
 */
public class Score {
    private int ndst;
    private int dst;
    private int db;
    private int bdb;
    private int cel;
public Score()
{
    ndst=0;
    dst=0;
    db=0;
    bdb=0;
    cel=0; 
}
    
    public int getNdst() {
        return ndst;
    }

    public void inkNdst() {
        this.ndst++;
    }

    public int getDst() {
        return dst;
    }

    public void inkDst() {
        this.dst++;
    }

    public int getDb() {
        return db;
    }

    public void inkDb() {
        this.db++;
    }

    public int getBdb() {
        return bdb;
    }

    public void inkBdb() {
        this.bdb++;
    }

    public int getCel() {
        return cel;
    }

    public void inkCel() {
        this.cel++;
    }

}
