package model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Empleado {

	Integer codigoEmpleado;

	String nombre;
	Departamento departamento;
	Double salario;

	@Override
	public String toString() {
		return "\n" + String.format("%d | %s | %d| %s", codigoEmpleado, nombre, departamento.getCodigoDepartamento(),
				salario);

	}

}
