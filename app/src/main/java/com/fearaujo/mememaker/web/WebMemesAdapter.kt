package com.fearaujo.mememaker.web

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.fearaujo.mememaker.R
import com.fearaujo.mememaker.databinding.ItemMemeImageBinding
import com.fearaujo.model.MemeModel
import com.squareup.picasso.Picasso

class WebMemesAdapter(val callback: SelectImage) : RecyclerView.Adapter<WebMemesAdapter.WebMemeViewHolder>() {

    private val memes = ArrayList<MemeModel>()
    lateinit var picasso: Picasso

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WebMemeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinding: ItemMemeImageBinding = DataBindingUtil.inflate(
                inflater, R.layout.item_meme_image, parent, false
        )
        picasso = Picasso.Builder(parent.context).loggingEnabled(true).build()

        return WebMemeViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return memes.size
    }

    override fun onBindViewHolder(holder: WebMemeViewHolder, position: Int) {
        val meme = memes[position]
        holder.bind(meme)
    }

    fun updateMemes(list: List<MemeModel>) {
        val oldSize = memes.size
        memes.addAll(list)

        val newSize = memes.size
        for (i in oldSize..newSize) {
            this.notifyItemInserted(i)
        }
    }

    inner class WebMemeViewHolder(itemView: ItemMemeImageBinding) : RecyclerView.ViewHolder(itemView.root) {

        private val viewDataBinding: ItemMemeImageBinding = itemView

        fun bind(obj: MemeModel) {

            //picasso.load(obj.image).fit().into(viewDataBinding.ivMemePreview)
            //val config = Bitmap.Config
            picasso.load(obj.image).resize(192, 128).centerCrop().into(viewDataBinding.ivMemePreview)
            viewDataBinding.executePendingBindings()

            viewDataBinding.ivMemePreview.setOnClickListener {
                if (obj.image != null) {
                    callback.onSelectImage(obj.image!!)
                }
            }
        }

    }

}