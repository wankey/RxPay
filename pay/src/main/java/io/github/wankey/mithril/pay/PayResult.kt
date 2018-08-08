package io.github.wankey.mithril.pay

/**
 *
 *
 * @author wankey
 *
 * create on 2018/7/28
 *
 */
class PayResult(val result: Int, val msg: String) {
  companion object {
    const val PAY_SUCCESS = 1
    const val PAY_ERROR = 2
    const val PAY_CANCEL = 3
  }
}