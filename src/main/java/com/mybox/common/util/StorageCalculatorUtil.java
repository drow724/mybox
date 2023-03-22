package com.mybox.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class StorageCalculatorUtil {

	public static Long byteToKiloByte(int length) {
		BigDecimal toKilo = new BigDecimal("0.000977");
		return toKilo.multiply(new BigDecimal(length)).setScale(0, RoundingMode.UP).longValue();
	}
}