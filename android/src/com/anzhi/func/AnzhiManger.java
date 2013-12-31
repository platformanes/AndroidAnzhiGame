package com.anzhi.func;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.util.Log;

import com.adobe.fre.FREContext;
import com.anzhi.usercenter.sdk.AnzhiCallback;
import com.anzhi.usercenter.sdk.AnzhiUserCenter;
import com.anzhi.usercenter.sdk.OfficialLoginCallback;
import com.anzhi.usercenter.sdk.item.CPInfo;

/**
 * @author Rect
 * @version  Time：2013-12-12 
 */
public class AnzhiManger {

	private static AnzhiManger instance;
	private AnzhiUserCenter anzhiCenter;
	private String appKey;
	private String appSecret;
	private String appChannel;
	private FREContext _context;
	private String TAG = "AnzhiManger";
	private String TAG_LOGIN = "AnzhiLogin";
	private String TAG_PAY = "AnzhiPay";
	
	public static AnzhiManger getInstance()
	{
		if(null == instance)
			instance = new AnzhiManger();
		return instance;
	}
	public void AnzhiInit(FREContext $context,String $appKey,String $appSecret,String $appChannel)
	{
		_context = $context;
		appKey = $appKey;
		appSecret = $appSecret;
		appChannel = $appChannel;
		
		final CPInfo info = new CPInfo();
		info.setAppKey(appKey);
		info.setSecret(appSecret);
		// info.setOpenOfficialLogin(true);
		info.setChannel(appChannel);
		anzhiCenter = AnzhiUserCenter.getInstance();
		anzhiCenter.setCPInfo(info);
		anzhiCenter.setCallback(mCallback);
//		anzhiCenter.setOfficialCallback(mOfficialCall);
		
		callBack(TAG,"init");
	}
	
	public void AnzhiLogin(FREContext $ctx)
	{
		if(null != anzhiCenter)
			anzhiCenter.login($ctx.getActivity());
		else
			callBack(TAG,"pleast init first!");
	}
	
	public void AnzhiLogout(FREContext $ctx)
	{
		if(null != anzhiCenter)
			anzhiCenter.logout($ctx.getActivity());
		else
			callBack(TAG,"pleast init first!");
	}
	
	public void AnzhiPay(FREContext $ctx,int orderID,int price,String body,String ext)
	{
		
		//(context,支付编号, 支付金额, 支付描述信息, 扩展信息);
		//支付编号：区别哪个支付点，值自定义
		//支付金额：单位(元)
		//扩展信息：用于游戏客户端与游戏服务端之间数据通信，支付完成后安智平台会将扩展信息内容传递给游戏服务端
		//注：当支付金额为0 时则自动切换为不定金额方式支付
		if(null != anzhiCenter)
		{
			BridgeActivity._context = $ctx;
			BridgeActivity.anzhiCenter = anzhiCenter;
			Intent intent = new Intent(BridgeActivity.MYACTIVITY_ACTION);
			intent.putExtra("orderID", orderID);
			intent.putExtra("price", price);
			intent.putExtra("body", body);
			intent.putExtra("ext", ext);
			Log.d(TAG, "---------startActivity-------");
			_context.getActivity().startActivityForResult(intent, 0);
			
		}
		else
			callBack(TAG,"pleast init first!");
	}
	public void AnzhiCenter(FREContext $ctx)
	{
		if(null != anzhiCenter)
			anzhiCenter.viewUserInfo($ctx.getActivity());
		else
			callBack(TAG,"pleast init first!");
	}
	
	AnzhiCallback mCallback = new AnzhiCallback() {
		@Override
		public void onCallback(CPInfo cpInfo, final String result) {
			Log.e("anzhi", "result " + result);
			try {
				JSONObject json = new JSONObject(result);
				String key = json.optString("callback_key");
				if ("key_login".equals(key)) {
					/**
					 * {”callback_key”:”key_login”,
					 *	”ver”:”1.0”,
					 *	“UID”:UID 唯一且不变
					 *	”code”:状态码,(200 登录成功，非200 登录失败),
					 *	”code_desc”:状态描述信息,
					 *	”login_name”:”账户名”, （唯一不变但可能出现火星文）
					 *	”nick_name”:”昵称”,
					 *	”sid”:”sid” }
					 */
					int code = json.optInt("code");
					String desc = json.optString("code_desc");
					String UID = json.optString("uid");
					String sid = json.optString("sid");
					String login_name = json.optString("login_name");
					String nick_name = json.optString("nick_name");
					Log.d("anzhiLogin", json.toString());
					
					if (code != 200) {
						callBack(TAG_LOGIN,"登录失败*2");
					} else {
						callBack(TAG_LOGIN,"success*0*"+UID+"*"+sid+"*"+desc+"*"+nick_name+"*"+login_name);
					}
				} else if ("key_pay".equals(key)) {
					/*{”callback_key”:”key_pay”,
						”ver”:”1.0”,
						”order_id”:”订单号”,
						”price”:”金额(元)”,
						”code”:状态码(200 支付成功，201 等待支付完成，其它值为失败),
						”code_desc”:状态描述信息,
						”time”:”时间”,
						“pay_type”:”支付方式ID,10 为安智币,11 为支付宝,12 为银联,13 为财付通,14 为充值卡”}
						*/
					
					Log.d("anzhiPay", json.toString());
					int code = json.optInt("code");
//					String desc = json.optString("desc");
//					String orderId = json.optString("order_id");
//					String price = json.optString("price");
//					String time = json.optString("time");
					if (code == 200) {
						callBack(TAG_PAY,"支付成功");
					} else if (code == 201){
						callBack(TAG_PAY,"等待支付完成");
					}
					else
					{
						callBack(TAG_PAY,"支付未完成");
					}
				} else if ("key_logout".equals(key)) {
					callBack("AnzhiExit","loginend");
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	};

	/**
	 * 官方登录结果
	 */
	OfficialLoginCallback mOfficialCall = new OfficialLoginCallback() {

		@Override
		public void onOfficialLoginResult(String result) {
			// ��¼���ص�
			Log.e("anzhi", result);
			try {
				/*{”callback_key”:”key_login”,
					”ver”:”1.0”,
					”code”:状态码,200 登录成功，非200 登录失败,
					”code_desc”:状态描述信息”,
					”sid”:”sid” }*/
				JSONObject json = new JSONObject(result);
				String key = json.optString("callback_key");
				if ("key_login".equals(key)) {
					int code = json.optInt("code");
//					String desc = json.optString("code_desc");
					if (code == 200) {
						
					} else {
						
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		@Override
		public String login(String user, String password) {
			//user 用户名，pwd 密码，extInfo 扩展信息(如验证码)
			//该方法为子线程，在该方法中请求游戏自己的登录接口,游戏服务端需要给客户端返回一个安智的sessiontoken
			Log.e("anzhi", "user = " + user + " pass = " + password);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return "123123"; 
		}
	};
	/**
	 * 结果传给AS端
	 */
	public void callBack(String $TAG,String status){
		Log.d($TAG, "-----status----"+status);
		_context.dispatchStatusEventAsync($TAG,status);
	}
}
