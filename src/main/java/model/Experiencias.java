package model;

import java.sql.Date;


public class Experiencias {

	private Integer user_id;
	private Integer  candidato_id;
	private String titulo;
	private String  tipo_emprego;
	private String  nome_empresa;
	private String  localidade;
	private Date  data_inicio;
	private Date  data_fim;
	private String setor;
	private String descricao;
	
	
	
	public Experiencias(Integer user_id, Integer candidato_id, String titulo, String tipo_emprego, String nome_empresa,
			String localidade, Date data_inicio, Date data_fim, String setor, String descricao) {
		super();
		this.user_id = user_id;
		this.candidato_id = candidato_id;
		this.titulo = titulo;
		this.tipo_emprego = tipo_emprego;
		this.nome_empresa = nome_empresa;
		this.localidade = localidade;
		this.data_inicio = data_inicio;
		this.data_fim = data_fim;
		this.setor = setor;
		this.descricao = descricao;
	}
	/**
	 * @return the user_id
	 */
	public Integer getUser_id() {
		return user_id;
	}
	/**
	 * @return the candidato_id
	 */
	public Integer getCandidato_id() {
		return candidato_id;
	}
	/**
	 * @return the titulo
	 */
	public String getTitulo() {
		return titulo;
	}
	/**
	 * @return the tipo_emprego
	 */
	public String getTipo_emprego() {
		return tipo_emprego;
	}
	/**
	 * @return the nome_empresa
	 */
	public String getNome_empresa() {
		return nome_empresa;
	}
	/**
	 * @return the localidade
	 */
	public String getLocalidade() {
		return localidade;
	}
	/**
	 * @return the data_inicio
	 */
	public Date getData_inicio() {
		return data_inicio;
	}
	/**
	 * @return the data_fim
	 */public Date getData_fim() {
		return data_fim;
	}
	/**
	 * @return the setor
	 */
	public String getSetor() {
		return setor;
	}
	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}
	/**
	 * @param user_id the user_id to set
	 */
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	/**
	 * @param candidato_id the candidato_id to set
	 */
	public void setCandidato_id(Integer candidato_id) {
		this.candidato_id = candidato_id;
	}
	/**
	 * @param titulo the titulo to set
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	/**
	 * @param tipo_emprego the tipo_emprego to set
	 */
	public void setTipo_emprego(String tipo_emprego) {
		this.tipo_emprego = tipo_emprego;
	}
	/**
	 * @param nome_empresa the nome_empresa to set
	 */
	public void setNome_empresa(String nome_empresa) {
		this.nome_empresa = nome_empresa;
	}
	/**
	 * @param localidade the localidade to set
	 */
	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}
	/**
	 * @param data_inicio the data_inicio to set
	 */
	public void setData_inicio(Date data_inicio) {
		this.data_inicio = data_inicio;
	}
	/**
	 * @param data_fim the data_fim to set
	 */
	public void setData_fim(Date data_fim) {
		this.data_fim = data_fim;
	}
	/**
	 * @param setor the setor to set
	 */
	public void setSetor(String setor) {
		this.setor = setor;
	}
	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	@Override
	public String toString() {
		return "Experiencias [user_id=" + user_id + ", candidato_id=" + candidato_id + ", titulo=" + titulo
				+ ", tipo_emprego=" + tipo_emprego + ", nome_empresa=" + nome_empresa + ", localidade=" + localidade
				+ ", data_inicio=" + data_inicio + ", data_fim=" + data_fim + ", setor=" + setor + ", descricao="
				+ descricao + "]";
	}
		
	
	
	

}
