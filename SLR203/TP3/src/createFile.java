import java.io.*;

public class createFile {
    public static void main(String[] args) throws Exception {
        //création du fichier texte
        Writer out = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("file.txt"), "UTF-8"));
        try {
            out.write("C'est un fichier encodé en UTF-8!");
        } finally {
            out.close();
        }
//fin création du fichier texte
//création du fichier binaire
        int i = 99;
        DataOutputStream os = new DataOutputStream(new FileOutputStream("file.bin"));
        os.writeInt(i);
        os.close();
//fin création du fichier binaire

    }
}
