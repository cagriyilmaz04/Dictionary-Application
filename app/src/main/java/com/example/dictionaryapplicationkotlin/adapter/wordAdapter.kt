package com.example.dictionaryapplicationkotlin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionaryapplicationkotlin.databinding.DictionaryRowBinding
import com.example.dictionaryapplicationkotlin.model.dbClass
import com.example.dictionaryapplicationkotlin.view.SecondFragmentDirections

class wordAdapter:RecyclerView.Adapter<wordAdapter.WordVH>() {
    var emptyList = emptyList<String>()
    class WordVH(val binding:DictionaryRowBinding):RecyclerView.ViewHolder(binding.root) {
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordVH {
         return WordVH(DictionaryRowBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }
    override fun onBindViewHolder(holder: WordVH, position: Int) {
       holder.binding.textViewWords.text=emptyList.get(position)
        holder.itemView.setOnClickListener {
            val data= dbClass(emptyList.get(position))
            val action=SecondFragmentDirections.actionSecondFragmentToMainFragment(data)
            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun getItemCount(): Int {
       return emptyList.size
    }
  fun  refreshData(list:List<String>){
      this.emptyList=list
      notifyDataSetChanged()
    }
}