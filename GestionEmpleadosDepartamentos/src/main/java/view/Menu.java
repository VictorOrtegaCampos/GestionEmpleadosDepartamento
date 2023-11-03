package view;

import java.util.List;
import dao.GestionEmpleadoDepartamento;
import io.IO;
import model.Departamento;
import model.Empleado;

public class Menu {

	public static void main(String[] args) {

		GestionEmpleadoDepartamento administracion = new GestionEmpleadoDepartamento();

		while (true) {
			
			System.out.println("""
					\n\n1.Buscar empleado por codigo
					2.Buscar departamento por codigo
					3.Mostrar departamentos
					4.Mostrar empleados
					5.Añadir un empleado
					6.Añadir un departamento
					7.Eliminar un empleado
					8.Eliminar un departamento
					9.Buscar empleado por nombre
					10.Buscar departamento por nombre
					11.Modificar empleado
					12.Modificar departamento
					13.Salir""");
			
			switch (IO.readInt()) {
			
			case 1:
				buscarEmpleadoPorCodigo(administracion);
				break;
			case 2:
				buscarDepartamentoPorCodigo(administracion);
				break;
			case 3:
				mostrarDepartamentos(administracion);
				break;
			case 4:
				mostrarEmpleados(administracion);
				break;
			case 5:
				anadirEmpleado(administracion);
				break;
			case 6:
				anadirDepartamento(administracion);
				break;
			case 7:
				borrarEmpleado(administracion);
				break;
			case 8:
				borrarDepartamento(administracion);
				break;
			case 9:
				buscarEmpleadoPorInicioDelNombre(administracion);
				break;
			case 10:
				buscarDepartamentoPorInicioDelNombre(administracion);
				break;
			case 11:
				modificarEmpleado(administracion);
				break;
			case 12:
				modificarDepartamento(administracion);
				break;
			case 13:
				cerrarBd(administracion);
				return;
			default:
				
			}
		}

	}
	
	/**
	 * Cerrar bases de datos
	 * @param administracion
	 */

	private static void cerrarBd(GestionEmpleadoDepartamento administracion) {
		administracion.close();
		;
	}

	/**
	 * Borrar departamento
	 * @param administracion
	 */
	
	private static void borrarDepartamento(GestionEmpleadoDepartamento administracion) {
		IO.print("Código ? ");
		Integer id = IO.readInt();
		boolean borrado = administracion.deleteDepartamento(id);
		IO.println(borrado ? "Borrado" : "No se ha podido borrar");
	}

	/**
	 * Borrar empleado
	 * @param administracion
	 */
	
	private static void borrarEmpleado(GestionEmpleadoDepartamento administracion) {
		IO.print("Código ? ");
		Integer id = IO.readInt();
		boolean borrado = administracion.deleteEmpleado(id);
		IO.println(borrado ? "Borrado" : "No se ha podido borrar");
	}

	/**
	 * Añadir empleado 
	 * @param administracion
	 */
	
	private static void anadirEmpleado(GestionEmpleadoDepartamento administracion) {
		IO.print("Nombre ? ");
		String nombre = IO.readString();
		IO.print("Salario ? ");
		Double salario = IO.readDouble();
		IO.print("Codigo departamento ? ");
		Departamento departamento = Departamento.builder().codigoDepartamento(IO.readInt()).build();

		boolean anadido = administracion
				.addEmpleado(Empleado.builder().nombre(nombre).salario(salario).departamento(departamento).build());
		IO.println(anadido ? "Añadido" : "No se ha podido añadir");
	}

	/**
	 * Añadir departamento
	 * @param administracion
	 */
	
	private static void anadirDepartamento(GestionEmpleadoDepartamento administracion) {
		IO.print("Nombre ? ");
		String nombre = IO.readString();
		IO.print("Codigo jefe ? ");
		Integer idJefe = IO.readInt();

		boolean anadido = administracion.addDepartamento(
				Departamento.builder().nombre(nombre).jefe(Empleado.builder().codigoEmpleado(idJefe).build()).build());
		IO.println(anadido ? "Añadido" : "No se ha podido añadir");
	}

