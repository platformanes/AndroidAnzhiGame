package com.anzhi.ane 
{ 
	/**
	 * 
	 * @author Rect  2013-5-6 
	 * 
	 */
	public class AnzhiEvents 
	{ 
		public function AnzhiEvents()
		{
		} 
		/**************************平台通知************************************/
		/**
		 *init 
		 */		
		public static const ANZHI_SDK_STATUS:String = "AnzhiInit";
		/**
		 * 用户登录
		 */
		public static const ANZHI_LOGIN_STATUS : String = "AnzhiLogin";
		
		/**
		 * 用户注销
		 */
		public static const ANZHI_LOGOUT_STATUS : String = "AnzhiExit";
		
		/**
		 * 充值
		 */
		public static const ANZHI_PAY_STATUS : String = "AnzhiPay";
		
		public static const ANZHI_CENTER_STATUC : String = "AnzhiCenter";
	} 
}