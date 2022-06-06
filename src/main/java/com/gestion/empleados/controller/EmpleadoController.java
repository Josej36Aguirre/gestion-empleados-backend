package com.gestion.empleados.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestion.empleados.repository.IEmpleadoRepository;
import com.gestion.empleados.exceptions.ResourceNotFoundException;
import com.gestion.empleados.model.*;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:4200/")
public class EmpleadoController {

	@Autowired
	private IEmpleadoRepository repository;
	
	//Este metodo se encarga de listar todos los empleados
	@GetMapping("/employees")
	public List<Empleado> listAllEmployees(){
		return repository.findAll();
	}
	
	//Este metodop se encarga de guardar un empleado
	@PostMapping("/employees")
	public Empleado saveEmployee(@RequestBody Empleado empleado) {
		return repository.save(empleado);
	}
	
	//Este metodo consulta un empleado por el ID
	@GetMapping("/employees/{id}")
	public ResponseEntity<Empleado> obtainEmployeeById(@PathVariable Long id){
		Empleado empleoyee = repository.findById(id).orElseThrow(()-> 
		new ResourceNotFoundException("NO existe el empleado con el ID: "+id));
		return ResponseEntity.ok(empleoyee);
	}
	
	@PutMapping("/employees/{id}")
	public ResponseEntity<Empleado> updateEmployee(@PathVariable Long id, @RequestBody Empleado detailsEmployee){
		Empleado empleoyee = repository.findById(id).orElseThrow(()-> 
		new ResourceNotFoundException("No existe el empleado con el ID"+id));
		empleoyee.setNombre(detailsEmployee.getNombre());
		empleoyee.setApellido(detailsEmployee.getApellido());
		empleoyee.setEmail(detailsEmployee.getEmail());
		
		Empleado employeeUpdate = repository.save(empleoyee);
		return ResponseEntity.ok(employeeUpdate);
	}
	
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id) {
		Empleado empleoyee = repository.findById(id).orElseThrow(()-> 
		new ResourceNotFoundException("No existe el empleado con el ID"+id));
		
		repository.delete(empleoyee);
		Map<String, Boolean> respuesta = new HashMap<>();
		respuesta.put("Eliminar", Boolean.TRUE);
		return ResponseEntity.ok(respuesta);
	}
}
