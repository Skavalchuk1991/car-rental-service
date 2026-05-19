package com.solvd.carrentalservice.dao.impl;

import com.solvd.carrentalservice.dao.BranchDao;
import com.solvd.carrentalservice.model.Branch;
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

public class BranchDaoImpl implements BranchDao {

    private static final Logger LOGGER = LogManager.getLogger(BranchDaoImpl.class);
    private final ConnectionPool pool = ConnectionPool.getInstance();

    @Override
    public void create(Branch branch) {
        String sql = "INSERT INTO branches (name, address, city, opened_at, rental_service_id) VALUES (?, ?, ?, ?, ?)";
        Connection conn = pool.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, branch.getName());
            ps.setString(2, branch.getAddress());
            ps.setString(3, branch.getCity());
            ps.setTimestamp(4, Timestamp.valueOf(branch.getOpenedAt()));
            ps.setLong(5, 1);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                branch.setId(rs.getLong(1));
            }
            LOGGER.info("Branch created with id: {}", branch.getId());
        } catch (SQLException e) {
            LOGGER.error("Error creating branch: {}", e.getMessage());
        } finally {
            pool.releaseConnection(conn);
        }
    }

    @Override
    public Optional<Branch> findById(Long id) {
        String sql = "SELECT * FROM branches WHERE id = ?";
        Connection conn = pool.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRow(rs));
            }
        } catch (SQLException e) {
            LOGGER.error("Error finding branch: {}", e.getMessage());
        } finally {
            pool.releaseConnection(conn);
        }
        return Optional.empty();
    }

    @Override
    public List<Branch> findAll() {
        String sql = "SELECT * FROM branches";
        List<Branch> list = new ArrayList<>();
        Connection conn = pool.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            LOGGER.error("Error finding all branches: {}", e.getMessage());
        } finally {
            pool.releaseConnection(conn);
        }
        return list;
    }

    @Override
    public void update(Branch branch) {
        String sql = "UPDATE branches SET name = ?, address = ?, city = ? WHERE id = ?";
        Connection conn = pool.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, branch.getName());
            ps.setString(2, branch.getAddress());
            ps.setString(3, branch.getCity());
            ps.setLong(4, branch.getId());
            ps.executeUpdate();
            LOGGER.info("Branch updated: {}", branch.getId());
        } catch (SQLException e) {
            LOGGER.error("Error updating branch: {}", e.getMessage());
        } finally {
            pool.releaseConnection(conn);
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM branches WHERE id = ?";
        Connection conn = pool.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
            LOGGER.info("Branch deleted: {}", id);
        } catch (SQLException e) {
            LOGGER.error("Error deleting branch: {}", e.getMessage());
        } finally {
            pool.releaseConnection(conn);
        }
    }

    private Branch mapRow(ResultSet rs) throws SQLException {
        Branch b = new Branch();
        b.setId(rs.getLong("id"));
        b.setName(rs.getString("name"));
        b.setAddress(rs.getString("address"));
        b.setCity(rs.getString("city"));
        b.setOpenedAt(rs.getTimestamp("opened_at").toLocalDateTime());
        return b;
    }
}
