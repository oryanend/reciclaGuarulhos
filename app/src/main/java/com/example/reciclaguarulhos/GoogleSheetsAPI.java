package com.example.reciclaguarulhos;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface GoogleSheetsAPI {
    // Link da API
    @POST("https://script.google.com/macros/s/AKfycby1VMN2nKOACaGmRSVTc8d2EadY8f7NSrycXy9n5nLAbKpMQL-RxmvcJ937Dv7xq3aW/exec")
    Call<Void> enviarDados(@Body DadosFormulario dados);
}
