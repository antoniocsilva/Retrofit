package br.com.devjs.retrofit.ui;

import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.devjs.retrofit.R;
import br.com.devjs.retrofit.api.dao.GitHubRepoDAO;
import br.com.devjs.retrofit.api.model.GitHubUsers;

import static java.lang.String.valueOf;

/**
 * Created by antonios on 22/08/17.
 */

public class RepositorioHelper {

    private final EditText campoUser;
    private final ListView campoRepo;

    private GitHubUsers users;

    public RepositorioHelper(MainActivity activity) {

        campoUser = (EditText) activity.findViewById(R.id.edit_user);
        campoRepo = (ListView) activity.findViewById(R.id.list_repository);

    }

    public GitHubUsers getUsers(){
        //GitHubUsers users = new GitHubUsers();
        users.setUser(campoUser.getText().toString());
        //users.setRepository(campoRepo.get);
        return users;
    }

    public String  tratarSTR(String oldStr){
        String newStr;
        newStr = oldStr.replace("[", "");
        newStr = newStr.replace("]", "");
        return newStr;
    }

}
