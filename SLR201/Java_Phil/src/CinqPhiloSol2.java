public class CinqPhiloSol2 {

    public static void main(String[] args) {

        Object fourchette0 = new Object();
        Object fourchette1 = new Object();
        Object fourchette2 = new Object();
        Object fourchette3 = new Object();
        Object fourchette4 = new Object();
        ArdoiseSol2 ardo = new ArdoiseSol2();

        Thread philo0 = new Thread(new PhilosopheSol2(fourchette0,fourchette1,0, ardo),"Aristotle");
        Thread philo1 = new Thread(new PhilosopheSol2(fourchette1,fourchette2,1, ardo),"Plato");
        Thread philo2 = new Thread(new PhilosopheSol2(fourchette2,fourchette3,2, ardo),"Confucius");
        Thread philo3 = new Thread(new PhilosopheSol2(fourchette3,fourchette4,3, ardo),"Descartes");
        Thread philo4 = new Thread(new PhilosopheSol2(fourchette4,fourchette0,4, ardo),"Epicurus");

        philo0.start();
        philo1.start();
        philo2.start();
        philo3.start();
        philo4.start();

        try {philo0.join();} catch (Exception e){e.printStackTrace();}
        try {philo1.join();} catch (Exception e){e.printStackTrace();}
        try {philo2.join();} catch (Exception e){e.printStackTrace();}
        try {philo3.join();} catch (Exception e){e.printStackTrace();}
        try {philo4.join();} catch (Exception e){e.printStackTrace();}
    }

}
