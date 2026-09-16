package br.com.mvc.model;

public class Troca {

    private Long id;
    private Long usuarioOferecendoId;
    private Long usuarioInteressadoId;
    private Long habilidadeId;
    private Usuario usuarioOferecendo;
    private Usuario usuarioInteressado;
    private Habilidade habilidade;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioOferecendoId() {
        return usuarioOferecendoId;
    }

    public void setUsuarioOferecendoId(Long usuarioOferecendoId) {
        this.usuarioOferecendoId = usuarioOferecendoId;
    }

    public Long getUsuarioInteressadoId() {
        return usuarioInteressadoId;
    }

    public void setUsuarioInteressadoId(Long usuarioInteressadoId) {
        this.usuarioInteressadoId = usuarioInteressadoId;
    }

    public Long getHabilidadeId() {
        return habilidadeId;
    }

    public void setHabilidadeId(Long habilidadeId) {
        this.habilidadeId = habilidadeId;
    }

    public Usuario getUsuarioOferecendo() {
        return usuarioOferecendo;
    }

    public void setUsuarioOferecendo(Usuario usuarioOferecendo) {
        this.usuarioOferecendo = usuarioOferecendo;
    }

    public Usuario getUsuarioInteressado() {
        return usuarioInteressado;
    }

    public void setUsuarioInteressado(Usuario usuarioInteressado) {
        this.usuarioInteressado = usuarioInteressado;
    }

    public Habilidade getHabilidade() {
        return habilidade;
    }

    public void setHabilidade(Habilidade habilidade) {
        this.habilidade = habilidade;
    }
}
