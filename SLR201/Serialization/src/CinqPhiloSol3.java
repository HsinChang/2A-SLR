import java.io.IOException;

public class CinqPhiloSol3 extends Thread {

    /*private CouvertSol3 fourchette0;
    private CouvertSol3 fourchette1;
    private CouvertSol3 fourchette2;
    private CouvertSol3 fourchette3;
    private CouvertSol3 fourchette4;

    private PhilosopheSol3 philo0;
    private PhilosopheSol3 philo1;
    private PhilosopheSol3 philo2;
    private PhilosopheSol3 philo3;
    private PhilosopheSol3 philo4;

   private CinqPhiloSol3() {
        this.fourchette0 = new CouvertSol3(false,0);
        this.fourchette1 = new CouvertSol3(false,1);
        this.fourchette2 = new CouvertSol3(false,2);
        this.fourchette3 = new CouvertSol3(false,3);
        this.fourchette4 = new CouvertSol3(false,4);

        this.philo0 = new PhilosopheSol3(fourchette0,fourchette1,"Aristotle");
        this.philo1 = new PhilosopheSol3(fourchette1,fourchette2,"Plato");
        this.philo2 = new PhilosopheSol3(fourchette2,fourchette3,"Confucius");
        this.philo3 = new PhilosopheSol3(fourchette3,fourchette4,"Descartes");
        this.philo4 = new PhilosopheSol3(fourchette4,fourchette0,"Epicurus");
    }
*/
    public static void main(String[] args) throws Exception {

        Runtime.getRuntime().addShutdownHook(new CinqPhiloSol3());

        CouvertSol3 fourchette0 = new CouvertSol3(false,0);
        CouvertSol3 fourchette1 = new CouvertSol3(false,1);
        CouvertSol3 fourchette2 = new CouvertSol3(false,2);
        CouvertSol3 fourchette3 = new CouvertSol3(false,3);
        CouvertSol3 fourchette4 = new CouvertSol3(false,4);

        Thread philo0 = new PhilosopheSol3(fourchette0,fourchette1,"Aristotle");
        Thread philo1 = new PhilosopheSol3(fourchette1,fourchette2,"Plato");
        Thread philo2 = new PhilosopheSol3(fourchette2,fourchette3,"Confucius");
        Thread philo3 = new PhilosopheSol3(fourchette3,fourchette4,"Descartes");
        Thread philo4 = new PhilosopheSol3(fourchette4,fourchette0,"Epicurus");

        fourchette0.initializePropritaire(philo4.getName(), philo0.getName());
        fourchette1.initializePropritaire(philo0.getName(), philo1.getName());
        fourchette2.initializePropritaire(philo1.getName(), philo2.getName());
        fourchette3.initializePropritaire(philo2.getName(), philo3.getName());
        fourchette4.initializePropritaire(philo3.getName(), philo4.getName());

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
        System.out.println("MMP"+);
    }
}
