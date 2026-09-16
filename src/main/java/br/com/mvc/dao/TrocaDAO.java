package br.com.mvc.dao;

import br.com.mvc.model.Habilidade;
import br.com.mvc.model.Troca;
import br.com.mvc.model.Usuario;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO = Data Access Object (acesso ao banco)
 *
 * Somente SQL e conversao ResultSet -> {@link Troca}.
 * Quem decide "quando" chamar cada metodo e o Service / Controller.
 */
public class TrocaDAO extends MysqlDAO {

    private static final String SELECT_BASE =
            "SELECT t.id, t.usuario_oferecendo_id, t.usuario_interessado_id, t.habilidade_id, "
                    + "uo.nome AS oferecendo_nome, ui.nome AS interessado_nome, h.nome AS habilidade_nome "
                    + "FROM trocas t "
                    + "INNER JOIN usuarios uo ON uo.id = t.usuario_oferecendo_id "
                    + "INNER JOIN usuarios ui ON ui.id = t.usuario_interessado_id "
                    + "INNER JOIN habilidades h ON h.id = t.habilidade_id ";

    public TrocaDAO() {
        super();
    }

    public List<Troca> listarTodos() {
        String sql = SELECT_BASE + "ORDER BY t.id";
        List<Troca> lista = new ArrayList<>();
        try (ResultSet rs = super.executar(sql)) {
            while (rs.next()) {
                lista.add(this.mapear(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar trocas.", e);
        }
        return lista;
    }

    public Troca buscarPorId(Long id) {
        String sql = SELECT_BASE + "WHERE t.id = ?";
        try (ResultSet rs = super.executar(sql, id)) {
            if (rs.next()) {
                return this.mapear(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar troca por id.", e);
        }
        return null;
    }

    public void inserir(Troca troca) {
        String sql =
                "INSERT INTO trocas (usuario_oferecendo_id, usuario_interessado_id, habilidade_id) "
                        + "VALUES (?, ?, ?)";
        try {
            super.executarUpdate(
                    sql,
                    troca.getUsuarioOferecendoId(),
                    troca.getUsuarioInteressadoId(),
                    troca.getHabilidadeId());
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir troca.", e);
        }
    }

    public void alterar(Troca troca) {
        String sql =
                "UPDATE trocas SET usuario_oferecendo_id = ?, usuario_interessado_id = ?, habilidade_id = ? "
                        + "WHERE id = ?";
        try {
            super.executarUpdate(
                    sql,
                    troca.getUsuarioOferecendoId(),
                    troca.getUsuarioInteressadoId(),
                    troca.getHabilidadeId(),
                    troca.getId());
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao alterar troca.", e);
        }
    }

    public void deletar(Long id) {
        String sql = "DELETE FROM trocas WHERE id = ?";
        try {
            super.executarUpdate(sql, id);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar troca.", e);
        }
    }

    public long contarPorUsuarioId(Long usuarioId) {
        String sql =
                "SELECT COUNT(*) FROM trocas "
                        + "WHERE usuario_oferecendo_id = ? OR usuario_interessado_id = ?";
        try (ResultSet rs = super.executar(sql, usuarioId, usuarioId)) {
            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao contar trocas por usuario.", e);
        }
        return 0;
    }

    public long contarPorHabilidadeId(Long habilidadeId) {
        String sql = "SELECT COUNT(*) FROM trocas WHERE habilidade_id = ?";
        try (ResultSet rs = super.executar(sql, habilidadeId)) {
            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao contar trocas por habilidade.", e);
        }
        return 0;
    }

    private Troca mapear(ResultSet rs) throws SQLException {
        Troca troca = new Troca();
        troca.setId(rs.getLong("id"));
        troca.setUsuarioOferecendoId(rs.getLong("usuario_oferecendo_id"));
        troca.setUsuarioInteressadoId(rs.getLong("usuario_interessado_id"));
        troca.setHabilidadeId(rs.getLong("habilidade_id"));

        Usuario oferecendo = new Usuario();
        oferecendo.setId(rs.getLong("usuario_oferecendo_id"));
        oferecendo.setNome(rs.getString("oferecendo_nome"));
        troca.setUsuarioOferecendo(oferecendo);

        Usuario interessado = new Usuario();
        interessado.setId(rs.getLong("usuario_interessado_id"));
        interessado.setNome(rs.getString("interessado_nome"));
        troca.setUsuarioInteressado(interessado);

        Habilidade habilidade = new Habilidade();
        habilidade.setId(rs.getLong("habilidade_id"));
        habilidade.setNome(rs.getString("habilidade_nome"));
        troca.setHabilidade(habilidade);

        return troca;
    }
}
