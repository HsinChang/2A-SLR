import com.google.gson.Gson;
import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetIP {
    public static void main(String[] args) throws IOException{
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://httpbin.org/ip")
                .build();

        try (Response response = client.newCall(request).execute()) {
            IP ip = new Gson().fromJson(response.body().string(), IP.class);
            System.out.println(ip.toString());
        }

    }
}
