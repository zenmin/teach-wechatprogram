package com.teach.wecharprogram.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.UnsupportedEncodingException;
import java.util.Date;

public final class JWTUtil {

	protected static final Logger logger = LoggerFactory.getLogger(JWTUtil.class);

	private static final String TOKEN_SECRET = "ZENGMIN";
	private static final String ISSUER = "USER";
	public static final String USERNO = "USERNO";
	public static final Long DATE = 30 * 24 * 3600 * 1000L; // 1个月

	/**
	 * 
	 * @param date
	 * @return
	 * @throws IllegalArgumentException
	 * @throws UnsupportedEncodingException
	 */
	public static String create(Long userNo, Long date) {
		try {
			return JWT.create().withIssuer(ISSUER).withClaim(USERNO, userNo.toString()).withExpiresAt(new Date(System.currentTimeMillis() + date)).sign(Algorithm.HMAC256(TOKEN_SECRET));
		} catch (IllegalArgumentException | JWTCreationException e) {
			logger.error("JWT生成失败", e);
			return "";
		}
	}

	/**
	 * 
	 * @param token
	 * @return
	 * @throws JWTVerificationException
	 * @throws IllegalArgumentException
	 * @throws UnsupportedEncodingException
	 */
	public static DecodedJWT verify(String token) throws JWTVerificationException, IllegalArgumentException, UnsupportedEncodingException {
		return JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).withIssuer(ISSUER).build().verify(token);
	}

	/**
	 * 
	 * @return
	 * @throws JWTVerificationException
	 * @throws IllegalArgumentException
	 * @throws UnsupportedEncodingException
	 */
	public static Long getUserNo(DecodedJWT decodedJWT) {
		return Long.valueOf(decodedJWT.getClaim(USERNO).asString());
	}

}
