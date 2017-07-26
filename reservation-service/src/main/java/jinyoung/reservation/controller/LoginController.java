package jinyoung.reservation.controller;

import java.io.*;
import java.math.*;
import java.net.*;
import java.security.*;
import java.util.*;

import javax.servlet.http.*;

import org.json.simple.*;
import org.json.simple.parser.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import jinyoung.reservation.domain.*;
import jinyoung.reservation.service.*;

@Controller
@PropertySource("classpath:/application.properties")
@RequestMapping("/login")
public class LoginController {
	@Value("${app.domain.url}")
	private static String DOMAIN_URL;
	private static final String COLLBACK_URL = URLEncoder.encode(DOMAIN_URL + "/login/oauth2callback");
	private static final String NAVER_OAUTH_CLIENT_ID = "KGCa149JUmPYQhSpiWSn";
	
	@Value("${naver.login.client.secret}")
	private static String NAVER_OAUTH_CLIENT_SECRET;
	
	private static final String REQUEST_URL = "https://nid.naver.com/oauth2.0/authorize?client_id="
			+ NAVER_OAUTH_CLIENT_ID + "&response_type=code&redirect_uri=" + COLLBACK_URL + "&state=";
	private static final String USER_PROFILE_URL = "https://openapi.naver.com/v1/nid/me";

	@Autowired
	UsersService userService;

	private String getAccessUrl(String state, String code) {
		String accessUrl = "https://nid.naver.com/oauth2.0/token?client_id=" + NAVER_OAUTH_CLIENT_ID + "&client_secret="
				+ NAVER_OAUTH_CLIENT_SECRET + "&grant_type=authorization_code" + "&state=" + state + "&code=" + code;
		return accessUrl;
	}

	@GetMapping
	public String login(HttpServletRequest request) {
		SecureRandom random = new SecureRandom();
		String state = new BigInteger(130, random).toString(32);
		request.getSession().setAttribute("state", state);
		return "redirect:" + REQUEST_URL + state;
	}

	@GetMapping("/oauth2callback")
	public String oauth2Callback(@RequestParam String state, @RequestParam String code, HttpServletRequest request)
			throws UnsupportedEncodingException {
		String storedState = (String) request.getSession().getAttribute("state"); // 세션에
		if (!state.equals(storedState)) {
			// 세션에 저장된 토큰과 인증을 요청해서 받은 토큰이 일치하는지 검증
			//System.out.println("401 unauthorized"); // 인증이 실패했을 때의 처리 부분입니다.
			return "redirect:/";
		}
		//성공한 부분
		String data = userService.getHtml(getAccessUrl(state, code), null);
		Map<String, String> map = userService.JSONStringToMap(data);
		String accessToken = map.get("access_token");
		String tokenType = map.get("token_type");
		String profileData = userService.getHtml(USER_PROFILE_URL, tokenType +" "+ accessToken); 
		// tokentype 와 accessToken을 조합한 값을 해더의 Authorization에 넣어 전송합니다. 결과 값은 xml로 출력됩니다. 	  
		JSONParser jsonParser = new JSONParser();
		JSONObject responseData = null;
		try {
			responseData = (JSONObject) jsonParser.parse(profileData);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Map<String,String> userMap = userService.JSONStringToMap(responseData.get("response").toString()); 
		StringBuffer returnUrl = (StringBuffer) request.getSession().getAttribute("returnUrl");
		if(userService.getUserBySnsId(userMap.get("id")) != null) {
			request.getSession().invalidate();
			request.getSession().setAttribute("user", userService.getUserBySnsId(userMap.get("id")));
			return "redirect:" + returnUrl;
		} else {
			Users user = new Users();
			user.setEmail(userMap.get("email"));
			user.setCreateDate(new Date());
			user.setSnsId(userMap.get("id"));
			user.setUsername(userMap.get("name"));
			user.setNickname(userMap.get("nickname"));
			user.setAdminFlag(0);
			userService.addUser(user);
			return "redirect:" + returnUrl;
		}		
	}
}
