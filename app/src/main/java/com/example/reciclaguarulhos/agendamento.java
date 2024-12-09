package com.example.reciclaguarulhos;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class agendamento extends AppCompatActivity {
    private GoogleSheetsAPI googleSheetsAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendamento);

        EditText editTitulo = findViewById(R.id.editTextTitulo);
        EditText editEndereco = findViewById(R.id.editTextRuaNumero);
        EditText editBairro = findViewById(R.id.editTextBairro);
        EditText editCep = findViewById(R.id.editTextCepAgendamento);
        EditText editDescricao = findViewById(R.id.editTextDesc);
        Button buttonAgendar = findViewById(R.id.buttonAgendar);
        TextView backButton = findViewById(R.id.txtCancelarAgendamento);

        backButton.setOnClickListener(v -> {
            // Fecha a atividade atual e retorna à anterior
            finish();
        });

        // Retrofit Config.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://script.google.com/macros/s/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        googleSheetsAPI = retrofit.create(GoogleSheetsAPI.class);

        buttonAgendar.setOnClickListener(v -> {
            String titulo = editTitulo.getText().toString();
            String endereco = editEndereco.getText().toString();
            String bairro = editBairro.getText().toString();
            String cep = editCep.getText().toString();
            String descricao = editDescricao.getText().toString();

            if (titulo.isEmpty() || endereco.isEmpty() || bairro.isEmpty() || cep.isEmpty() || descricao.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                return;
            }

            DadosFormulario dados = new DadosFormulario(titulo, endereco, bairro, cep, descricao);

            // POST do Google Planilhas
            googleSheetsAPI.enviarDados(dados).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(agendamento.this, "Agendamento enviado com sucesso!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(agendamento.this, "Falha ao enviar dados!", Toast.LENGTH_SHORT).show();
                        Log.e("GoogleSheets", "Erro: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(agendamento.this, "Erro de rede: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("GoogleSheets", "Erro: ", t);
                }

            });
        });

    }
    }
