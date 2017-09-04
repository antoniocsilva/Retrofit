package br.com.devjs.retrofit.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.devjs.retrofit.R;
import br.com.devjs.retrofit.api.dao.GitHubRepoDAO;
import br.com.devjs.retrofit.api.model.GitHubUsers;
import br.com.devjs.retrofit.ui.adapter.GitHubRepoAdapter;
import br.com.devjs.retrofit.api.model.GitHubRepo;
import br.com.devjs.retrofit.api.service.GitHubClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private EditText editUser;
    private ListView listView;
    private String userGit;

    private List<GitHubRepo> repositorios;
    private Intent itListUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editUser = (EditText) findViewById(R.id.edit_user);
        listView = (ListView) findViewById(R.id.pagination_list);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list_users, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_baixar:
                userGit = editUser.getText().toString();
                if (!userGit.isEmpty()) {
                    MainActivityTask myTask = new MainActivityTask();
                    myTask.execute();
                } else {
                    Toast.makeText(this, R.string.nome, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.menu_salvar:
                RepositorioHelper helper = new RepositorioHelper(this);
                GitHubUsers users = new GitHubUsers();
                GitHubRepoDAO dao = new GitHubRepoDAO(this);

                users.setUser(editUser.getText().toString());
                users.setRepository(helper.tratarSTR(String.valueOf(repositorios)));

                if (!editUser.getText().toString().isEmpty()) {

                    if (!dao.ehUsers(editUser.getText().toString())) {
                        dao.insere(users);
                        Toast.makeText(this, getString(R.string.rep) + users.getUser() +
                                getString(R.string.salvo), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, getString(R.string.rep) + users.getUser() +
                                getString(R.string.existe), Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(MainActivity.this, ListUserActivity.class);

                        intent.putExtra("user", users);

                        startActivity(intent);
                    }
                    dao.close();
                    editUser.setText("");
                    listView.setAdapter(null);

                } else {
                    Toast.makeText(this, R.string.verifique, Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.menu_listar:
                itListUser = new Intent(this, ListUserActivity.class);
                startActivity(itListUser);
                //Toast.makeText(this, String.valueOf(boo), Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public class MainActivityTask extends AsyncTask<Call<List<GitHubRepo>>, Void, List<GitHubRepo>> {

        @Override
        protected List<GitHubRepo> doInBackground(Call<List<GitHubRepo>>... calls) {

            Retrofit.Builder builder = new Retrofit.Builder().baseUrl("https://api.github.com/")
                    .addConverterFactory(GsonConverterFactory.create());

            Retrofit retrofit = builder.build();

            final GitHubClient client = retrofit.create(GitHubClient.class);

            Call<List<GitHubRepo>> call = client.reposForUser(userGit);

            call.enqueue(new Callback<List<GitHubRepo>>() {

                @Override
                public void onResponse(Call<List<GitHubRepo>> call, Response<List<GitHubRepo>> response) {
                    repositorios = response.body();
                    listView.setAdapter(new GitHubRepoAdapter(MainActivity.this, repositorios));
                    //Toast.makeText(MainActivity.this, String.valueOf(repositorios.getClass()), Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onFailure(Call<List<GitHubRepo>> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "error :(", Toast.LENGTH_SHORT).show();
                }
            });

            return null;
        }

    }

}