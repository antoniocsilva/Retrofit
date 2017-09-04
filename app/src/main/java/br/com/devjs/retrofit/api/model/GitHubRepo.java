package br.com.devjs.retrofit.api.model;

/**
 * Created by antonios on 21/08/17.
 * Classe para extrair os dados do servidor
 */

public class GitHubRepo {

    //Nome do repositório
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getName();
    }

}
