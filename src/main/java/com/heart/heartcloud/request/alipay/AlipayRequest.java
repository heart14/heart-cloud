package com.heart.heartcloud.request.alipay;

import java.util.List;

/**
 * @ClassName: AlipayRequest
 * @Description: https://docs.open.alipay.com/api_1/alipay.trade.page.pay
 * @Author: Heart
 * @Date: 2019/7/25 14:14
 */
public class AlipayRequest {

    /**
     * 商户订单号,64个字符以内、可包含字母、数字、下划线；需保证在商户端不重复
     */
    private String out_trade_no;


    /**
     * 销售产品码，与支付宝签约的产品码名称。 注：目前仅支持FAST_INSTANT_TRADE_PAY
     */
    private String product_code = "FAST_INSTANT_TRADE_PAY";

    /**
     * 订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]。
     */
    private Double total_amount;

    /**
     * 订单标题
     */
    private String subject;

    /**
     * 订单描述
     */
    private String body;

    /**
     * 绝对超时时间，格式为yyyy-MM-dd HH:mm:ss
     */
    private String time_expire;

    /**
     * 订单包含的商品列表信息，json格式，其它说明详见商品明细说明
     */
    private List<GoodsDetail> goods_detail;

    private class GoodsDetail {

        /**
         * 商品的编号
         */
        private String goods_id;

        /**
         * 支付宝定义的统一商品编号
         */
        private String alipay_goods_id;

        /**
         * 商品名称
         */
        private String goods_name;

        /**
         * 商品数量
         */
        private Integer quantity;

        /**
         * 商品单价，单位为元
         */
        private Double price;

        /**
         * 商品类目
         */
        private String goods_category;

        /**
         * 商品类目树，从商品类目根节点到叶子节点的类目id组成，类目id值使用|分割
         */
        private String categories_tree;

        /**
         * 商品描述信息
         */
        private String body;

        /**
         * 商品的展示地址
         */
        private String show_url;

        public GoodsDetail() {
        }

        public String getGoods_id() {
            return goods_id;
        }

        public GoodsDetail setGoods_id(String goods_id) {
            this.goods_id = goods_id;
            return this;
        }

        public String getAlipay_goods_id() {
            return alipay_goods_id;
        }

