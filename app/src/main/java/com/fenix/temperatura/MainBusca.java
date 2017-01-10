package com.fenix.temperatura;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.Button;

/**
 * Created by adilmar on 10/01/17.
 */

public class MainBusca extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.busca);

        Button button1 = (Button) findViewById(R.id.bbuscar);

        button1.setOnClickListener(new View.OnClickListener() {

            SearchView sv = (SearchView) findViewById(R.id.cbusca);

            @Override
            public void onClick(View view) {

                String cidade = String.valueOf(sv.getQuery());
                Intent intent = new Intent(view.getContext(), Main.class);
                intent.putExtra("cidade", cidade);
                startActivity(intent);

                //Intent Intent = new Intent(view.getContext(), Main.class);
                //view.getContext().startActivity(Intent);
            }
        });


    }

    /**
     * Called when the user clicks the Send button
     */
    public void sendMessage(View view) {
        // Do something in response to button
    }
}


