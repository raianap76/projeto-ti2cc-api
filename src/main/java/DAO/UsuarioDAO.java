package dao;
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
	
	
	public boolean insert(Usuario usuario) {
		boolean status = false;
		System.out.println("ele entra sim");
		try {
			Statement st = conexao.createStatement();
			st.executeUpdate("INSERT INTO usuario.usuario (user_id, tipo_usuario, senha) "
		               + "VALUES ("+usuario.getUserId()+ ", '" + usuario.getTipoUsuario() + "', '"  
				       + usuario.getSenha() + "', );");
		  //  st.setTimestamp(1, Timestamp.valueOf(employee.firstName()));
			//st.setDate(2, Date.valueOf(employee.firstName()));
			//st.executeUpdate();
			
			st.close();
			status = true;
			System.out.println("deu erro não" + st);
		} catch (SQLException u) {  
			System.out.println("deu erro ao inserir dados na tabela mas n sei  "+ u);
			
		}
		return status;
	}

	public Usuario[] getUsuario() {
		Usuario[] usuario = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM usuario.usuario");		
	         if(rs.next()){
	             rs.last();
	             usuario = new Usuario[rs.getRow()];
	             rs.beforeFirst();

	             for(int i = 0; rs.next(); i++) {
	            	 usuario[i] = new Usuario(rs.getString("user_id"), rs.getString("tipo_usuario"), 
	                		                  rs.getString("senha"));
	             }
	          }
	          st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return usuario;
	}
	public Usuario get(String id) {
		Usuario usuario = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM usuario.usuario WHERE id="+id;
			ResultSet rs = st.executeQuery(sql);	
	        if(rs.next()){            
	        	usuario = new Usuario(rs.getString("user_id"), rs.getString("tipo_usuario"), 
		                  rs.getString("senha"));
	        }
	        st.close();
		} catch (Exception e) {
			System.out.println("execao get " +e);
			System.err.println(e.getMessage());
		}
		return usuario;
	}
	
	public boolean delete(String user_id) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM usuario.usuario WHERE id = " + user_id);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	public boolean atualizarUsuario(Usuario usuario) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			String sql = "UPDATE usuario.usuario SET senha = '" + usuario.getSenha() + "', tipoUsuario = '"  
				       + usuario.getTipoUsuario() + "', email = '" + " WHERE id = " + usuario.getUserId();
			st.executeUpdate(sql);
			System.out.println("atualizado com sucesso " +st);
			st.close();
			status = true;
		} catch (SQLException u) {  
			System.out.println("execao put " +u);
			throw new RuntimeException(u);
		}
		return status;
	}
}