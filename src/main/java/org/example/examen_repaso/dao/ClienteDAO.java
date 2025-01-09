package org.example.examen_repaso.dao;

import org.example.examen_repaso.model.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteDAO {

    public void create(Cliente cliente);

    public List<Cliente> getAll();
    public Optional<Cliente> find(int id);

    public void update(Cliente socio);

    public void delete(int id);

    public void save(Cliente cliente);
}

