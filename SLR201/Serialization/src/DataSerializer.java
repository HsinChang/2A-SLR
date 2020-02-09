import java.io.*;

public class DataSerializer implements Serializable{

    private CouvertSol3 object;

    public DataSerializer(CouvertSol3 object){
        this.object = object;
    }

    public void serialise() {
        try {
            FileOutputStream fout = new FileOutputStream("file.ser");
            ObjectOutputStream out = new ObjectOutputStream(fout);

            out.writeObject(object);

            out.close();
            fout.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
