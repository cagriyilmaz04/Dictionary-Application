package com.example.dictionaryapplicationkotlin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionaryapplicationkotlin.databinding.RecyclerRowBinding
import com.example.dictionaryapplicationkotlin.model.DictionaryModel
import com.example.dictionaryapplicationkotlin.model.sendingModel
import com.example.dictionaryapplicationkotlin.view.MainFragmentDirections
import com.example.dictionaryapplicationkotlin.viewmodel.historyViewModel

class RecyclerAdapter():RecyclerView.Adapter<RecyclerAdapter.TutucuVH>() {
    var emptyList= emptyList<DictionaryModel>()
    class TutucuVH(val binding:RecyclerRowBinding):RecyclerView.ViewHolder(binding.root) {

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TutucuVH {
            return TutucuVH(RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }
    override fun onBindViewHolder(holder: TutucuVH, position: Int) {
        holder.binding.textViewDefinitions.text=emptyList.get(0).meanings?.get(0)?.definitions?.get(position)?.definition
        holder.itemView.setOnClickListener {
            val data=sendingModel(emptyList.get(0).word,emptyList.get(0).meanings?.get(0)?.partOfSpeech, emptyList.get(0).meanings?.get(0)?.definitions?.get(position))
            val action= MainFragmentDirections.actionMainFragmentToSecondFragment(data)
            Navigation.findNavController(it).navigate(action)
        }


    }


    override fun getItemCount(): Int {
            return emptyList.get(0).meanings!![0].definitions!!.size
    }
    fun refreshData(List:List<DictionaryModel>){
        this.emptyList=List
        notifyDataSetChanged()
    }

}