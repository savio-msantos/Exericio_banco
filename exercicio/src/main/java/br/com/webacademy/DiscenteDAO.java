package br.com.webacademy;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DiscenteDAO {
    public void salvar(Discente discente) throws Exception {
        var sql = "INSERT INTO discente (nome, periodoAtual, matricula) VALUES (?, ?, ?)";
        try (var conexao = Conexao.obterConexao();
             var statement = conexao.prepareStatement(sql)) {
            statement.setString(1, discente.nome());
            statement.setInt(2, discente.periodoAtual());
            statement.setDouble(3, discente.matricula());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Erro ao salvar o discente: " + e.getMessage());
        }
    }

    public List<Discente> buscarTodos() throws Exception {
        var sql = "SELECT * FROM discente";
        List<Discente> discentes = new ArrayList<>();
        try (var conexao = Conexao.obterConexao();
             var statement = conexao.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                discentes.add(new Discente(
                        resultSet.getLong("id"),
                        resultSet.getString("nome"),
                        resultSet.getInt("periodoAtual"),
                        resultSet.getDouble("matricula")));
            }
        } catch (SQLException e) {
            throw new Exception(e);
        }
        return discentes;
    }

    public Discente buscarPorId(Long id) throws Exception {
        var sql = "SELECT * FROM discente WHERE id = ?";
        try (var conexao = Conexao.obterConexao();
             var statement = conexao.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Discente(
                            resultSet.getLong("id"),
                            resultSet.getString("nome"),
                            resultSet.getInt("periodoAtual"),
                            resultSet.getDouble("matricula"));
                }
            }
        }
        return null;
    }

    public void atualizar(Discente discente) throws Exception {
        var sql = "UPDATE discente SET nome = ?, periodoAtual = ?, matricula = ? WHERE id = ?";
        try (var conexao = Conexao.obterConexao();
             var statement = conexao.prepareStatement(sql)) {
            statement.setString(1, discente.nome());
            statement.setInt(2, discente.periodoAtual());
            statement.setDouble(3, discente.matricula());
            statement.setLong(4, discente.id());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

    public void excluir(Long id) throws Exception {
        var sql = "DELETE FROM discente WHERE id = ?";
        try (var conexao = Conexao.obterConexao();
             var statement = conexao.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }
}