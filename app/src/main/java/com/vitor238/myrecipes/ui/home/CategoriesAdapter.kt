package com.vitor238.myrecipes.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vitor238.myrecipes.data.model.Category
import com.vitor238.myrecipes.databinding.ItemCategoriesBinding

class CategoriesAdapter(val onClickListener: (type: String) -> Unit) :
    ListAdapter<Category, CategoriesAdapter.CategoryViewHolder>(CategoriesDiffUtils()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding =
            ItemCategoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CategoryViewHolder(binding: ItemCategoriesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val textCategoryName = binding.textCategoryName
        private val imageThumbnail = binding.imageThumbnail

        fun bind(category: Category) {
            textCategoryName.text = textCategoryName.context.getString(category.name)
            Glide.with(imageThumbnail.context)
                .load(category.thumbnail)
                .into(imageThumbnail)
            imageThumbnail.setOnClickListener {
                onClickListener.invoke(category.type)
            }
        }
    }

    class CategoriesDiffUtils : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.type == newItem.type
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }
    }
}