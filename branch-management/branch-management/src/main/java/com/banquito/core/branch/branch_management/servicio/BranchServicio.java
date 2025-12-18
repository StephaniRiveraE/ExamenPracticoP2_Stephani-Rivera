package com.banquito.core.branch.branch_management.servicio;

import com.banquito.core.branch.branch_management.modelo.Branch;
import com.banquito.core.branch.branch_management.modelo.BranchHoliday;
import com.banquito.core.branch.branch_management.repositorio.BranchRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class BranchServicio {

    private final BranchRepository repositorio;

    public List<Branch> listarTodas() {
        log.info("Obteniendo listado de todas las sucursales");
        return repositorio.findAll();
    }

    public Branch obtenerPorId(String id) {
        log.info("Buscando sucursal por ID: {}", id);
        return repositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));
    }

    @Transactional
    public Branch crear(Branch branch) {
        log.info("Creando nueva sucursal: {}", branch.getName());
        branch.setState("ACTIVE");
        branch.setCreationDate(LocalDateTime.now());
        branch.setLastModifiedDate(LocalDateTime.now());
        branch.setBranchHolidays(new ArrayList<>());
        return repositorio.save(branch);
    }

    @Transactional
    public Branch actualizarTelefono(String id, String nuevoTelefono) {
        log.info("Actualizando telefono de sucursal: {}", id);
        Branch branch = obtenerPorId(id);
        branch.setPhoneNumber(nuevoTelefono);
        branch.setLastModifiedDate(LocalDateTime.now());
        return repositorio.save(branch);
    }

    @Transactional
    public void agregarFeriados(String id, List<BranchHoliday> holidays) {
        log.info("Agregando feriados a sucursal: {}", id);
        Branch branch = obtenerPorId(id);
        branch.getBranchHolidays().addAll(holidays);
        repositorio.save(branch);
    }

    @Transactional
    public void eliminarFeriado(String id, LocalDate fecha) {
        log.info("Eliminando feriado del {} en sucursal {}", fecha, id);
        Branch branch = obtenerPorId(id);
        branch.getBranchHolidays().removeIf(h -> h.getDate().equals(fecha));
        repositorio.save(branch);
    }

    public List<BranchHoliday> listarFeriados(String id) {
        log.info("Consultando todos los feriados para la sucursal: {}", id);
        return obtenerPorId(id).getBranchHolidays();
    }

    public boolean esFeriado(String id, LocalDate fecha) {
        log.info("Verificando si la fecha {} es feriado en sucursal {}", fecha, id);
        Branch branch = obtenerPorId(id);
        return branch.getBranchHolidays().stream()
                .anyMatch(h -> h.getDate().equals(fecha));
    }
}