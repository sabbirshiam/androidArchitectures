package com.ssh.androidarchitectures.mvi

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssh.androidarchitectures.model.Country
import com.ssh.androidarchitectures.utils.CountriesDiffUtil
import com.ssh.androidarchitectures.view.CountryItemView
import timber.log.Timber

class CountriesViewAdapter(
    private var posts: List<Country>,
    private val onItemClicked: OnClickItemListener
) :
    RecyclerView.Adapter<CountriesViewAdapter.CountryViewHolder>() {

    interface OnClickItemListener {
        fun onItemClick(data: Country, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        return CountryViewHolder(CountryItemView(parent.context)).apply {
            this.itemView.setOnClickListener {
                Timber.d("clicked position: $bindingAdapterPosition")
                // onItemClicked?.invoke(posts[bindingAdapterPosition], bindingAdapterPosition)
                onItemClicked.onItemClick(posts[bindingAdapterPosition], bindingAdapterPosition)
            }
        }
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bindData(posts[position])
    }

    override fun getItemCount(): Int = posts.size

    fun addData(list: List<Country>) {
//        posts.clear()
//        posts.addAll(list)
        updateList(posts, list)
    }

    class CountryViewHolder(itemView: CountryItemView) : RecyclerView.ViewHolder(itemView) {
        private fun getView(): CountryItemView = itemView as CountryItemView
        fun bindData(data: Country) = getView().bindData(data = data)
    }

    private fun updateList(oldList: List<Country>, newList: List<Country>) {
        val diffResult = DiffUtil.calculateDiff(CountriesDiffUtil(oldList, newList))
        posts = newList
        diffResult.dispatchUpdatesTo(this)
    }
}