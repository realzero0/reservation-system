package bicycle.reservation.service;

import java.util.*;

import bicycle.reservation.domain.*;

public interface UsersService {
	public Integer addUser(Users user);
	public Collection<Users> getAll();
	public Users getUserById(Long userId);
	public Users getUserBySnsId(String userSnsId);
	public Map<String, String> JSONStringToMap(String str);
	public String getHtml(String url, String authorization);
}
