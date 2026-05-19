package com.solvd.carrentalservice.dao.impl;

import com.solvd.carrentalservice.dao.EmployeeDao;
import com.solvd.carrentalservice.model.Employee;
import com.solvd.carrentalservice.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeeDaoImpl implements EmployeeDao {

    private static final Logger LOGGER = LogManager.getLogger(EmployeeDaoImpl.class);
    private final ConnectionPool pool = ConnectionPool.getInstance();

    @Override
    public void create(Employee emp) {
        String sql = "INSERT INTO employees (first_name, last_name, position, salary, hired_at, branch_id) VALUES (?, ?, ?, ?, ?, ?)";
        Connection conn = pool.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, emp.getFirstName());
            ps.setString(2, emp.getLastName());
            ps.setString(3, emp.getPosition());
            ps.setDouble(4, emp.getSalary());
            ps.setDate(5, Date.valueOf(emp.getHiredAt()));
            ps.setLong(6, 1);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                emp.setId(rs.getLong(1));
            }
            LOGGER.info("Employee created with id: {}", emp.getId());
        } catch (SQLException e) {
            LOGGER.error("Error creating employee: {}", e.getMessage());
        } finally {
            pool.releaseConnection(conn);
        }
    }

    @Override
    public Optional<Employee> findById(Long id) {
        String sql = "SELECT * FROM employees WHERE id = ?";
        Connection conn = pool.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRow(rs));
            }
        } catch (SQLException e) {
            LOGGER.error("Error finding employee: {}", e.getMessage());
        } finally {
            pool.releaseConnection(conn);
        }
        return Optional.empty();
    }

    @Override
    public List<Employee> findAll() {
        String sql = "SELECT * FROM employees";
        List<Employee> list = new ArrayList<>();
        Connection conn = pool.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            LOGGER.error("Error finding all employees: {}", e.getMessage());
        } finally {
            pool.releaseConnection(conn);
        }
        return list;
    }

    @Override
    public void update(Employee emp) {
        String sql = "UPDATE employees SET first_name = ?, last_name = ?, position = ?, salary = ? WHERE id = ?";
        Connection conn = pool.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, emp.getFirstName());
            ps.setString(2, emp.getLastName());
            ps.setString(3, emp.getPosition());
            ps.setDouble(4, emp.getSalary());
            ps.setLong(5, emp.getId());
            ps.executeUpdate();
            LOGGER.info("Employee updated: {}", emp.getId());
        } catch (SQLException e) {
            LOGGER.error("Error updating employee: {}", e.getMessage());
        } finally {
            pool.releaseConnection(conn);
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM employees WHERE id = ?";
        Connection conn = pool.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
            LOGGER.info("Employee deleted: {}", id);
        } catch (SQLException e) {
            LOGGER.error("Error deleting employee: {}", e.getMessage());
        } finally {
            pool.releaseConnection(conn);
        }
    }

    @Override
    public List<Employee> findByBranchId(Long branchId) {
        String sql = "SELECT * FROM employees WHERE branch_id = ?";
        List<Employee> list = new ArrayList<>();
        Connection conn = pool.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, branchId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            LOGGER.error("Error finding employees by branch: {}", e.getMessage());
        } finally {
            pool.releaseConnection(conn);
        }
        return list;
    }

    private Employee mapRow(ResultSet rs) throws SQLException {
        Employee e = new Employee();
        e.setId(rs.getLong("id"));
        e.setFirstName(rs.getString("first_name"));
        e.setLastName(rs.getString("last_name"));
        e.setPosition(rs.getString("position"));
        e.setSalary(rs.getDouble("salary"));
        e.setHiredAt(rs.getDate("hired_at").toLocalDate());
        return e;
    }
}
