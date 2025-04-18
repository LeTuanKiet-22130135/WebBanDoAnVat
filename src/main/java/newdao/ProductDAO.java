package newdao;

import newdao.DBConnection;
import newmodel.Product;
import newmodel.Variant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    public ProductDAO() {
        Connection conn = DBConnection.getConnection();
    }

    public List<Product> getAllProducts() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products";
        VariantDAO variantDAO = new VariantDAO();

        try (Connection conn = DBConnection.getConnection()) {
            assert conn != null;
            try (PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Product product = new Product();
                    product.setId(rs.getInt("id"));
                    product.setCode(rs.getString("code"));
                    product.setName(rs.getString("name"));
                    product.setDesc(rs.getString("desc"));
                    product.setImg(rs.getString("img"));
                    product.setTypeId(rs.getInt("typeId"));
                    product.setVariantId(rs.getInt("variantId"));
                    product.setCreateAt(rs.getDate("createAt"));

                    Variant variant = variantDAO.getFirstVariantByProductId(product.getId());
                    if (variant != null) {
                        product.setPrice(variant.getPrice()); // Add price to model
                    }

                    list.add(product);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Product getProductById(int id) {
        String sql = "SELECT * FROM products WHERE id = ?";
        Product product = null;
        VariantDAO variantDAO = new VariantDAO();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    product = new Product();
                    product.setId(rs.getInt("id"));
                    product.setCode(rs.getString("code"));
                    product.setName(rs.getString("name"));
                    product.setDesc(rs.getString("desc"));
                    product.setImg(rs.getString("img"));
                    product.setTypeId(rs.getInt("typeId"));
                    product.setVariantId(rs.getInt("variantId"));
                    product.setCreateAt(rs.getDate("createAt"));

                    // Set variants
                    List<Variant> variants = variantDAO.getVariantsByProductId(id);
                    product.setVariants(variants);

                    // Set price from the first variant if available
                    if (!variants.isEmpty()) {
                        product.setPrice(variants.get(0).getPrice());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return product;
    }


}
