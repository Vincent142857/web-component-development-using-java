package vn.aptech.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import vn.aptech.dto.ProductDto;

public class ProductDb {
	
	
	public static List<ProductDto> getAll(){
		List<ProductDto> result = new ArrayList<ProductDto>();
		String sql = "SELECT * FROM products";
		try(
				Connection cn = ConnectDb.getConnectMySQL(); 
				Statement stmt = cn.createStatement();
		) {
			ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {                
                ProductDto p = new ProductDto();
                p.setId(rs.getInt(1));
                p.setName(rs.getString(2));
                p.setPrice(rs.getInt(3));
                p.setImage(rs.getString(4));
                
                result.add(p);
            }
			
		}catch (SQLException e) {
	        e.printStackTrace();
	        System.out.println("Error: " + e.getMessage());
		}
		return result;
	}
	
	public static ProductDto getById(int id){
		String sql = "SELECT * FROM products where id=?";
		ProductDto p = new ProductDto();
		try(
				Connection cn = ConnectDb.getConnectMySQL();
				PreparedStatement psmt = cn.prepareStatement(sql);
		) {
			psmt.setInt(1,id);
            ResultSet rs = psmt.executeQuery();
            while (rs.next()) {                
                p.setId(rs.getInt(1));
                p.setName(rs.getString(2));
                p.setPrice(rs.getInt(3));
                p.setImage(rs.getString(4));
            }
		}catch (Exception e) {
	        e.printStackTrace();
		}
		return p;
	}
	
	public static List<ProductDto> search(String name){
		List<ProductDto> result = new ArrayList<ProductDto>();
		String sql = "SELECT * FROM products where name like ?";
		try(
				Connection cn = ConnectDb.getConnectMySQL();
				PreparedStatement psmt = cn.prepareStatement(sql);
		) {
			psmt.setString(1, "%" + name + "%");
            ResultSet rs = psmt.executeQuery();
            while (rs.next()) {                
                ProductDto p = new ProductDto();
                p.setId(rs.getInt(1));
                p.setName(rs.getString(2));
                p.setPrice(rs.getInt(3));
                p.setImage(rs.getString(4));
                
                result.add(p);
            }
		}catch (Exception e) {
	        e.printStackTrace();
		}
		return result;
	}
	
	public static boolean create(ProductDto product) {
		String query = "insert into products(name,price,image) values (?,?,?)";
		try(
				Connection cn = ConnectDb.getConnectMySQL(); 
				PreparedStatement psmt = cn.prepareStatement(query);
		) {
			psmt.setString(1, product.getName());
			psmt.setInt(2, product.getPrice());
			psmt.setString(3, product.getImage());
			return psmt.executeUpdate()>0;
		}catch (SQLException e) {
	        System.out.println("Error: " + e.getMessage());
		}
		return false;
	}
	
	public static boolean update(ProductDto p) {
		String query = "update products set name=?, price=?,image=? where id=?";
		try(
				Connection cn = ConnectDb.getConnectMySQL();
				PreparedStatement psmt = cn.prepareStatement(query);
		){
			psmt.setString(1, p.getName());
			psmt.setInt(2, p.getPrice());
			psmt.setString(3, p.getImage());
			psmt.setInt(4, p.getId());
			return psmt.executeUpdate() > 0;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean delete(int id) {
		String query = "delete from products where id=?";
		try(
				Connection cn = ConnectDb.getConnectMySQL();
				PreparedStatement psmt = cn.prepareStatement(query);
		) {
			psmt.setInt(1, id);
			return psmt.executeUpdate() > 0;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
