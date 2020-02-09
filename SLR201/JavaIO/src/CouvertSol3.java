public class CouvertSol3 {
    String propritaire;
    String demandeur;
    boolean demande;
    boolean sale;
    int couvert_ID;

    public CouvertSol3(boolean sale, int id) {
        this.sale = sale;
        this.couvert_ID = id;
        this.demande = false;
    }

    public synchronized void faireSale(boolean status){
        sale = status;
    }

    public synchronized boolean estSale(){
        return this.sale;
    }

    public synchronized void setPropritaire(String owner){
        this.propritaire = owner;
    }

    public synchronized String getPropritaire(){
        return propritaire;
    }

    public synchronized void setDemandeur(String request){
        this.demandeur = request;
        this.demande = true;
    }

    public synchronized void clearDemandeur(){
        this.demande = false;
        this.demandeur = null;
    }

    public synchronized void initializePropritaire(String str1, String str2) throws Exception {
        int c = str1.compareTo(str2);
        if(c < 0) this.propritaire = str1;
        if(c > 0) this.propritaire = str2;
        if(c == 0) throw new Exception("Putain ! C'set la même personne !");
    }

}
