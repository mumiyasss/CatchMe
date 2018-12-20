package com.grebnevstudio.catchme

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.grebnevstudio.catchme.ui.ChatListFragment
import com.grebnevstudio.catchme.ui.SettingsFragment
import kotlinx.android.synthetic.main.container_viewpager.view.*

class ViewPagerFragment : Fragment() {

    private lateinit var globalView: View

    companion object {
        fun newInstance() = ViewPagerFragment()
    }

    fun getActionBar() = (activity as AppCompatActivity).supportActionBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        globalView = inflater.inflate(R.layout.container_viewpager, container, false)
        (activity as AppCompatActivity).setSupportActionBar(globalView.my_toolbar as Toolbar)
        (activity as AppCompatActivity).supportActionBar?.apply {
            title = null
        }

        with(globalView) {
            viewpager.adapter =
                    MainFragmentPagerAdapter((activity as AppActivity).supportFragmentManager)
            viewpager.addOnPageChangeListener(Listener())
        }
        return globalView
    }

    private inner class Listener : ViewPager.SimpleOnPageChangeListener() {
        private val subtitleColorShade: Int = 0x7d

        private val subtitleSize: Float = 20.0f
        private val titleSize: Float = 40.0f
        private val titleSubtitleSizeDelta = titleSize - subtitleSize
        private val searchIconTranslation: Int
        private val titleLayoutTranslation: Int
        private var lastPositionOffset = 0.0f

        init {
            val displayMetrics = DisplayMetrics()
            (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
            searchIconTranslation = -(displayMetrics.heightPixels / 9)
            titleLayoutTranslation = displayMetrics.widthPixels / 3
        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            with(globalView) {
                val difference = lastPositionOffset - positionOffset
                if ((difference > 0.01 || difference < 0.01) && positionOffset > 0.01) {
                    val greyShade = (subtitleColorShade * positionOffset).toInt()
                    toolbar_title.setTextColor(Color.rgb(greyShade, greyShade, greyShade))
                    val blackShade = (subtitleColorShade * (1 - positionOffset)).toInt()
                    toolbar_subtitle.setTextColor(Color.rgb(blackShade, blackShade, blackShade))

                    toolbar_title.textSize = titleSize - titleSubtitleSizeDelta * positionOffset
                    toolbar_subtitle.textSize = subtitleSize + titleSubtitleSizeDelta * positionOffset

                    search_icon.translationY = searchIconTranslation * positionOffset
                    titles_layout.translationX = titleLayoutTranslation * positionOffset
                    lastPositionOffset = positionOffset
                }
            }
        }
    }

    private inner class MainFragmentPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getCount() = 2

        override fun getItem(position: Int) =
            when (position) {
                0 -> ChatListFragment()
                1 -> SettingsFragment()
                else -> ChatListFragment()
            }
    }


}
