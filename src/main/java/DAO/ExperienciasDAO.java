package DAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Experiencias;

public class ExperienciasDAO extends DAO{

	public ExperienciasDAO() {
		super();
		conectar();
	}
	
	
	public void finalize() {
		close();
	}
	
	public boolean insert(Experiencias experiencias) {
		boolean status = false;
		System.out.println("ele entra sim");
		try {
			Statement st = conexao.createStatement();
			st.executeUpdate("INSERT INTO experiencias.experiencias (user_id, candidato_id, titulo, tipo_emprego, nome_empresa, localidade, data_inicio, data_fim, setor, descricao) "
		               + "VALUES ("+experiencias.getUser_id()+ ", '" + experiencias.getCandidato_id() + "','" + experiencias.getTitulo() + "', '"  
				       + experiencias.getTipo_emprego() + "', '" + experiencias.getNome_empresa() + "', '" + experiencias.getLocalidade() + "', '" + experiencias.getData_inicio() + "', '"
				    		   + experiencias.getData_fim() + "', '" + experiencias.getSetor() + "', '"
		               + experiencias.getDescricao() + "');");
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
	
	public Experiencias[] getExperiencias() {
		Experiencias[] experiencias = null;

		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM experiencias.experiencias");
			if (rs.next()) {
				rs.last();
				experiencias = new Experiencias[rs.getRow()];
				rs.beforeFirst();

				for (int i = 0; rs.next(); i++) {
					experiencias[i] = new Experiencias(rs.getInt("user_id"), rs.getInt("candidato_id"), rs.getString("titulo"), rs.getString("tipo_emprego"),
							rs.getString("nome_empresa"), rs.getString("localidade"), rs.getDate("data_inicio"),
							 rs.getDate("data_fim"), rs.getString("setor"),rs.getString("descricao"));
				}
			}
			st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return experiencias;
	}

	public Experiencias get(Integer id) {
		Experiencias experiencias = null;

		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM experiencias.experiencias WHERE id=" + id;
			ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				experiencias = new Experiencias(rs.getInt("user_id"), rs.getInt("candidato_id"), rs.getString("titulo"), rs.getString("tipo_emprego"),
						rs.getString("nome_empresa"), rs.getString("localidade"), rs.getDate("data_inicio"),
						 rs.getDate("data_fim"), rs.getString("setor"),rs.getString("descricao"));;
			}
			st.close();
		} catch (Exception e) {
			System.out.println("execao get " + e);
			System.err.println(e.getMessage());
		}
		return experiencias;
	}
	
	public boolean delete(Integer user_id) {
		boolean status = false;
		try {
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM experiencias.experiencias WHERE id = " + user_id);
			st.close();
			status = true;
		} catch (SQLException u) {
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public boolean atualizarExperiencias(Experiencias experiencias) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			String sql = "UPDATE experiencias.experiencias SET titulo = '" + experiencias.getTitulo() + "', tipo_emprego = '"  
				       + experiencias.getTipo_emprego() + "', nome_empresa = '" + experiencias.getNome_empresa() + "', localidade = '" + experiencias.getLocalidade() + "', data_inicio = '" + experiencias.getData_inicio() + "', data_fim = '" + experiencias.getData_fim() + "', setor = '" + experiencias.getSetor() + "', descricao = '" + experiencias.getDescricao() + "'"
					   + " WHERE id = " + experiencias.getUser_id();
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
