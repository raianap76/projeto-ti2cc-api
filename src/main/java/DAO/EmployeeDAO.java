package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Employee;






public class EmployeeDAO extends DAO {	
	
	public EmployeeDAO() {
		super();
		conectar();
	}
	
	
	public void finalize() {
		close();
	}
	
	
	public boolean insert(Employee employee) {
		boolean status = false;
		System.out.println("ele entra sim");
		try {
			Statement st = conexao.createStatement();
			st.executeUpdate("INSERT INTO employee.employee (id, firstname, lastname, email) "
		               + "VALUES ("+employee.getId()+ ", '" + employee.getFirstName() + "', '"  
				       + employee.getLastName() + "', '" + employee.getEmail() + "');");
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

	public Employee[] getEmployee() {
		Employee[] employee = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM employee.employee");		
	         if(rs.next()){
	             rs.last();
	             employee = new Employee[rs.getRow()];
	             rs.beforeFirst();

	             for(int i = 0; rs.next(); i++) {
	            	 employee[i] = new Employee(rs.getString("id"), rs.getString("firstname"), 
	                		                  rs.getString("lastname"), rs.getString("email"));
	             }
	          }
	          st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return employee;
	}
	public Employee get(String id) {
		Employee employee = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM employee.employee WHERE id="+id;
			ResultSet rs = st.executeQuery(sql);	
	        if(rs.next()){            
	        	employee = new Employee(rs.getString("id"), rs.getString("firstname"), 
		                  rs.getString("lastname"), rs.getString("email"));
	        }
	        st.close();
		} catch (Exception e) {
			System.out.println("execao get " +e);
			System.err.println(e.getMessage());
		}
		return employee;
	}
	
	public boolean delete(String id) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM employee.employee WHERE id = " + id);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	public boolean atualizarUsuario(Employee employee) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			String sql = "UPDATE employee.employee SET firstname = '" + employee.getFirstName() + "', lastname = '"  
				       + employee.getLastName() + "', email = '" + employee.getEmail() + "'"
					   + " WHERE id = " + employee.getId();
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