        public GoodsDetail setAlipay_goods_id(String alipay_goods_id) {
            this.alipay_goods_id = alipay_goods_id;
            return this;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public GoodsDetail setGoods_name(String goods_name) {
            this.goods_name = goods_name;
            return this;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public GoodsDetail setQuantity(Integer quantity) {
            this.quantity = quantity;
            return this;
        }

        public Double getPrice() {
            return price;
        }

        public GoodsDetail setPrice(Double price) {
            this.price = price;
            return this;
        }

        public String getGoods_category() {
            return goods_category;
        }

        public GoodsDetail setGoods_category(String goods_category) {
            this.goods_category = goods_category;
            return this;
        }

        public String getCategories_tree() {
            return categories_tree;
        }

        public GoodsDetail setCategories_tree(String categories_tree) {
            this.categories_tree = categories_tree;
            return this;
        }

        public String getBody() {
            return body;
        }

        public GoodsDetail setBody(String body) {
            this.body = body;
            return this;
        }

        public String getShow_url() {
            return show_url;
        }

        public GoodsDetail setShow_url(String show_url) {
            this.show_url = show_url;
            return this;
        }

        @Override
        public String toString() {
            return "GoodsDetail{" +
                    "goods_id='" + goods_id + '\'' +
                    ", alipay_goods_id='" + alipay_goods_id + '\'' +
                    ", goods_name='" + goods_name + '\'' +
                    ", quantity=" + quantity +
                    ", price=" + price +
                    ", goods_category='" + goods_category + '\'' +
                    ", categories_tree='" + categories_tree + '\'' +
                    ", body='" + body + '\'' +
                    ", show_url='" + show_url + '\'' +
                    '}';
        }
    }

    /**
     * 公用回传参数，如果请求时传递了该参数，则返回给商户时会回传该参数。支付宝只会在同步返回（包括跳转回商户网站）和异步通知时将该参数原样返回。本参数必须进行UrlEncode之后才可以发送给支付宝。
     */
    private String passback_params;

    /**
     * 业务扩展参数
     */
    private List<ExtendParams> extend_params;

    private class ExtendParams {

        /**
         * 系统商编号
         * 该参数作为系统商返佣数据提取的依据，请填写系统商签约协议的PID
         */
        private String sys_service_provider_id;

        /**
         * 使用花呗分期要进行的分期数
         */
        private String hb_fq_num;

        /**
         * 使用花呗分期需要卖家承担的手续费比例的百分值，传入100代表100%
         */
        private String hb_fq_seller_percent;

        /**
         * 行业数据回流信息, 详见：地铁支付接口参数补充说明
         */
        private String industry_reflux_info;

        /**
         * 卡类型
         */
        private String card_type;

        public String getSys_service_provider_id() {
            return sys_service_provider_id;
        }

        public ExtendParams setSys_service_provider_id(String sys_service_provider_id) {
            this.sys_service_provider_id = sys_service_provider_id;
            return this;
        }

        public String getHb_fq_num() {
            return hb_fq_num;
        }

        public ExtendParams setHb_fq_num(String hb_fq_num) {
            this.hb_fq_num = hb_fq_num;
            return this;
        }

        public String getHb_fq_seller_percent() {
            return hb_fq_seller_percent;
        }

        public ExtendParams setHb_fq_seller_percent(String hb_fq_seller_percent) {
            this.hb_fq_seller_percent = hb_fq_seller_percent;
            return this;
        }

        public String getIndustry_reflux_info() {
            return industry_reflux_info;
        }

        public ExtendParams setIndustry_reflux_info(String industry_reflux_info) {
            this.industry_reflux_info = industry_reflux_info;
            return this;
        }

        public String getCard_type() {
            return card_type;
        }

        public ExtendParams setCard_type(String card_type) {
            this.card_type = card_type;
            return this;
        }

        @Override
        public String toString() {
            return "ExtendParams{" +
                    "sys_service_provider_id='" + sys_service_provider_id + '\'' +
                    ", hb_fq_num='" + hb_fq_num + '\'' +
                    ", hb_fq_seller_percent='" + hb_fq_seller_percent + '\'' +
                    ", industry_reflux_info='" + industry_reflux_info + '\'' +
                    ", card_type='" + card_type + '\'' +
                    '}';
        }
    }

    /**
     * 商品主类型 :0-虚拟类商品,1-实物类商品 注：虚拟类商品不支持使用花呗渠道
     */
    private String goods_type;

    /**
     * 该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m
     */
    private String timeout_express;

    /**
     * 优惠参数 注：仅与支付宝协商后可用
     */
    private String promo_params;

    /**
     * 描述分账信息，json格式，详见分账参数说明
     */
    private List<RoyaltyInfo> royalty_info;

    private class RoyaltyInfo {

    }

    /**
     * 间连受理商户信息体，当前只对特殊银行机构特定场景下使用此字段
     */
    private List<SubMerchant> sub_merchant;

    private class SubMerchant {

    }

    /**
     * 商户原始订单号，最大长度限制32位
     */
    private String merchant_order_no;

    /**
     * 可用渠道,用户只能在指定渠道范围内支付，多个渠道以逗号分割 注，与disable_pay_channels互斥 渠道列表：https://docs.open.alipay.com/common/wifww7
     */
    private String enable_pay_channels;

    /**
     * 商户门店编号
     */
    private String store_id;

    /**
     * 禁用渠道,用户不可用指定渠道支付，多个渠道以逗号分割 注，与enable_pay_channels互斥 渠道列表：https://docs.open.alipay.com/common/wifww7
     */
    private String disable_pay_channels;

    /**
     * PC扫码支付的方式，支持前置模式和
     * <p>
     * 跳转模式。
     * 前置模式是将二维码前置到商户
     * 的订单确认页的模式。需要商户在
     * 自己的页面中以 iframe 方式请求
     * 支付宝页面。具体分为以下几种：
     * 0：订单码-简约前置模式，对应 iframe 宽度不能小于600px，高度不能小于300px；
     * 1：订单码-前置模式，对应iframe 宽度不能小于 300px，高度不能小于600px；
     * 3：订单码-迷你前置模式，对应 iframe 宽度不能小于 75px，高度不能小于75px；
     * 4：订单码-可定义宽度的嵌入式二维码，商户可根据需要设定二维码的大小。
     * <p>
     * 跳转模式下，用户的扫码界面是由支付宝生成的，不在商户的域名下。
     * 2：订单码-跳转模式
     */
    private String qr_pay_mode = "2";

    /**
     * 商户自定义二维码宽度
     * 注：qr_pay_mode=4时该参数生效
     */
    private Integer qrcode_width;

    /**
     * 描述结算信息，json格式，详见结算参数说明
     */
    private List<SettleInfo> settle_info;

    private class SettleInfo {

    }

    /**
     * 开票信息
     */
    private List<InvoiceInfo> invoice_info;

    private class InvoiceInfo {

    }

    /**
     * 签约参数，支付后签约场景使用
     */
    private List<AgreementSignParams> agreement_sign_params;

    private class AgreementSignParams {

    }

    /**
     * 请求后页面的集成方式。
     * 取值范围：
     * 1. ALIAPP：支付宝钱包内
     * 2. PCWEB：PC端访问
     * 默认值为PCWEB。
     */
    private String integration_type;

    /**
     * 请求来源地址。如果使用ALIAPP的集成方式，用户中途取消支付会返回该地址。
     */
    private String request_from_url;

    /**
     * 商户传入业务信息，具体值要和支付宝约定，应用于安全，营销等参数直传场景，格式为json格式
     */
    private String business_params;

    /**
     * 外部指定买家
     */
    private List<ExtUserInfo> ext_user_info;

    private class ExtUserInfo {

    }

    public AlipayRequest() {
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public AlipayRequest setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
        return this;
    }

    public String getProduct_code() {
        return product_code;
    }

    public AlipayRequest setProduct_code(String product_code) {
        this.product_code = product_code;
        return this;
    }

    public Double getTotal_amount() {
        return total_amount;
    }

    public AlipayRequest setTotal_amount(Double total_amount) {
        this.total_amount = total_amount;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public AlipayRequest setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public String getBody() {
        return body;
    }

    public AlipayRequest setBody(String body) {
        this.body = body;
        return this;
    }

    public String getTime_expire() {
        return time_expire;
    }

    public AlipayRequest setTime_expire(String time_expire) {
        this.time_expire = time_expire;
        return this;
    }

    public List<GoodsDetail> getGoods_detail() {
        return goods_detail;
    }

    public AlipayRequest setGoods_detail(List<GoodsDetail> goods_detail) {
        this.goods_detail = goods_detail;
        return this;
    }

    public String getPassback_params() {
        return passback_params;
    }

    public AlipayRequest setPassback_params(String passback_params) {
        this.passback_params = passback_params;
        return this;
    }

    public List<ExtendParams> getExtend_params() {
        return extend_params;
    }

    public AlipayRequest setExtend_params(List<ExtendParams> extend_params) {
        this.extend_params = extend_params;
        return this;
    }

    public String getGoods_type() {
        return goods_type;
    }

    public AlipayRequest setGoods_type(String goods_type) {
        this.goods_type = goods_type;
        return this;
    }

    public String getTimeout_express() {
        return timeout_express;
    }

    public AlipayRequest setTimeout_express(String timeout_express) {
        this.timeout_express = timeout_express;
        return this;
    }

    public String getPromo_params() {
        return promo_params;
    }

    public AlipayRequest setPromo_params(String promo_params) {
        this.promo_params = promo_params;
        return this;
    }

    public List<RoyaltyInfo> getRoyalty_info() {
        return royalty_info;
    }

    public AlipayRequest setRoyalty_info(List<RoyaltyInfo> royalty_info) {
        this.royalty_info = royalty_info;
        return this;
    }

    public List<SubMerchant> getSub_merchant() {
        return sub_merchant;
    }

    public AlipayRequest setSub_merchant(List<SubMerchant> sub_merchant) {
        this.sub_merchant = sub_merchant;
        return this;
    }

    public String getMerchant_order_no() {
        return merchant_order_no;
    }

    public AlipayRequest setMerchant_order_no(String merchant_order_no) {
        this.merchant_order_no = merchant_order_no;
        return this;
    }

    public String getEnable_pay_channels() {
        return enable_pay_channels;
    }

    public AlipayRequest setEnable_pay_channels(String enable_pay_channels) {
        this.enable_pay_channels = enable_pay_channels;
        return this;
    }

    public String getStore_id() {
        return store_id;
    }

    public AlipayRequest setStore_id(String store_id) {
        this.store_id = store_id;
        return this;
    }

    public String getDisable_pay_channels() {
        return disable_pay_channels;
    }

    public AlipayRequest setDisable_pay_channels(String disable_pay_channels) {
        this.disable_pay_channels = disable_pay_channels;
        return this;
    }

    public String getQr_pay_mode() {
        return qr_pay_mode;
    }

    public AlipayRequest setQr_pay_mode(String qr_pay_mode) {
        this.qr_pay_mode = qr_pay_mode;
        return this;
    }

    public Integer getQrcode_width() {
        return qrcode_width;
    }

    public AlipayRequest setQrcode_width(Integer qrcode_width) {
        this.qrcode_width = qrcode_width;
        return this;
    }

    public List<SettleInfo> getSettle_info() {
        return settle_info;
    }

    public AlipayRequest setSettle_info(List<SettleInfo> settle_info) {
        this.settle_info = settle_info;
        return this;
    }

    public List<InvoiceInfo> getInvoice_info() {
        return invoice_info;
    }

    public AlipayRequest setInvoice_info(List<InvoiceInfo> invoice_info) {
        this.invoice_info = invoice_info;
        return this;
    }

    public List<AgreementSignParams> getAgreement_sign_params() {
        return agreement_sign_params;
    }

    public AlipayRequest setAgreement_sign_params(List<AgreementSignParams> agreement_sign_params) {
        this.agreement_sign_params = agreement_sign_params;
        return this;
    }

    public String getIntegration_type() {
        return integration_type;
    }

    public AlipayRequest setIntegration_type(String integration_type) {
        this.integration_type = integration_type;
        return this;
    }

    public String getRequest_from_url() {
        return request_from_url;
    }

    public AlipayRequest setRequest_from_url(String request_from_url) {
        this.request_from_url = request_from_url;
        return this;
    }

    public String getBusiness_params() {
        return business_params;
    }

    public AlipayRequest setBusiness_params(String business_params) {
        this.business_params = business_params;
        return this;
    }

    public List<ExtUserInfo> getExt_user_info() {
        return ext_user_info;
    }

    public AlipayRequest setExt_user_info(List<ExtUserInfo> ext_user_info) {
        this.ext_user_info = ext_user_info;
        return this;
    }

    @Override
    public String toString() {
        return "AlipayRequest{" +
                "out_trade_no='" + out_trade_no + '\'' +
                ", product_code='" + product_code + '\'' +
                ", total_amount=" + total_amount +
                ", subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                ", time_expire='" + time_expire + '\'' +
                ", goods_detail=" + goods_detail +
                ", passback_params='" + passback_params + '\'' +
                ", extend_params=" + extend_params +
                ", goods_type='" + goods_type + '\'' +
                ", timeout_express='" + timeout_express + '\'' +
                ", promo_params='" + promo_params + '\'' +
                ", royalty_info=" + royalty_info +
                ", sub_merchant=" + sub_merchant +
                ", merchant_order_no='" + merchant_order_no + '\'' +
                ", enable_pay_channels='" + enable_pay_channels + '\'' +
                ", store_id='" + store_id + '\'' +
                ", disable_pay_channels='" + disable_pay_channels + '\'' +
                ", qr_pay_mode='" + qr_pay_mode + '\'' +
                ", qrcode_width=" + qrcode_width +
                ", settle_info=" + settle_info +
                ", invoice_info=" + invoice_info +
                ", agreement_sign_params=" + agreement_sign_params +
                ", integration_type='" + integration_type + '\'' +
                ", request_from_url='" + request_from_url + '\'' +
                ", business_params='" + business_params + '\'' +
                ", ext_user_info=" + ext_user_info +
                '}';
    }
}
