package com.rizalord.dewakomik


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_home.*
import okhttp3.*
import java.io.IOException
import java.io.Serializable

/**
 * A simple [Fragment] subclass.
 */
class homeFragment : Fragment() {

    lateinit var recyclerv : RecyclerView
    lateinit var carousel : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var root : View = inflater.inflate(R.layout.fragment_home, container, false)
        recyclerv = root.findViewById<View>(R.id.recyclerView) as RecyclerView
        recyclerv.layoutManager = LinearLayoutManager(activity)

        fetchJson()
        fetchCarousel()

        return root
    }


    private fun fetchCarousel(){
        val url = "http://" + Constants.ip + ":80/dewakomikapi/api/manga?carousel=a"
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("Request Failed")
                println(e.message.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()

                val gson = GsonBuilder().create()
                val home = gson.fromJson(body , Home::class.java)

                if(home.status == true){
                    activity?.runOnUiThread {
                        Glide.with(activity!!).load(home.data.get(0).image2).into(gambar1)
                        Glide.with(activity!!).load(home.data.get(1).image2).into(gambar2)
                        Glide.with(activity!!).load(home.data.get(2).image2).into(gambar3)
                        Glide.with(activity!!).load(home.data.get(3).image2).into(gambar4)
                    }

                    gambar1.setOnClickListener {
                        var intent = Intent(activity , MangaDetail::class.java)
                        intent.putExtra("data" , home.data.get(0))
                        intent.putExtra("kode" , "1")
                        startActivity(intent)
                    }
                    gambar2.setOnClickListener {
                        var intent = Intent(activity , MangaDetail::class.java)
                        intent.putExtra("data" , home.data.get(1))
                        intent.putExtra("kode" , "1")
                        startActivity(intent)
                    }
                    gambar3.setOnClickListener {
                        var intent = Intent(activity , MangaDetail::class.java)
                        intent.putExtra("data" , home.data.get(2))
                        intent.putExtra("kode" , "1")
                        startActivity(intent)
                    }
                    gambar4.setOnClickListener {
                        var intent = Intent(activity , MangaDetail::class.java)
                        intent.putExtra("data" , home.data.get(3))
                        intent.putExtra("kode" , "1")
                        startActivity(intent)
                    }
                }
            }
        })
    }

    private fun fetchJson(){


        val url = "http://" + Constants.ip + ":80/dewakomikapi/api/manga?latest=a"
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("Request Failed")
                println(e.message.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()

                val gson = GsonBuilder().create()
                val home = gson.fromJson(body , Home::class.java)

                if(home.status == true){
                    activity?.runOnUiThread{
                        recyclerv.adapter = MainAdapter(home)
                    }
                }


            }
        })

    }



//    Dummy class for Carousel



//    Dummy class for RecyclerView
    class Home(val status : Boolean , val data: List<subData>) : Serializable
    class subData(val id : String , val image : String , val title : String ,
                  val author : String ,val status : String , val genre : String,
                  val sinopsis : String , val chapter_id : String ,
                  val date_created : String, val chap_num : String , val date_uploaded : String,
                  val image2 : String) : Serializable




}






