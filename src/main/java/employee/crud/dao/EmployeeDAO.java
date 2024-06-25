package employee.crud.dao;

import employee.crud.bean.Employee;

import java.util.List;

public interface EmployeeDAO {
    // 1 - Insert Employee
    public boolean addEmployee(Employee employee);
    // 2 - Update
    public boolean updateEmployee(Employee employee);
    // 3 - Delete
    public boolean deleteEmployee(int employeeId);
    // 4 - Get all
    public List<Employee> getAllEmployee();
    // 5 - Get one
    public Employee getEmployee(int employeeId);
}
