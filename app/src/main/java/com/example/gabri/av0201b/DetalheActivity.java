package com.example.gabri.av0201b;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;

public class DetalheActivity extends AppCompatActivity {
    String title;
    /*Webservice*/
    String token = "246bf886104d519a1d2bf62aef1054ff";
    String service_uri = "http://api.themoviedb.org/3/movie/";
    String image_uri = "https://image.tmdb.org/t/p/w370";
    String url;
    String key = "";
    String filme_id = "";
    String customHtml = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            " <meta charset=\"utf-8\">\n" +
            " <meta name=\"viewport\" content=\"width=device-width\">\n" +
            " <title>MovieFun Trailer</title>\n" +
            "</head>\n" +
            "<body>\n" +
            " <h1>O filme não tem trailer!</h1>"+
            "</body>\n" +
            "</html>";

    WebView webView;
    //web.loadData(customHtml, "text/html", "UTF-8");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);

        Bundle parametros = getIntent().getExtras();

        //System.out.println("param+arg " + getArguments());
        if (parametros!=null) {
            title = parametros.get("title").toString();
            Log.v("Main", title);
            TextView titleTxtV = (TextView) findViewById(R.id.nomeTextView);
            titleTxtV.setText(title);
            if (parametros.get("filme_id").toString()!=null) {
                filme_id = parametros.get("filme_id").toString();


            }
            Log.v("Main", key);

        }
        Ion.with(getBaseContext())
                .load("https://api.themoviedb.org/3/movie/"+filme_id+"/videos?api_key=246bf886104d519a1d2bf62aef1054ff&language=pt-BR")
                .setLogging("MyLogs", Log.DEBUG)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if(e != null){
                            Log.e("Detalhe","Erro ao baixar o JSON");
                            return ;
                        }
                        try{
                            JsonArray traillers = result.getAsJsonArray("results");
                            Log.v("Main-","Total traillers : " + traillers.size());
                            JsonObject traillerJson;
                            if (traillers.size()>0) {
                                traillerJson = (JsonObject) traillers.get(0).getAsJsonObject();
                                key = traillerJson.get("key").getAsString();
                                Log.v("Main-", key);
                                customHtml = "<!DOCTYPE html>\n" +
                                        "<html>\n" +
                                        "<head>\n" +
                                        " <meta charset=\"utf-8\">\n" +
                                        " <meta name=\"viewport\" content=\"width=device-width\">\n" +
                                        " <title>MovieFun Trailer</title>\n" +
                                        "</head>\n" +
                                        "<body>\n" +
                                        "<iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/"+ key +"\" frameborder=\"0\" allowfullscreen></iframe>\n" +
                                        "</body>\n" +
                                        "</html>";
                            }
                            //System.out.print("Main-"+traillers);

 /*Faça Sua Magica Aqui */

                            webView = (WebView) findViewById(R.id.webView);
                            
                            webView.getSettings().setJavaScriptEnabled(true);
                            webView.loadData(customHtml,"text/html; charset=UTF-8", null);

                        }catch (JsonIOException er){
                            Log.e("Main","Erro ao parsear o JSON");
                        }
                    }
                });
    }
    @Override
    public void onResume(){
        super.onResume();

    }
}
