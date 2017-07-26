package bicycle.reservation.dao;

public class ProductPriceSqls {
	public static final String SELECT_PRODUCT_PRICES_BY_PRODUCT_ID ="SELECT `id`, `product_id`, `price_type`, `price`, `discount_rate`, `create_date`, `modify_date` FROM `reservation`.`product_price` where product_id = :productId";
}
