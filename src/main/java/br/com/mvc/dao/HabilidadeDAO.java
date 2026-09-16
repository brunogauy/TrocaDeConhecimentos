package br.com.mvc.dao;

import br.com.mvc.model.Habilidade;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO = Data Access Object (acesso ao banco)
 *
 * Somente SQL e conversao ResultSet -> {@link Habilidade}.
 * Quem decide "quando" chamar cada metodo e o Service / Controller.
 */
public class HabilidadeDAO extends MysqlDAO {

    public HabilidadeDAO() {
        super();
    }

    public List<Habilidade> listarTodos() {
        String sql = "SELECT id, nome FROM habilidades ORDER BY id";
        List<Habilidade> lista = new ArrayList<>();
        try (ResultSet rs = super.executar(sql)) {
            while (rs.next()) {
                lista.add(this.mapear(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar habilidades.", e);
        }
        return lista;
    }

    public Habilidade buscarPorId(Long id) {
        String sql = "SELECT id, nome FROM habilidades WHERE id = ?";
        try (ResultSet rs = super.executar(sql, id)) {
            if (rs.next()) {
                return this.mapear(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar habilidade por id.", e);
        }
        return null;
    }

    public Habilidade buscarPorNome(String nome) {
        String sql = "SELECT id, nome FROM habilidades WHERE nome = ?";
        try (ResultSet rs = super.executar(sql, nome)) {
            if (rs.next()) {
                return this.mapear(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar habilidade por nome.", e);
        }
        return null;
    }

    public void inserir(Habilidade habilidade) {
        String sql = "INSERT INTO habilidades (nome) VALUES (?)";
        try {
            super.executarUpdate(sql, habilidade.getNome());
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir habilidade.", e);
        }
    }

    public void alterar(Habilidade habilidade) {
        String sql = "UPDATE habilidades SET nome = ? WHERE id = ?";
        try {
            super.executarUpdate(sql, habilidade.getNome(), habilidade.getId());
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao alterar habilidade.", e);
        }
    }

    public void deletar(Long id) {
        String sql = "DELETE FROM habilidades WHERE id = ?";
        try {
            super.executarUpdate(sql, id);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar habilidade.", e);
        }
    }

    private Habilidade mapear(ResultSet rs) throws SQLException {
        Habilidade habilidade = new Habilidade();
        habilidade.setId(rs.getLong("id"));
        habilidade.setNome(rs.getString("nome"));
        return habilidade;
    }
}
