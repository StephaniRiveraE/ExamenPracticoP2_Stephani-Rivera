package com.banquito.core.branch.branch_management.controlador;

import com.banquito.core.branch.branch_management.dto.BranchRQ;
import com.banquito.core.branch.branch_management.modelo.Branch;
import com.banquito.core.branch.branch_management.modelo.BranchHoliday;
import com.banquito.core.branch.branch_management.servicio.BranchServicio;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/branch-management/v1/branch")
@Tag(name = "Sucursal", description = "Endpoints para BanQuito")
@AllArgsConstructor
public class BranchControlador {

    private final BranchServicio servicio;

    @GetMapping
    @Operation(summary = "1. Obtener listado de todas las sucursales")
    public ResponseEntity<List<Branch>> listar() {
        log.info("Peticion recibida para listar todas las sucursales");
        return ResponseEntity.ok(servicio.listarTodas());
    }

    @PostMapping
    @Operation(summary = "2. Crear una Sucursal")
    public ResponseEntity<Branch> crear(@Valid @RequestBody BranchRQ branchRQ) {
        log.info("Peticion recibida para crear sucursal: {}", branchRQ.getName());
        // Mapeo manual del DTO a la Entidad para asegurar persistencia
        Branch branch = Branch.builder()
                .name(branchRQ.getName())
                .emailAddress(branchRQ.getEmailAddress())
                .phoneNumber(branchRQ.getPhoneNumber())
                .build();
        return ResponseEntity.ok(servicio.crear(branch));
    }

    @GetMapping("/{id}")
    @Operation(summary = "3. Obtener sucursal por su ID")
    public ResponseEntity<Branch> obtenerPorId(@PathVariable String id) {
        log.info("Peticion recibida para obtener sucursal con ID: {}", id);
        return ResponseEntity.ok(servicio.obtenerPorId(id));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "4. Modificar teléfono")
    public ResponseEntity<Branch> actualizarTelefono(@PathVariable String id, @RequestParam String phone) {
        log.info("Peticion recibida para actualizar telefono de sucursal ID: {}", id);
        return ResponseEntity.ok(servicio.actualizarTelefono(id, phone));
    }

    @PostMapping("/{id}/holidays")
    @Operation(summary = "5. Crear feriados")
    public ResponseEntity<Void> agregarFeriados(@PathVariable String id, @RequestBody List<BranchHoliday> holidays) {
        log.info("Peticion recibida para agregar {} feriados a sucursal ID: {}", holidays.size(), id);
        servicio.agregarFeriados(id, holidays);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/holidays")
    @Operation(summary = "6. Eliminar feriados")
    public ResponseEntity<Void> eliminarFeriado(@PathVariable String id, @RequestParam LocalDate date) {
        log.info("Peticion recibida para eliminar feriado del {} en sucursal ID: {}", date, id);
        servicio.eliminarFeriado(id, date);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/holidays")
    @Operation(summary = "7. Obtener todos los feriados de una sucursal")
    public ResponseEntity<List<BranchHoliday>> obtenerFeriados(@PathVariable String id) {
        log.info("Peticion recibida para obtener feriados de sucursal ID: {}", id);
        return ResponseEntity.ok(servicio.listarFeriados(id));
    }

    @GetMapping("/{id}/is-holiday")
    @Operation(summary = "8. Verificar si es feriado")
    public ResponseEntity<Boolean> verificarFeriado(@PathVariable String id, @RequestParam LocalDate date) {
        log.info("Peticion recibida para verificar feriado el {} en sucursal ID: {}", date, id);
        return ResponseEntity.ok(servicio.esFeriado(id, date));
    }
}