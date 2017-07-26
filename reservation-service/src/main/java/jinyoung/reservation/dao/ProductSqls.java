package jinyoung.reservation.dao;

public class ProductSqls {
	public static final String SELECT_BY_ID = "SELECT product.id, category_id, description, sales_start, sales_end, sales_flag, event, content\n"
			+ "FROM reservation.product, reservation.product_detail\n" + "where product.id = product_detail.product_id and product.id = :productId\n";
	public static final String SELECT_PRODUCTS_OF_ALL_CATEGORY_IN_PAGE = "SELECT product.id, category_id, description, sales_start, sales_end, sales_flag, event, content\n"
			+ "FROM reservation.product, reservation.product_detail\n" + "where product.id = product_detail.product_id\n" + "limit :page, 4";
	public static final String SELECT_BY_CATE_ID_IN_PAGE = "SELECT product.id, category_id, description, sales_start, sales_end, sales_flag, event, content\n"
			+ "FROM reservation.product, reservation.product_detail\n" + "where product.id = product_detail.product_id product.category_id = :categoryId\n" + "limit :page, 4";
	public static final String COUNT_ALL = "SELECT count(*)  FROM product";
	public static final String COUNT_BY_CATE_ID = "SELECT count(*)  FROM product where product.category_id = :categoryId";
}
