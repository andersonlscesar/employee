package employee.crud.controller;

import employee.crud.bean.Employee;
import employee.crud.dao.EmployeeDAO;
import employee.crud.dao.EmployeeDAOImpl;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

@WebServlet(name = "EmployeeControllerServlet", urlPatterns = {"", "/add", "/get", "/update", "/delete"})
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
        getAllEmployees(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getServletPath();

        switch(action) {
            case "/add":
                addNewEmployee(request, response);
                break;
            case "/update":
                updateEmployee(request, response);
                break;
            case "/delete":
                deleteEmployee(request, response);
                break;
            case "/get":
                getEmployee(request, response);
                break;
            default:
                getAllEmployees(request, response);
                break;
        }
    }

    private void addNewEmployee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name     = request.getParameter("name");
        String email    = request.getParameter("email");
        String phone    = request.getParameter("phone");
        String address  = request.getParameter("address");
        Employee employee = new Employee(name, email, phone, address);
        employeeDAO.addEmployee(employee);
        response.sendRedirect( request.getContextPath() + "/" );
    }

    private void updateEmployee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
        int id = Integer.parseInt(request.getParameter("id"));
        String name     = request.getParameter("name");
        String email    = request.getParameter("email");
        String phone    = request.getParameter("phone");
        String address  = request.getParameter("address");
        Employee employee = new Employee(id, name, email, phone, address);
        employeeDAO.updateEmployee(employee);
        response.sendRedirect( request.getContextPath() + "/");
    }

    private void deleteEmployee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String employeeIds = request.getParameter("employeeIds");
        if (employeeIds != null && !employeeIds.isEmpty()) {
            StringTokenizer tokenizer = new StringTokenizer(employeeIds, ",");
            while (tokenizer.hasMoreElements()) {
                int employeeId = Integer.parseInt(tokenizer.nextToken());
                employeeDAO.deleteEmployee(employeeId);
            }
        }
        response.sendRedirect(request.getContextPath() + "/");
    }


    private void getAllEmployees(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
        List<Employee> employees = employeeDAO.getAllEmployee();
        request.setAttribute("employees", employees);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
        dispatcher.forward(request, response);
    }

    private void getEmployee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
        int id = Integer.parseInt(request.getParameter("employeeId"));
        Employee employee = employeeDAO.getEmployee(id);
        ObjectMapper mapper = new ObjectMapper();
        String employeeStr = mapper.writeValueAsString(employee);
        response.setContentType("application/json");
        ServletOutputStream servletOutputStream = response.getOutputStream();
        servletOutputStream.write(employeeStr.getBytes());
        servletOutputStream.flush();
        servletOutputStream.close();
    }

}