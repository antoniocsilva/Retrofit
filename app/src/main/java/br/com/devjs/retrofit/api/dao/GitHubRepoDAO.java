package br.com.devjs.retrofit.api.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import br.com.devjs.retrofit.api.model.GitHubUsers;

/**
 * Created by antonios on 19/08/17.
 */

public class GitHubRepoDAO extends SQLiteOpenHelper {

    public GitHubRepoDAO(Context context) {
        super(context, "GitHub", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Repositorios (id INTEGER PRIMARY KEY, " +
                "user TEXT NOT NULL, " +
                "repository TEXT);";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "";
        switch (oldVersion) {
            case 1:
                //sql = "ALTER TABLE Repositorios ADD COLUMN caminhoFoto TEXT";
                db.execSQL(sql); // indo para versao 2
        }

    }

    public void insere(GitHubUsers repo) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = pegaDados(repo);

        db.insert("Repositorios", null, dados);
    }

    @NonNull
    private ContentValues pegaDados(GitHubUsers repo) {
        ContentValues dados = new ContentValues();
        dados.put("user", repo.getUser());
        dados.put("repository", repo.getRepository());
        return dados;
    }

    public void deleta(GitHubUsers repo) {
        SQLiteDatabase db = getWritableDatabase();

        String[] params = {String.valueOf(repo.getId())};
        db.delete("Repositorios", "id = ?", params);
    }

    public void alterar(GitHubUsers repo, GitHubUsers id) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = pegaDados(repo);
        db.update("Repositorios", values, "id=?", new String[]{String.valueOf(id.getId())});

    }

    public boolean ehUsers(String user) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Repositorios WHERE user = ?", new String[]{user});
        int resultados = c.getCount();
        c.close();
        return resultados > 0;
    }

    public List<GitHubUsers> buscarUsers() {
        String sql = "SELECT * FROM Repositorios;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        List<GitHubUsers> users = new ArrayList<GitHubUsers>();
        while (c.moveToNext()) {
            GitHubUsers user = new GitHubUsers();
            user.setId(c.getLong(c.getColumnIndex("id")));
            user.setUser(c.getString(c.getColumnIndex("user")));
            user.setRepository(c.getString(c.getColumnIndex("repository")));

            users.add(user);
        }
        c.close();
        return users;
    }

}
