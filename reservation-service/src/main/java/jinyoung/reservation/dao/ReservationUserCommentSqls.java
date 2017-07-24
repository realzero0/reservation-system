package jinyoung.reservation.dao;

public class ReservationUserCommentSqls {
	public static final String SELECT_BY_PRO_ID = "SELECT avg(score) FROM reservation_user_comment WHERE product_id = :productId";
	public static final String SELECT_COUNT_BY_PRO_ID = "SELECT count(*) FROM reservation_user_comment WHERE product_id = :productId";
	public static final String SELECT_COMMENTS_BY_PRO_ID = "select comment.id comment_id, product.name product_name, comment.comment, score, username, comment.create_date "
			+ "from product, reservation_user_comment comment, users "
			+ "where comment.product_id = product.id and users.id = comment.user_id and product.id = :productId limit :page, :count";
	public static final String SELECT_COMMENT_BY_USER_ID_AND_PRODUCT_ID = "SELECT id, product_id, user_id, score, comment, create_date, modify_date\n"
			+ "FROM reservation.reservation_user_comment\n" + "WHERE product_id = :productId and user_id = :userId\n" + "group by product_id";

}
