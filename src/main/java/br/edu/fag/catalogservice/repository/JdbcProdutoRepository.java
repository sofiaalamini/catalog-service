package br.edu.fag.catalogservice.repository;

import br.edu.fag.catalogservice.domain.Produto;
import br.edu.fag.catalogservice.entity.ProdutoEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class JdbcProdutoRepository implements ProdutoRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcProdutoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long salvar(Produto produto) {
        ProdutoEntity entity = ProdutoEntity.de(produto);
        KeyHolder chaveGerada = new GeneratedKeyHolder();

        jdbcTemplate.update(conexao -> {
            PreparedStatement comando = conexao.prepareStatement(
                    "INSERT INTO produtos (nome, descricao, preco, criado_em) VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            comando.setString(1, entity.nome());
            comando.setString(2, entity.descricao());
            comando.setDouble(3, entity.preco());
            comando.setTimestamp(4, java.sql.Timestamp.valueOf(entity.criadoEm()));
            return comando;
        }, chaveGerada);

        return chaveGerada.getKey().longValue();
    }
}
