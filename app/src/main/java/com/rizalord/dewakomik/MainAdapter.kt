

package com.rizalord.dewakomik

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row_latest.view.*
import kotlinx.android.synthetic.main.row_list.view.*

class MainAdapter(val home : homeFragment.Home) : RecyclerView.Adapter<CustomViewHolder>(){
    override fun getItemCount(): Int {
        return home.data.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        var layoutInflater = LayoutInflater.from(parent?.context)
        var row = layoutInflater.inflate(R.layout.row_latest , parent , false)

        return CustomViewHolder(row)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        Glide.with(holder.v).load(home.data.get(position).image.toString())
            .into(holder.v.latestImage)
        holder.v.lastTitle.text = home.data.get(position).title.toString()
        holder.v.lastDate.text = home.data.get(position).date_uploaded.toString()
        holder.v.lastChapter.text = "Chapter " + home.data.get(position).chap_num.toString()

        holder.v.btnRead.setOnClickListener({
            var intent = Intent(holder.v.context , MangaDetail::class.java)
            intent.putExtra("data" , home.data.get(position))
            intent.putExtra("kode" , "1")
            holder.v.context.startActivity(intent)
        })
    }
}

class CustomViewHolder(var v : View) : RecyclerView.ViewHolder(v){

}
