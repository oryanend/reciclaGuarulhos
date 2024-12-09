package com.example.reciclaguarulhos;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper db;
    EditText editEmail, editSenha;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Margens do layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = new DatabaseHelper(this);
        editEmail = findViewById(R.id.editEmail);
        editSenha = findViewById(R.id.editSenha);
        btnLogin = findViewById(R.id.btnLogin);
        TextView txtLink = findViewById(R.id.txtCadastreLink);

        txtLink.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, telaSignup.class);
            startActivity(intent);
        });

        btnLogin.setOnClickListener(v -> {
            String email = editEmail.getText().toString().trim();
            String senha = editSenha.getText().toString().trim();

            if (email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(MainActivity.this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            } else {
                // Validação de User
                boolean usuarioValido = db.validarUsuario(email, senha);
                if (usuarioValido) {
                    String userId = db.getIdUsuarioByEmail(email);
                    Intent intent = new Intent(MainActivity.this, Home.class);
                    intent.putExtra("idUsuario", userId);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, "Email ou senha inválidos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Intent intent = new Intent(MainActivity.this, Home.class);
    }
}
