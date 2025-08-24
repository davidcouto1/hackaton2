package com.desafio.controller;

import com.desafio.service.ProdutoService;
import com.desafio.service.ProdutoService.ProdutoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {
    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public List<ProdutoDTO> listarProdutos() throws Exception {
        return produtoService.buscarProdutos();
    }
}
