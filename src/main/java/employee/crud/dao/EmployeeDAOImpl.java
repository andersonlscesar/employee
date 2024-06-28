package employee.crud.dao;

import employee.crud.bean.Employee;
import employee.crud.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {

    private static Connection connection = DBConnection.getConnection();

    @Override
    public boolean addEmployee(Employee employee) {

        try {
            String query = "INSERT INTO employee(name, email, phone, address) VALUES (?, ?, ?, ?)";
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, employee.getName());
            pst.setString(2, employee.getEmail());
            pst.setString(3, employee.getPhone());
            pst.setString(4, employee.getAddress());
            int result = pst.executeUpdate();
            return result == 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateEmployee(Employee employee) {

        try {
            String query = "UPDATE employee SET name = ?, email = ?, phone = ?, address = ? WHERE id = ? ";
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, employee.getName());
            pst.setString(2, employee.getEmail());
            pst.setString(3, employee.getPhone());
            pst.setString(4, employee.getAddress());
            pst.setInt(5, employee.getId());
            int result = pst.executeUpdate();
            return result == 1;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteEmployee(int employeeId) {

        try {
            String query = "DELETE FROM employee WHERE id = ?";
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setInt(1, employeeId);
            int result = pst.executeUpdate();
            return result == 1;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Employee> getAllEmployee() {

        try {
            String query = "SELECT * FROM employee order by id desc";
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            List<Employee> employees = new ArrayList<Employee>();
            while(rs.next()) {
                Employee employee = new Employee();
                employee.setId(rs.getInt("id"));
                employee.setName(rs.getString("name"));
                employee.setEmail(rs.getString("email"));
                employee.setPhone(rs.getString("phone"));
                employee.setAddress(rs.getString("address"));
                employees.add(employee);
            }
            return employees;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Employee getEmployee(int employeeId) {

        try {
            String query = "SELECT * FROM employee WHERE id = ?";
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setInt(1, employeeId);
            ResultSet rs = pst.executeQuery();
            Employee employee = new Employee();

            if ( rs.next() ) {
                employee.setId(rs.getInt("id"));
                employee.setName(rs.getString("name"));
                employee.setEmail(rs.getString("email"));
                employee.setPhone(rs.getString("phone"));
                employee.setAddress(rs.getString("address"));
            }

            return employee;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
