package org.example.aurorajewelry;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;


@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setAttribute("message", "Welcome to Aurora Jewelry!");
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }


    public void destroy() {
    }
}