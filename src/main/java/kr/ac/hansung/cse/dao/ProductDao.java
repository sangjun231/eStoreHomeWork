package kr.ac.hansung.cse.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import kr.ac.hansung.cse.model.Product;

@Repository
public class ProductDao {
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public List<Product> getProducts(){
		String sqlStatement = "SELECT * FROM product"; 
		return jdbcTemplate.query(sqlStatement, new RowMapper<Product>() {//record 형태로 받아오는 데이터를 object 형태로 변환하는 작업

			@Override
			public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
			
				Product product = new Product();
				product.setId(rs.getInt("id"));
				product.setName(rs.getString("name"));
				product.setCategory(rs.getString("category"));
				product.setPrice(rs.getInt("price"));
				product.setManufacturer(rs.getString("manufacturer"));
				product.setUnitInStock(rs.getInt("unitInStock"));
				product.setDescription(rs.getString("description"));
				
				return product;
			} 
			
		});
	}
	
	public boolean addProduct(Product product){
		String name = product.getName();
		String category = product.getCategory();
		int price = product.getPrice();
		String manufacturer = product.getManufacturer();
		int unitInStock = product.getUnitInStock();
		String description = product.getDescription();
		
		String sqlStatement = "Insert into product (name, category, price, manufacturer, unitInStock, description) values (?, ?, ?, ?, ?, ?)"; 
		return (jdbcTemplate.update(sqlStatement, new Object[]{name, category, price, manufacturer, unitInStock, description }) ==1 ); 

	}
	
	public boolean deleteProduct(int id){
		
		String sqlStatement = "DELETE FROM product WHERE id = ?"; 
		return (jdbcTemplate.update(sqlStatement, new Object[]{id}) ==1 ); 

	}

	public Product getProductById(int id) {
		String sqlStatement = "SELECT * FROM product where id = ?"; 
		return jdbcTemplate.queryForObject(sqlStatement,new Object[]{id},
				new RowMapper<Product>() {

			@Override
			public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
			
				Product product = new Product();
				product.setId(rs.getInt("id"));
				product.setName(rs.getString("name"));
				product.setCategory(rs.getString("category"));
				product.setPrice(rs.getInt("price"));
				product.setManufacturer(rs.getString("manufacturer"));
				product.setUnitInStock(rs.getInt("unitInStock"));
				product.setDescription(rs.getString("description"));
				
				return product;
			} 
			
		});
	}
	
	public boolean updateProduct(Product product){
		int id = product.getId();
		String name = product.getName();
		String category = product.getCategory();
		int price = product.getPrice();
		String manufacturer = product.getManufacturer();
		int unitInStock = product.getUnitInStock();
		String description = product.getDescription();
		
		String sqlStatement = "UPDATE product SET name = ?, category = ?, price = ?, manufacturer = ?, unitInStock = ?, description = ? WHERE id = ? "; 
		return (jdbcTemplate.update(sqlStatement, new Object[]{name, category, price, manufacturer, unitInStock, description, id}) ==1 ); 

	}
}
