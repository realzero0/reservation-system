package bicycle.reservation.dao;

public class ReservationInfoSqls {
	public static final String SELECT_RESERVATION_INFOS_BY_USER_ID = "SELECT id, product_id, user_id, general_ticket_count, youth_ticket_count, child_ticket_count, reservation_name, reservation_tel, reservation_email, reservation_date, reservation_type, create_date, modify_date\n"
			+ "FROM reservation.reservation_info\n" + "where user_id = :userId\n" + "order by reservation_type asc, reservation_date desc";
	public static final String UPDATE_RESERVATION_INFO = "UPDATE reservation.reservation_info\n"
			+ "SET product_id = :productId,\n" + "user_id = :userId,\n"
			+ "general_ticket_count = :generalTicketCount,\n" + "youth_ticket_count = :youthTicketCount,\n"
			+ "child_ticket_count = :childTicketCount,\n" + "reservation_name = :reservationName,\n"
			+ "reservation_tel = :reservationTel,\n" + "reservation_email = :reservationEmail,\n"
			+ "reservation_date = :reservationDate,\n" + "reservation_type = :reservationType,\n"
			+ "create_date = :createDate,\n" + "modify_date = :modifyDate\n" + "WHERE id = :id";
	public static final String SELECT_RESERVATION_INFO_BY_BOOKING_NUMBER = "SELECT id, product_id, user_id, general_ticket_count, youth_ticket_count, child_ticket_count, reservation_name, reservation_tel, reservation_email, reservation_date, reservation_type, create_date, modify_date\n"
			+ "FROM reservation.reservation_info\n" + "where id = :id\n";
}
