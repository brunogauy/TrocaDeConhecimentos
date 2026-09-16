package br.com.mvc.service;

import br.com.mvc.dao.HabilidadeDAO;
import br.com.mvc.dao.TrocaDAO;
import br.com.mvc.dao.UsuarioDAO;
import br.com.mvc.model.Troca;

import java.util.List;

/**
 * SERVICE de Troca — regras de negocio ficam aqui.
 *
 * Controller so chama estes metodos e decide a view.
 * DAO so executa SQL.
 */
public class TrocaService {

    private final TrocaDAO trocaDAO;
    private final UsuarioDAO usuarioDAO;
    private final HabilidadeDAO habilidadeDAO;

    public TrocaService() {
        this.trocaDAO = new TrocaDAO();
        this.usuarioDAO = new UsuarioDAO();
        this.habilidadeDAO = new HabilidadeDAO();
    }

    public List<Troca> listar() {
        return this.trocaDAO.listarTodos();
    }

    public Troca buscarPorId(Long id) {
        if (id == null) {
            return null;
        }
        return this.trocaDAO.buscarPorId(id);
    }

    /**
     * Regra de salvamento:
     * - sem id -> cadastro novo
     * - com id -> alteracao (troca precisa existir)
     * - usuario oferecendo, usuario interessado e habilidade sao obrigatorios e precisam existir
     * - usuario oferecendo e interessado nao podem ser o mesmo usuario
     */
    public void salvar(Troca troca) {
        if (troca == null) {
            throw new IllegalArgumentException("Troca e obrigatoria.");
        }

        this.validarCamposObrigatorios(troca);
        this.validarUsuarioOferecendoExistente(troca.getUsuarioOferecendoId());
        this.validarUsuarioInteressadoExistente(troca.getUsuarioInteressadoId());
        this.validarHabilidadeExistente(troca.getHabilidadeId());
        this.validarUsuariosDiferentes(troca);

        if (troca.getId() == null) {
            this.trocaDAO.inserir(troca);
            return;
        }

        if (this.trocaDAO.buscarPorId(troca.getId()) == null) {
            throw new IllegalArgumentException("Troca nao encontrada para alteracao.");
        }
        this.trocaDAO.alterar(troca);
    }

    /**
     * Regra de exclusao:
     * - id obrigatorio
     * - troca precisa existir
     */
    public void deletar(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id e obrigatorio para excluir.");
        }
        if (this.trocaDAO.buscarPorId(id) == null) {
            throw new IllegalArgumentException("Troca nao encontrada.");
        }
        this.trocaDAO.deletar(id);
    }

    private void validarCamposObrigatorios(Troca troca) {
        if (troca.getUsuarioOferecendoId() == null) {
            throw new IllegalArgumentException("Usuario oferecendo e obrigatorio.");
        }
        if (troca.getUsuarioInteressadoId() == null) {
            throw new IllegalArgumentException("Usuario interessado e obrigatorio.");
        }
        if (troca.getHabilidadeId() == null) {
            throw new IllegalArgumentException("Habilidade e obrigatoria.");
        }
    }

    private void validarUsuarioOferecendoExistente(Long usuarioOferecendoId) {
        if (this.usuarioDAO.buscarPorId(usuarioOferecendoId) == null) {
            throw new IllegalArgumentException("Usuario oferecendo informado nao existe.");
        }
    }

    private void validarUsuarioInteressadoExistente(Long usuarioInteressadoId) {
        if (this.usuarioDAO.buscarPorId(usuarioInteressadoId) == null) {
            throw new IllegalArgumentException("Usuario interessado informado nao existe.");
        }
    }

    private void validarHabilidadeExistente(Long habilidadeId) {
        if (this.habilidadeDAO.buscarPorId(habilidadeId) == null) {
            throw new IllegalArgumentException("Habilidade informada nao existe.");
        }
    }

    private void validarUsuariosDiferentes(Troca troca) {
        if (troca.getUsuarioOferecendoId().equals(troca.getUsuarioInteressadoId())) {
            throw new IllegalArgumentException("Usuario oferecendo e interessado nao podem ser o mesmo.");
        }
    }
}
