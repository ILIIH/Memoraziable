package com.example.add_new_card.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.add_new_card.databinding.AnswerAdapterItemBinding
import com.example.add_new_card_data.model.Answer

class AnswersAdapters : ListAdapter<Answer, AnswersAdapters.AnswerItemViewHolder>(DiffCallBack()) {

    val bindings = mutableListOf<AnswerAdapterItemBinding>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerItemViewHolder {
        val view = AnswerAdapterItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )
        return AnswerItemViewHolder(view)
    }

    fun clear() {
        bindings.clear()
    }
    fun getAllAnswers() = bindings.map {
            Answer(
            answer = it.answer.text.toString(),
            description = it.answerHint.text.toString(),
            correct = it.answerTrue.isChecked,
        )
    }
    override fun onBindViewHolder(holder: AnswerItemViewHolder, position: Int) {
        holder.bind()
    }

    inner class AnswerItemViewHolder(
        private val binding: AnswerAdapterItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            clearAnswerItem()

            binding.answerTrue.setOnClickListener { }
            binding.hint.setOnClickListener {
                binding.answerHintLayout.visibility = if (binding.answerHintLayout.visibility == View.VISIBLE) {
                    View.GONE
                } else {
                    View.VISIBLE
                }
            }
            bindings.add(binding)
        }

        private fun clearAnswerItem() {
            binding.answer.text.clear()
            binding.hint.isChecked = false
            binding.answerHint.text.clear()
            binding.answerHintLayout.visibility = View.GONE
            binding.answerTrue.isChecked = false
        }
    }

    internal class DiffCallBack : DiffUtil.ItemCallback<Answer>() {
        override fun areItemsTheSame(oldItem: Answer, newItem: Answer): Boolean =
            oldItem.answer == newItem.answer

        override fun areContentsTheSame(oldItem: Answer, newItem: Answer): Boolean =
            oldItem == newItem
    }
}
