package jinyoung.reservation.dao;

public class ReservationUserCommentSqls {
	public static final String SELECT_BY_PRO_ID = "SELECT avg(score) FROM reservation_user_comment WHERE product_id = :productId";
	public static final String SELECT_COUNT_BY_PRO_ID = "SELECT count(*) FROM reservation_user_comment WHERE product_id = :productId";
	public static final String SELECT_COMMENTS_BY_PRO_ID = "select comment.id comment_id, product.name product_name, comment.comment, score, username, comment.create_date "
			+ "from product, reservation_user_comment comment, users "
			+ "where comment.product_id = product.id and users.id = comment.user_id and product.id = :productId limit :page, :count";
}
