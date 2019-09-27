package com.rizalord.dewakomik


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.row_list.view.*
import okhttp3.*
import java.io.IOException
import java.io.Serializable

/**
 * A simple [Fragment] subclass.
 */
class listFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var root : View = inflater.inflate(R.layout.fragment_list, container, false)
        var recyclerView = root.findViewById<View>(R.id.recyclerViewList) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity)


        fetchJson(recyclerView)
        // Inflate the layout for this fragment
        return root
    }

    class AdapterList(val homefeed : HomeFeed) : RecyclerView.Adapter<CustomViewHolder>(){
        override fun getItemCount(): Int {
            return homefeed.data.count()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
            var layoutInflater = LayoutInflater.from(parent?.context)
            var row = layoutInflater.inflate(R.layout.row_list , parent , false)
            return CustomViewHolder(row)
        }

        override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

            holder.v.mangaTitle.text = homefeed.data.get(position).title.toString()
            holder.v.genre.text = homefeed.data.get(position).genre.toString()
            Glide.with(holder.v).load(homefeed.data.get(position).image.toString())
                .into(holder.v.imgList)

            holder.v.setOnClickListener({
                var intent = Intent(holder.v.context , MangaDetail::class.java)
                intent.putExtra("data" , homefeed.data.get(position))
                intent.putExtra("kode" , "2")
                holder.v.context.startActivity(intent)
            })
        }
    }

    class CustomViewHolder(var v : View) : RecyclerView.ViewHolder(v){

    }

    private fun fetchJson(rv : RecyclerView){


        val url = Constants.ip+"api/manga/"
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
                val homefeed = gson.fromJson(body , HomeFeed::class.java)

                if(homefeed.status == true){
                    activity?.runOnUiThread {
                        rv.adapter = AdapterList(homefeed)
                    }
                }
            }
        })

    }


}

class HomeFeed(val status : Boolean , val data: List<subData>) : Serializable

class subData(val id:Int , val image : String ,  val title: String,val author : String , val status : String ,
              val sinopsis : String , val chapter_id : String , val genre : String ) : Serializable
