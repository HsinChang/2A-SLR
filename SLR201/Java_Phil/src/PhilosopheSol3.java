public class PhilosopheSol3 implements Runnable{

    private CouvertSol3 fourchetteG;
    private CouvertSol3 fourchetteD;

    public PhilosopheSol3(CouvertSol3 fourchetteG, CouvertSol3 fourchetteD) {
        this.fourchetteG = fourchetteG;
        this.fourchetteD = fourchetteD;
    }

    @Override
    public void run() {
        while(true){
            while ((fourchetteG.getPropritaire() == Thread.currentThread().getName()) && (fourchetteD.getPropritaire() == Thread.currentThread().getName()) && (fourchetteG.estSale() == false) && (fourchetteD.estSale() == false)){
                synchronized (fourchetteG){
                    System.out.println(Thread.currentThread().getName()+" prend la fourchette à gauche");
                    synchronized (fourchetteD) {
                        System.out.println(Thread.currentThread().getName()+" prend la fourchette à droite");
                        System.out.println(Thread.currentThread().getName()+" commence à manger");
                        try {
                            Thread.sleep((long) (Math.random()*256));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    fourchetteG.faireSale(true);
                    fourchetteD.faireSale(true);
                }

                System.out.println(Thread.currentThread().getName()+" a bien mangé et commence à penser");
                try {
                    Thread.sleep((long) (Math.random()*256));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            while((fourchetteG.getPropritaire() != Thread.currentThread().getName()) && (fourchetteG.demande == false)){
                fourchetteG.setDemandeur(Thread.currentThread().getName());
            }
            while((fourchetteD.getPropritaire() != Thread.currentThread().getName()) && (fourchetteD.demande == false)){
                fourchetteD.setDemandeur(Thread.currentThread().getName());
            }
            while((fourchetteG.getPropritaire() == Thread.currentThread().getName()) && (fourchetteG.estSale() == true) && (fourchetteG.demande == true)){
                fourchetteG.faireSale(false);
                fourchetteG.setPropritaire(fourchetteG.demandeur);
            }
            while((fourchetteD.getPropritaire() == Thread.currentThread().getName()) && (fourchetteD.estSale() == true) && (fourchetteD.demande == true)){
                fourchetteD.faireSale(false);
                fourchetteD.setPropritaire(fourchetteD.demandeur);
            }
            while((fourchetteG.getPropritaire() == Thread.currentThread().getName()) && (fourchetteG.demandeur == Thread.currentThread().getName())){
                fourchetteG.clearDemandeur();
            }
            while((fourchetteD.getPropritaire() == Thread.currentThread().getName()) && (fourchetteD.demandeur == Thread.currentThread().getName())){
                fourchetteD.clearDemandeur();
            }
        }
    }

}
