import java.io.PrintStream;

public class PhilosopheSol3 extends Thread{

    private CouvertSol3 fourchetteG;
    private CouvertSol3 fourchetteD;
    private PrintStream ps;
    private int nom_des_fois;
    private String name;

    public PhilosopheSol3(CouvertSol3 fourchetteG, CouvertSol3 fourchetteD, PrintStream ps, String name) {
        this.fourchetteG = fourchetteG;
        this.fourchetteD = fourchetteD;
        this.ps = ps;
        this.nom_des_fois = 0;
        this.name = name;
    }
    
    public String obtenirName(){
        return this.name;
    }

    public int obtenirNum(){
        return this.nom_des_fois;
    }

    @Override
    public void run() {
        while(true){
            while ((fourchetteG.getPropritaire() == this.name) && (fourchetteD.getPropritaire() == this.name) && (fourchetteG.estSale() == false) && (fourchetteD.estSale() == false)){
                synchronized (fourchetteG){
                    ps.println(this.name+" prend la fourchette à gauche");
                    synchronized (fourchetteD) {
                        ps.println(this.name+" prend la fourchette à droite");
                        ps.println(this.name+" commence à manger");
                        this.nom_des_fois += 1;
                        try {
                            Thread.sleep((long) (Math.random()*256));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    fourchetteG.faireSale(true);
                    fourchetteD.faireSale(true);
                }

                ps.println(this.name+" a bien mangé et commence à penser");
                try {
                    Thread.sleep((long) (Math.random()*256));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            while((fourchetteG.getPropritaire() != this.name) && (fourchetteG.demande == false)){
                fourchetteG.setDemandeur(this.name);
            }
            while((fourchetteD.getPropritaire() != this.name) && (fourchetteD.demande == false)){
                fourchetteD.setDemandeur(this.name);
            }
            while((fourchetteG.getPropritaire() == this.name) && (fourchetteG.estSale() == true) && (fourchetteG.demande == true)){
                fourchetteG.faireSale(false);
                fourchetteG.setPropritaire(fourchetteG.demandeur);
            }
            while((fourchetteD.getPropritaire() == this.name) && (fourchetteD.estSale() == true) && (fourchetteD.demande == true)){
                fourchetteD.faireSale(false);
                fourchetteD.setPropritaire(fourchetteD.demandeur);
            }
            while((fourchetteG.getPropritaire() == this.name) && (fourchetteG.demandeur == this.name)){
                fourchetteG.clearDemandeur();
            }
            while((fourchetteD.getPropritaire() == this.name) && (fourchetteD.demandeur == this.name)){
                fourchetteD.clearDemandeur();
            }
        }
    }

}
