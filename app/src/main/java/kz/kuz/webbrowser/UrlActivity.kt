package kz.kuz.webbrowser

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class UrlActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fm = supportFragmentManager
        var fragment = fm.findFragmentById(R.id.fragment_container)
        if (fragment == null) {
            val args = Bundle()
            args.putParcelable("page_url", intent.data)
            fragment = UrlFragment()
            fragment.setArguments(args)
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commitNow()
        }
    }

    // добавлено для выполнения задания (переход на предыдущую страницу при нажатии кнопки Back)
    override fun onBackPressed() {
        val webView = UrlFragment.mWebView
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}