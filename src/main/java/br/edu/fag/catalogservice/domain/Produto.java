package br.edu.fag.catalogservice.domain;

import java.time.LocalDateTime;

public record Produto(
        Long id,
        String nome,
        String descricao,
        Double preco,
        LocalDateTime criadoEm) {

    private static final double PRECO_DE_PRODUTO_DE_ALTO_VALOR = 1000.00;

    public static Produto criar(String nome, String descricao, Double preco) {
        if (nome == null || nome.isBlank()) {
            throw new RegraDeNegocioException("O nome do produto é obrigatório.");
        }

        if (preco == null) {
            throw new RegraDeNegocioException("O preço do produto deve ser um número válido.");
        }

        if (preco <= 0) {
            throw new RegraDeNegocioException("O preço do produto deve ser maior que zero.");
        }

        nome = nome.trim();
        descricao = descricao == null ? null : descricao.trim();

        if (nome.length() < 3) {
            throw new RegraDeNegocioException(
                    "O nome do produto deve ter pelo menos 3 caracteres.");
        }

        if (preco >= PRECO_DE_PRODUTO_DE_ALTO_VALOR
                && (descricao == null || descricao.isBlank())) {
            throw new RegraDeNegocioException(
                    "Produtos a partir de R$ 1.000,00 devem possuir uma descrição.");
        }

        double precoArredondado = Math.round(preco * 100.0) / 100.0;
        return new Produto(null, nome, descricao, precoArredondado, LocalDateTime.now());
    }

    public Produto comId(Long id) {
        return new Produto(id, nome, descricao, preco, criadoEm);
    }
}
