import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * This example demonstrates the use of the {@link ResponseHandler} to simplify
 * the process of processing the HTTP response and releasing associated resources.
 */
public class ApachePostFiles {

    static final String FILE1_BIN = "file1.bin";
    static final String FILE2_BIN = "file2.bin";
    static final String TEXTFILENAME = "temp.txt";

    public final static void main(String[] args) throws Exception {

        new File("uploads").mkdirs();
//création du fichier texte


        Writer out = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("uploads/"+TEXTFILENAME), "UTF-8"));
        try {
            out.write("C'est un fichier encodé en UTF-8!");
        } finally {
            out.close();
        }
        File textfile = new File("uploads/"+TEXTFILENAME);
//fin création du fichier texte

//création du fichier binaire
        int i = 99;
        DataOutputStream os = new DataOutputStream(new FileOutputStream("uploads/"+FILE1_BIN));
        os.writeInt(i);
        os.close();
        File binaryfile1 = new File("uploads/"+FILE1_BIN);
        i = 98;
        os = new DataOutputStream(new FileOutputStream("uploads/"+FILE2_BIN));
        os.writeInt(i);
        os.close();
        File binaryfile2 = new File("uploads/"+FILE2_BIN);
//fin création du fichier binaire


        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost httppost = new HttpPost("http://httpbin.org/post?");

            FileBody fileBody1 = new FileBody(textfile, ContentType.DEFAULT_BINARY);
            FileBody fileBody2 = new FileBody(binaryfile1, ContentType.DEFAULT_BINARY);
            FileBody fileBody3 = new FileBody(binaryfile2, ContentType.DEFAULT_BINARY);
            StringBody stringBody1 = new StringBody("Message 1", ContentType.MULTIPART_FORM_DATA);
            StringBody stringBody2 = new StringBody("Message 2", ContentType.MULTIPART_FORM_DATA);
//
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.addPart("upfile", fileBody1);
            builder.addPart("binary1", fileBody2);
            builder.addPart("binary2", fileBody3);
            builder.addPart("text1", stringBody1);
            builder.addPart("text2", stringBody2);
            System.out.println("Executing request " + httppost.getRequestLine());
            HttpEntity entity = builder.build();

            httppost.setEntity(entity);

            // Create a custom response handler
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

                @Override
                public String handleResponse(
                        final HttpResponse response) throws ClientProtocolException, IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity) : null;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }

            };
            String responseBody = httpclient.execute(httppost, responseHandler);
            System.out.println("----------------------------------------");
            System.out.println(responseBody);
        } finally {
            httpclient.close();
        }
    }

}
