public class CinqPhilo {

    public static void main(String[] args) {

        Object fourchette0 = new Object();
        Object fourchette1 = new Object();
        Object fourchette2 = new Object();
        Object fourchette3 = new Object();
        Object fourchette4 = new Object();

        Thread philo0 = new Thread(new MangerEtPenser(fourchette0,fourchette1),"Aristotle");
        Thread philo1 = new Thread(new MangerEtPenser(fourchette1,fourchette2),"Plato");
        Thread philo2 = new Thread(new MangerEtPenser(fourchette2,fourchette3),"Confucius");
        Thread philo3 = new Thread(new MangerEtPenser(fourchette3,fourchette4),"Descartes");
        Thread philo4 = new Thread(new MangerEtPenser(fourchette4,fourchette0),"Epicurus");

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
