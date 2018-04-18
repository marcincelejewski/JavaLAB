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
public class Histogram {
private String indeks;
private float score;

public Histogram(String i, float sc)
{
    this.indeks=i;
    this.score=sc;
}

    public String getIndeks() {
        return indeks;
    }

    public void setIndeks(String indeks) {
        this.indeks = indeks;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

}
