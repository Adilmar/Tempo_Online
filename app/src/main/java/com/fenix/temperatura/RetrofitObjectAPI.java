package com.fenix.temperatura;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by navneet on 4/6/16.
 */
public interface RetrofitObjectAPI {

    /*
     * Retrofit get annotation with our URL
     * And our method that will return us details of student.
    */
    //@GET("cep/38408210")
    @GET("v1/current.json?key=89ca93b7b33340098a9112523170501")
    Call<Tempo> getCurrentDetails(@Query("q") String cidade);
}
