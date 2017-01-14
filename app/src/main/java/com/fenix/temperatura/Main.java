package com.fenix.temperatura;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class Main extends AppCompatActivity implements TextToSpeech.OnInitListener {

    String url = "http://api.apixu.com/";

    TextView text_id_1, text_name_1, text_marks_1, imagem;
    TextView text_id_2, text_name_2, text_marks_2;

    public String codigo;
    public View someView;
    public ImageView imageView;
    public String cidade;

    public String frase;

    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tts = new TextToSpeech(this, this);

        imageView = (ImageView) findViewById(R.id.imageView);

        someView = findViewById(R.id.tela);

        imagem = (TextView) findViewById(R.id.url);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        text_id_1 = (TextView) findViewById(R.id.text_id_1);
        text_name_1 = (TextView) findViewById(R.id.text_name_1);
        text_marks_1 = (TextView) findViewById(R.id.text_marks_1);

        text_id_2 = (TextView) findViewById(R.id.text_id_2);
        text_name_2 = (TextView) findViewById(R.id.text_name_2);
        text_marks_2 = (TextView) findViewById(R.id.text_marks_2);

        Intent intent = getIntent();
        cidade = intent.getStringExtra("cidade");

        text_marks_2.setText(cidade);

        getRetrofitObject();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, cidade, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    void getRetrofitObject() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitObjectAPI service = retrofit.create(RetrofitObjectAPI.class);

        Call<Tempo> call = service.getCurrentDetails(cidade);


        call.enqueue(new Callback<Tempo>() {
            @Override
            public void onResponse(Response<Tempo> response, Retrofit retrofit) {

                try {
                    codigo = response.body().getCurrent().getCondition().getCode();


                    if (codigo.equals("1180") || codigo.equals("1186") || codigo.equals("1189") ||
                            codigo.equals("1192") || codigo.equals("1195") || codigo.equals("1183")) {

                        someView.setBackgroundResource(R.mipmap.tchuva);

                    }

                    new AsyncTaskLoadImage(imageView).execute("http:" + response.body().getCurrent().getCondition().getIcon());
                    text_name_1.setText("Temperatura  :  " + response.body().getCurrent().getTemp_c());
                    text_id_1.setText("Umidade  :  " + response.body().getCurrent().getHumidity());

                    Log.d("sucesso", "sucesso");

                } catch (Exception e) {
                    Log.d("onResponse", "There is an error");
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("onFailure", t.toString());
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onInit(int status) {
        // TODO Auto-generated method stub
        //TTS is successfully initialized
        if (status == TextToSpeech.SUCCESS) {
            //Setting speech language
            int result = tts.setLanguage(new Locale("pt"));
            speakOut();
            //tts.speak(teste, TextToSpeech.QUEUE_FLUSH, null);
            //If your device doesn't support language you set above
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                //Cook simple toast message with message
                Toast.makeText(this, "Language not supported", Toast.LENGTH_LONG).show();
                Log.e("TTS", "Language is not supported");
            }
            //Enable the button - It was disabled in main.xml (Go back and Check it)
            else {
                //btnSpeak.setEnabled(true);
            }
            //TTS is not initialized properly
        } else {
            Toast.makeText(this, "TTS Initilization Failed", Toast.LENGTH_LONG).show();
            Log.e("TTS", "Initilization Failed");
        }
    }

    private void speakOut() {
        //Get the text typed

        tts.speak("Previsão do tempo agora em: "+String.valueOf(text_marks_2.getText()), TextToSpeech.QUEUE_FLUSH, null);
    }


    public void onDestroy() {
        // Don't forget to shutdown!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }
}
