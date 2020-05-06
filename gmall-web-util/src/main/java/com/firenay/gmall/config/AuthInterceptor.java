package com.firenay.gmall.config;

import com.alibaba.fastjson.JSON;
import com.firenay.gmall.util.HttpClientUtil;
import io.jsonwebtoken.impl.Base64UrlCodec;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;


/**
 * <p>Title: AuthInterceptor</p>
 * Description：整个Web模块的拦截器
 * date：2020/5/5 11:18
 */
@Component
public class AuthInterceptor implements  HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String token = request.getParameter("newToken");

		// 当 token 不为空时放入 cookie
		if (token != null) {
			CookieUtil.setCookie(request, response, "token", token, WebConst.COOKIE_MAXAGE, false);
		}

		// 当用户访问非登录之后的页面，登录之后，继续访问其他业务模块时，url 并没有newToken，但是后台可能将token 放入了cookie 中！
		if (token == null) {
			token = CookieUtil.getCookieValue(request, "token", false);
		}
		if (token != null) {
			Map map = getUserMapByToken(token);
			String nickName = (String) map.get("nickName");
			request.setAttribute("nickName", nickName);
		}

		// 判断方法上面有没有这个注解
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		LoginRequire loginRequire = handlerMethod.getMethodAnnotation(LoginRequire.class);
		if (loginRequire != null) {
			String salt = request.getHeader("X-firenay-for");
			System.out.println("test");
			String result = HttpClientUtil.doGet(WebConst.VERIFY_ADDRESS + "?token=" + token + "&salt=" + salt);
			if ("success".equals(result)) {
				Map map = getUserMapByToken(token);
				String userId = (String) map.get("userId");
				request.setAttribute("userId", userId);
				return true;
			} else {
				// 这种情况就是必须要登录
				if (loginRequire.autoRedirect()) {
					// 必须登录！跳转到页面！
					// 先获取到url
					String requestURL = request.getRequestURL().toString();
					System.out.println("requestURL:" + requestURL); // http://item.gmall.com/36.html
					// 将url 进行转换
					// http%3A%2F%2Fitem.firenay.com%2F38.html
					String encodeURL = URLEncoder.encode(requestURL, "UTF-8");
					System.out.println("encodeURL：" + encodeURL);
					// http://passport.firenay.com/index?originUrl=http%3A%2F%2Fitem.firenay.com%2F38.html
					response.sendRedirect(WebConst.LOGIN_ADDRESS + "?originUrl=" + encodeURL);
					return false;
				}
			}
		}
		System.out.println("来自com.firenay.gmall.config.AuthInterceptor类=> nickName：");
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}

	/**
	 * 解密 token
	 * 这里为了获取 PasswordController 类中保存的 nickName
	 */
	private Map getUserMapByToken(String token) {
		// 获取中间那一段
		String tokenUserInfo = StringUtils.substringBetween(token, ".");
		// 将tokenUserInfo 进行 base64 解码
		Base64UrlCodec base64UrlCodec = new Base64UrlCodec();
		// 解码之后得到byte数组
		byte[] decode = base64UrlCodec.decode(tokenUserInfo);
		// 需要先将decode 转成String
		String mapJson = null;
		try {
			mapJson = new String(decode, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		// 将字符串转换为map 直接返回！
		return JSON.parseObject(mapJson, Map.class);
	}
}
