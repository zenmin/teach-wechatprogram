package com.teach.wecharprogram.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

/**
 * @author wujing
 */
public final class SignUtil {

	protected static final Logger logger = LoggerFactory.getLogger(SignUtil.class);

	private SignUtil() {

	}

	public static String getByLecturer(BigDecimal totalIncome, BigDecimal historyMoney, BigDecimal enableBalances, BigDecimal freezeBalances) {
		int t = totalIncome.add(historyMoney).add(enableBalances).add(freezeBalances).multiply(new BigDecimal(100)).intValue();
		return MD5Util.MD5("roncoo" + t);
	}

}
