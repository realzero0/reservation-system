package jinyoung.reservation.dao;

public class ProductSqls {
	public static final String SELECT_BY_ID = "SELECT product.id, product.category_id, name, description, event, content, file.id file_id, place_name, place_lot, place_street, tel, homepage "
			+ "FROM product, product_detail, product_image, file, display_info "
			+ "where product.id=product_detail.product_id and display_info.product_id = product.id and product.id=product_image.product_id and product_image.file_id=file.id and "
			+ "product.id = :productId group by product.id";
	public static final String SELECT_ALL_IN_PAGE = "SELECT product.id, product.category_id, name, description, event, content, file.id file_id, place_name, place_lot, place_street, tel, homepage "
			+ "FROM product, product_detail, product_image, file, display_info "
			+ "where product.id=product_detail.product_id and display_info.product_id = product.id and product.id=product_image.product_id and product_image.file_id=file.id "
			+ "group by product.id limit :page, 4";
	public static final String SELECT_BY_CATE_ID_IN_PAGE = "SELECT product.id, product.category_id, name, description, event, content, file.id file_id, place_name, place_lot, place_street, tel, homepage "
			+ "FROM product, product_detail, product_image, file, display_info where product.id=product_detail.product_id and display_info.product_id = product.id and product.id=product_image.product_id and product_image.file_id=file.id and "
			+ "product.category_id = :categoryId group by product.id limit :page, 4";
	public static final String COUNT_ALL = "SELECT count(*)  FROM product";
	public static final String COUNT_BY_CATE_ID = "SELECT count(*)  FROM product where product.category_id = :categoryId";
}