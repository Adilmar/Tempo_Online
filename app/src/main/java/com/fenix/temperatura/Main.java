package com.fenix.temperatura;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class Main extends AppCompatActivity implements LoadImageTask.Listener {

    String url = "http://api.apixu.com/";
    TextView text_id_1, text_name_1, text_marks_1, imagem;
    TextView text_id_2, text_name_2, text_marks_2;
    //public static final String IMAGE_URL = "http://cdn.apixu.com/weather/64x64/day/113.png";

    public String codigo;

    //background dinamico
    //LinearLayout  vi = (LinearLayout) findViewById(R.id.tela);

    public View someView;

    //imagem viewer
    public ImageView mImageView;
    public ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = (ImageView) findViewById(R.id.imageView);
        imageView = (ImageView) findViewById(R.id.imageView);

        View someView = findViewById(R.id.tela);
        //someView.setBackgroundResource(R.mipmap.tchuva);

        imagem = (TextView) findViewById(R.id.url);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        text_id_1 = (TextView) findViewById(R.id.text_id_1);
        text_name_1 = (TextView) findViewById(R.id.text_name_1);
        text_marks_1 = (TextView) findViewById(R.id.text_marks_1);

        text_id_2 = (TextView) findViewById(R.id.text_id_2);
        text_name_2 = (TextView) findViewById(R.id.text_name_2);
        text_marks_2 = (TextView) findViewById(R.id.text_marks_2);

        String url = "https://www.gstatic.com/webp/gallery3/1.sm.png";
        //new AsyncTaskLoadImage(imageView).execute(url);


//      Button ButtonObject= (Button) findViewById(R.id.RetrofitObject);

        getRetrofitObject();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
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

        Call<Tempo> call = service.getCurrentDetails();


        call.enqueue(new Callback<Tempo>() {
            @Override
            public void onResponse(Response<Tempo> response, Retrofit retrofit) {

                try {
                    codigo = response.body().getCurrent().getCondition().getCode();

                    if (codigo.equals("1180") || codigo.equals("1186") || codigo.equals("1189") ||
                            codigo.equals("1192") || codigo.equals("1195")) {

                        someView.setBackgroundResource(R.mipmap.tchuva);

                    }

                    new AsyncTaskLoadImage(imageView).execute("http:" + response.body().getCurrent().getCondition().getIcon());
                    imagem.setText("http:" + response.body().getCurrent().getCondition().getIcon());
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
    public void onImageLoaded(Bitmap bitmap) {

        mImageView.setImageBitmap(bitmap);
    }

    @Override
    public void onError() {
        Toast.makeText(this, "Error Loading Image !", Toast.LENGTH_SHORT).show();
    }


}
