package bicycle.reservation.dao;

public class ReservationUserCommentImageSqls {
	public static final String SELECT_BY_COMMENT_ID = "SELECT file_id FROM reservation_user_comment_image where reservation_user_comment_id = :commentId";
}
