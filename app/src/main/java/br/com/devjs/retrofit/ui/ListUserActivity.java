package br.com.devjs.retrofit.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.devjs.retrofit.R;
import br.com.devjs.retrofit.api.dao.GitHubRepoDAO;
import br.com.devjs.retrofit.api.model.GitHubRepo;
import br.com.devjs.retrofit.api.model.GitHubUsers;
import br.com.devjs.retrofit.ui.adapter.GitHubRepoAdapter;

import static android.R.layout.simple_list_item_1;

public class ListUserActivity extends AppCompatActivity {

    private ListView listaUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user);

        listaUsers = (ListView) findViewById(R.id.list_users);

        carregarLista();
        registerForContextMenu(listaUsers);

        listaUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View item, int position, long id) {
                GitHubUsers user = (GitHubUsers) listaUsers.getItemAtPosition(position);
                String repo = user.getRepository();
                Intent itRepository = new Intent(ListUserActivity.this, ListRepositoryActivity.class);
                itRepository.putExtra("repositoryList", repo);
                startActivity(itRepository);

            }
        });


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {

        //deletar um item da lista do repositorio
        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                GitHubUsers user = (GitHubUsers) listaUsers.getItemAtPosition(info.position);

                GitHubRepoDAO users = new GitHubRepoDAO(ListUserActivity.this);
                users.deleta(user);

                users.close();

                carregarLista();
                Toast.makeText(ListUserActivity.this, getString(R.string.delete_repo) + user.getUser(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        //deletar um item da lista do repositorio
        MenuItem atualizar = menu.add("Atualizar");
        atualizar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                GitHubUsers id = (GitHubUsers) listaUsers.getItemAtPosition(info.position);

                Intent intent = getIntent();
                GitHubUsers user = (GitHubUsers) intent.getSerializableExtra("user");

                GitHubRepoDAO users = new GitHubRepoDAO(ListUserActivity.this);

                if (user != null){
                    users.alterar(user,id);
                    Toast.makeText(ListUserActivity.this, getString(R.string.rep) + user.getUser() + getString(R.string.atualizado), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(ListUserActivity.this, getString(R.string.sem_atualizar)
                            + id.getUser() + getString(R.string.volte), Toast.LENGTH_SHORT).show();
                }
                users.close();
                carregarLista();
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void carregarLista() {
        GitHubRepoDAO dao = new GitHubRepoDAO(this);
        List<GitHubUsers> usersList = dao.buscarUsers();
        dao.close();

        ArrayAdapter<GitHubUsers> adapter = new ArrayAdapter<GitHubUsers>
                (this, simple_list_item_1, usersList);
        listaUsers.setAdapter(adapter);
    }

}
