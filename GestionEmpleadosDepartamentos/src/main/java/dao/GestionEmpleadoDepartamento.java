package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Departamento;
import model.Empleado;

public class GestionEmpleadoDepartamento {

	private Connection conn = null;

	/**
	 * Constructor
	 */
	public GestionEmpleadoDepartamento() {
		conn = BD.getConnection();
		createTables();
	}

	/**
	 * Cierra la base de datos
	 */
	public void close() {
		BD.close();
	}
	
	/**
	 * Borra un departamento
	 * @param id
	 * @return true ? Departamento borrado : Fallo al borrarlo 
	 */

	public boolean deleteDepartamento(Integer id) {
		String sql = "DELETE FROM departamento WHERE id = ?";

		try {
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setInt(1, id);

			if (ps.executeUpdate() > 0) {
				sql = "UPDATE empleado SET idDepartamento = NULL WHERE idDepartamento = ?  ";
				ps = conn.prepareStatement(sql);
				ps.setInt(1, id);

				return ps.executeUpdate() > 0;
			}
		} catch (SQLException e) {

		}
		return false;
	}
	
	/**
	 * Borrar un empleado
	 * @param id
	 * @return true ? Empleado borrado : Fallo al borrarlo
	 */

	public boolean deleteEmpleado(Integer id) {
		String sql = "DELETE FROM empleado WHERE idEmpleado = ?";

		try {
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setInt(1, id);

			if (ps.executeUpdate() > 0) {
				sql = "UPDATE departamento SET idJefe = NULL WHERE idJefe = ?  ";
				ps = conn.prepareStatement(sql);
				ps.setInt(1, id);

				return ps.executeUpdate() > 0;
			}
		} catch (SQLException e) {

		}
		return false;
	}
	
	/**
	 * Añadir un departamento
	 * @param dep
	 * @return true ? Departamento añadido : Fallo al añadirlo
	 */

