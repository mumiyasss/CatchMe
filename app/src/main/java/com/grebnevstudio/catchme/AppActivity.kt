package com.grebnevstudio.catchme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class AppActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.container_app_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ViewPagerFragment.newInstance())
                .commitNow()
        }
    }

}
