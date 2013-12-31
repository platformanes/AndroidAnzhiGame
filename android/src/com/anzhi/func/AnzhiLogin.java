package com.anzhi.func;

import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;

/**
 * 执行登录
 * @author Rect
 * @version  Time：2013-5-8 
 */
public class AnzhiLogin implements FREFunction {

	private String TAG = "AnzhiLogin";
	private FREContext _context;
	@Override
	public FREObject call(final FREContext context, FREObject[] arg1) {
		// TODO Auto-generated method stub
		_context = context;
		FREObject result = null;  
		// TODO Auto-generated method stub
		//--------------------------------
		AnzhiManger.getInstance().AnzhiLogin(_context);
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
