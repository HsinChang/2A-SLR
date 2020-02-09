import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpGet {
    OkHttpClient client = new OkHttpClient();

    String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public static void main(String[] args) throws IOException {
        OkHttpGet get = new OkHttpGet();
        String response = get.run("https://httpbin.org/get?message1=bonjour&message2=au+revoir&mess+age+3=special+" +
                "character%3A+%22+%27+%2F+%5C+%25+%7E+%21+%40+%23+%24+%25+%5E+%26+*+%28+%29+etc..." +
                "&m+e+s+s+a+g+e+4=C%27est+l%27%C3%A9t%C3%A9+%C3%A0+Paris%21");
        System.out.println(response);
    }
}