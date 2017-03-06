package io.github.mayubao.pay_library;

/**
 * 支付宝支付API
 * 
 * 使用:
 * 
 * AliPayAPI.getInstance().apply(config).sendPayReq(aliPayReq);
 *
 * Created by mayubao on 2017/3/5.
 * Contact me 345269374@qq.com
 */
public class AliPayAPI {
	
	private Config mConfig;

	/**
	 * 获取支付宝支付API
	 */
    private static final Object mLock = new Object();
    private static AliPayAPI mInstance;

    public static AliPayAPI getInstance(){
        if(mInstance == null){
            synchronized (mLock){
                if(mInstance == null){
                    mInstance = new AliPayAPI();
                }
            }
        }
        return mInstance;
    }
	
    
    /**
     * 配置支付宝配置
     * @param config
     * @return
     */
    public AliPayAPI apply(Config config){
    	this.mConfig = config;
    	return this;
    }

	/**
	 * 发送支付宝支付请求
	 * @param aliPayReq
	 */
    public void sendPayReq(AliPayReq aliPayReq){
    	aliPayReq.send();
    }


	/**
	 * 发送支付宝支付请求
	 * @param aliPayReq2
	 */
	public void sendPayReq(AliPayReq2 aliPayReq2){
		aliPayReq2.send();
	}
    
    
    /**
     * 支付宝支付配置
     * @author Administrator
     *
     */
    public static class Config{
        //ali pay config
        // 商户私钥，pkcs8格式
        private String aliRsaPrivate;
        // 支付宝公钥
        private String aliRsaPublic;
        // 商户PID
        // 签约合作者身份ID
        private String partner;
        // 商户收款账号
        // 签约卖家支付宝账号
        private String seller;
        
        public String getAliRsaPrivate() {
			return aliRsaPrivate;
		}

		public void setAliRsaPrivate(String aliRsaPrivate) {
			this.aliRsaPrivate = aliRsaPrivate;
		}

		public String getAliRsaPublic() {
			return aliRsaPublic;
		}

		public void setAliRsaPublic(String aliRsaPublic) {
			this.aliRsaPublic = aliRsaPublic;
		}

		public String getPartner() {
			return partner;
		}

		public void setPartner(String partner) {
			this.partner = partner;
		}

		public String getSeller() {
			return seller;
		}

		public void setSeller(String seller) {
			this.seller = seller;
		}

		public static class Builder{
            //ali pay config
            // 商户私钥，pkcs8格式
            private String aliRsaPrivate;
            // 支付宝公钥
            private String aliRsaPublic;
            // 商户PID
            // 签约合作者身份ID
            private String partner;
            // 商户收款账号
            // 签约卖家支付宝账号
            private String seller;
            
            public Builder() {
				super();
			}

			public Builder setRsaPrivate(String aliRsaPrivate){
            	this.aliRsaPrivate = aliRsaPrivate;
            	return this;
            }
			
			public Builder setRsaPublic(String aliRsaPublic){
            	this.aliRsaPublic = aliRsaPublic;
            	return this;
            }
			
			public Builder setPartner(String partner){
            	this.partner = partner;
            	return this;
            }
			
			public Builder setSeller(String seller){
            	this.seller = seller;
            	return this;
            }
			
			public Config create(){
				Config conf = new Config();
				conf.aliRsaPrivate = this.aliRsaPrivate;
				conf.aliRsaPublic = this.aliRsaPublic;
				conf.partner = this.partner;
				conf.seller = this.seller;
				return conf;
			}
        }
    }
}
