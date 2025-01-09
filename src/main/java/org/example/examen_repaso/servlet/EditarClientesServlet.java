package org.example.examen_repaso.servlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.examen_repaso.dao.ClienteDAOImpl;
import org.example.examen_repaso.model.Cliente;


import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "EditarClientesServlet", value = "/EditarClientesServlet")
public class EditarClientesServlet extends HttpServlet {

    private ClienteDAOImpl socioDAO = new ClienteDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("socioId");

        //Si el id no es nulo editamos la pag.
        if (idParam != null) {
            try {
                int id = Integer.parseInt(idParam);
                Optional<Cliente> socio = socioDAO.find(id);
                // Comprobamos si la categoría está dentro del rango permitido (1-10 en este caso)
                //if (cliente.getCategoria() >= 1 && cliente.getCategoria() <= 10) {
                //    request.setAttribute("codigo", cliente);
                //} else {
                //    request.setAttribute("error", "La categoría del socio está fuera del rango permitido (1-5).");
                //}
                if (socio.isPresent()) {
                    request.setAttribute("codigo", socio.get());
                } else {
                    request.setAttribute("error", "No se encontró el socio con ese ID.");
                }
            } catch (NumberFormatException e) {
                request.setAttribute("error", "El ID no es válido. Asegúrese de que el ID es un número.");
            }
        } else {
            //En caso contrario muestra un error
            request.setAttribute("error", "El parámetro 'id' es obligatorio.");
        }

        //LO que no se es por qué al editarlo, me crea un nuevo socio en vez de modificarme el que tenia... Curioso!
        request.getRequestDispatcher("/WEB-INF/jsp/formularioSocioB.jsp").forward(request, response);
    }

}