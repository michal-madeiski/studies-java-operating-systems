import java.util.ArrayList;
import java.util.List;

public class Algorytmy {
    private List<Proces> listaProcesow;

    public Algorytmy(ArrayList<Proces> listaProcesow) {
        this.listaProcesow = listaProcesow;
    }

    public List<Proces> getListaProcesow() {
        return listaProcesow;
    }

    public void setListaProcesow(List<Proces> listaProcesow) {
        this.listaProcesow = listaProcesow;
    }

    public double FCFS() {
        double calkowityCzasOczekiwania = 0;
        ArrayList<Proces> listaProcesowPom1 = new ArrayList<>();

        for (int i = 0; i < listaProcesow.size(); i++) {
            listaProcesowPom1.add(new Proces(listaProcesow.get(i).getNrProcesu(), listaProcesow.get(i).getDlugoscFazy(),
                    listaProcesow.get(i).getMomentZgloszenia(), listaProcesow.get(i).getCzasOczekiwania(),
                    listaProcesow.get(i).getCzasPozostaly()));
        }
        listaProcesowPom1.sort(ComparatoryProcesow.ComparatorMomentZgloszenia);

        double aktualnyCzasDoZakonczeniaTrwajacychProcesow = listaProcesowPom1.get(0).getDlugoscFazy();

        for (int i = 1; i < listaProcesowPom1.size(); i++) {
            if (aktualnyCzasDoZakonczeniaTrwajacychProcesow <= listaProcesowPom1.get(i).getMomentZgloszenia()) {
                aktualnyCzasDoZakonczeniaTrwajacychProcesow = listaProcesowPom1.get(i).getMomentZgloszenia() + listaProcesowPom1.get(i).getDlugoscFazy();
            } else {
                calkowityCzasOczekiwania += (aktualnyCzasDoZakonczeniaTrwajacychProcesow - listaProcesowPom1.get(i).getMomentZgloszenia());
                aktualnyCzasDoZakonczeniaTrwajacychProcesow += listaProcesowPom1.get(i).getDlugoscFazy();
            }
        }

        return calkowityCzasOczekiwania/listaProcesow.size();
    }

    public double SJF() {
        double calkowityCzasOczekiwania = 0;
        double osCzasu = 0;

        ArrayList<Proces> listaProcesowPom2 = new ArrayList<>();
        ArrayList<Proces> kolejka = new ArrayList<>();
        ArrayList<Proces> zrealizowane = new ArrayList<>();

        for (int i = 0; i < listaProcesow.size(); i++) {
            listaProcesowPom2.add(new Proces(listaProcesow.get(i).getNrProcesu(), listaProcesow.get(i).getDlugoscFazy(),
                    listaProcesow.get(i).getMomentZgloszenia(), listaProcesow.get(i).getCzasOczekiwania(),
                    listaProcesow.get(i).getCzasPozostaly()));
        }
        listaProcesowPom2.sort(ComparatoryProcesow.ComparatorMomentZgloszenia);

        zrealizowane.add(listaProcesowPom2.get(0));
        osCzasu += listaProcesowPom2.get(0).getDlugoscFazy();
        calkowityCzasOczekiwania += listaProcesowPom2.get(0).getMomentZgloszenia();
        listaProcesowPom2.remove(0);

        while (zrealizowane.size() != listaProcesow.size()) {
            int licznik = 0;
            for (int i = 0; i < listaProcesowPom2.size(); i++) {
                if (osCzasu >= listaProcesowPom2.get(i).getMomentZgloszenia()) {
                    kolejka.add(listaProcesowPom2.get(i));
                    licznik ++;
                }
            }

            for (int i = 0; i < licznik; i++) {
                listaProcesowPom2.remove(0);
            }

            if (kolejka.size() != 0) {
                kolejka.sort(ComparatoryProcesow.ComparatorDlugoscFazy);
                zrealizowane.add(kolejka.get(0));
                calkowityCzasOczekiwania += osCzasu - kolejka.get(0).getMomentZgloszenia();
                osCzasu += kolejka.get(0).getDlugoscFazy();
                kolejka.remove(0);
            } else {
                calkowityCzasOczekiwania += listaProcesowPom2.get(0).getMomentZgloszenia() - osCzasu;
                osCzasu = listaProcesowPom2.get(0).getMomentZgloszenia() + listaProcesowPom2.get(0).getDlugoscFazy();
                zrealizowane.add(listaProcesowPom2.get(0));
                listaProcesowPom2.remove(0);
            }
        }

        return calkowityCzasOczekiwania/listaProcesow.size();
    }

