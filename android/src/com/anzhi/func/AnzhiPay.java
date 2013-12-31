package com.anzhi.func;

import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;

/**
 * 执行付费
 * @author Rect
 * @version  Time：2013-5-8 
 */
public class AnzhiPay implements FREFunction {

	private String TAG = "AnzhiPay";
	private FREContext _context;
	@Override
	public FREObject call(final FREContext context, FREObject[] arg1) {
		// TODO Auto-generated method stub
		_context = context;
		FREObject result = null; 
		// TODO Auto-generated method stub
		//--------------------------------
		//在这里做付费的操作 我这里直接传回。。
		int orderID = 0;
		int price = 0;
		String body = null;
		String ext = null;
		
		try
		{
			orderID = arg1[0].getAsInt();
			price = arg1[1].getAsInt();
			body = arg1[2].getAsString();
			ext = arg1[3].getAsString();
			
			AnzhiManger.getInstance().AnzhiPay(_context, orderID, price, body, ext);
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
