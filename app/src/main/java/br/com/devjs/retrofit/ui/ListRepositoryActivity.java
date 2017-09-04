package br.com.devjs.retrofit.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.devjs.retrofit.R;
import br.com.devjs.retrofit.api.dao.GitHubRepoDAO;
import br.com.devjs.retrofit.api.model.GitHubRepo;
import br.com.devjs.retrofit.api.model.GitHubUsers;

import static android.R.layout.simple_list_item_1;

public class ListRepositoryActivity extends AppCompatActivity {

    private RepositorioHelper helper;
    private List<GitHubRepo> repositorios;
    private ListView campoRepo;
    private String  repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_repository);

        campoRepo = (ListView) findViewById(R.id.list_repository);
        Intent intent = getIntent();
        repository = intent.getStringExtra("repositoryList");

        List<String> repositoryList = new ArrayList<String>(Arrays.asList(repository.split(",")));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, repositoryList);

        campoRepo.setAdapter(adapter);

        //Toast.makeText(ListRepositoryActivity.this, String.valueOf(repositoryList.getClass()), Toast.LENGTH_LONG).show();

    }
}
