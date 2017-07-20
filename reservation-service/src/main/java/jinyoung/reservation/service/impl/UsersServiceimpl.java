package jinyoung.reservation.service.impl;

import java.io.*;
import java.net.*;
import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.core.type.*;
import com.fasterxml.jackson.databind.*;

import jinyoung.reservation.dao.*;
import jinyoung.reservation.domain.*;
import jinyoung.reservation.service.*;

@Service
public class UsersServiceimpl implements UsersService {

	@Autowired
	UsersDao usersDao;
	
	@Override
	public Integer addUser(Users user) {
		return usersDao.insert(user);
	}
	
	@Override
	public Collection<Users> getAll() {
		return usersDao.selectAll();
	}
	
	@Override
	public Users getUserById(Long userId) {
		return usersDao.selectById(userId);
	}
	
	@Override
	public Map<String, String> JSONStringToMap(String str) {
		Map<String, String> map = new HashMap<String, String>();
		ObjectMapper mapper = new ObjectMapper();

		try {
			map = mapper.readValue(str, new TypeReference<HashMap<String, String>>() {
			});
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	@Override
	public String getHtml(String url, String authorization) {
		HttpURLConnection httpRequest = null;
		String resultValue = null;
		try {
			URL u = new URL(url);
			httpRequest = (HttpURLConnection) u.openConnection();
			httpRequest.setRequestProperty("Content-type", "text/xml; charset=UTF-8");

			if (authorization != null) {
				httpRequest.setRequestProperty("Authorization", authorization);
			}
			httpRequest.connect();
			BufferedReader in = new BufferedReader(new InputStreamReader(httpRequest.getInputStream(), "UTF-8"));

			StringBuffer sb = new StringBuffer();
			String line = null;
			while ((line = in.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
			resultValue = sb.toString();
		} catch (IOException e) {

		} finally {
			if (httpRequest != null) {
				httpRequest.disconnect();
			}
		}
		return resultValue;
	}

	@Override
	public Users getUserBySnsId(String userSnsId) {
		return usersDao.selectBySnsId(userSnsId);
	}
}
