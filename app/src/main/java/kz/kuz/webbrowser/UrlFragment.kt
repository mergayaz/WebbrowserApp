package kz.kuz.webbrowser

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class UrlFragment : Fragment() {
    private lateinit var mUri: Uri
    private lateinit var mProgressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mUri = arguments?.getParcelable("page_url")!!
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        activity?.setTitle(R.string.toolbar_title)
        val view = inflater.inflate(R.layout.url_page, container, false)
        mProgressBar = view.findViewById(R.id.progress_bar)
        mProgressBar.max = 100 // значения в диапазоне 0-100
        mWebView = view.findViewById(R.id.web_view)
        mWebView.settings.javaScriptEnabled = true // включаем Javascript
        mWebView.settings.loadWithOverviewMode = true // необходимо, чтобы страница
        mWebView.settings.useWideViewPort = true // помещалась в окне приложения
        mWebView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(webView: WebView, newProgress: Int) {
                // обновляем ProgressBar в зависимости от статуса загрузки
                if (newProgress == 100) {
                    mProgressBar.visibility = View.GONE
                } else {
                    mProgressBar.visibility = View.VISIBLE
                    mProgressBar.progress = newProgress
                }
            }

            override fun onReceivedTitle(webView: WebView, title: String) {
                // заголовок сайта вставляем в виде подзаголовка представления
                val activity = activity as AppCompatActivity?
                activity!!.supportActionBar!!.subtitle = title
            }
        }
        WebView.setWebContentsDebuggingEnabled(true)
//        mWebView.webViewClient = WebViewClient() // создаём веб-клиента
        mWebView.webViewClient = MyWebViewClient() // добавлено для выполнения задания
        mWebView.loadUrl(mUri.toString()) // загружаем страницу
        return view
    }

    // нижекласс создан для выполнения задания (если пользователь нажимает на ссылку, отличную от
    // http или https, тогда запускается интент на открытие, чтобы не выходила ошибочная страница
    private inner class MyWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            return if (URLUtil.isHttpUrl(url) || URLUtil.isHttpsUrl(url)) {
                false
            } else {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
                true
            }
        }
    }

    companion object {
        lateinit var mWebView: WebView
    }
}