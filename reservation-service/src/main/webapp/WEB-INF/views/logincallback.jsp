<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page="" import="java.net.URLEncoder" %="">
<%@ page="" import="java.net.URL" %="">
<%@ page="" import="java.net.HttpURLConnection" %="">
<%@ page="" import="java.io.BufferedReader" %="">
<%@ page="" import="java.io.InputStreamReader" %="">
<%@ page="" contenttype="text/html;charset=UTF-8" language="java" %="">
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>네이버로그인</title>
  </head>
  <body>
  <% string="" clientid="YOUR_CLIENT_ID" ;="" 애플리케이션="" 클라이언트="" 아이디값";="" clientsecret="YOUR_CLIENT_SECRET" 시크릿값";="" code="request.getParameter("code");" state="request.getParameter("state");" redirecturi="URLEncoder.encode("YOUR_CALLBACK_URL"," "utf-8");="" apiurl;="" apiurl="https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&" +="client_id=" clientid;="" clientsecret;="" redirecturi;="" code;="" state;="" access_token="" refresh_token="" system.out.println("apiurl="+apiURL);
    try {
      URL url = new URL(apiURL);
      HttpURLConnection con = (HttpURLConnection)url.openConnection();
      con.setRequestMethod(" get");="" int="" responsecode="con.getResponseCode();" bufferedreader="" br;="" system.out.print("responsecode="+responseCode);
      if(responseCode==200) { // 정상 호출
        br = new BufferedReader(new InputStreamReader(con.getInputStream()));
      } else {  // 에러 발생
        br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
      }
      String inputLine;
      StringBuffer res = new StringBuffer();
      while ((inputLine = br.readLine()) != null) {
        res.append(inputLine);
      }
      br.close();
      if(responseCode==200) {
        out.println(res.toString());
      }
    } catch (Exception e) {
      System.out.println(e);
    }
  %>
  </body>
</html>