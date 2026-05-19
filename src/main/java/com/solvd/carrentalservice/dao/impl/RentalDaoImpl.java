package com.solvd.carrentalservice.dao.impl;

import com.solvd.carrentalservice.dao.RentalDao;
import com.solvd.carrentalservice.model.Car;
import com.solvd.carrentalservice.model.Category;
import com.solvd.carrentalservice.model.Insurance;
import com.solvd.carrentalservice.model.Payment;
import com.solvd.carrentalservice.model.Rental;
import com.solvd.carrentalservice.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RentalDaoImpl implements RentalDao {

    private static final Logger LOGGER = LogManager.getLogger(RentalDaoImpl.class);
    private final ConnectionPool pool = ConnectionPool.getInstance();

    @Override
    public void create(Rental rental) {
        String sql = "INSERT INTO rentals (start_date, end_date, total_price, active, created_at, customer_id, car_id, insurance_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn = pool.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setDate(1, Date.valueOf(rental.getStartDate()));
            ps.setDate(2, Date.valueOf(rental.getEndDate()));
            ps.setDouble(3, rental.getTotalPrice());
            ps.setBoolean(4, rental.isActive());
            ps.setTimestamp(5, Timestamp.valueOf(rental.getCreatedAt()));
            ps.setLong(6, 1);
            ps.setLong(7, rental.getCar() != null ? rental.getCar().getId() : 0);
            if (rental.getInsurance() != null) {
                ps.setLong(8, rental.getInsurance().getId());
            } else {
                ps.setNull(8, Types.BIGINT);
            }
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                rental.setId(rs.getLong(1));
            }
            LOGGER.info("Rental created with id: {}", rental.getId());
        } catch (SQLException e) {
            LOGGER.error("Error creating rental: {}", e.getMessage());
        } finally {
            pool.releaseConnection(conn);
        }
    }

    @Override
    public Optional<Rental> findById(Long id) {
        String sql = "SELECT * FROM rentals WHERE id = ?";
        Connection conn = pool.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRow(rs));
            }
        } catch (SQLException e) {
            LOGGER.error("Error finding rental: {}", e.getMessage());
        } finally {
            pool.releaseConnection(conn);
        }
        return Optional.empty();
    }

    @Override
    public List<Rental> findAll() {
        String sql = "SELECT * FROM rentals";
        List<Rental> list = new ArrayList<>();
        Connection conn = pool.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            LOGGER.error("Error finding all rentals: {}", e.getMessage());
        } finally {
            pool.releaseConnection(conn);
        }
        return list;
    }

    @Override
    public void update(Rental rental) {
        String sql = "UPDATE rentals SET start_date = ?, end_date = ?, total_price = ?, active = ? WHERE id = ?";
        Connection conn = pool.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(rental.getStartDate()));
            ps.setDate(2, Date.valueOf(rental.getEndDate()));
            ps.setDouble(3, rental.getTotalPrice());
            ps.setBoolean(4, rental.isActive());
            ps.setLong(5, rental.getId());
            ps.executeUpdate();
            LOGGER.info("Rental updated: {}", rental.getId());
        } catch (SQLException e) {
            LOGGER.error("Error updating rental: {}", e.getMessage());
        } finally {
            pool.releaseConnection(conn);
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM rentals WHERE id = ?";
        Connection conn = pool.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
            LOGGER.info("Rental deleted: {}", id);
        } catch (SQLException e) {
            LOGGER.error("Error deleting rental: {}", e.getMessage());
        } finally {
            pool.releaseConnection(conn);
        }
    }

    @Override
    public List<Rental> findByCustomerId(Long customerId) {
        String sql = "SELECT * FROM rentals WHERE customer_id = ?";
        List<Rental> list = new ArrayList<>();
        Connection conn = pool.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, customerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            LOGGER.error("Error finding rentals by customer: {}", e.getMessage());
        } finally {
            pool.releaseConnection(conn);
        }
        return list;
    }

    @Override
    public List<Rental> findAllWithDetails() {
        String sql = """
                SELECT r.id, r.start_date, r.end_date, r.total_price, r.active, r.created_at,
                       c.id AS car_id, c.brand, c.model, c.year, c.daily_rate, c.available, c.created_at AS car_created,
                       cat.id AS cat_id, cat.name AS cat_name, cat.description AS cat_desc, cat.price_multiplier,
                       b.id AS branch_id, b.name AS branch_name, b.address, b.city, b.opened_at,
                       cust.id AS cust_id, cust.first_name, cust.last_name, cust.email, cust.phone, cust.verified, cust.registered_at,
                       ins.id AS ins_id, ins.type AS ins_type, ins.coverage_amount, ins.active AS ins_active, ins.valid_until,
                       p.id AS pay_id, p.amount, p.paid, p.payment_date
                FROM rentals r
                JOIN customers cust ON r.customer_id = cust.id
                JOIN cars c ON r.car_id = c.id
                JOIN categories cat ON c.category_id = cat.id
                JOIN branches b ON c.branch_id = b.id
                LEFT JOIN insurances ins ON r.insurance_id = ins.id
                LEFT JOIN payments p ON p.rental_id = r.id
                """;
        List<Rental> list = new ArrayList<>();
        Connection conn = pool.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Rental rental = new Rental();
                rental.setId(rs.getLong("id"));
                rental.setStartDate(rs.getDate("start_date").toLocalDate());
                rental.setEndDate(rs.getDate("end_date").toLocalDate());
                rental.setTotalPrice(rs.getDouble("total_price"));
                rental.setActive(rs.getBoolean("active"));
                rental.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());

                Car car = new Car();
                car.setId(rs.getLong("car_id"));
                car.setBrand(rs.getString("brand"));
                car.setModel(rs.getString("model"));
                car.setYear(rs.getInt("year"));
                car.setDailyRate(rs.getDouble("daily_rate"));
                car.setAvailable(rs.getBoolean("available"));
                car.setCreatedAt(rs.getTimestamp("car_created").toLocalDateTime());

                Category cat = new Category();
                cat.setId(rs.getLong("cat_id"));
                cat.setName(rs.getString("cat_name"));
                cat.setDescription(rs.getString("cat_desc"));
                cat.setPriceMultiplier(rs.getDouble("price_multiplier"));
                car.setCategory(cat);

                rental.setCar(car);

                long insId = rs.getLong("ins_id");
                if (!rs.wasNull()) {
                    Insurance ins = new Insurance();
                    ins.setId(insId);
                    ins.setType(rs.getString("ins_type"));
                    ins.setCoverageAmount(rs.getDouble("coverage_amount"));
                    ins.setActive(rs.getBoolean("ins_active"));
                    ins.setValidUntil(rs.getTimestamp("valid_until").toLocalDateTime());
                    rental.setInsurance(ins);
                }

                long payId = rs.getLong("pay_id");
                if (!rs.wasNull()) {
                    Payment pay = new Payment();
                    pay.setId(payId);
                    pay.setAmount(rs.getDouble("amount"));
                    pay.setPaid(rs.getBoolean("paid"));
                    Timestamp payDate = rs.getTimestamp("payment_date");
                    if (payDate != null) {
                        pay.setPaymentDate(payDate.toLocalDateTime());
                    }
                    rental.setPayment(pay);
                }

                list.add(rental);
            }
            LOGGER.info("Found {} rentals with details (7-table join)", list.size());
        } catch (SQLException e) {
            LOGGER.error("Error in findAllWithDetails: {}", e.getMessage());
        } finally {
            pool.releaseConnection(conn);
        }
        return list;
    }

    private Rental mapRow(ResultSet rs) throws SQLException {
        Rental r = new Rental();
        r.setId(rs.getLong("id"));
        r.setStartDate(rs.getDate("start_date").toLocalDate());
        r.setEndDate(rs.getDate("end_date").toLocalDate());
        r.setTotalPrice(rs.getDouble("total_price"));
        r.setActive(rs.getBoolean("active"));
        r.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return r;
    }
}
