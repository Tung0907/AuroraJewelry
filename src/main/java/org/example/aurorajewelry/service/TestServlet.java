package org.example.aurorajewelry.service;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;


import java.io.IOException;

@WebServlet("/test")
public class TestServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.getWriter().write("TEST OK");
    }
}
