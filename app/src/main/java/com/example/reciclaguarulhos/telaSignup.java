package com.example.reciclaguarulhos;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

public class telaSignup extends FragmentActivity {

    private EditText editNome, editCpf, editEndereco, editCep, editEmail, editSenha;
    private Button buttonCadastrar;
    private UsuarioDAO usuarioDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_signup);
        TextView txtLink = findViewById(R.id.txtLoginLink);
        
        txtLink.setOnClickListener(v -> {
            Intent intent = new Intent(telaSignup.this, MainActivity.class);
            startActivity(intent);
        });


        usuarioDAO = new UsuarioDAO(this);

        editNome = findViewById(R.id.editTextTitulo);
        editCpf = findViewById(R.id.editTextRuaNumero);
        editEndereco = findViewById(R.id.editTextBairro);
        editCep = findViewById(R.id.editTextCepAgendamento);
        editEmail = findViewById(R.id.editTextDesc);
        editSenha = findViewById(R.id.editTextSenha);
        buttonCadastrar = findViewById(R.id.buttonAgendar);

        buttonCadastrar.setOnClickListener(v -> {
            String nome = editNome.getText().toString();
            String cpf = editCpf.getText().toString();
            String endereco = editEndereco.getText().toString();
            String cep = editCep.getText().toString();
            String email = editEmail.getText().toString();
            String senha = editSenha.getText().toString();

            if (nome.isEmpty() || cpf.isEmpty() || endereco.isEmpty() || cep.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            } else {
                boolean sucesso = usuarioDAO.cadastrarUsuario(nome, cpf, endereco, cep, email, senha);
                if (sucesso) {
                    Toast.makeText(this, "Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(telaSignup.this, MainActivity.class);
                    startActivity(intent);
                    finish(); // Fecha a tela de cadastro
                } else {
                    Toast.makeText(this, "Erro ao cadastrar usuário!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        usuarioDAO.close();
        super.onDestroy();
    }

}
