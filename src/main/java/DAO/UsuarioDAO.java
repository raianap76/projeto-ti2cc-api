package DAO;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Usuario;

public class UsuarioDAO extends DAO {

	public UsuarioDAO() {
		super();
		conectar();
	}

	public void finalize() {
		close();
	}

	public String criptografica(String senha ) throws Exception {
		String s = senha;
		MessageDigest m = MessageDigest.getInstance("MD5");
		m.update(s.getBytes(), 0, s.length());
		System.out.println("Texto Original: " + s);
		s = new BigInteger(1, m.digest()).toString(16);
		System.out.println("Texto Criptografado: " + new BigInteger(1, m.digest()).toString(16));
		return s;
	}

	public boolean insert(Usuario usuario) throws Exception{
		boolean status = false;
		System.out.println("ele entra sim");
		try {

			String sql = "insert into dbti2cc.usuario  (user_id, tipo_usuario, senha)" + "values(?,?,?)";
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.setInt(1, usuario.getUserId());
			stmt.setString(2, usuario.getTipoUsuario());
			stmt.setString(3, criptografica(usuario.getSenha()));

			stmt.execute();
			stmt.close();

			status = true;
			System.out.println("deu erro n�o" + stmt);
		} catch (SQLException u) {
			System.out.println("deu erro ao inserir dados na tabela mas n sei  " + u);

		}
		return status;
	}

	public Usuario[] getUsuario() {
		Usuario[] usuario = null;

		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM dbti2cc.usuario");
			if (rs.next()) {
				rs.last();
				usuario = new Usuario[rs.getRow()];
				rs.beforeFirst();

				for (int i = 0; rs.next(); i++) {
					usuario[i] = new Usuario(rs.getInt("user_id"), rs.getString("tipo_usuario"), rs.getString("senha"));
				}
			}
			st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return usuario;
	}

	public Usuario get(Integer id) {
		Usuario usuario = null;

		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM dbti2cc.usuario WHERE user_id=" + id;
			ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				usuario = new Usuario(rs.getInt("user_id"), rs.getString("tipo_usuario"), rs.getString("senha"));
			}
			st.close();
		} catch (Exception e) {
			System.out.println("execao get " + e);
			System.err.println(e.getMessage());
		}
		return usuario;
	}

	public boolean delete(Integer user_id) {
		boolean status = false;
		try {
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM dbti2cc.usuario WHERE user_id = " + user_id);
			st.close();
			status = true;
		} catch (SQLException u) {
			System.out.println("execao delete " + u);
			throw new RuntimeException(u);
		}
		return status;
	}

	public boolean atualizarUsuario(Usuario usuario) {
		boolean status = false;
		try {
			Statement st = conexao.createStatement();
			String sql = "UPDATE dbti2cc.usuario SET senha = '" + usuario.getSenha() + "', tipo_usuario = '"
					+ usuario.getTipoUsuario() + "' WHERE user_id = " + usuario.getUserId();
			st.executeUpdate(sql);
			System.out.println("atualizado com sucesso " + st);
			st.close();
			status = true;
		} catch (SQLException u) {
			System.out.println("execao put " + u);
			throw new RuntimeException(u);
		}
		return status;
	}
}