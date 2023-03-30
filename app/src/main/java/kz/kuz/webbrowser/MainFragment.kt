package kz.kuz.webbrowser

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

// здесь мы открываем веб-страницу двумя способами: в отдельном окне и внутри приложения
// в манифесте при регистрации активности, где находится виджет WebView, указываем configChanges
// этот атрибут необходим, чтобы при перезагрузке активность сама перерабатывала изменения
// конфигурации, чтобы не было перезагрузки браузера
class MainFragment : Fragment() {
    // методы фрагмента должны быть открытыми
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        activity!!.setTitle(R.string.toolbar_title)
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        val btn1 = view.findViewById<Button>(R.id.button1)
        btn1.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://spice.101.kz"))
            // используется неявный интент для открытия браузера в отдельном окне
            startActivity(i)
        }
        val btn2 = view.findViewById<Button>(R.id.button2)
        btn2.setOnClickListener {
            val i = Intent(activity, UrlActivity::class.java)
//            i.data = Uri.parse("market://details?id=com.google.android.youtube")
            i.data = Uri.parse("https://spice.101.kz")
            startActivity(i)
            // открываем новую активность, в которой, во фрагменте присутствует виджет WebView
            // в интент вкладываем URL адрес
        }
        return view
    }
}