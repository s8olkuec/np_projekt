package np2017;

import java.util.LinkedList;

/**
 * Dies ist eine unvollständige Implementierung der Tutor Klasse
 * aus der Aufgabenstellung.
 *
 * Sie können diese Klasse nach Belieben verändern bzw. erweitern.
 */
public class Tutor extends Thread {

    /**
     * Der Professor, dem Rückmeldung gegeben werden soll.
     */
    private final Professor prof;

    /**
     * Die Aufgabe, die dieser Tutor korrigieren wird.
     */
    private final int exercise;

    /**
     * Zu Debug-Zwecken.
     */
    private int finished;
    
    private Stapel rechts; //k
    private Stapel links; //k

    

    
    
    /**
     * Erstelle einen neuen Tutor.
     *
     * @param prof     Professor
     * @param exercise Aufgabe, die korrigiert wird.
     */
    public Tutor(final Professor prof, final int exercise,Stapel r,Stapel l) {
        this.prof = prof;
        this.exercise = exercise;
    
           this.rechts= r;
           this.links=l;  

            
        }
        	
        
    

    /**
     * Nimmt einen Klausur vom rechten Stapel.
     *
     * @return die Klausur, die vom Stapel genommen wurde.
     */
    protected Exam fetchNextExam() {
        
        return (this.rechts.nehmen());
    }


    /**
     * Der Lebensinhalt des Tutors: Er versucht bis zu seinem Tod Klausuren von seinem
     * rechten Stapel zu nehmen und in dieser eine spezifische Aufgabe zu korrigieren.
     * Wenn er eine Klausur erwischt hat, in der diese noch nicht korrigiert war, dann erhöht er
     * einen Counter. Wir empfehlen diesen Counter zu Debug-Zwecken zu benutzen und
     * approximativ festzustellen, ob jeder Tutor am Ende alle Klausuren genau einmal
     * korrigiert hat.
     */
    public void run() {
    	
        boolean done = false;

        while (!done)  {
            Exam exam = fetchNextExam();
            if (exam==null)break; //muss noch geändert werden

            if (exam.correct(exercise)) {
                // Die Aufgabe wurde noch nicht korrigiert
                finished++;
                if(exam.isAllCorrected()) {continue;} //todo: an den Professor weitergeben, continue um das nach linkslegen zu umgehen 
            }
            this.links.legen(exam);


        }
    }


    /**
     * Zu Debug-Zwecken.
     *
     * @return Die Anzahl der korrigierten Klausuren.
     */
    public int getFinished() {
        return finished;
    }

}
