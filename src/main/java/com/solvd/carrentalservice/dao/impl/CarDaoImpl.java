package com.solvd.carrentalservice.dao.impl;

import com.solvd.carrentalservice.dao.CarDao;
import com.solvd.carrentalservice.model.Car;
import com.solvd.carrentalservice.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CarDaoImpl implements CarDao {

    private static final Logger LOGGER = LogManager.getLogger(CarDaoImpl.class);
    private final ConnectionPool pool = ConnectionPool.getInstance();

    @Override
    public void create(Car car) {
        String sql = "INSERT INTO cars (brand, model, year, daily_rate, available, created_at, category_id, branch_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn = pool.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, car.getBrand());
            ps.setString(2, car.getModel());
            ps.setInt(3, car.getYear());
            ps.setDouble(4, car.getDailyRate());
            ps.setBoolean(5, car.isAvailable());
            ps.setTimestamp(6, java.sql.Timestamp.valueOf(car.getCreatedAt()));
            ps.setLong(7, car.getCategory() != null ? car.getCategory().getId() : 0);
            ps.setLong(8, 0);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                car.setId(rs.getLong(1));
            }
            LOGGER.info("Car created with id: {}", car.getId());
        } catch (SQLException e) {
            LOGGER.error("Error creating car: {}", e.getMessage());
        } finally {
            pool.releaseConnection(conn);
        }
    }

    @Override
    public Optional<Car> findById(Long id) {
        String sql = "SELECT * FROM cars WHERE id = ?";
        Connection conn = pool.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRow(rs));
            }
        } catch (SQLException e) {
            LOGGER.error("Error finding car: {}", e.getMessage());
        } finally {
            pool.releaseConnection(conn);
        }
        return Optional.empty();
    }

    @Override
    public List<Car> findAll() {
        String sql = "SELECT * FROM cars";
        List<Car> cars = new ArrayList<>();
        Connection conn = pool.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cars.add(mapRow(rs));
            }
        } catch (SQLException e) {
            LOGGER.error("Error finding all cars: {}", e.getMessage());
        } finally {
            pool.releaseConnection(conn);
        }
        return cars;
    }

    @Override
    public void update(Car car) {
        String sql = "UPDATE cars SET brand = ?, model = ?, year = ?, daily_rate = ?, available = ? WHERE id = ?";
        Connection conn = pool.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, car.getBrand());
            ps.setString(2, car.getModel());
            ps.setInt(3, car.getYear());
            ps.setDouble(4, car.getDailyRate());
            ps.setBoolean(5, car.isAvailable());
            ps.setLong(6, car.getId());
            ps.executeUpdate();
            LOGGER.info("Car updated: {}", car.getId());
        } catch (SQLException e) {
            LOGGER.error("Error updating car: {}", e.getMessage());
        } finally {
            pool.releaseConnection(conn);
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM cars WHERE id = ?";
        Connection conn = pool.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
            LOGGER.info("Car deleted: {}", id);
        } catch (SQLException e) {
            LOGGER.error("Error deleting car: {}", e.getMessage());
        } finally {
            pool.releaseConnection(conn);
        }
    }

    @Override
    public List<Car> findByBranchId(Long branchId) {
        String sql = "SELECT * FROM cars WHERE branch_id = ?";
        List<Car> cars = new ArrayList<>();
        Connection conn = pool.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, branchId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cars.add(mapRow(rs));
            }
        } catch (SQLException e) {
            LOGGER.error("Error finding cars by branch: {}", e.getMessage());
        } finally {
            pool.releaseConnection(conn);
        }
        return cars;
    }

    private Car mapRow(ResultSet rs) throws SQLException {
        Car car = new Car();
        car.setId(rs.getLong("id"));
        car.setBrand(rs.getString("brand"));
        car.setModel(rs.getString("model"));
        car.setYear(rs.getInt("year"));
        car.setDailyRate(rs.getDouble("daily_rate"));
        car.setAvailable(rs.getBoolean("available"));
        car.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return car;
    }
}
