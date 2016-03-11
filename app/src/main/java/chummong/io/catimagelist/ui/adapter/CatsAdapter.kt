package chummong.io.catimagelist.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import chummong.io.catimagelist.R
import chummong.io.catimagelist.model.dto.Photo
import chummong.io.catimagelist.util.glide.GlideRequestListener
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import org.jetbrains.anko.find
import org.jetbrains.anko.layoutInflater
import kotlin.properties.Delegates

/**
 * Created by LeeJongHun on 2016-03-07.
 */

interface OnClickListener {
    fun onClick(view: View, position: Int)
}

class CatsAdapter(val listener: OnClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val VIEW_ITEM = 0
    private val VIEW_FOOTER = 1

    var items: List<Photo> by Delegates.observable(emptyList()) { prop, old, new ->
        notifyDataSetChanged()
    }

    var footer: ViewFooterHolder? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        when (viewType) {
            VIEW_ITEM -> {
                return ViewItemHolder(parent.context.layoutInflater.inflate(R.layout.item_cats_list, parent, false))
            }
            VIEW_FOOTER -> {
                if (footer == null) {
                    footer = ViewFooterHolder(parent.context.layoutInflater.inflate(R.layout.item_cats_footer, parent, false))
                }
                return footer
            }
            else -> return null
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewItemHolder) {
            holder.view.setOnClickListener { view -> listener.onClick(view, position) }
            holder.setImage(getItem(position))
        }
    }

    override fun getItemCount(): Int {
        return items.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        if (isFooter(position)) {
            return VIEW_FOOTER
        }
        else {
            return VIEW_ITEM
        }
    }

    private fun getItem(position: Int): Photo {
        return items[position]
    }

    fun isFooter(position: Int): Boolean {
        return items.isEmpty() || position == items.size
    }
}

class ViewItemHolder(val view: View) : RecyclerView.ViewHolder(view) {
    private val image: ImageView = view.find(R.id.image)
    private val progress: ProgressBar = view.find(R.id.progress)
    private val title: TextView = view.find(R.id.title)

    fun setImage(item: Photo) {
        if (!item.title.isEmpty()) {
            title.visibility = View.VISIBLE
            title.text = item.title
        }

        progress.visibility = View.VISIBLE

        Glide.with(view.context)
                .load(item.imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .listener(GlideRequestListener(progress))
                .into(image)
    }
}

class ViewFooterHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val progress: ProgressBar = view.find(R.id.progress)

    fun showProgress() {
        progress.visibility = View.VISIBLE
    }

    fun hideProgoress() {
        progress.visibility = View.GONE
    }

}