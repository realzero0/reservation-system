package jinyoung.reservation.dao;

public class ProductImageSqls {
	public static final String SELECT_A_FILE_ID_BY_PRODUCT_ID = "SELECT file_id FROM reservation.product_image where product_id = :productId limit 1";
}
