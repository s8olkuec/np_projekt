package np2017;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;



import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * Dies ist eine unvollständige Implementierung der Professor Klasse aus
 * der Aufgabenstellung.
 *
 * Sie können diese Klasse nach Belieben verändern bzw. erweitern.
 */
public class Professor {

    /**
     * Default-Anzahl der Exams.
     */
    public static final int DEFAULT_EXAMS = 100;
    /**
     * Default-Anzahl der Aufgaben.
     */
    public static final int DEFAULT_EXERCISES = 12;

    /**
     * Enthält die Tutoren-Threads.
     */
    private final Tutor[] tutors;
    
    
    private final Stapel[] exstap; //k
    

    /**
     * Anzahl an Aufgaben in der Klausur.
     */
    private final int exerciseCount;


    /**
     * @param exerciseCount Die Anzahl der Aufgaben in der Klausur.
     */
    public Professor(final int exerciseCount) {
        this.exerciseCount = exerciseCount;
        tutors = new Tutor[exerciseCount];
        exstap= new Stapel[exerciseCount]; //k
        
    }


    /**
     * Das ist die eigentliche Hauptmethode, die während der Korrektur ausgeführt wird.
     *
     * @param exams Die Klausuren für die Tutoren
     */
    public void superviseCorrection(final Collection<Exam> exams) {


        setUpTutors(exams);


    }

    /**
     * Hier starten wir alle Tutoren und verteilen die übergebenen Klausuren.
     *
     * @param exams Die Klausuren für die Tutoren
     */
    private void setUpTutors(final Collection<Exam> exams) {

    	Iterator<Exam> it= exams.iterator();
    	int s=exams.size();
    	
        for (int i = 0; i < exerciseCount; i++) { 
        
        	exstap[i]= new Stapel(); 
            int a= s/(exerciseCount-i);
            s=s-a;
        	for(int j=0;j<a;j++) {exstap[i].innllegen(it.next());} //k verteilt die klausuren auf die exstap[]
        	
        	
        }
    	
    	
        for (int i = 0; i < exerciseCount; i++) {
            tutors[i] = new Tutor(this, i,exstap[i],exstap[(i+1)%exerciseCount]);
            
            
        }


        for (Tutor a : tutors) {        	
            a.start();
        }
    }


    /**
     * Gibt die Hilfeseite aus und beendet anschließend das Programm.
     *
     * @param options  Alle Command-Line-Options die ausgegeben werden sollen.
     * @param exitcode Der Exitcode beim Beenden.
     */
    private static void printHelpAndExit(final Options options, final int exitcode) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("java -jar <klausur.jar>", options, true);
        System.exit(exitcode);
    }


    /**
     * Es ist möglich dem Programm per Komandozeilen-Parameter die Anzahl der
     * Klausuren und die Anzahl der Aufgaben zu übergeben. Siehe dazu die
     * Hilfe, die mit "-h" ausgegeben wird.
     * 
     * Am Ende wird ausgegeben, wie viele Millisekunden die Korrektur gebraucht hat.
     *
     * @param argv die Komandozeilen-Parameter
     */
    public static void main(final String[] argv) {
        Options options = new Options();
        options.addOption(Option.builder("e")
                .hasArg()
                .argName("number")
                .longOpt("exams")
                .desc("number of exams to correct (default: " + Integer.toString(DEFAULT_EXAMS) + ")")
                .type(Integer.TYPE)
                .build()
        );
        options.addOption(Option.builder("t")
                .hasArg()
                .argName("number")
                .longOpt("tasks")
                .desc("number of tasks on each exam (default: " + Integer.toString(DEFAULT_EXERCISES) + ")")
                .type(Integer.TYPE)
                .build()
        );
        options.addOption(Option.builder("h")
                .longOpt("help")
                .desc("print this help")
                .build()
        );

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, argv);
        } catch (ParseException exp) {
            System.out.println("Unexcepted exception: " + exp.getMessage());
            System.exit(1);
        }

        if (cmd.hasOption("help")) {
            printHelpAndExit(options, 0);
        }

        int examCount = DEFAULT_EXAMS;
        if (cmd.hasOption("exams")) {
            String argument = cmd.getOptionValue("exams");
            if (argument != null) {
                try {
                    examCount = Integer.parseInt(argument);
                } catch (NumberFormatException exp) {
                    printHelpAndExit(options, 1);
                }
            }
        }

        int excCount = DEFAULT_EXERCISES;
        if (cmd.hasOption("tasks")) {
            String argument = cmd.getOptionValue("tasks");
            if (argument != null) {
                try {
                    excCount = Integer.parseInt(argument);
                } catch (NumberFormatException exp) {
                    printHelpAndExit(options, 1);
                }
            }
        }

        System.out.println("Correction of " + examCount + " exams, each with " + excCount + " exercises.");

        // Die Teilnehmer "schreiben die Klausuren"
        LinkedList<Exam> exams = new LinkedList<Exam>();
        for (int i = 0; i < examCount; i++) {
            exams.push(new Exam(excCount));
        }

        Professor prof = new Professor(excCount);

        long start = System.currentTimeMillis();
        prof.superviseCorrection(exams);
        System.out.println("Time: " + (System.currentTimeMillis() - start) + "ms");
    }

}
