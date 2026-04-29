package web.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import web.service.LoginService;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        System.out.println("[LoginServlet] GET");
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        System.out.println("[LoginServlet] POST");

        String username = req.getParameter("username");
        String password = req.getParameter("passwd");
        String dob      = req.getParameter("dob");
        System.out.println("Username/password/dob: " + username + ", " + password + ", " + dob);

        boolean ok = LoginService.login(username, password, dob);
        String status = ok ? "success" : "fail";

        StringBuilder html = new StringBuilder();
        html.append("<html>")
            .append("<head><title>").append(status).append("</title></head>")
            .append("<body>")
            .append("<h2 id=\"status\">Login status: ").append(status).append("</h2>")
            .append("</body>")
            .append("</html>");

        resp.setContentType("text/html");
        resp.setStatus(ok ? HttpServletResponse.SC_OK : HttpServletResponse.SC_UNAUTHORIZED);
        PrintWriter writer = resp.getWriter();
        writer.println(html.toString());
    }
}