public class PhilosopheSol2 implements Runnable {

    private Object fourchetteG;
    private Object fourchetteD;
    private int PhiloID;
    private ArdoiseSol2 ardo;

    public PhilosopheSol2(Object fourchetteG, Object fourchetteD, int PhiloID, ArdoiseSol2 ardo) {
        this.fourchetteG = fourchetteG;
        this.fourchetteD = fourchetteD;
        this.PhiloID = PhiloID;
        this.ardo = ardo;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep((long) (Math.random()*256));
            } catch (Exception e) {
                e.printStackTrace();
            }
            ardo.setArdoise(PhiloID, ArdoiseSol2.States.AFFAME);
            while (ardo.checkArdoise(PhiloID)){
                synchronized (fourchetteG){
                    System.out.println(Thread.currentThread().getName()+" prend la fourchette à gauche");

                    synchronized (fourchetteD) {
                        System.out.println(Thread.currentThread().getName()+" prend la fourchette à droit");
                        System.out.println(Thread.currentThread().getName()+" commence à manger");
                        ardo.setArdoise(PhiloID, ArdoiseSol2.States.MANGER);
                        try {
                            Thread.sleep((long) (Math.random()*256));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }

                System.out.println(Thread.currentThread().getName()+" a bien mangé et commence à penser");
                ardo.setArdoise(PhiloID, ArdoiseSol2.States.PENSER);
                try {
                    Thread.sleep((long) (Math.random()*256));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ardo.setArdoise(PhiloID, ArdoiseSol2.States.AFFAME);
            }
        }
    }
}
