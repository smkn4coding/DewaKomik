
package com.rizalord.dewakomik

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row_chapters.view.*
import java.text.SimpleDateFormat
import java.util.*

class ListAdapter(val homeFoodd: MangaDetail.HomeFoodd, val detail : String)  : RecyclerView.Adapter<CustomVH>(){
    override fun getItemCount(): Int {
        return homeFoodd.data.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomVH {
        var layoutInfalter = LayoutInflater.from(parent.context)
        var row = layoutInfalter.inflate(R.layout.row_chapters , parent , false)
        return CustomVH(row)
    }

    override fun onBindViewHolder(holder: CustomVH, position: Int) {

        holder.view.textNum.text = "Chapter " + homeFoodd.data.get(position).chap_num.toString()
        holder.view.textDate.text = homeFoodd.data.get(position).date_uploaded.toString()
        holder.view.setOnClickListener({
            var intent = Intent(holder.view.context , ViewImage::class.java)
            intent.putExtra("dataId" , homeFoodd.data.get(position).manga_chap_read.toString())
            intent.putExtra("chapter" , homeFoodd.data.get(position).chap_num.toString())
            intent.putExtra("detail" , detail.toString())
            holder.view.context.startActivity(intent)
        })
    }

    private fun getDateTime(s: String): String? {
        try {
            val sdf = SimpleDateFormat("MM/dd/yyyy")
            val netDate = Date(s.toLong() * 1000)
            return sdf.format(netDate)
        } catch (e: Exception) {
            return e.toString()
        }
    }
}

class CustomVH(var view : View) : RecyclerView.ViewHolder(view){

}