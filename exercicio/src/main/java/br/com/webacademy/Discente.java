package br.com.webacademy;

public record Discente(Long id, String nome, int periodoAtual, Double matricula) {
    public Discente(String nome, int periodoAtual, Double matricula) {
        this(null, nome, periodoAtual, matricula);
    }
}