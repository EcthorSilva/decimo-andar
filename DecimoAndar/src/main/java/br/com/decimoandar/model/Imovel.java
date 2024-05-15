package br.com.decimoandar.model;

import java.util.ArrayList;
import java.util.List;

public class Imovel {

    private int idImovel;
    private String tipoImovel;
    private String tipoVenda;
    private String valor;
    private String endereco;
    private String numero;
    private String cidade;
    private String uf;
    private String metrosQuadrados;
    private String numQuartos;
    private String numBanheiros;
    private String cep;
    private String descricaoImovel;
    private int userId;
    private List<String> imagePaths;

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

    public Imovel(List<String> imagePaths) {
        this.imagePaths = imagePaths != null ? imagePaths : new ArrayList<>();
    }

    public List<String> getImagePaths() {
        return imagePaths;
    }

    public void setImagePaths(List<String> imagePaths) {
        this.imagePaths = imagePaths;
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

    public int getIdImovel() { return idImovel; }

    public void setIdImovel(int idImovel) { this.idImovel = idImovel; }

    public String getTipoVenda() { return tipoVenda; }

    public void setTipoVenda(String tipoVenda) { this.tipoVenda = tipoVenda; }

    public String getValor() { return valor; }

    public void setValor(String valor) { this.valor = valor; }

    public String getNumero() { return numero; }

    public void setNumero(String numero) { this.numero = numero; }

    public String getCidade() { return cidade; }

    public void setCidade(String cidade) { this.cidade = cidade; }

    public String getUf() { return uf; }

    public void setUf(String uf) { this.uf = uf; }
}