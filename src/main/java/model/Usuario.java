package model;

public class Usuario {

	private Integer user_id;
	private String tipo_usuario;
	private String senha;

	public Usuario(Integer user_id, String tipo_usuario, String senha) {
		super();
		this.user_id = user_id;
		this.tipo_usuario = tipo_usuario;
		this.senha = senha;

	}

	public Integer getUserId() {
		return user_id;
	}

	public void setUserId(Integer user_id) {
		this.user_id = user_id;
	}

	public String getTipoUsuario() {
		return tipo_usuario;
	}

	public void setTipoUsuario(String tipo_usuario) {
		this.tipo_usuario = tipo_usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
}