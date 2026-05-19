package com.solvd.carrentalservice.dao.impl;

import com.solvd.carrentalservice.dao.CustomerDao;
import com.solvd.carrentalservice.model.Customer;
import com.solvd.carrentalservice.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerDaoImpl implements CustomerDao {

    private static final Logger LOGGER = LogManager.getLogger(CustomerDaoImpl.class);
    private final ConnectionPool pool = ConnectionPool.getInstance();

    @Override
    public void create(Customer customer) {
        String sql = "INSERT INTO customers (first_name, last_name, email, phone, verified, registered_at, rental_service_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        Connection conn = pool.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, customer.getFirstName());
            ps.setString(2, customer.getLastName());
            ps.setString(3, customer.getEmail());
            ps.setString(4, customer.getPhone());
            ps.setBoolean(5, customer.isVerified());
            ps.setTimestamp(6, Timestamp.valueOf(customer.getRegisteredAt()));
            ps.setLong(7, 1);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                customer.setId(rs.getLong(1));
            }
            LOGGER.info("Customer created with id: {}", customer.getId());
        } catch (SQLException e) {
            LOGGER.error("Error creating customer: {}", e.getMessage());
        } finally {
            pool.releaseConnection(conn);
        }
    }

    @Override
    public Optional<Customer> findById(Long id) {
        String sql = "SELECT * FROM customers WHERE id = ?";
        Connection conn = pool.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRow(rs));
            }
        } catch (SQLException e) {
            LOGGER.error("Error finding customer: {}", e.getMessage());
        } finally {
            pool.releaseConnection(conn);
        }
        return Optional.empty();
    }

    @Override
    public List<Customer> findAll() {
        String sql = "SELECT * FROM customers";
        List<Customer> list = new ArrayList<>();
        Connection conn = pool.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            LOGGER.error("Error finding all customers: {}", e.getMessage());
        } finally {
            pool.releaseConnection(conn);
        }
        return list;
    }

    @Override
    public void update(Customer customer) {
        String sql = "UPDATE customers SET first_name = ?, last_name = ?, email = ?, phone = ?, verified = ? WHERE id = ?";
        Connection conn = pool.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, customer.getFirstName());
            ps.setString(2, customer.getLastName());
            ps.setString(3, customer.getEmail());
            ps.setString(4, customer.getPhone());
            ps.setBoolean(5, customer.isVerified());
            ps.setLong(6, customer.getId());
            ps.executeUpdate();
            LOGGER.info("Customer updated: {}", customer.getId());
        } catch (SQLException e) {
            LOGGER.error("Error updating customer: {}", e.getMessage());
        } finally {
            pool.releaseConnection(conn);
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM customers WHERE id = ?";
        Connection conn = pool.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
            LOGGER.info("Customer deleted: {}", id);
        } catch (SQLException e) {
            LOGGER.error("Error deleting customer: {}", e.getMessage());
        } finally {
            pool.releaseConnection(conn);
        }
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        String sql = "SELECT * FROM customers WHERE email = ?";
        Connection conn = pool.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRow(rs));
            }
        } catch (SQLException e) {
            LOGGER.error("Error finding customer by email: {}", e.getMessage());
        } finally {
            pool.releaseConnection(conn);
        }
        return Optional.empty();
    }

    private Customer mapRow(ResultSet rs) throws SQLException {
        Customer c = new Customer();
        c.setId(rs.getLong("id"));
        c.setFirstName(rs.getString("first_name"));
        c.setLastName(rs.getString("last_name"));
        c.setEmail(rs.getString("email"));
        c.setPhone(rs.getString("phone"));
        c.setVerified(rs.getBoolean("verified"));
        c.setRegisteredAt(rs.getTimestamp("registered_at").toLocalDateTime());
        return c;
    }
}
