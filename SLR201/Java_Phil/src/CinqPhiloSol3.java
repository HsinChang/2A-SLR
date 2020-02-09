public class CinqPhiloSol3 {

    public static void main(String[] args) throws Exception {

        CouvertSol3 fourchette0 = new CouvertSol3(false,0);
        CouvertSol3 fourchette1 = new CouvertSol3(false,1);
        CouvertSol3 fourchette2 = new CouvertSol3(false,2);
        CouvertSol3 fourchette3 = new CouvertSol3(false,3);
        CouvertSol3 fourchette4 = new CouvertSol3(false,4);

        Thread philo0 = new Thread(new PhilosopheSol3(fourchette0,fourchette1),"Aristotle");
        Thread philo1 = new Thread(new PhilosopheSol3(fourchette1,fourchette2),"Plato");
        Thread philo2 = new Thread(new PhilosopheSol3(fourchette2,fourchette3),"Confucius");
        Thread philo3 = new Thread(new PhilosopheSol3(fourchette3,fourchette4),"Descartes");
        Thread philo4 = new Thread(new PhilosopheSol3(fourchette4,fourchette0),"Epicurus");

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

}
