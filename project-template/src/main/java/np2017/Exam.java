package np2017;


/**
 * Dies ist eine unvollständige Implementierung der Exam Klasse aus
 * der Aufgabenstellung. Sie bietet die öffentlichen Methoden correct
 * und finish an, sowie einige privaten Methoden, die dafür sorgen,
 * dass tatsächlich Rechenleistung und Speicher während der Methoden
 * correct und finish verbraucht wird. Dies erleichtert es Ihnen die
 * Effizienz Ihrer Implementierung auf verschiedenen (Mulit-Core)
 * Architekturen zu testen.
 *
 * Sie können diese Klasse nach belieben verändern bzw. erweitern. Sie sollten
 * an den angegebenen Stellen jedoch keine Veränderung vornehmen.
 */
public class Exam {

    /**
     * Verändert die Dauer der Methode correct.
     */
    private static final int SCALE_CORRECT = 1000000;
    /**
     * Verändert die Dauer der Methode finish.
     */
    private static final int SCALE_FINISH = 100000;

    /**
     * Gibt für jede Aufgabe an, ob sie bereits korrigiert wurde.
     */
    private final boolean[] corrected;

    /**
     * @param exerciseCount Die Anzahl der Aufgaben in der Prüfung.
     */
    public Exam(final int exerciseCount) {
        this.corrected = new boolean[exerciseCount];
        for (int i = 0; i < this.corrected.length; i++) {
            corrected[i] = false;
        }
    }

    /**
     * @param exercise Die Nummer der Aufgabe.
     * @return true, wenn die Aufgabe schon korrigiert wurde, false wenn nicht.
     */
    public boolean isCorrected(final int exercise) {

        return this.corrected[exercise];

    }


    /**
     * @param exercise Die Aufgabennummer, die korrigiert werden soll.
     * @return true, falls die Aufgabe gerade korrigiert wurde; false wenn sie schon korrigiert war.
     */
    public boolean correct(final int exercise) {
      if (this.isCorrected(exercise)) return false;  //k 
        Exam.doCorrection();

        return true;
    }


    /**
     * Der letzte Schritt.
     */
    public void finish() {

        Exam.doFinish();

    }
    
    
    public boolean isAllCorrected()  //k
    {
    	for(boolean b:this.corrected) {if(!b) return false;}
    	return true;
    }


    // Ändern Sie NICHT die Methoden unterhalb dieser Zeile!

    /* Rechenleistung verschwenden!
     * (DO NOT CHANGE THIS METHOD)
     */
    private static int xorShift(int y) {
        y ^= y << 6;
        y ^= y >>> 21;
        y ^= y << 7;

        return Math.abs(y);
    }

    /* Rechenleistung verschwenden!
     * (DO NOT CHANGE THIS METHOD)
     */
    private static int mod(int x, int y) {
        x = Math.abs(x);
        y = Math.abs(y);

        while (x >= y)
            x = x - y;

        return x;
    }

    /* Rechenleistung verschwenden!
     * (DO NOT CHANGE THIS METHOD)
     */
    private static boolean eatCookies(int n, int s) {
        int y = (xorShift(n)) % s;
        int test = 0;

        for (int i = 2; i < y; i++)
            if (mod(y, i) == 0)
                test = test + 1;

        return test > 0;
    }

    /* Rechenleistung verschwenden!
     * (DO NOT CHANGE THIS METHOD)
     */
    public static void doCorrection() {
        int i = Thread.currentThread().hashCode();
        eatCookies(i * (i + 12345), SCALE_CORRECT);
    }

    /* Rechenleistung verschwenden!
     * (DO NOT CHANGE THIS METHOD)
     */
    public static void doFinish() {
        int i = Thread.currentThread().hashCode();
        eatCookies(i * (i + 12345), SCALE_FINISH);
    }

}
