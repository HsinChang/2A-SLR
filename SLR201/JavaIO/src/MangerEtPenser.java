public class MangerEtPenser implements Runnable{
    private Object fourchette1;
    private Object fourchette2;

    public MangerEtPenser(Object fourchette1, Object fourchette2) {
        this.fourchette1= fourchette1;
        this.fourchette2 = fourchette2;

    }

    @Override
    public void run() {
        while (true) {
            synchronized (fourchette1){
                System.out.println(Thread.currentThread().getName()+" prend la première fourchette à côté");

                synchronized (fourchette2) {
                    System.out.println(Thread.currentThread().getName()+" prend la deuxième fourchette à côté");
                    System.out.println(Thread.currentThread().getName()+" commence à manger");
                    try {
                        Thread.sleep((long) (Math.random()*256));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

            System.out.println(Thread.currentThread().getName()+" a bien mangé et commence à penser");
            try {
                Thread.sleep((long) (Math.random()*256));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
