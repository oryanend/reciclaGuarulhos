package com.example.reciclaguarulhos;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "reciclaguarulhos.db";
    private static final int DATABASE_VERSION = 1;
    private SQLiteDatabase database;

    public static final String TABLE_USUARIOS = "usuarios";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOME = "nome";
    public static final String COLUMN_CPF = "cpf";
    public static final String COLUMN_ENDERECO = "endereco";
    public static final String COLUMN_CEP = "cep";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_SENHA = "senha";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_USUARIOS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NOME + " TEXT NOT NULL, " +
                    COLUMN_CPF + " TEXT NOT NULL UNIQUE, " +
                    COLUMN_ENDERECO + " TEXT NOT NULL, " +
                    COLUMN_CEP + " TEXT NOT NULL, " +
                    COLUMN_EMAIL + " TEXT NOT NULL UNIQUE, " +
                    COLUMN_SENHA + " TEXT NOT NULL" +
                    ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        database = getWritableDatabase();
    }

    public boolean validarUsuario(String email, String senha) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM usuarios WHERE email = ? AND senha = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email, senha});
        boolean resultado = cursor.getCount() > 0;
        cursor.close();
        return resultado;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIOS);
        onCreate(db);
    }


    public String getNomeUsuario(String idUsuario) {
        if (idUsuario == null || idUsuario.isEmpty()) {
            Log.e("DatabaseHelper", "ID do usuário é nulo ou vazio");
            return "Usuário não encontrado";
        }

        String nomeUsuario = null;
        Cursor cursor = null;
        try {
            cursor = database.rawQuery("SELECT nome FROM usuarios WHERE id = ?", new String[]{idUsuario});
            if (cursor.moveToFirst()) {
                nomeUsuario = cursor.getString(0);
                Log.d("DatabaseHelper", "Nome do usuário encontrado: " + nomeUsuario);
            } else {
                Log.e("DatabaseHelper", "Nenhum usuário encontrado com o ID: " + idUsuario);
            }
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Erro ao buscar nome do usuário", e);
        } finally {
            if (cursor != null) cursor.close();
        }
        return nomeUsuario;
    }

    public String getIdUsuarioByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String idUsuario = null;

        Cursor cursor = db.rawQuery("SELECT id FROM usuarios WHERE email = ?", new String[]{email});
        if (cursor.moveToFirst()) {
            idUsuario = cursor.getString(0);
        }
        cursor.close();

        return idUsuario;
    }


    public void listarUsuarios() {
        Cursor cursor = database.rawQuery("SELECT id, nome FROM usuarios", null);
        if (cursor.moveToFirst()) {
            do {
                Log.d("DatabaseHelper", "ID: " + cursor.getString(0) + ", Nome: " + cursor.getString(1));
            } while (cursor.moveToNext());
        } else {
            Log.d("DatabaseHelper", "Nenhum usuário encontrado.");
        }
        cursor.close();
    }


}

