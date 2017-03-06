package io.github.mayubao.pay_library;

/**
 * 微信支付API
 * 
 * 使用:
 * 
 * WechatPayAPI.getInstance().sendPayReq(wechatPayReq);
 *
 * Created by mayubao on 2017/3/5.
 * Contact me 345269374@qq.com
 */
public class WechatPayAPI {

	/**
	 * 获取微信支付API
	 */
    private static final Object mLock = new Object();
    private static WechatPayAPI mInstance;

    public static WechatPayAPI getInstance(){
        if(mInstance == null){
            synchronized (mLock){
                if(mInstance == null){
                    mInstance = new WechatPayAPI();
                }
            }
        }
        return mInstance;
    }
    
    /**
     * 发送微信支付请求
     * @param wechatPayReq
     */
    public void sendPayReq(WechatPayReq wechatPayReq){
    	wechatPayReq.send();
    }
}
