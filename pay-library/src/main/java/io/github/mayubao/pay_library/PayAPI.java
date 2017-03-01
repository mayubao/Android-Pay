package io.github.mayubao.pay_library;

/**
 * 支付的API
 *
 * Created by mayubao on 2017/2/22.
 * Contact me 345269374@qq.com
 */
public class PayAPI {


    private static final Object mLock = new Object();
    private static PayAPI mInstance;

    public static PayAPI getInstance(){
        if(mInstance == null){
            synchronized (mLock){
                if(mInstance == null){
                    mInstance = new PayAPI();
                }
            }
        }
        return mInstance;
    }


    /**
     * 支付宝支付请求
     * @param aliPayRe
     */
    public void sendPayRequest(AliPayReq aliPayRe){
        AliPayAPI.getInstance().sendPayReq(aliPayRe);
    }


    /**
     * 微信支付请求
     * @param wechatPayReq
     */
    public void sendPayRequest(WechatPayReq wechatPayReq){
        WechatPayAPI.getInstance().sendPayReq(wechatPayReq);
    }

}

