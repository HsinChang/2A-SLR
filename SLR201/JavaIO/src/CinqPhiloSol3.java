import java.io.*;

public class CinqPhiloSol3 extends Thread{

    private static PhilosopheSol3 philo0 = null;
    private static PhilosopheSol3 philo1 = null;
    private static PhilosopheSol3 philo2 = null;
    private static PhilosopheSol3 philo3 = null;
    private static PhilosopheSol3 philo4 = null;
    private static PrintStream out;
    
    public static void main(String[] args) throws Exception {

        Runtime.getRuntime().addShutdownHook(new CinqPhiloSol3());

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Veuillez saisir le ficher pour le rapport:");
        String file_Name;
        file_Name = br.readLine();
        File f = new File(file_Name);
        out = new PrintStream(new FileOutputStream(f));

        //CinqPhiloSol3 philo = new CinqPhiloSol3();


        CouvertSol3 fourchette0 = new CouvertSol3(false,0);
        CouvertSol3 fourchette1 = new CouvertSol3(false,1);
        CouvertSol3 fourchette2 = new CouvertSol3(false,2);
        CouvertSol3 fourchette3 = new CouvertSol3(false,3);
        CouvertSol3 fourchette4 = new CouvertSol3(false,4);

        philo0 = new PhilosopheSol3(fourchette0, fourchette1, out,"Aristotle");
        philo1 = new PhilosopheSol3(fourchette1, fourchette2, out,"Plato");
        philo2 = new PhilosopheSol3(fourchette2, fourchette3, out,"Confucius");
        philo3 = new PhilosopheSol3(fourchette3, fourchette4, out,"Descartes");
        philo4 = new PhilosopheSol3(fourchette4, fourchette0, out,"Epicurus");

        fourchette0.initializePropritaire(philo4.obtenirName(), philo0.obtenirName());
        fourchette1.initializePropritaire(philo0.obtenirName(), philo1.obtenirName());
        fourchette2.initializePropritaire(philo1.obtenirName(), philo2.obtenirName());
        fourchette3.initializePropritaire(philo2.obtenirName(), philo3.obtenirName());
        fourchette4.initializePropritaire(philo3.obtenirName(), philo4.obtenirName());

        philo0.start();
        philo1.start();
        philo2.start();
        philo3.start();
        philo4.start();

        try {philo0.join();} catch (Exception e){ e.printStackTrace();}
        try {philo1.join();} catch (Exception e){ e.printStackTrace();}
        try {philo2.join();} catch (Exception e){ e.printStackTrace();}
        try {philo3.join();} catch (Exception e){ e.printStackTrace();}
        try {philo4.join();} catch (Exception e){ e.printStackTrace();}
    }

    @Override
    public void run() {

        out.println(philo0.obtenirName()+" a mangé "+ String.valueOf(philo0.obtenirNum())+" fois.");
        out.println(philo1.obtenirName()+" a mangé "+ String.valueOf(philo1.obtenirNum())+" fois.");
        out.println(philo2.obtenirName()+" a mangé "+ String.valueOf(philo2.obtenirNum())+" fois.");
        out.println(philo3.obtenirName()+" a mangé "+ String.valueOf(philo3.obtenirNum())+" fois.");
        out.println(philo4.obtenirName()+" a mangé "+ String.valueOf(philo4.obtenirNum())+" fois.");
    }
}
