package jinyoung.reservation.dao;

public class FileSqls {
	public static final String SELECT_IMAGES_BY_PRODUCT_ID = "SELECT file.id file_id, product_id, file_name, save_file_name, file_length, content_type\n"
			+ "FROM product_image, file where product_image.file_id = file.id and product_id = :productId;";
	public static final String SELECT_IMAGE_BY_FILE_ID = "SELECT file.id file_id, file_name, save_file_name, file_length, content_type\n"
			+ "FROM file where file.id = :fileId;";
	//testdd
}
