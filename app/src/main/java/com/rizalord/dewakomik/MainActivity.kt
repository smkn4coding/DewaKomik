package com.rizalord.dewakomik

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.R.attr.src
import android.content.Context
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.core.graphics.drawable.RoundedBitmapDrawable
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.widget.ScrollView
import android.widget.TableLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(!isNetworkAvailable()){
            Toast.makeText(this,"Koneksi bermasalah",Toast.LENGTH_SHORT)
                .show()

            finishAffinity()
        }

//


        val adapter = MyViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(homeFragment() , "Home")
        adapter.addFragment(listFragment() , "List")
        adapter.addFragment(favoriteFragment() , "Search")
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
//        tabLayout.getTabAt(0)?.setIcon(R.drawable.ic_home_black_24dp)
//        tabLayout.getTabAt(1)?.setIcon(R.drawable.ic_list_black_24dp)
//        tabLayout.getTabAt(2)?.setIcon(R.drawable.ic_favorite_black_24dp)


    }

    class MyViewPagerAdapter(manager : FragmentManager) : FragmentPagerAdapter(manager){

        private val fragmentList : MutableList<Fragment> = ArrayList()
        private val titleList : MutableList<String> = ArrayList()

        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }

        override fun getCount(): Int {
            return fragmentList.size
        }

        fun addFragment(fragment : Fragment , title : String){
            fragmentList.add(fragment)
            titleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titleList.get(position).toString()
        }
    }


    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE)
        return if (connectivityManager is ConnectivityManager) {
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            networkInfo?.isConnected ?: false
        } else false
    }

}
