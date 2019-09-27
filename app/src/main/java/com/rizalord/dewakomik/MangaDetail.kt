package com.rizalord.dewakomik

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_manga_detail.*
import okhttp3.*
import java.io.IOException
import java.io.Serializable

class MangaDetail : AppCompatActivity() {

    lateinit var data : subData
    lateinit var data2 : homeFragment.subData
    lateinit var kode : String
    var cek = true
    lateinit var url : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manga_detail)


        kode = intent.extras?.get("kode") as String
        if(kode == "2"){
            data =  intent.extras?.get("data") as subData
            cek = true
        }else{
            data2 =  intent.extras?.get("data") as homeFragment.subData
            cek = false
        }


        if(cek == true){
            supportActionBar?.setTitle(data.title)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            comicTitle.text = data.title.toString()
            author.text = data.author.toString()
            status.text = data.status.toString()
            Glide.with(this).load(data.image).into(midImage)
            Glide.with(this).load(data.image).into(bgImage)
            sinopsis.text = data.sinopsis.toString()
            url =  Constants.ip+"api/manga?chaps=" +
                    data.chapter_id.toString()
        }else{
            supportActionBar?.setTitle(data2.title)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            comicTitle.text = data2.title?.toString()
            author.text = data2.author?.toString()
            status.text = data2.status?.toString()
            Glide.with(this).load(data2?.image).into(midImage)
            Glide.with(this).load(data2?.image).into(bgImage)
            sinopsis.text = data2.sinopsis?.toString()
            url = Constants.ip +"api/manga?chaps=" +
                    data2.chapter_id?.toString()
        }


        listChapters.layoutManager = LinearLayoutManager(this)
        fetchJson()

    }

    private fun fetchJson(){

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
                val homefood = gson.fromJson(body , HomeFoodd::class.java)
                println(body)


                if(homefood.status == true){
                    runOnUiThread {
                        if(cek == true){
                            listChapters.adapter = ListAdapter(homefood , data.chapter_id.toString())
                        }else{
                            listChapters.adapter = ListAdapter(homefood , data2.chapter_id.toString())
                        }
                    }
                }



            }
        })
    }

    class HomeFoodd( val status : Boolean , val data: List<subDatak>) : Serializable
    class subDatak(val id_table : String , val id_manga : String , val chap_num : String,
                   val manga_chap_read : String , val date_uploaded : String)




}

