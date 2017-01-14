package com.fenix.temperatura;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Locale;

/**
 * Created by adilmar on 10/01/17.
 */

public class MainBusca extends AppCompatActivity implements TextToSpeech.OnInitListener {

    SearchView sv;

    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.busca);

        tts = new TextToSpeech(this, this);

        sv = (SearchView) findViewById(R.id.cbusca);

        sv.setQueryHint("Digite a cidade!");

        Button button1 = (Button) findViewById(R.id.bbuscar);

        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String cidade = String.valueOf(sv.getQuery());
                Intent intent = new Intent(view.getContext(), Main.class);
                intent.putExtra("cidade", cidade);
                startActivity(intent);

            }
        });


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
        String text = "Oi";
        tts.speak("Digite a cidade", TextToSpeech.QUEUE_FLUSH, null);
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


