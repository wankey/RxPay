package io.github.wankey.mithril.pay.wepay

import android.app.Activity
import android.text.TextUtils.isEmpty
import com.tencent.mm.opensdk.constants.Build
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.modelpay.PayReq
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import io.github.wankey.mithril.pay.PayResult
import io.github.wankey.mithril.pay.util.BusUtils
import io.reactivex.Observable
import io.reactivex.functions.Consumer

/**
 *
 *
 * @author wankey
 *
 * create on 2018/7/28
 *
 */
class RxWePay {
  fun pay(activity: Activity, appId: String?, partnerId: String?, nonceStr: String?, timestamp: String?, prePayId: String?, sign: String?): Observable<PayResult> {
    return Observable.create<PayResult> {
      if (it.isDisposed) {
        return@create
      }
      val result = checkIsEmpty(appId, partnerId, nonceStr, timestamp, prePayId, sign)
      if (!isEmpty(result)) {
        it.onError(IllegalArgumentException("$result is required"))
        it.onComplete()
        return@create
      }

      val msgApi = WXAPIFactory.createWXAPI(activity, appId, true)
      msgApi.registerApp(appId)

      if (msgApi.wxAppSupportAPI < Build.PAY_SUPPORTED_SDK_INT) {
        it.onError(IllegalArgumentException("current version of WeChat is unsupported"))
        it.onComplete()
        return@create
      }

      val request = PayReq()
      request.appId = appId
      request.partnerId = partnerId
      request.prepayId = prePayId
      request.packageValue = "Sign=WXPay"
      request.nonceStr = nonceStr
      request.timeStamp = timestamp
      request.sign = sign
      val isSend = msgApi.sendReq(request)
      if (!isSend) {
        it.onError(IllegalArgumentException("pay error"))
        it.onComplete()
        return@create
      } else {
        BusUtils.default.doSubscribe(
            BaseResp::class.java,
            Consumer { baseResp ->
              when (baseResp.errCode) {
                BaseResp.ErrCode.ERR_OK -> PayResult(PayResult.PAY_SUCCESS, baseResp.errStr)
                BaseResp.ErrCode.ERR_USER_CANCEL -> PayResult(PayResult.PAY_CANCEL, baseResp.errStr)
                else -> PayResult(PayResult.PAY_ERROR, baseResp.errStr)
              }
            }, Consumer { it.message?.let { it1 -> PayResult(PayResult.PAY_ERROR, it1) } })
      }
    }

  }

  private fun checkIsEmpty(appId: String?, partnerId: String?, nonceStr: String?, timestamp: String?, prePayId: String?, sign: String?): String {
    return if (isEmpty(appId))
      "appId"
    else if (isEmpty(partnerId))
      "partnerId"
    else if (isEmpty(nonceStr))
      "nonceStr"
    else if (isEmpty(timestamp))
      "timestamp"
    else if (isEmpty(prePayId))
      "prePayId"
    else if (isEmpty(sign)) "sign" else ""
  }

  companion object {
    val instance: RxWePay by lazy { RxWePay() }
  }
}