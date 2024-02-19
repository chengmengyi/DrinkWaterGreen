package lsnq.drink.drinkwater

import lsnq.drink.drinkwater.databinding.ActivityWebviewBinding
import lsnq.drink.drinkwater.view.BaseActivity

class WebViewActivity : BaseActivity<ActivityWebviewBinding>() {

    override fun layoutId() = R.layout.activity_webview
    private var url: String = ""

    override fun onCreate() {
        url = intent.getStringExtra("webUrl") ?: ""

        binding.webView.apply {
            settings.apply {
                useWideViewPort = false
                javaScriptEnabled = true
                loadsImagesAutomatically = true
            }
        }
        binding.webView.loadUrl(url)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.webView.destroy()
    }
}