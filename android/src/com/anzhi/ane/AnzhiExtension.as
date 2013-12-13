package com.anzhi.ane 
{ 
	import flash.events.EventDispatcher;
	import flash.events.IEventDispatcher;
	import flash.events.StatusEvent;
	import flash.external.ExtensionContext;
	
	/**
	 * 
	 * @author Rect  2013-5-6 
	 * 
	 */
	public class AnzhiExtension extends EventDispatcher 
	{ 
		
		
		private static const ANZHI_FUNCTION_INIT:String = "anzhi_function_init";//与java端中Map里的key一致
		private static const ANZHI_FUNCTION_LOGIN:String = "anzhi_function_login";//与java端中Map里的key一致
		private static const ANZHI_FUNCTION_CENTER:String = "anzhi_function_center";//与java端中Map里的key一致
		private static const ANZHI_FUNCTION_PAY:String = "anzhi_function_pay";//与java端中Map里的key一致
		private static const ANZHI_FUNCTION_EXIT:String = "anzhi_function_exit";//与java端中Map里的key一致
		
		private static const EXTENSION_ID:String = "com.anzhi.ane";//与extension.xml中的id标签一致
		private var extContext:ExtensionContext;
		
		/**单例的实例*/
		private static var _instance:AnzhiExtension; 
		public function AnzhiExtension(target:IEventDispatcher=null)
		{
			super(target);
			if(extContext == null) {
				extContext = ExtensionContext.createExtensionContext(EXTENSION_ID, "");
				extContext.addEventListener(StatusEvent.STATUS, statusHandler);
			}
			
		} 
		
		//第二个为参数，会传入java代码中的FREExtension的createContext方法
		/**
		 * 获取实例
		 * @return DLExtension 单例
		 */
		public static function getInstance():AnzhiExtension
		{
			if(_instance == null) 
				_instance = new AnzhiExtension();
			return _instance;
		}
		
		/**
		 * 转抛事件
		 * @param event 事件
		 */
		private function statusHandler(event:StatusEvent):void
		{
			dispatchEvent(event);
		}
		
		/**
		 * 
		 * @param appKey
		 * @param appSecret
		 * @param appChannel
		 * @return 
		 * 
		 */			
		public function AnzhiInit(appKey:String,appSecret:String,appChannel:String):String{
			if(extContext ){
				return extContext.call(ANZHI_FUNCTION_INIT,appKey,appSecret,appChannel) as String;
			}
			return "call AnzhiInit failed";
		} 
		
		public function AnzhiCenter(key:int):String{
			if(extContext ){
				return extContext.call(ANZHI_FUNCTION_CENTER,key) as String;
			}
			return "call AnzhiCenter failed";
		} 
		/**
		 *登录发送函数  
		 * @param key 暂时传什么都可以  留着可能要用
		 * @return 
		 * 
		 */		
		public function AnzhiLogIn(key:int):String{
			if(extContext ){
				return extContext.call(ANZHI_FUNCTION_LOGIN,key) as String;
			}
			return "call AnzhiLogIn failed";
		} 
		/**
		 * 
		 * @param orderID
		 * @param price
		 * @param body
		 * @param ext
		 * @return 
		 * 
		 */			 
		public function AnzhiPay(orderID:int,price:int,body:String,ext:String):String{
			if(extContext){ 
				return extContext.call(ANZHI_FUNCTION_PAY,orderID,price,body,ext)as String;
			}
			return "call AnzhiPay failed";
		}
		
		/**
		 *退出SDK时候调用   这个函数只在退出游戏的时候调用  
		 * @param key
		 * @return 
		 * 
		 */		
		public function AnzhiExit(key:int):String{
			if(extContext){ 
				return extContext.call(ANZHI_FUNCTION_EXIT,key) as String;
			}
			return "call exit failed";
		}
	} 
}