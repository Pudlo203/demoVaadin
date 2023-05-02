package com.example.application.data.sprawy;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SprawaServiceJDBC {
    private final String url = "jdbc:postgresql://localhost/demoV";
    private final String username = "postgres";
    private final String password = "pudlom";

    public void save(Sprawy sprawy) {
        String sql = "INSERT INTO sprawy (nr_sprawy, temat, data) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, sprawy.getNrSprawy());
            stmt.setString(2, sprawy.getTemat());
            stmt.setTimestamp(3,Timestamp.valueOf(sprawy.getData()));
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                sprawy = new Sprawy((long) rs.getInt(1), sprawy.getNrSprawy(), sprawy.getTemat(), sprawy.getData());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Sprawy> findAll() {
        List<Sprawy> sprawy = new ArrayList<>();
        String sql = "SELECT * FROM sprawy";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Long id = (long) rs.getInt("id");
                String nrSprawy = rs.getString("numer_sprawy");
                String tytul = rs.getString("tytul");
                LocalDateTime dataIGodzina = rs.getTimestamp("data_i_godzina").toLocalDateTime();

                Sprawy sprawa = new Sprawy(id, nrSprawy, tytul, dataIGodzina);
                sprawy.add(sprawa);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sprawy;
    }
}
