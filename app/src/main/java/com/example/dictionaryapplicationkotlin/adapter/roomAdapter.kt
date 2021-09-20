package com.example.dictionaryapplicationkotlin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionaryapplicationkotlin.HistoryFragmentDirections
import com.example.dictionaryapplicationkotlin.databinding.RoomLayoutBinding
import com.example.dictionaryapplicationkotlin.model.dbClass
import com.example.dictionaryapplicationkotlin.viewmodel.historyViewModel

class roomAdapter(val viewModel:historyViewModel,val context:Context,val textView:TextView,private val listener:Listener) :RecyclerView.Adapter<roomAdapter.TutucuAdapter>(){
    interface Listener {
        fun onItemClick(value:Int)
    }

    var emptyList = ArrayList<dbClass>()
    class TutucuAdapter(val binding:RoomLayoutBinding):RecyclerView.ViewHolder(binding.root) {
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TutucuAdapter {
        return TutucuAdapter(RoomLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }
    override fun onBindViewHolder(holder: TutucuAdapter, position: Int) {
        holder.binding.textViewRoom.text=emptyList.get(position).string
        holder.itemView.setOnClickListener {
            val data= dbClass(emptyList.get(position).string)
            val action=  HistoryFragmentDirections.actionHistoryFragmentToMainFragment(data)
            Navigation.findNavController(it).navigate(action)
        }
        holder.binding.imageView2Delete.setOnClickListener {
            val data= dbClass(emptyList.get(position).string)

            listener.onItemClick(position)


        }

    }
    override fun getItemCount(): Int {
        return emptyList.size
    }
    fun  refreshData(list:ArrayList<dbClass>){
        this.emptyList=list
        notifyDataSetChanged()
    }


}