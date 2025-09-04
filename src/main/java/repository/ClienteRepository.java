package com.empresa.app.repository;

import com.empresa.app.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Date;
import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    List<Cliente> findByNomeContainingIgnoreCase(String nome);
    List<Cliente> findByDataCadastroBetween(Date inicio, Date fim);
}
