package com.rizalord.dewakomik

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row_read_chaps.view.*

class ReadAdapter(val chap : hFood)  : RecyclerView.Adapter<CustomViewH>(){
    override fun getItemCount(): Int {
        return chap.data.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewH {
        var layoutInflater = LayoutInflater.from(parent.context)
        val row = layoutInflater.inflate(R.layout.row_read_chaps , parent , false)
        return CustomViewH(row)
    }

    override fun onBindViewHolder(holder: CustomViewH, position: Int) {
        Glide.with(holder.view.context).load(chap.data.get(position).image)
            .into(holder.view.imageToView)
    }
}

class CustomViewH(var view : View) : RecyclerView.ViewHolder(view){

}