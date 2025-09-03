package com.example.univcalendar;

import retrofit2.Call;
import retrofit2.http.GET;
import java.util.List;

public interface EdtApi {
    @GET("edt") // ton endpoint sur http://127.0.0.1:5000/edt
    Call<List<Cours>> getEdt();
}