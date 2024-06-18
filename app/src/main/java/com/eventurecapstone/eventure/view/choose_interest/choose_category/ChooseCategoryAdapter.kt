package com.eventurecapstone.eventure.view.choose_interest.choose_category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.eventurecapstone.eventure.R
import com.eventurecapstone.eventure.data.entity.Category
import com.eventurecapstone.eventure.databinding.ItemChooseCategoryBinding

class ChooseCategoryAdapter (
    private val categories: List<Category>,
    private val viewModel: ChooseCategoryViewModel
) : RecyclerView.Adapter<ChooseCategoryAdapter.CategoryViewHolder>() {

    inner class CategoryViewHolder(private val binding: ItemChooseCategoryBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(category: Category, isSelected: Boolean) {
            binding.tvCategoryTitle.text = category.name
            binding.categoryLayout.setBackgroundColor(
                if (isSelected) {
                    // Color when item is selected
                    ContextCompat.getColor(binding.root.context, R.color.green_70)
                } else {
                    // Default color when item is not selected
                    ContextCompat.getColor(binding.root.context, android.R.color.transparent)
                }
            )
            binding.root.setOnClickListener {
                viewModel.toggleCategorySelection(category)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemChooseCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        val isSelected = viewModel.selectedCategories.value?.contains(category) ?: false
        holder.bind(category, isSelected)
    }

    override fun getItemCount(): Int = categories.size
}