	/**
	 * Mostrar empleados
	 * @param administracion
	 */
	
	private static void mostrarEmpleados(GestionEmpleadoDepartamento administracion) {
		IO.print(administracion.showEmpleados());
		
	}

	/**
	 * Mostrar departamento
	 * @param administracion
	 */
	
	private static void mostrarDepartamentos(GestionEmpleadoDepartamento administracion) {
		IO.print(administracion.showDepartamentos());
	}

	/**
	 * Buscar empleado por nombre
	 * @param administracion
	 */
	
	private static void buscarEmpleadoPorInicioDelNombre(GestionEmpleadoDepartamento administracion) {
		IO.print("El nombre empieza por ? ");
		String inicio = IO.readString();
		List<Empleado> empleados = administracion.buscarEmpleadoNombre(inicio);
		IO.println(empleados);
	}

	/**
	 * Buscar departamento por nombre
	 * @param administracion
	 */
	
	private static void buscarDepartamentoPorInicioDelNombre(GestionEmpleadoDepartamento administracion) {
		IO.print("El nombre empieza por ? ");
		String inicio = IO.readString();
		List<Departamento> departamentos = administracion.buscarDepartamentoNombre(inicio);
		IO.println(departamentos);
	}
	
	/**
	 * Buscar empleado por codigo
	 * @param administracion
	 */

	private static void buscarEmpleadoPorCodigo(GestionEmpleadoDepartamento administracion) {
		IO.print("Código ? ");
		Integer id = IO.readInt();
		Empleado e = administracion.buscarEmpleadoId(id);
		IO.println(e);
	}

	/**
	 * Buscar departamento por codigo
	 * @param administracion
	 */
	
	private static void buscarDepartamentoPorCodigo(GestionEmpleadoDepartamento administracion) {
		IO.print("Código ? ");
		Integer id = IO.readInt();
		Departamento d = administracion.buscarDepartamentoId(id);
		IO.println(d);
	}

	/**
	 * Modificar empleado
	 * @param administracion
	 */
	
	private static void modificarEmpleado(GestionEmpleadoDepartamento administracion) {
		IO.print("Código del empleado a modificar ? ");
		Integer id = IO.readInt();
		Empleado empleado = administracion.buscarEmpleadoId(id);
		IO.print("Nombre ? ");
		String nombre = IO.readString();
		IO.print("Departamento ?");
		Integer idDepartamento = IO.readInt();
		Departamento d = Departamento.builder().codigoDepartamento(idDepartamento).build();
		IO.print("Salario ? ");
		double salario = IO.readDouble();
		boolean anadido = false;
		if (empleado != null) {
			if (!nombre.isBlank()) {
				empleado.setNombre(nombre);
			}
			empleado.setDepartamento(d);
			empleado.setSalario(salario);
		}

		anadido = administracion.updateEmpleado(empleado);
		IO.println(anadido ? "Modificado" : "No se ha podido modificar");
	}

	/**
	 * Modificar departamento
	 * @param administracion
	 */
	
	private static void modificarDepartamento(GestionEmpleadoDepartamento administracion) {
		IO.print("Código del departamento a modificar ? ");
		Integer id = IO.readInt();
		Departamento departamento = administracion.buscarDepartamentoId(id);
		IO.print("Nombre ? ");
		String nombre = IO.readString();
		IO.print("Jefe ?");
		Integer idJefe = IO.readInt();
		Empleado e = Empleado.builder().codigoEmpleado(idJefe).build();
		boolean anadido = false;

		if (departamento != null) {

			if (!nombre.isBlank()) {
				departamento.setNombre(nombre);
			}
			departamento.setJefe(e);
			anadido = administracion.updateDepartamento(departamento);

		}

		IO.println(anadido ? "Modificado" : "No se ha podido modificar");

	}

}
