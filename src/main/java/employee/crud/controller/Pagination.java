package employee.crud.controller;

import java.io.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet(name = "Pagination", value = "/Pagination-servlet")
public class Pagination extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final int ENTRIES_PER_PAGE = 5;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int page = 1;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        // Simulate fetching data from a database
        int totalEntries = 25; // Example total entries
        int start = (page - 1) * ENTRIES_PER_PAGE;
        int end = Math.min(start + ENTRIES_PER_PAGE, totalEntries);

        // Create JSON response
        JSONObject jsonResponse = new JSONObject();
        JSONArray entries = new JSONArray();

        for (int i = start; i < end; i++) {
            JSONObject entry = new JSONObject();
            entry.put("id", i + 1);
            entry.put("name", "Employee " + (i + 1));
            entries.put(entry);
        }

        jsonResponse.put("entries", entries);
        jsonResponse.put("currentPage", page);
        jsonResponse.put("totalPages", (int) Math.ceil((double) totalEntries / ENTRIES_PER_PAGE));
        jsonResponse.put("totalEntries", totalEntries);

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(jsonResponse.toString());
        out.flush();
    }

}