	public boolean addDepartamento(Departamento dep) {
		String sql = "SELECT idEmpleado FROM empleado WHERE idEmpleado = ?";
		PreparedStatement ps;

		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, dep.getJefe().getCodigoEmpleado());
			ResultSet rs = ps.executeQuery();
			if (rs.next() || dep.getJefe().getCodigoEmpleado() == 0) {
				sql = "INSERT INTO departamento (nombre, idJefe) VALUES (?, ?)";
				ps = conn.prepareStatement(sql);
				ps.setString(1, dep.getNombre());
				if (dep.getJefe().getCodigoEmpleado() == 0) {
					ps.setObject(2, null);
				} else {
					ps.setInt(2, dep.getJefe().getCodigoEmpleado());
				}

				return ps.executeUpdate() > 0;

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Añadir un empleado
	 * @param emp
	 * @return true ? Empleado añadido : Fallo al añadirlo
	 */

	public boolean addEmpleado(Empleado emp) {

		String sql = "SELECT idDepartamento FROM departamento WHERE idDepartamento = ?";
		PreparedStatement ps;

		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, emp.getDepartamento().getCodigoDepartamento());
			ResultSet rs = ps.executeQuery();

			if (rs.next() || emp.getDepartamento().getCodigoDepartamento() == 0) {
				sql = "INSERT INTO empleado (nombre, salario, idDepartamento) VALUES (?, ?, ?)";
				ps = conn.prepareStatement(sql);
				ps.setString(1, emp.getNombre());
				ps.setDouble(2, emp.getSalario());
				if (emp.getDepartamento().getCodigoDepartamento() == 0) {
					ps.setObject(3, null);
				} else {
					ps.setInt(3, emp.getDepartamento().getCodigoDepartamento());
				}

				return ps.executeUpdate() > 0;

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;

	}
	
	/**
	 * Mostrar departamentos
	 * @return Lista de departamentos
	 */

	public List<Departamento> showDepartamentos() {
		String sql = "SELECT * FROM departamento";
		List<Departamento> departamentos = new ArrayList<Departamento>();

		try {
			ResultSet rs = conn.createStatement().executeQuery(sql);

			while (rs.next()) {
				Departamento d = readDepartamento(rs);
				departamentos.add(d);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return departamentos;
	}
	
	/**
	 * Mostrar empleados
	 * @return Lista de empleados
	 */

	public List<Empleado> showEmpleados() {
		String sql = "SELECT * FROM empleado";
		List<Empleado> empleados = new ArrayList<Empleado>();

		try {
			ResultSet rs = conn.createStatement().executeQuery(sql);

			while (rs.next()) {
				Empleado e = readEmpleado(rs);
				empleados.add(e);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return empleados;
	}
	
	/**
	 * Buscar empleados por ID
	 * @param id
	 * @return true ? Empleado : No existe el empleado
	 */

	public Empleado buscarEmpleadoId(Integer id) {

		String sql = "SELECT * FROM empleado WHERE idEmpleado = ?";
		Empleado e = null;

		try {

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				e = readEmpleado(rs);
			}

		} catch (SQLException ex) {

		}

		return e;

	}
	
	/**
	 * Buscar departamento por ID
	 * @param id
	 * @return true ? Departamento : No existe el departamento
	 */

	public Departamento buscarDepartamentoId(Integer id) {

		String sql = "SELECT * FROM departamento WHERE idDepartamento = ?";
		Departamento d = null;

		try {

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				d = readDepartamento(rs);
			}

		} catch (SQLException ex) {

		}

		return d;

	}
	
	/**
	 * Buscar empleado por nombre
	 * @param n
	 * @return true ? Empleado : No existe el empleado
	 */

	public List<Empleado> buscarEmpleadoNombre(String n) {

		String sql = "SELECT * FROM empleado WHERE nombre LIKE ?";
		List<Empleado> empleados = new ArrayList<Empleado>();

		try {

			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Empleado e = readEmpleado(rs);
				empleados.add(e);
			}
			ps.setString(1, n + "%");

		} catch (SQLException e) {

		}

		return empleados;

	}
	
	/**
	 * Buscar departamento por nombre
	 * @param n
	 * @return true ? Departamento : No existe el departamento
	 */

	public List<Departamento> buscarDepartamentoNombre(String n) {

		String sql = "SELECT * FROM departamento WHERE nombre LIKE ?";
		List<Departamento> departamentos = new ArrayList<Departamento>();

		try {

			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Departamento d = readDepartamento(rs);
				departamentos.add(d);
			}
			ps.setString(1, n + "%");

		} catch (SQLException e) {

		}

		return departamentos;

	}
	
	/**
	 * Instanciar departamento
	 * @param rs
	 * @return Departamento
	 */

	private Departamento readDepartamento(ResultSet rs) {
		try {
			Integer identificador = rs.getInt("idDepartamento");
			String nombre = rs.getString("nombre");
			Empleado jefe = Empleado.builder().codigoEmpleado(rs.getInt("idJefe")).build();

			return Departamento.builder().codigoDepartamento(identificador).nombre(nombre).jefe(jefe).build();
		} catch (SQLException e) {
		}
		return null;
	}
	
	/**
	 * Instanciar empleado
	 * @param rs
	 * @return Empleado
	 */

	private Empleado readEmpleado(ResultSet rs) {
		try {
			Integer identificador = rs.getInt("idEmpleado");
			String nombre = rs.getString("nombre");
			Departamento departamento = Departamento.builder().codigoDepartamento(rs.getInt("idDepartamento")).build();
			Double salario = rs.getDouble("salario");

			return Empleado.builder().codigoEmpleado(identificador).nombre(nombre).departamento(departamento)
					.salario(salario).build();
		} catch (SQLException e) {
		}
		return null;
	}
	
	/**
	 * Creacion de tablas
	 */

	private void createTables() {
		String tablaEmpleado = null;
		String tablaDepartamento = null;
		if (BD.typeDB.equals("sqlite")) {
			tablaEmpleado = """
						CREATE TABLE IF NOT EXISTS empleado (
							idEmpleado INTEGER PRIMARY KEY AUTOINCREMENT,
							nombre TEXT NOT NULL,
							salario REAL,
							idDepartamento INTEGER
						);
					""";
			tablaDepartamento = """
						CREATE TABLE IF NOT EXISTS departamento (
								idDepartamento INTEGER PRIMARY KEY AUTOINCREMENT,
								nombre TEXT NOT NULL,
								idJefe INTEGER
								)
					""";

		}
		if (BD.typeDB.equals("mariadb")) {
			tablaEmpleado = """
						CREATE TABLE IF NOT EXISTS empleado (
							idEmpleado INT PRIMARY KEY AUTO_INCREMENT,
							nombre VARCHAR(255),
							salario DOUBLE(10,2) DEFAULT NULL,
							idDepartamento INT
						)
					""";
			tablaDepartamento = """
						CREATE TABLE IF NOT EXISTS departamento (
							idDepartamento INT PRIMARY KEY AUTO_INCREMENT,
							nombre VARCHAR(255) NOT NULL,
							idJefe INT
						)
					""";
		}
		try {
			conn.createStatement().executeUpdate(tablaEmpleado);
			conn.createStatement().executeUpdate(tablaDepartamento);
		} catch (SQLException e) {
		}
	}
	
	/**
	 * Modificar empleado
	 * @param emp
	 * @return true ? Empleado modificado : No existe el empleado
	 */

	public boolean updateEmpleado(Empleado emp) {
		String sql = """
				SELECT idDepartamento FROM departamento WHERE idDepartamento = ?
				""";
		PreparedStatement ps;

		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, emp.getDepartamento().getCodigoDepartamento());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				sql = """
						UPDATE empleado
						SET nombre = ?, salario = ?, departamento = ?
						WHERE idEmpleado = ?
						""";
				ps.setString(1, emp.getNombre());
				ps.setDouble(2, emp.getSalario());
				ps.setInt(3, emp.getDepartamento().getCodigoDepartamento());
				ps.setInt(4, emp.getCodigoEmpleado());
				return ps.executeUpdate() > 0;
			}
		} catch (SQLException e) {
		}
		return false;
	}
	
	/**
	 * Modificar departamento
	 * @param d
	 * @return true ? Departamento modificado : No existe el departamento
	 */

	public boolean updateDepartamento(Departamento d) {
		String sql = """
				SELECT idEmpleado FROM empleado WHERE idEmpleado = ?
				""";
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, d.getJefe().getCodigoEmpleado());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				sql = """
								UPDATE departamento
						 		SET nombre = ?, idJefe = ?
						 		WHERE idDepartamento = ?

						""";
				ps = conn.prepareStatement(sql);
				ps.setString(1, d.getNombre());
				ps.setInt(2, d.getJefe().getCodigoEmpleado());
				ps.setInt(3, d.getCodigoDepartamento());
				return ps.executeUpdate() > 0;
			}
		} catch (SQLException e) {
		}
		return false;
	}

}
