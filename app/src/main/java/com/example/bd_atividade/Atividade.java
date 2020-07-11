package com.example.bd_atividade;

public class Atividade {
    private int codigo;
    private String nomeAtividade;
    private String responsavel;
    private int prioridade;

    public Atividade()
    {
    }

    public Atividade(int codigo, String nomeAtividade, String responsavel, int prioridade)
    {
        this.codigo = codigo;
        this.nomeAtividade = nomeAtividade;
        this.responsavel = responsavel;
        this.prioridade = prioridade;
    }

    public int getCodigo()
    {
        return codigo;
    }

    public void setCodigo(int codigo)
    {
        this.codigo = codigo;
    }

    public String getNomeAtividade() {
        return nomeAtividade;
    }

    public void setNomeAtividade(String nomeAtividade)
    {
        this.nomeAtividade = nomeAtividade;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel)
    {
        this.responsavel = responsavel;
    }

    public int getPrioridade()
    {
        return prioridade;
    }

    public void setPrioridade(int prioridade)
    {
        this.prioridade = prioridade;
    }

    @Override
    public String toString()
    {
        return "Atividade{" +
                "codigo=" + codigo +
                ", nomeAtividade='" + nomeAtividade + '\'' +
                ", responsavel='" + responsavel + '\'' +
                ", prioridade=" + prioridade +
                '}';
    }
}
