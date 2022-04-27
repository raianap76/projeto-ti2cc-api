package com.knf.spark.api.demo;

public class EmployeeServiceImpl implements EmployeeService {
	private EmployeeDAO employeeDao = new EmployeeDAO();

	@Override
	public void addEmployee(Employee emp) {
		// employeeMap.put(emp.getId(), emp);
		System.out.print(emp);
	}

	@Override
	public boolean addEmployeeBanco(Employee emp) {
		Employee employee = new Employee(emp.getId(), emp.getFirstName(), emp.getLastName(), emp.getEmail());
		boolean status = false;
		if (employeeDao.insert(employee) == true) {
			status = true;
		}
		return status;
	}

	@Override
	public Employee[] getEmployees() {
		return employeeDao.getEmployee();

	}

	@Override
	public Employee getEmployee(String id) {
		return employeeDao.get(id);
	}

	@Override
	public boolean editEmployee(Employee forEdit) throws EmployeeException {
		try {
			if (forEdit.getId() == null)
				return false;

			Employee toEdit = employeeDao.get(forEdit.getId());

			if (toEdit == null)
				return false;

			if (forEdit.getEmail() != null) {
				toEdit.setEmail(forEdit.getEmail());
			}
			if (forEdit.getFirstName() != null) {
				toEdit.setFirstName(forEdit.getFirstName());
			}
			if (forEdit.getLastName() != null) {
				toEdit.setLastName(forEdit.getLastName());
			}

			boolean status = false;
			System.out.println("edit"+toEdit);
			if (employeeDao.atualizarUsuario(toEdit) == true) {
				status = true;
			}
			return status;
		} catch (Exception ex) {
		
			throw new EmployeeException(ex.getMessage());
		}
	}

	@Override
	public void deleteEmployee(String id) {
		employeeDao.delete(id);
	}

	@Override
	public boolean employeeExist(String id) {
		// return employeeMap.containsKey(id);
		return false;
	}

}