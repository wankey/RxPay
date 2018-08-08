package io.github.wankey.mithril.pay

import android.app.Activity
import io.github.wankey.mithril.pay.alipay.RxAliPay
import io.github.wankey.mithril.pay.wepay.RxWePay
import io.reactivex.Observable

/**
 * RxPay
 *
 * @author wankey
 *
 * create on 2018/7/28
 *
 */
object RxPay {
  const val PAY_TYPE_ALI_PAY = 1
  const val PAY_TYPE_WE_PAY = 2

  fun pay(activity: Activity, type: Int, param: PayBean): Observable<PayResult> {
    return when (type) {
      PAY_TYPE_ALI_PAY -> RxAliPay.instance.pay(activity, param.paySign)
      PAY_TYPE_WE_PAY -> {
        RxWePay.instance.pay(activity, param.appId, param.partnerId, param.nonceStr, param.timestamp, param.prePayId, param.sign)
      }
      else -> {
        Observable.error(IllegalArgumentException("未支持的支付方式"))
      }
    }
  }

}