package com.solvd.carrentalservice.dao.impl;

import com.solvd.carrentalservice.dao.PaymentDao;
import com.solvd.carrentalservice.model.Payment;
import com.solvd.carrentalservice.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PaymentDaoImpl implements PaymentDao {

    private static final Logger LOGGER = LogManager.getLogger(PaymentDaoImpl.class);
    private final ConnectionPool pool = ConnectionPool.getInstance();

    @Override
    public void create(Payment payment) {
        String sql = "INSERT INTO payments (amount, paid, payment_date, rental_id) VALUES (?, ?, ?, ?)";
        Connection conn = pool.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setDouble(1, payment.getAmount());
            ps.setBoolean(2, payment.isPaid());
            if (payment.getPaymentDate() != null) {
                ps.setTimestamp(3, Timestamp.valueOf(payment.getPaymentDate()));
            } else {
                ps.setNull(3, Types.TIMESTAMP);
            }
            ps.setLong(4, 1);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                payment.setId(rs.getLong(1));
            }
            LOGGER.info("Payment created with id: {}", payment.getId());
        } catch (SQLException e) {
            LOGGER.error("Error creating payment: {}", e.getMessage());
        } finally {
            pool.releaseConnection(conn);
        }
    }

    @Override
    public Optional<Payment> findById(Long id) {
        String sql = "SELECT * FROM payments WHERE id = ?";
        Connection conn = pool.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRow(rs));
            }
        } catch (SQLException e) {
            LOGGER.error("Error finding payment: {}", e.getMessage());
        } finally {
            pool.releaseConnection(conn);
        }
        return Optional.empty();
    }

    @Override
    public List<Payment> findAll() {
        String sql = "SELECT * FROM payments";
        List<Payment> list = new ArrayList<>();
        Connection conn = pool.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            LOGGER.error("Error finding all payments: {}", e.getMessage());
        } finally {
            pool.releaseConnection(conn);
        }
        return list;
    }

    @Override
    public void update(Payment payment) {
        String sql = "UPDATE payments SET amount = ?, paid = ?, payment_date = ? WHERE id = ?";
        Connection conn = pool.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, payment.getAmount());
            ps.setBoolean(2, payment.isPaid());
            if (payment.getPaymentDate() != null) {
                ps.setTimestamp(3, Timestamp.valueOf(payment.getPaymentDate()));
            } else {
                ps.setNull(3, Types.TIMESTAMP);
            }
            ps.setLong(4, payment.getId());
            ps.executeUpdate();
            LOGGER.info("Payment updated: {}", payment.getId());
        } catch (SQLException e) {
            LOGGER.error("Error updating payment: {}", e.getMessage());
        } finally {
            pool.releaseConnection(conn);
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM payments WHERE id = ?";
        Connection conn = pool.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
            LOGGER.info("Payment deleted: {}", id);
        } catch (SQLException e) {
            LOGGER.error("Error deleting payment: {}", e.getMessage());
        } finally {
            pool.releaseConnection(conn);
        }
    }

    @Override
    public Optional<Payment> findByRentalId(Long rentalId) {
        String sql = "SELECT * FROM payments WHERE rental_id = ?";
        Connection conn = pool.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, rentalId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRow(rs));
            }
        } catch (SQLException e) {
            LOGGER.error("Error finding payment by rental: {}", e.getMessage());
        } finally {
            pool.releaseConnection(conn);
        }
        return Optional.empty();
    }

    private Payment mapRow(ResultSet rs) throws SQLException {
        Payment p = new Payment();
        p.setId(rs.getLong("id"));
        p.setAmount(rs.getDouble("amount"));
        p.setPaid(rs.getBoolean("paid"));
        Timestamp payDate = rs.getTimestamp("payment_date");
        if (payDate != null) {
            p.setPaymentDate(payDate.toLocalDateTime());
        }
        return p;
    }
}
