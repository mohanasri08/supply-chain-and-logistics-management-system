import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAO {

    public static boolean addSupplier(Supplier supplier) {
        String sql = "INSERT INTO suppliers (supplier_name, contact_person, email, phone, address, rating) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            if (con == null) return false;

            ps.setString(1, supplier.getSupplierName());
            ps.setString(2, supplier.getContactPerson());
            ps.setString(3, supplier.getEmail());
            ps.setString(4, supplier.getPhone());
            ps.setString(5, supplier.getAddress());
            ps.setDouble(6, supplier.getRating() > 0 ? supplier.getRating() : 5.0);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<Supplier> getAllSuppliers() {
        List<Supplier> list = new ArrayList<>();
        String sql = "SELECT * FROM suppliers ORDER BY id DESC";
        try (Connection con = DBConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (con == null) return list;

            while (rs.next()) {
                list.add(new Supplier(
                    rs.getInt("id"),
                    rs.getString("supplier_name"),
                    rs.getString("contact_person"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("address"),
                    rs.getDouble("rating")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static boolean deleteSupplier(int id) {
        String sql = "DELETE FROM suppliers WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            if (con == null) return false;

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
