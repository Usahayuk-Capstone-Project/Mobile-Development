import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.usahayuk.R

class RecommendationAdapter(private var recommendations: List<String>) :
    RecyclerView.Adapter<RecommendationAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recom, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recommendation = recommendations[position]
        holder.bind(recommendation)
    }

    override fun getItemCount(): Int {
        return recommendations.size
    }

    fun setData(recommendations: List<String>) {
        this.recommendations = recommendations
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvRecommendation: TextView = itemView.findViewById(R.id.jenis_usaha)

        fun bind(recommendation: String) {
            tvRecommendation.text = recommendation
        }
    }
}