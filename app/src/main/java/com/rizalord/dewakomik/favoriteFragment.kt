package com.rizalord.dewakomik


import android.content.Context
import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_favorite.*
import kotlinx.android.synthetic.main.fragment_favorite.view.*
import kotlinx.android.synthetic.main.row_list.view.*
import okhttp3.*
import java.io.IOException

/**
 * A simple [Fragment] subclass.
 */
class favoriteFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        var root : View = inflater.inflate(R.layout.fragment_favorite, container, false)
        var inputKeyword : EditText = root.inputFind as EditText

        inputKeyword.setOnEditorActionListener() { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE ||
                actionId == EditorInfo.IME_ACTION_GO) {
                //your code here
                var keyword : String = inputKeyword.text.toString()

                search(inputKeyword , keyword)





                return@setOnEditorActionListener true
            }
            false

        }



        return root




    }

    private fun search(inputKey : EditText ,keyword : String){
        if(keyword.isEmpty()){
            inputKey.setError("The field can't be blank!")
        }else{

            inputKey.hideKeyboard()

            val url = "http://" + Constants.ip + ":80/dewakomikapi/api/manga?title=" + keyword
            val request = Request.Builder().url(url).build()
            val client = OkHttpClient()
            client.newCall(request).enqueue(object : Callback{
                override fun onFailure(call: Call, e: IOException) {
                    println("Request Failed")
                    println(e.message.toString())
                }

                override fun onResponse(call: Call, response: Response) {
                    val body = response.body?.string()

                    val gson = GsonBuilder().create()
                    val home = gson.fromJson(body , homeFragment.Home::class.java)

                    activity?.runOnUiThread {
                        if(home.status == true){
                            notfound.visibility = View.INVISIBLE
                            searchCycle.visibility = View.VISIBLE
//                          jika ketemu

                            searchCycle.layoutManager = LinearLayoutManager(activity)
                            searchCycle.adapter = SearchAdapter(home)


                        }else{
                            notfound.visibility = View.VISIBLE
                            searchCycle.visibility = View.GONE
                        }
                    }

                }
            })
        }
    }

    class SearchAdapter(val home : homeFragment.Home) : RecyclerView.Adapter<CustomViewHoler>(){

        override fun getItemCount(): Int {
            return home.data.count()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHoler {
            var layoutInflater = LayoutInflater.from(parent.context)
            var row = layoutInflater.inflate(R.layout.row_list , parent , false)
            return CustomViewHoler(row)
        }

        override fun onBindViewHolder(holder: CustomViewHoler, position: Int) {
            holder.v.mangaTitle.text = home.data.get(position).title.toString()
            holder.v.genre.text = home.data.get(position).genre.toString()
            Glide.with(holder.v).load(home.data.get(position).image.toString())
                .into(holder.v.imgList)

            holder.v.setOnClickListener({
                var intent = Intent(holder.v.context , MangaDetail::class.java)
                intent.putExtra("data" , home.data.get(position))
                intent.putExtra("kode" , "1")
                holder.v.context.startActivity(intent)
            })
        }
//        override fun getItemCount(): Int {
//            return homefeed.data.count()
//        }
//
//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHoler {
//            var layoutInflater = LayoutInflater.from(parent?.context)
//            var row = layoutInflater.inflate(R.layout.row_list , parent , false)
//            return CustomViewHoler(row)
//        }
//
//        override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
//
//            holder.v.mangaTitle.text = homefeed.data.get(position).title.toString()
//            holder.v.genre.text = homefeed.data.get(position).genre.toString()
//            Glide.with(holder.v).load(homefeed.data.get(position).image.toString())
//                .into(holder.v.imgList)
//
//            holder.v.setOnClickListener({
//                var intent = Intent(holder.v.context , MangaDetail::class.java)
//                intent.putExtra("data" , homefeed.data.get(position))
//                intent.putExtra("kode" , "2")
//                holder.v.context.startActivity(intent)
//            })
//        }
    }

    class CustomViewHoler(var v : View) : RecyclerView.ViewHolder(v){

    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }




}
