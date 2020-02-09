import com.google.gson.*;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetMessages {


    public static void main(String[] args) throws IOException{

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://httpbin.org/get?message1=bonjour&message2=au+revoir&mess+age+3=special+" +
                        "character%3A+%22+%27+%2F+%5C+%25+%7E+%21+%40+%23+%24+%25+%5E+%26+*+%28+%29+etc..." +
                        "&m+e+s+s+a+g+e+4=C%27est+l%27%C3%A9t%C3%A9+%C3%A0+Paris%21")
                .build();

        /*Gson gson = new GsonBuilder()
                .registerTypeAdapter(Messages.class, new MessagesDeserializer())
                .create();*/

        try(Response response = client.newCall(request).execute()) {
            ArgsWithMessages argsWithMessages = new Gson().fromJson(response.body().string(), ArgsWithMessages.class);
            Messages messages = argsWithMessages.getArgs();
            System.out.println(messages.toString());
        }
    }


    /*public static class MessagesDeserializer implements JsonDeserializer<Messages> {
        @Override
        public Messages deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

            JsonObject jsonObject = json.getAsJsonObject();

            JsonElement jsonArgs = jsonObject.get("args");

            Messages messages = new Messages(jsonMessage1.getAsString(), jsonMessage2.getAsString(),
                    jsonMessage3.getAsString(), jsonMessage4.getAsString());

            return messages;
        }
    }*/
}
