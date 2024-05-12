package br.com.decimoandar.model;

public class Imovel {

    private String tipoImovel;
    private String endereco;
    private String metrosQuadrados;
    private String numQuartos;
    private String numBanheiros;
    private String cep;
    private String descricaoImovel;
    private int userId;

    public Imovel() {

    }

    public Imovel(String tipoImovel, String endereco, String metrosQuadrados, String numQuartos, String numBanheiros, String cep, String descricaoImovel) {
        this.tipoImovel = tipoImovel;
        this.endereco = endereco;
        this.metrosQuadrados = metrosQuadrados;
        this.numQuartos = numQuartos;
        this.numBanheiros = numBanheiros;
        this.cep = cep;
        this.descricaoImovel = descricaoImovel;
    }

    public String getTipoImovel() {
        return tipoImovel;
    }

    public void setTipoImovel(String tipoImovel) {
        this.tipoImovel = tipoImovel;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getMetrosQuadrados() {
        return metrosQuadrados;
    }

    public void setMetrosQuadrados(String metrosQuadrados) {
        this.metrosQuadrados = metrosQuadrados;
    }

    public String getNumQuartos() {
        return numQuartos;
    }

    public void setNumQuartos(String numQuartos) {
        this.numQuartos = numQuartos;
    }

    public String getNumBanheiros() {
        return numBanheiros;
    }

    public void setNumBanheiros(String numBanheiros) {
        this.numBanheiros = numBanheiros;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getDescricaoImovel() {
        return descricaoImovel;
    }

    public void setDescricaoImovel(String descricaoImovel) {
        this.descricaoImovel = descricaoImovel;
    }

    public int getUserId() { return userId; }

    public void setUserId(int userId) { this.userId = userId; }
}