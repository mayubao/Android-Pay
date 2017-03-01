# Android-Pay
支持微信和支付宝两种主流支付的集成库， 两行代码实现微信支付， 三行代码实现支付宝支付

## 引入

对应的项目中的build.gradle文件添加依赖：

```
dependencies {
    //添加支付库
    compile 'io.github.mayubao:pay_library:1.0.0'
}
```

## 使用

### 微信支付使用

```

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

```

>注意：这里没有金额设置，金额的信息已经包含在预支付码prepayid了。

### 支付宝支付使用

```

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

        PayAPI.getInstance().sendPayRequest(aliPayReq);

```

## 混淆

```

#pay_library
-dontwarn io.github.mayubao.pay_library.**
-keep class io.github.mayubao.pay_library.** {*;}

#wechat pay
-dontwarn com.tencent.**
-keep class com.tencent.** {*;}


#alipay
-dontwarn com.alipay.**
-keep class com.alipay.** {*;}

-dontwarn  com.ta.utdid2.**
-keep class com.ta.utdid2.** {*;}

-dontwarn  com.ut.device.**
-keep class com.ut.device.** {*;}

-dontwarn  org.json.alipay.**
-keep class corg.json.alipay.** {*;}
```


## 文档

### 微信支付官方文档 支付流程
https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=8_5

### 支付宝支付官方文档 支付流程
https://doc.open.alipay.com/docs/doc.htm?spm=a219a.7629140.0.0.sdGXaH&treeId=204&articleId=105296&docType=1
  


## 注意

### 微信支付

 - 微信支付必须要在**正式签名**和**正确包名**的应用中才能成功调起。(**重点)

    即商户在微信开放平台申请开发应用后对应包名和对应签名的应用才能成功调起。
    详情请参考微信支付的开发流程文档。
    
    https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=8_5
    
 - 微信支付API没有在客户端显示的设置回调，回调是在Server端设置的。(与支付宝支付的区别，支付宝的回调是在客户端设置的)
    
### 支付宝支付 

 - 支付宝支付为了保证交易双方的身份和数据安全， 需要配置双方密钥。

    详情请参考支付宝支付的密钥处理体系文档。
    
    https://doc.open.alipay.com/docs/doc.htm?spm=a219a.7629140.0.0.1wPnBT&treeId=204&articleId=106079&docType=1

