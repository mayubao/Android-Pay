package io.github.mayubao.pay_library;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * 微信支付请求
 * 
 * @author Administrator
 *
 */
public class WechatPayReq implements IWXAPIEventHandler {
	
	private static final String TAG = WechatPayReq.class.getSimpleName();

	private Activity mActivity;

	//微信支付AppID
	private String appId;
	//微信支付商户号
	private String partnerId;
	//预支付码（重要）
	private String prepayId;
	//"Sign=WXPay"
	private String packageValue;
	private String nonceStr;
	//时间戳
	private String timeStamp;
	//签名
	private String sign;
	
	//微信支付核心api
    IWXAPI mWXApi;
	
	public WechatPayReq() {
		super();
	}


	/**
	 * 发送微信支付请求
	 */
	public void send() {
        mWXApi = WXAPIFactory.createWXAPI(mActivity, null);
        mWXApi.handleIntent(mActivity.getIntent(), this);
        
        mWXApi.registerApp(this.appId);
        
        PayReq request = new PayReq();

        request.appId = this.appId;
        request.partnerId = this.partnerId;
        request.prepayId= this.prepayId;
        request.packageValue = this.packageValue != null ? this.packageValue : "Sign=WXPay";
        request.nonceStr= this.nonceStr;
        request.timeStamp= this.timeStamp;
        request.sign = this.sign;
        
        mWXApi.sendReq(request);
	}
	
	public static class Builder{
		//上下文
		private Activity activity;
		//微信支付AppID
		private String appId;
		//微信支付商户号
		private String partnerId;
		//预支付码（重要）
		private String prepayId;
		//"Sign=WXPay"
		private String packageValue="Sign=WXPay";
		private String nonceStr;
		//时间戳
		private String timeStamp;
		//签名
		private String sign;
		public Builder() {
			super();
		}
		
		public Builder with(Activity activity){
			this.activity = activity;
			return this;
		}
		
		/**
		 * 设置微信支付AppID
		 * @param appId
		 * @return
		 */
		public Builder setAppId(String appId){
			this.appId = appId;
			return this;
		}
		
		/**
		 * 微信支付商户号
		 * @param partnerId
		 * @return
		 */
		public Builder setPartnerId(String partnerId){
			this.partnerId = partnerId;
			return this;
		}
		
		/**
		 * 设置预支付码（重要）
		 * @param prepayId
		 * @return
		 */
		public Builder setPrepayId(String prepayId){
			this.prepayId = prepayId;
			return this;
		}
		
		
		/**
		 * 设置
		 * @param packageValue
		 * @return
		 */
		public Builder setPackageValue(String packageValue){
			this.packageValue = packageValue;
			return this;
		}
		
		
		/**
		 * 设置
		 * @param nonceStr
		 * @return
		 */
		public Builder setNonceStr(String nonceStr){
			this.nonceStr = nonceStr;
			return this;
		}
		
		/**
		 * 设置时间戳
		 * @param timeStamp
		 * @return
		 */
		public Builder setTimeStamp(String timeStamp){
			this.timeStamp = timeStamp;
			return this;
		}
		
		/**
		 * 设置签名
		 * @param sign
		 * @return
		 */
		public Builder setSign(String sign){
			this.sign = sign;
			return this;
		}
		
		
		
		public WechatPayReq create(){
			WechatPayReq wechatPayReq = new WechatPayReq();
			
			wechatPayReq.mActivity = this.activity;
			//微信支付AppID
			wechatPayReq.appId = this.appId;
			//微信支付商户号
			wechatPayReq.partnerId = this.partnerId;
			//预支付码（重要）
			wechatPayReq.prepayId = this.prepayId;
			//"Sign=WXPay"
			wechatPayReq.packageValue = this.packageValue;
			wechatPayReq.nonceStr = this.nonceStr;
			//时间戳
			wechatPayReq.timeStamp = this.timeStamp;
			//签名
			wechatPayReq.sign = this.sign;
			
			return wechatPayReq;
		}
		
	}
	
	
	//微信支付监听
	private OnWechatPayListener mOnWechatPayListener;
	public WechatPayReq setOnWechatPayListener(OnWechatPayListener onWechatPayListener) {
		this.mOnWechatPayListener = onWechatPayListener;
		return this;
	}

	/**
	 * 微信支付监听
	 * @author Administrator
	 *
	 */
	public interface OnWechatPayListener{
		public void onPaySuccess(int errorCode);
		public void onPayFailure(int errorCode);
	}

	@Override
	public void onReq(BaseReq baseReq) {
		Toast.makeText(this.mActivity, "onReq===>>>get baseReq.getType : "+baseReq.getType(), Toast.LENGTH_LONG).show();
        Log.d(TAG,"onReq===>>>get baseReq.getType : "+baseReq.getType());
	}


	@Override
	public void onResp(BaseResp resp) {
        Toast.makeText(this.mActivity, "onResp===>>>get resp.getType : "+ resp.getType(), Toast.LENGTH_LONG).show();
        
//        0	成功	展示成功页面
//        -1	错误	可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。
//        -2	用户取消	无需处理。发生场景：用户不支付了，点击取消，返回APP。
        
        if(resp.getType()== ConstantsAPI.COMMAND_PAY_BY_WX){
            Log.d(TAG,"onPayFinish,errCode="+resp.errCode);
            if(this.mOnWechatPayListener != null){
            	if(resp.errCode == BaseResp.ErrCode.ERR_OK){ //        0 成功	展示成功页面
            		this.mOnWechatPayListener.onPaySuccess(resp.errCode);
            	}else{//  -1	错误       -2	用户取消
            		this.mOnWechatPayListener.onPayFailure(resp.errCode);	
            	}
            }
        }
	}
}
