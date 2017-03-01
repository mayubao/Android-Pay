package io.github.mayubao.pay_library;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    /**
     * 微信支付Test
     */
    public void testWechatPay(){
        String appid        = "";
        String partnerid    = "";
        String prepayid     = "";
        String noncestr     = "";
        String timestamp    = "";
        String sign         = "";
        WechatPayReq wechatPayReq = new WechatPayReq.Builder()
                .with(this) //activity实例
                .setAppId(appid) //微信支付AppID
                .setPartnerId(partnerid)//微信支付商户号
                .setPrepayId(prepayid)//预支付码
//								.setPackageValue(wechatPayReq.get)//"Sign=WXPay"
                .setNonceStr(noncestr)
                .setTimeStamp(timestamp)//时间戳
                .setSign(sign)//签名
                .create();

        PayAPI.getInstance().sendPayRequest(wechatPayReq);
//								.setOnWechatPayListener(new OnWechatPayListener() {
//
//									@Override
//									public void onPaySuccess(int errorCode) {
//										ToastUtil.show(mContext, "支付成功" + errorCode);
//
//									}
//
//									@Override
//									public void onPayFailure(int errorCode) {
//										ToastUtil.show(mContext, "支付失败" + errorCode);
//
//									}
//								});
//        WechatPayAPI.getInstance().sendPayReq(wechatPayReq);

        PayAPI.getInstance().sendPayRequest(wechatPayReq);

    }


    /**
     * 支付宝支付测试
     */
    public void testAliPay(){
        String rsa_private      = "";
        String rsa_public       = "";
        String partner          = "";
        String seller           = "";

        Activity activity       = this;
        String outTradeNo       = "";
        String price            = "";
        String orderSubject     = "";
        String orderBody        = "";
        String callbackUrl      = "";

        AliPayAPI.Config config = new AliPayAPI.Config.Builder()
                .setRsaPrivate(rsa_private) //设置私钥
                .setRsaPublic(rsa_public)//设置公钥
                .setPartner(partner) //设置商户
                .setSeller(seller) //设置商户收款账号
                .create();

        AliPayReq aliPayReq = new AliPayReq.Builder()
                .with(activity)//Activity实例
                .apply(config)//支付宝支付通用配置
                .setOutTradeNo(outTradeNo)//设置唯一订单号
                .setPrice(price)//设置订单价格
                .setSubject(orderSubject)//设置订单标题
                .setBody(orderBody)//设置订单内容 订单详情
                .setCallbackUrl(callbackUrl)//设置回调地址
                .create()//
                .setOnAliPayListener(null);//
        AliPayAPI.getInstance().apply(config).sendPayReq(aliPayReq);

        PayAPI.getInstance().sendPayRequest(aliPayReq);
    }
}
