package io.github.wankey.mithril.pay.alipay

import android.app.Activity
import com.alipay.sdk.app.PayTask
import io.github.wankey.mithril.pay.PayResult
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

/**
 *
 *
 * @author wankey
 *
 * create on 2018/7/28
 *
 */
class RxAliPay {
  fun pay(activity: Activity, paySign: String?): Observable<PayResult> {
    if (paySign.isNullOrEmpty()) {
      return Observable.error(IllegalArgumentException("paySign is invalid"))
    } else {
      return Observable.just(activity)
          .map { PayTask(activity) }
          .map {
            val result = it.payV2(paySign, true)
            return@map result
          }
          .map {
            val payStatus = it.get("resultStatus")
            return@map when (payStatus) {
              "9000" -> {
                PayResult(PayResult.PAY_SUCCESS, "支付成功")
              }
              "6001" -> {
                PayResult(PayResult.PAY_CANCEL, "支付已取消")
              }
              else -> {
                PayResult(PayResult.PAY_ERROR, "支付失败")
              }
            }
          }
          .subscribeOn(Schedulers.io())
          .observeOn(Schedulers.io())
    }
  }

  companion object {
    val instance: RxAliPay by lazy { RxAliPay() }
  }
}