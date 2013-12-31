package com.anzhi.func;



import org.json.JSONException;
import org.json.JSONObject;

import com.adobe.fre.FREContext;
import com.anzhi.usercenter.sdk.AnzhiCallback;
import com.anzhi.usercenter.sdk.AnzhiUserCenter;
import com.anzhi.usercenter.sdk.item.CPInfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class BridgeActivity extends Activity implements
OnClickListener {
	//声明开启Activity的Action
	public static final String MYACTIVITY_ACTION = "com.anzhi.func.BridgeActivity";
	private String TAG = "BridgeActivity";
	private String TAG_LOGIN = "AnzhiLogin";
	private String TAG_PAY = "AnzhiPay";
	public static FREContext _context;
	private LinearLayout layout;
	private int orderID;
	private int price;
	private String body;
	private String ext;
	public static AnzhiUserCenter anzhiCenter;
	public static Boolean isFirst = true;
	protected static final int UPDATE_TEXT = 0;
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case 1:
			finish();
			break;
		case 3:

			break;
		}
	}
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//构建界面

		super.onCreate(savedInstanceState);
		Log.d(TAG, "---------onCreate-------");

		// 隐藏标题栏  
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);  
		// 隐藏状态栏  
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.HORIZONTAL);//VERTICAL
		layout.setBackgroundResource(_context.getResourceId("drawable.bg"));
		this.setContentView(layout);
		layout.setId(1);
		layout.setOnClickListener(this);

		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		orderID = bundle.getInt("orderID");
		price = bundle.getInt("price");
		body = bundle.getString("body");
		ext = bundle.getString("ext");
		if(isFirst)
		{
			anzhiCenter.setCallback(mCallbackPay);
			isFirst = false;
		}
		anzhiCenter.pay(this, orderID, (float)price, body, ext);


	}



	/**
	 * 结果传给AS端
	 */
	public void callBack(String $TAG,String status){
		Log.d($TAG, "-----status----"+status);
		_context.dispatchStatusEventAsync($TAG,status);
	}
	AnzhiCallback mCallbackPay = new AnzhiCallback() {
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
	@Override  
	public boolean onKeyDown(int keyCode, KeyEvent event)  
	{  
		if (keyCode == KeyEvent.KEYCODE_BACK )  
		{  
			finish();
		}  
		return super.onKeyDown(keyCode, event);

	}  

}
