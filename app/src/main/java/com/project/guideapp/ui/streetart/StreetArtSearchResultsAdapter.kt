import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.guideapp.R
import com.project.guideapp.databinding.ItemStreetArtListBinding
import com.project.guideapp.databinding.ItemStreetArtSearchResultBinding
import com.project.guideapp.network.dto.ExhibitsDTO
import com.squareup.picasso.Picasso

class StreetArtSearchResultsAdapter(
    private val exhibitsList: List<ExhibitsDTO>,
    private val onClickListener: OnClickListener
) :
    RecyclerView.Adapter<StreetArtSearchResultsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemStreetArtSearchResultBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onClickListener.onClick(exhibitsList[position])
        }
        holder.bind(exhibitsList[position])
    }

    override fun getItemCount(): Int {
        return exhibitsList.size
    }

    class ViewHolder(private val binding: ItemStreetArtSearchResultBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(exhibits: ExhibitsDTO) {
            binding.ivImage
            binding.tvTitle.text = exhibits.name.split(",")[0]
            if (exhibits.images.isNotEmpty())
                Picasso.get()
                    .load(exhibits.images[0].imageUri)
                    .placeholder(R.drawable.ic_option_menu)
                    .into(binding.ivImage)
            binding.tvDescription.text = exhibits.story
        }
    }

    class OnClickListener(val clickListener: (exhibitDTO: ExhibitsDTO) -> Unit) {
        fun onClick(exhibitDTO: ExhibitsDTO) = clickListener(exhibitDTO)
    }

}