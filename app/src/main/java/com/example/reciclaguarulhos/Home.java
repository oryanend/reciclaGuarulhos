package com.example.reciclaguarulhos;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        String idUsuario = getIntent().getStringExtra("idUsuario");

        if (idUsuario == null || idUsuario.isEmpty()) {
            Log.e("HomeActivity", "ID do usuário é inválido");
            Toast.makeText(this, "Erro ao carregar usuário", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        Log.d("HomeActivity", "ID do usuário recebido: " + idUsuario);
        String nomeUsuario = dbHelper.getNomeUsuario(idUsuario);

        TextView textView = findViewById(R.id.txtUsername);
        if (nomeUsuario == null || nomeUsuario.isEmpty()) {
            textView.setText("Usuário não encontrado");
            Log.e("HomeActivity", "Nenhum nome de usuário encontrado para o ID: " + idUsuario);
        } else {
            textView.setText(nomeUsuario);
            Log.d("HomeActivity", "Nome do usuário exibido: " + nomeUsuario);
        }

        CardView cardAgendamento = findViewById(R.id. cardAgendamento);
        CardView cardMapa = findViewById(R.id.cardMapa);
        ImageView imgLogout = findViewById(R.id.imgLogout);

        imgLogout.setOnClickListener(v -> {
            getSharedPreferences("user_session", MODE_PRIVATE).edit().clear().apply();
            Toast.makeText(Home.this, "Logout realizado com sucesso!", Toast.LENGTH_SHORT).show();
            Intent intentLogin = new Intent(Home.this, MainActivity.class);
            startActivity(intentLogin);
            finish();
        });

        imgLogout.setOnClickListener(v ->{
            getSharedPreferences("user_session", MODE_PRIVATE).edit().clear().apply();
            Toast.makeText(Home.this, "Logout realizado com sucesso!", Toast.LENGTH_SHORT).show();
            Intent intentLogin = new Intent(Home.this, MainActivity.class);
            startActivity(intentLogin);
            finish();
        });

        cardAgendamento.setOnClickListener(v -> {
            Intent intentAgendamento = new Intent(Home.this, agendamento.class);
            startActivity(intentAgendamento);
        });

        cardMapa.setOnClickListener(v -> {
            Intent intentMapa = new Intent(Home.this, MapsActivity.class);
            startActivity(intentMapa);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}