package br.com.mvc.controller;

import br.com.mvc.model.Troca;
import br.com.mvc.service.HabilidadeService;
import br.com.mvc.service.TrocaService;
import br.com.mvc.service.UsuarioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Controller de Troca.
 * Ponte entre rota, service e view — sem regra de negocio.
 */
@WebServlet("/trocas")
public class TrocaServlet extends BaseServlet {

    private static final String LISTA = "/WEB-INF/jsp/trocas/lista.jsp";
    private static final String FORM = "/WEB-INF/jsp/trocas/form.jsp";

    private final TrocaService trocaService = new TrocaService();
    private final UsuarioService usuarioService = new UsuarioService();
    private final HabilidadeService habilidadeService = new HabilidadeService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        switch (this.acao(req)) {
            case "novo" -> this.form(req, resp, null);
            case "editar" -> this.form(req, resp, this.trocaService.buscarPorId(this.paramLong(req, "id")));
            case "excluir" -> {
                try {
                    this.trocaService.deletar(this.paramLong(req, "id"));
                } catch (IllegalArgumentException e) {
                    req.setAttribute("erro", e.getMessage());
                    req.setAttribute("trocas", this.trocaService.listar());
                    this.forward(req, resp, LISTA);
                    return;
                }
                this.redirect(req, resp, "/trocas");
            }
            default -> {
                req.setAttribute("trocas", this.trocaService.listar());
                this.forward(req, resp, LISTA);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        Troca troca = this.fromRequest(req);

        try {
            this.trocaService.salvar(troca);
            this.redirect(req, resp, "/trocas");
        } catch (IllegalArgumentException e) {
            req.setAttribute("erro", e.getMessage());
            this.form(req, resp, troca);
        }
    }

    private void form(HttpServletRequest req, HttpServletResponse resp, Troca troca)
            throws ServletException, IOException {

        if ("editar".equals(this.acao(req)) && troca == null) {
            this.redirect(req, resp, "/trocas");
            return;
        }

        req.setAttribute("troca", troca);
        req.setAttribute("usuarios", this.usuarioService.listar());
        req.setAttribute("habilidades", this.habilidadeService.listar());
        this.forward(req, resp, FORM);
    }

    private Troca fromRequest(HttpServletRequest req) {
        Troca troca = new Troca();
        troca.setId(this.paramLong(req, "id"));
        troca.setUsuarioOferecendoId(this.paramLong(req, "usuarioOferecendoId"));
        troca.setUsuarioInteressadoId(this.paramLong(req, "usuarioInteressadoId"));
        troca.setHabilidadeId(this.paramLong(req, "habilidadeId"));
        return troca;
    }
}
