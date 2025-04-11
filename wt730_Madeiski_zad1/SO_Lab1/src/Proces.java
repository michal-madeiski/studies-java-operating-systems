public class Proces {
    private int nrProcesu;
    private int dlugoscFazy;
    private int momentZgloszenia;
    private int czasOczekiwania;
    private int czasPozostaly;

    public Proces(int nrProcesu, int dlugoscFazy, int momentZgloszenia, int czasOczekiwania, int czasPozostaly) {
        this.nrProcesu = nrProcesu;
        this.dlugoscFazy = dlugoscFazy;
        this.momentZgloszenia = momentZgloszenia;
        this.czasOczekiwania = czasOczekiwania;
        this.czasPozostaly = czasPozostaly;
    }

    public int getNrProcesu() {
        return nrProcesu;
    }

    public void setNrProcesu(int nrProcesu) {
        this.nrProcesu = nrProcesu;
    }

    public int getDlugoscFazy() {
        return dlugoscFazy;
    }

    public void setDlugoscFazy(int dlugoscFazy) {
        this.dlugoscFazy = dlugoscFazy;
    }

    public int getMomentZgloszenia() {
        return momentZgloszenia;
    }

    public void setMomentZgloszenia(int momentZgloszenia) {
        this.momentZgloszenia = momentZgloszenia;
    }

    public int getCzasOczekiwania() {
        return czasOczekiwania;
    }

    public void setCzasOczekiwania(int czasOczekiwania) {
        this.czasOczekiwania = czasOczekiwania;
    }

    public int getCzasPozostaly() {
        return czasPozostaly;
    }

    public void setCzasPozostaly(int czasPozostaly) {
        this.czasPozostaly = czasPozostaly;
    }

    public String toString() {
        return "nrProcesu: " + nrProcesu + " dlugoscFazy: " + dlugoscFazy + " momentZgloszenia: " + momentZgloszenia +
        " czasOczekiwania: " + czasOczekiwania + " czasPozostaly: " + czasPozostaly;
    }
}
