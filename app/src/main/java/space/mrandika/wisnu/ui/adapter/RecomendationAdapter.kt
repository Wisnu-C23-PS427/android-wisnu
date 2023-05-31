package space.mrandika.wisnu.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import space.mrandika.wisnu.R
import space.mrandika.wisnu.Recomedation

class RecomendationAdapter(private val listItems: List<Recomedation>):RecyclerView.Adapter<RecomendationAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_items, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listItems[position]
        Glide.with(holder.itemView.context)
            .load(data.image)
            .into(holder.imageView)
        holder.city.text = data.city
        holder.place.text = data.destination
    }

    override fun getItemCount(): Int = listItems.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
         val imageView: ImageView = itemView.findViewById(R.id.iv_card_image)
         val city : TextView = itemView.findViewById(R.id.tv_city_name)
         val place : TextView = itemView.findViewById(R.id.tv_place_name)

    }
}