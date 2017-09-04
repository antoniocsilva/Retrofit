package br.com.devjs.retrofit.api.model;

import java.io.Serializable;

/**
 * Created by antonios on 21/08/17.
 * Classe para auxiliar no armazenamento do banco
 */

public class GitHubUsers implements Serializable{

    private Long id;
    private String user;
    private String repository;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getRepository() {
        return repository;
    }

    public void setRepository(String repository) {
        this.repository = repository;
    }

    @Override
    public String toString() {
        return getId() + " - " + getUser();
    }

}
