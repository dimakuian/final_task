package com.epam.hospital.db.DAO;

import com.epam.hospital.db.entity.Nurse;
import com.epam.hospital.db.entity.Staff;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NurseController extends AbstractController<Nurse, Integer> {

    public static final Logger logger = Logger.getLogger(NurseController.class);

    public static final String SELECT_ALL_NURSE = "SELECT * FROM nurse";

    public static final String SELECT_NURSE_BY_ID = "SELECT * FROM nurse WHERE id = ? ";

    public static final String DELETE_NURSE_BY_ID = "DELETE FROM nurse WHERE id = ? ";


    public static final String INSERT_NURSE =
            "INSERT INTO nurse (first_name, last_name, age, gender, staff_id) VALUES (?, ?, ?, ?, ?)";


    public NurseController() throws SQLException {
    }

    public NurseController(Connection connection, PoolConnectionBuilder poolConnectionBuilder) {
        super(connection, poolConnectionBuilder);
    }

    @Override
    public List<Nurse> getAll() throws SQLException {
        List<Nurse> nurseList = new ArrayList<>();
        try (PreparedStatement ps = getPrepareStatement(SELECT_ALL_NURSE)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Nurse nurse = new Nurse();
                nurse.setId(rs.getInt("id"));
                nurse.setFirstName(rs.getString("first_name"));
                nurse.setLastName(rs.getString("last_name"));
                nurse.setAge(rs.getDate("age"));
                nurse.setGender(rs.getString("gender"));
                StaffController staffController = new StaffController();
                Staff staff = staffController.getEntityById(rs.getInt("staff_id"));
                nurse.setStaff(staff);
                nurseList.add(nurse);
            }
        } catch (SQLException e) {
            logger.error("Can't execute query", e);
            throw new SQLException("Can't execute query");
        }
        return nurseList;
    }


    @Override
    public Nurse update(Nurse entity) throws SQLException {
        logger.info("this empty");
        return null;
    }

    @Override
    public Nurse getEntityById(Integer id) throws SQLException {
        try (PreparedStatement ps = getPrepareStatement(SELECT_NURSE_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Nurse nurse = new Nurse();
                nurse.setId(id);
                nurse.setFirstName(rs.getString("first_name"));
                nurse.setLastName(rs.getString("last_name"));
                nurse.setAge(rs.getDate("age"));
                nurse.setGender(rs.getString("gender"));
                StaffController staffController = new StaffController();
                Staff staff = staffController.getEntityById(rs.getInt("stuff_id"));
                nurse.setStaff(staff);
                return nurse;
            }
        } catch (SQLException e) {
            logger.error("Can't execute query", e);
            throw new SQLException("Can't execute query");
        }
        logger.error("Can't find the nurse");
        throw new SQLException("Can't find the nurse");
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        try (PreparedStatement ps = getPrepareStatement(DELETE_NURSE_BY_ID)) {
            ps.setInt(1, id);
            ps.execute();
            return true;
        } catch (SQLException e) {
            logger.error("Can't execute query", e);
            throw new SQLException("Can't execute query");
        }
    }

    @Override
    public boolean insert(Nurse nurse) throws SQLException {
        try (PreparedStatement ps = getPrepareStatement(INSERT_NURSE)) {
            ps.setString(1, nurse.getFirstName());
            ps.setString(2, nurse.getLastName());
            ps.setDate(3, nurse.getAge());
            ps.setString(4, nurse.getGender());
            ps.setInt(5, nurse.getStaff().getId());
            ps.execute();
            return true;
        } catch (SQLException e) {
            logger.error("Can't execute query", e);
            throw new SQLException("Can't execute query", e);
        }
    }
}
