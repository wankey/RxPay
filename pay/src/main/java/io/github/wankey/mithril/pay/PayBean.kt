package io.github.wankey.mithril.pay

/**
 * 支付参数Bean
 * 支付宝支付、微信支付分别使用各自对应的构造函数
 * @author wankey
 *
 * create on 2018/7/28
 *
 */
data class PayBean(val paySign: String? = null,
    val appId: String? = null,
    val partnerId: String? = null,
    val nonceStr: String? = null,
    val timestamp: String? = null,
    val prePayId: String? = null,
    val sign: String? = null) {
  /**
   * 支付宝支付时使用的构造函数
   */
  constructor(paySign: String) : this(paySign, null, null, null, null, null, null)

  /**
   * 微信支付时使用的构造函数
   * @param appId 微信开放平台的appId
   * @param partnerId partnerId
   * @param nonceStr nonceStr
   * @param timestamp timestamp
   * @param prePayId prePayId
   * @param sign sign
   */
  constructor(appId: String, partnerId: String, nonceStr: String, timestamp: String, prePayId: String, sign: String) : this(null, appId, partnerId, nonceStr, timestamp, prePayId, sign)
}