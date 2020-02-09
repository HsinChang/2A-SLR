import java.io.*;

public class DataUnserializer implements Serializable{

    private CouvertSol3 object;

    public void Dataunserializer(){
    }

    public void unserialise() {
        try {
            FileInputStream fin = new FileInputStream("file.ser");
            ObjectInputStream in = new ObjectInputStream(fin);

            object = (CouvertSol3)in.readObject();

            in.close();
            fin.close();
        } catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}