package com.rizalord.dewakomik

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_manga_detail.*
import kotlinx.android.synthetic.main.activity_view_image.*
import okhttp3.*
import java.io.IOException

class ViewImage : AppCompatActivity() {

    lateinit var data : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_image)

        data = intent.extras?.get("dataId") as String
        val chapter = intent.extras?.get("chapter") as String

        supportActionBar?.setTitle("Chapter " + chapter.toString())
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewRecyclerView.layoutManager = LinearLayoutManager(this)


        fetchJson()
    }

    private fun fetchJson(){
        val url = "http://" + Constants.ip + ":80/dewakomikapi/api/manga?chap=" +
                data.toString()
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
                val accept = gson.fromJson(body , hFood::class.java)
                if(accept.status == true){
                    runOnUiThread {
                        viewRecyclerView.adapter = ReadAdapter(accept)
                    }
                }
            }
        })
    }
}

class hFood(val status : Boolean , val data : List<subDatal>)
class subDatal(val image : String)
