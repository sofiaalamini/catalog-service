package br.edu.fag.catalogservice.service;

import br.edu.fag.catalogservice.domain.Produto;
import br.edu.fag.catalogservice.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public Produto criar(String nome, String descricao, Double preco) {
        Produto produto = Produto.criar(nome, descricao, preco);
        Long id = produtoRepository.salvar(produto);
        return produto.comId(id);
    }
}
