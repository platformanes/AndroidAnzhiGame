package com.anzhi.ane;

import java.util.HashMap;
import java.util.Map;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.anzhi.func.AnzhiCenter;
import com.anzhi.func.AnzhiExit;
import com.anzhi.func.AnzhiInit;
import com.anzhi.func.AnzhiLogin;
import com.anzhi.func.AnzhiPay;

/**
 * @author Rect
 * @version  Time：2013-5-8 
 */
public class AnzhiContext extends FREContext {
	/**
	 * INIT sdk
	 */
	private static final String ANZHI_FUNCTION_INIT = "anzhi_function_init";
	/**
	 * 登录Key
	 */
	private static final String ANZHI_FUNCTION_LOGIN = "anzhi_function_login";
	private static final String ANZHI_FUNCTION_CENTER = "anzhi_function_center";
	/**
	 * 付费Key
	 */
	private static final String ANZHI_FUNCTION_PAY = "anzhi_function_pay";
	/**
	 * 退出Key
	 */
	private static final String ANZHI_FUNCTION_EXIT = "anzhi_function_exit";
	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public Map<String, FREFunction> getFunctions() {
		// TODO Auto-generated method stub
		Map<String, FREFunction> map = new HashMap<String, FREFunction>();
//	       //映射
		   map.put(ANZHI_FUNCTION_INIT, new AnzhiInit());
	       map.put(ANZHI_FUNCTION_LOGIN, new AnzhiLogin());
	       map.put(ANZHI_FUNCTION_CENTER, new AnzhiCenter());
	       map.put(ANZHI_FUNCTION_PAY, new AnzhiPay());
	       map.put(ANZHI_FUNCTION_EXIT, new AnzhiExit());
	       return map;
	}

}
