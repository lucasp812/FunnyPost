package service;

import org.json.JSONArray;
import org.json.JSONObject;
import entite.Post;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WebService {

  public static List<Post> fetchPosts() {
    List<Post> posts = new ArrayList<>();
    try {
      URL url = new URL("https://jsonplaceholder.typicode.com/posts");
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setConnectTimeout(20000);
      connection.setReadTimeout(20000);
      connection.setUseCaches(true);
      connection.setRequestMethod("GET");
      connection.setRequestProperty("Accept", "application/json");
      connection.setRequestProperty("Content-Type", "application/json");

      int responseCode = connection.getResponseCode();
      if (responseCode == 200) {
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
          response.append(inputLine);
        }
        in.close();

        JSONArray jsonArray = new JSONArray(response.toString());

        for (int i = 0; i < jsonArray.length(); i++) {
          JSONObject jsonObject = jsonArray.getJSONObject(i);
          Post post = new Post(
              jsonObject.getInt("userId"),
              jsonObject.getInt("id"),
              jsonObject.getString("title"),
              jsonObject.getString("body")
              );
          posts.add(post);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return posts;
  }


}
