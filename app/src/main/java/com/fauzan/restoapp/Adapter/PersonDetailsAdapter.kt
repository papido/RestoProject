package com.fauzan.restoapp.Adapter

import com.fauzan.restoapp.Room.Person

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fauzan.restoapp.databinding.SingleReviewBinding

class PersonDetailsAdapter(private val listener : PersonDetailsClickListener) : ListAdapter<Person, PersonDetailsAdapter.PersonDetailsViewHolder>(DiffUtilCallback()) {

    inner class PersonDetailsViewHolder(private val binding: SingleReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.editBtn.setOnClickListener {
                listener.onEditPersonClick(getItem(adapterPosition))
            }
            binding.deleteBtn.setOnClickListener{
                listener.onDeletePersonClick(getItem(adapterPosition))
            }
        }
        fun bind(person: Person){
            binding.personNameTv.text = person.name
            binding.personReviewTv.text = person.review
        }
    }

    class DiffUtilCallback : DiffUtil.ItemCallback<Person>(){
        override fun areItemsTheSame(oldItem: Person, newItem: Person) = oldItem.pId == newItem.pId

        override fun areContentsTheSame(oldItem: Person, newItem: Person) = oldItem == newItem

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonDetailsViewHolder {
        return PersonDetailsViewHolder(SingleReviewBinding.inflate(LayoutInflater.from(parent.context),parent , false))
    }

    override fun onBindViewHolder(holder: PersonDetailsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    interface PersonDetailsClickListener{
        fun onEditPersonClick(person: Person)
        fun onDeletePersonClick(person: Person)
    }
}