package chummong.io.catimagelist.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import chummong.io.catimagelist.R
import chummong.io.catimagelist.dto.Photo
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import org.jetbrains.anko.find
import org.jetbrains.anko.layoutInflater
import kotlin.properties.Delegates

/**
 * Created by LeeJongHun on 2016-03-07.
 */

class CatsAdapter : RecyclerView.Adapter<ViewHolader>() {

    private val VIEWTYPE_ITEM = 1;
    private val VIEWTYPE_LOADER = 2;

    var items: List<Photo> by Delegates.observable(emptyList()) { prop, old, new ->
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolader? {
        when (viewType) {
            VIEWTYPE_ITEM -> return ViewHolader(parent.context.layoutInflater.inflate(R.layout.item_cats_list, parent, false))
            VIEWTYPE_LOADER -> return
            else {
                return null
            }
        }


    }

    override fun onBindViewHolder(holder: ViewHolader, postion: Int) {
        holder.setImage(items.get(postion))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        if (!items.isEmpty() && position == itemCount - 1) {
            return VIEWTYPE_LOADER
        }
        else {
            return VIEWTYPE_ITEM
        }
    }
}

class ViewHolader(val view: View) : RecyclerView.ViewHolder(view) {
    private val image: ImageView = view.find(R.id.image)
    private val progress: ProgressBar = view.find(R.id.progress)
    private val title: TextView = view.find(R.id.title)

    fun setImage(item: Photo) {
        if (!item.title.isEmpty()) {
            title.visibility = View.VISIBLE
            title.text = item.title
        }

        val url = "http://farm${item.farm}.staticflickr.com/${item.server}/${item.id}_${item.secret}_m.jpg"

        Glide.with(view.context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(image)
    }
}

