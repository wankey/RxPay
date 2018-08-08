package io.github.wankey.mithril.pay

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import io.github.wankey.mithril.pay.util.BusUtils

/**
 *
 *
 * @author wankey
 *
 * create on 2018/7/28
 *
 */
class WXPayEntryActivity : Activity(), IWXAPIEventHandler {
  private lateinit var api: IWXAPI

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    api = WXAPIFactory.createWXAPI(this, Config.instance.wxId)
    api.handleIntent(intent, this)
  }

  override fun onNewIntent(intent: Intent) {
    super.onNewIntent(intent)
    api.handleIntent(intent, this)
  }

  override fun onReq(baseReq: BaseReq) {

  }

  override fun onResp(baseResp: BaseResp) {
    BusUtils.default.post(baseResp)
    finish()
  }
}