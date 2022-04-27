package com.knf.spark.api.demo;


public interface EmployeeService {
	public void addEmployee(Employee employee);
	public boolean addEmployeeBanco(Employee employee);
	public Employee[] getEmployees();

	public Employee getEmployee(String id);

	public boolean editEmployee(Employee employee) throws EmployeeException;

	public void deleteEmployee(String id);

	public boolean employeeExist(String id);
}