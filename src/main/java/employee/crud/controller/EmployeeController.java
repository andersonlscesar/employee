package employee.crud.controller;



import employee.crud.bean.Employee;
import employee.crud.dao.EmployeeDAO;
import employee.crud.dao.EmployeeDAOImpl;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "EmployeeControllerServlet", value = "/EmployeeController-servlet")
public class EmployeeController extends HttpServlet {

    EmployeeDAO employeeDAO = null;

    public EmployeeController() {
        super();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        employeeDAO = new EmployeeDAOImpl();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getServletPath();

        switch(action) {
            case "add":
            {
                addNewEmployee(request, response);
                break;
            }
            case "update":
            {
                updateEmployee(request, response);
                break;
            }
            case "delete":
            {
                deleteEmployee(request, response);
                break;
            }
            case "list":
            {
                getAllEmployees(request, response);
                break;
            }
            case "get":
            {
                getEmployee(request, response);
                break;
            }

        }
    }

    private void addNewEmployee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    private void updateEmployee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
    }

    private void deleteEmployee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
    }

    private void getAllEmployees(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
        List<Employee> employees = employeeDAO.getAllEmployee();
        request.setAttribute("employees", employees);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
        dispatcher.forward(request, response);
    }

    private void getEmployee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
    }

}