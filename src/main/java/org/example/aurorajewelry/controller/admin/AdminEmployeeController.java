package org.example.aurorajewelry.controller.admin;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

import org.example.aurorajewelry.dao.EmployeeDAO;
import org.example.aurorajewelry.model.Employee;

import java.io.IOException;

@WebServlet(name="adminEmployees", urlPatterns={"/admin/employees"})
public class AdminEmployeeController extends HttpServlet {
    private EmployeeDAO employeeDAO = new EmployeeDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("employees", employeeDAO.findAll());
        req.getRequestDispatcher("/views/admin/employees.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("create".equals(action)) {
            Employee e = new Employee();
            e.setFullName(req.getParameter("fullname"));
            e.setEmail(req.getParameter("email"));
            e.setPassword(req.getParameter("password"));
            e.setRole(req.getParameter("role"));
            employeeDAO.create(e);
        } else if ("update".equals(action)) {
            Employee e = new Employee();
            e.setEmployeeId(Integer.parseInt(req.getParameter("id")));
            e.setFullName(req.getParameter("fullname"));
            e.setEmail(req.getParameter("email"));
            e.setPassword(req.getParameter("password"));
            e.setRole(req.getParameter("role"));
            employeeDAO.update(e);
        } else if ("delete".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            employeeDAO.delete(id);
        }
        resp.sendRedirect(req.getContextPath()+"/admin/employees");
    }
}

