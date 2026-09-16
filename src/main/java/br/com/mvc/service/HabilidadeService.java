package br.com.mvc.service;

import br.com.mvc.dao.HabilidadeDAO;
import br.com.mvc.dao.TrocaDAO;
import br.com.mvc.model.Habilidade;

import java.util.List;

/**
 * SERVICE de Habilidade — regras de negocio ficam aqui.
 *
 * Controller so chama estes metodos e decide a view.
 * DAO so executa SQL.
 */
public class HabilidadeService {

    private final HabilidadeDAO habilidadeDAO;
    private final TrocaDAO trocaDAO;

    public HabilidadeService() {
        this.habilidadeDAO = new HabilidadeDAO();
        this.trocaDAO = new TrocaDAO();
    }

    public List<Habilidade> listar() {
        return this.habilidadeDAO.listarTodos();
    }

    public Habilidade buscarPorId(Long id) {
        if (id == null) {
            return null;
        }
        return this.habilidadeDAO.buscarPorId(id);
    }

    /**
     * Regra de salvamento:
     * - sem id -> cadastro novo
     * - com id -> alteracao (habilidade precisa existir)
     * - nome obrigatorio e unico
     */
    public void salvar(Habilidade habilidade) {
        if (habilidade == null) {
            throw new IllegalArgumentException("Habilidade e obrigatoria.");
        }

        habilidade.setNome(this.normalizar(habilidade.getNome()));
        this.validarNomeObrigatorio(habilidade.getNome());
        this.validarNomeUnico(habilidade);

        if (habilidade.getId() == null) {
            this.habilidadeDAO.inserir(habilidade);
            return;
        }

        if (this.habilidadeDAO.buscarPorId(habilidade.getId()) == null) {
            throw new IllegalArgumentException("Habilidade nao encontrada para alteracao.");
        }
        this.habilidadeDAO.alterar(habilidade);
    }

    /**
     * Regra de exclusao:
     * - id obrigatorio
     * - habilidade precisa existir
     * - nao pode ter troca vinculada
     */
    public void deletar(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id e obrigatorio para excluir.");
        }
        if (this.habilidadeDAO.buscarPorId(id) == null) {
            throw new IllegalArgumentException("Habilidade nao encontrada.");
        }
        if (this.trocaDAO.contarPorHabilidadeId(id) > 0) {
            throw new IllegalArgumentException(
                    "Nao e possivel excluir: existem trocas vinculadas a esta habilidade.");
        }
        this.habilidadeDAO.deletar(id);
    }

    private void validarNomeObrigatorio(String nome) {
        if (nome == null) {
            throw new IllegalArgumentException("Nome e obrigatorio.");
        }
    }

    private void validarNomeUnico(Habilidade habilidade) {
        Habilidade existente = this.habilidadeDAO.buscarPorNome(habilidade.getNome());
        if (existente == null) {
            return;
        }
        if (habilidade.getId() == null) {
            throw new IllegalArgumentException("Ja existe uma habilidade com este nome.");
        }
        if (!existente.getId().equals(habilidade.getId())) {
            throw new IllegalArgumentException("Ja existe uma habilidade com este nome.");
        }
    }

    private String normalizar(String valor) {
        if (valor == null) {
            return null;
        }
        String limpo = valor.trim();
        return limpo.isEmpty() ? null : limpo;
    }
}
