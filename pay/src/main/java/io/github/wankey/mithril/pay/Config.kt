package io.github.wankey.mithril.pay

/**
 *
 *
 * @author wankey
 *
 * create on 2018/7/28
 *
 */
class Config {
  var wxId: String? = null
    private set

  fun wxId(id: String): Config {
    wxId = id
    return this
  }

  companion object {
    val instance: Config by lazy { Config() }
  }

}