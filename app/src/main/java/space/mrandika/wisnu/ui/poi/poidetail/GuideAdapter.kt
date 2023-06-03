package space.mrandika.wisnu.ui.poi.poidetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import space.mrandika.wisnu.R
import space.mrandika.wisnu.model.guide.Guide

class GuideAdapter(private val listItems: List<Guide>?, private val onItemClick: (Guide) -> Unit): RecyclerView.Adapter<GuideAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_guide, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listItems?.get(position)
        Glide.with(holder.itemView.context)
            .load(data?.image)
            .into(holder.imageView)
        holder.tvName.text = data?.name
        holder.tvPrice.text = data?.price.toString()

        holder.itemView.setOnClickListener {
            data?.let { guide ->
                onItemClick(guide)
            }
        }
    }

    override fun getItemCount(): Int = listItems?.size!!.toInt()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.iv_image_guide)
        val tvName : TextView = itemView.findViewById(R.id.tv_name)
        val tvPrice : TextView = itemView.findViewById(R.id.tv_price)
    }
}