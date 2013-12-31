package com.anzhi.func;

import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;

/**
 * 初始化SDK
 * @author Rect
 * @version  Time：2013-5-8 
 */
public class AnzhiInit implements FREFunction {

	private String TAG = "AnzhiInit";
	private FREContext _context;
	public static FREContext ctx;
	@Override
	public FREObject call(final FREContext context, FREObject[] arg1) {
		// TODO Auto-generated method stub
		_context = context;
		ctx = context;
		FREObject result = null; 
		// TODO Auto-generated method stub
		//--------------------------------
		String appKey;
		String appSecret;
		String appChannel;
		try
		{
			appKey = arg1[0].getAsString();
			appSecret = arg1[1].getAsString();
			appChannel = arg1[2].getAsString();
			
			AnzhiManger.getInstance().AnzhiInit(_context, appKey, appSecret, appChannel);
			
			callBack("k:"+appKey);
			callBack("s:"+appSecret);
			callBack("c:"+appChannel);
		}
		catch (Exception e) {
			// TODO: handle exception
			callBack("argv is error");
			return null;
		}
		callBack("success");
		//--------------------------------
		
		return result;
	}

	/**
	 * 结果传给AS端
	 */
	public void callBack(String status){
		Log.d(TAG, "-----status----"+status);
		_context.dispatchStatusEventAsync(TAG, "status:"+status);
	}

}
