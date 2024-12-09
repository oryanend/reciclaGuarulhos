package com.example.reciclaguarulhos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class UsuarioDAO {

    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public UsuarioDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public boolean cadastrarUsuario(String nome, String cpf, String endereco, String cep, String email, String senha) {
        try {
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COLUMN_NOME, nome);
            values.put(DatabaseHelper.COLUMN_CPF, cpf);
            values.put(DatabaseHelper.COLUMN_ENDERECO, endereco);
            values.put(DatabaseHelper.COLUMN_CEP, cep);
            values.put(DatabaseHelper.COLUMN_EMAIL, email);
            values.put(DatabaseHelper.COLUMN_SENHA, senha);

            Log.d("UsuarioDAO", "Tentando cadastrar usuário: " + nome + ", " + email);

            long result = database.insert(DatabaseHelper.TABLE_USUARIOS, null, values);
            if (result == -1) {
                Log.e("UsuarioDAO", "Erro ao cadastrar usuário: result = -1");
            } else {
                Log.d("UsuarioDAO", "Usuário cadastrado com sucesso: ID = " + result);
            }

            return result != -1;
        } catch (Exception e) {
            Log.e("UsuarioDAO", "Erro ao cadastrar usuário", e);
            return false;
        }
    }

    public void close() {
        database.close();
    }
}
