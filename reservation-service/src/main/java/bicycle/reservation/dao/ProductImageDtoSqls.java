package bicycle.reservation.dao;

public class ProductImageDtoSqls {
	public static final String SELECT_BY_ID = "SELECT product_id, file_name, save_file_name, type  FROM product_image, file where product_image.file_id = file.id and product_id = :productId;";
}
