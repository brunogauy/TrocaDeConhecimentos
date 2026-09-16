package br.com.mvc.controller;

import br.com.mvc.model.Habilidade;
import br.com.mvc.service.HabilidadeService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Controller de Habilidade.
 * Ponte entre rota, service e view — sem regra de negocio.
 */
@WebServlet("/habilidades")
public class HabilidadeServlet extends BaseServlet {

    private static final String LISTA = "/WEB-INF/jsp/habilidades/lista.jsp";
    private static final String FORM = "/WEB-INF/jsp/habilidades/form.jsp";

    private final HabilidadeService habilidadeService = new HabilidadeService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        switch (this.acao(req)) {
            case "novo" -> this.form(req, resp, null);
            case "editar" -> this.form(req, resp, this.habilidadeService.buscarPorId(this.paramLong(req, "id")));
            case "excluir" -> {
                try {
                    this.habilidadeService.deletar(this.paramLong(req, "id"));
                } catch (IllegalArgumentException e) {
                    req.setAttribute("erro", e.getMessage());
                    req.setAttribute("habilidades", this.habilidadeService.listar());
                    this.forward(req, resp, LISTA);
                    return;
                }
                this.redirect(req, resp, "/habilidades");
            }
            default -> {
                req.setAttribute("habilidades", this.habilidadeService.listar());
                this.forward(req, resp, LISTA);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        Habilidade habilidade = this.fromRequest(req);

        try {
            this.habilidadeService.salvar(habilidade);
            this.redirect(req, resp, "/habilidades");
        } catch (IllegalArgumentException e) {
            req.setAttribute("erro", e.getMessage());
            this.form(req, resp, habilidade);
        }
    }

    private void form(HttpServletRequest req, HttpServletResponse resp, Habilidade habilidade)
            throws ServletException, IOException {

        if ("editar".equals(this.acao(req)) && habilidade == null) {
            this.redirect(req, resp, "/habilidades");
            return;
        }

        req.setAttribute("habilidade", habilidade);
        this.forward(req, resp, FORM);
    }

    private Habilidade fromRequest(HttpServletRequest req) {
        Habilidade habilidade = new Habilidade();
        habilidade.setId(this.paramLong(req, "id"));
        habilidade.setNome(this.param(req, "nome"));
        return habilidade;
    }
}