    public double SRTF() {
        double calkowityCzasOczekiwania = 0;
        double czasWykonaniaProcesow = 0;

        for (Proces proces : listaProcesow) {
            czasWykonaniaProcesow += proces.getDlugoscFazy();
        }

        Proces aktualny;

        ArrayList<Proces> listaProcesowPom3 = new ArrayList<>();
        ArrayList<Proces> oczekujace = new ArrayList<>();

        for (int i = 0; i < listaProcesow.size(); i++) {
            listaProcesowPom3.add(new Proces(listaProcesow.get(i).getNrProcesu(), listaProcesow.get(i).getDlugoscFazy(),
                    listaProcesow.get(i).getMomentZgloszenia(), listaProcesow.get(i).getCzasOczekiwania(),
                    listaProcesow.get(i).getCzasPozostaly()));
        }
        listaProcesowPom3.sort(ComparatoryProcesow.ComparatorMomentZgloszenia);

        aktualny = listaProcesowPom3.get(0);
        //calkowityCzasOczekiwania += listaProcesowPom3.get(0).getMomentZgloszenia();
        listaProcesowPom3.remove(0);

        for (int i = 0; i < czasWykonaniaProcesow; i++) {
            int licznik = 0;
            for (int j = 0; j < listaProcesowPom3.size(); j++) {
                if (listaProcesowPom3.get(j).getMomentZgloszenia() == i) {
                    oczekujace.add(listaProcesowPom3.get(j));
                    licznik ++;
                }
            }

            for (int k = 0; k < licznik; k++) {
                listaProcesowPom3.removeFirst();
            }

            oczekujace.sort(ComparatoryProcesow.ComparatorDlugoscFazy);
            //aktualny.setDlugoscFazy(aktualny.getDlugoscFazy() - 1);

            if (oczekujace.size() > 0) {
                if (oczekujace.get(0).getDlugoscFazy() < aktualny.getDlugoscFazy()) {
                    Proces pomProc = aktualny;
                    pomProc.setDlugoscFazy(aktualny.getDlugoscFazy());
                    aktualny = oczekujace.get(0);
                    oczekujace.remove(0);
                    aktualny.setDlugoscFazy(aktualny.getDlugoscFazy() - 1);
                    oczekujace.add(pomProc);
                    calkowityCzasOczekiwania += oczekujace.size();
                    oczekujace.sort(ComparatoryProcesow.ComparatorDlugoscFazy);
                } else {
                    if (aktualny.getDlugoscFazy() > 0) {
                        aktualny.setDlugoscFazy(aktualny.getDlugoscFazy() - 1);
                        calkowityCzasOczekiwania += oczekujace.size();
                    } else {
                        aktualny = oczekujace.get(0);
                        oczekujace.remove(0);
                        if (aktualny.getDlugoscFazy() > 0) {
                            aktualny.setDlugoscFazy(aktualny.getDlugoscFazy() - 1);
                            calkowityCzasOczekiwania += oczekujace.size();
                        }
                    }
                }
            } else {
                if (aktualny.getDlugoscFazy() > 0) {
                    aktualny.setDlugoscFazy(aktualny.getDlugoscFazy() - 1);
                }
            }
        }
        return calkowityCzasOczekiwania / listaProcesow.size();
    }

    public double RR (int kwant) {
        double calkowityCzasOczekiwania = 0;

        ArrayList<Proces> listaProcesowPom4 = new ArrayList<>();
        for (int i = 0; i < listaProcesow.size(); i++) {
            listaProcesowPom4.add(new Proces(listaProcesow.get(i).getNrProcesu(), listaProcesow.get(i).getDlugoscFazy(),
                    listaProcesow.get(i).getMomentZgloszenia(), listaProcesow.get(i).getCzasOczekiwania(),
                    listaProcesow.get(i).getCzasPozostaly()));
        }
        listaProcesowPom4.sort(ComparatoryProcesow.ComparatorMomentZgloszenia);

        int osCzasu = 0;
        int prevOsCzasu = -1;
        int zrealizowane  = 0;
        int oczekujace = -1;
        Proces aktualny = listaProcesowPom4.getFirst();


        while (zrealizowane < listaProcesowPom4.size()) {
            for (Proces proces : listaProcesowPom4) {
                if (proces.getMomentZgloszenia() <= osCzasu && proces.getMomentZgloszenia() > prevOsCzasu) {
                    oczekujace++;
                }
            }

            if (aktualny.getDlugoscFazy() - kwant > 0) {
                prevOsCzasu = osCzasu;
                osCzasu += kwant;
                aktualny.setDlugoscFazy(aktualny.getDlugoscFazy() - kwant);
                aktualny.setMomentZgloszenia(-2);
                listaProcesowPom4.removeFirst();
                listaProcesowPom4.add(aktualny);
                aktualny = listaProcesowPom4.getFirst();
                if (oczekujace != -1) calkowityCzasOczekiwania += oczekujace*kwant;
                //oczekujace++;
            }
            else {
                if (oczekujace != -1) {
                    calkowityCzasOczekiwania += oczekujace*aktualny.getDlugoscFazy();
                    oczekujace--;
                }
                prevOsCzasu = osCzasu;
                osCzasu += aktualny.getDlugoscFazy();
                listaProcesowPom4.removeFirst();
                if (!listaProcesowPom4.isEmpty()) aktualny = listaProcesowPom4.getFirst();
                zrealizowane++;
            }
        }

        return calkowityCzasOczekiwania / listaProcesow.size();
    }
}
