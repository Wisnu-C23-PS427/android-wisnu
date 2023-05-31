import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import space.mrandika.wisnu.R

class IconsAdapter(private val imageList: List<Int>) : RecyclerView.Adapter<IconsAdapter.IconViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IconViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.icon_items, parent, false)
        return IconViewHolder(view)
    }

    override fun onBindViewHolder(holder: IconViewHolder, position: Int) {
        val imageResId = imageList[position]
        holder.bind(imageResId)
    }

    override fun getItemCount(): Int = imageList.size

    inner class IconViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val iconImageView: ImageView = itemView.findViewById(R.id.btn_icons)

        fun bind(imageResId: Int) {
            iconImageView.setImageResource(imageResId)
        }
    }
}
