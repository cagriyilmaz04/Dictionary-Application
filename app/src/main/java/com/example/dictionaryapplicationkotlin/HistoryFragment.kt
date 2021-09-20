package com.example.dictionaryapplicationkotlin

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dictionaryapplicationkotlin.adapter.roomAdapter
import com.example.dictionaryapplicationkotlin.databinding.FragmentHistoryBinding
import com.example.dictionaryapplicationkotlin.model.dbClass
import com.example.dictionaryapplicationkotlin.util.ExtensionFunctions.hide
import com.example.dictionaryapplicationkotlin.util.ExtensionFunctions.show
import com.example.dictionaryapplicationkotlin.viewmodel.historyViewModel

class HistoryFragment : Fragment(),roomAdapter.Listener {
    private  var _binding:FragmentHistoryBinding?=null
    private val binding get() = _binding!!
    lateinit var viewModel:historyViewModel
    lateinit var Adapter:roomAdapter
    var indeks=-1
    var dizi=ArrayList<dbClass>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding= FragmentHistoryBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbarHistory)
        (requireActivity() as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        (requireActivity() as AppCompatActivity).supportActionBar!!.setDisplayShowHomeEnabled(true)
        binding.toolbarHistory.title=resources.getString(R.string.history)
        viewModel=ViewModelProvider(this).get(historyViewModel::class.java)
        binding.recyclerViewHistory.layoutManager=LinearLayoutManager(requireContext())
        Adapter= roomAdapter(viewModel,requireContext(),binding.textView,this)
        observeData()
        binding.recyclerViewHistory.adapter=Adapter
        super.onViewCreated(view, savedInstanceState)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.history_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.delete->{
                viewModel.deleteAllWords()
                dizi.clear()
                Adapter.refreshData(dizi)
                binding.recyclerViewHistory.adapter=Adapter
                binding.textView.visibility=View.VISIBLE
                return true
            }
        }
        return false
    }
    private fun observeData() {
        viewModel.readAllWordsFromVM.observe(viewLifecycleOwner){
            dizi.addAll(it)
            Adapter.refreshData(dizi)
            if(dizi.size==0){
                binding.textView.visibility=View.VISIBLE
            }else{
                binding.textView.hide()
            }
        }
    }
    override fun onItemClick(value: Int) {
        indeks=value
        if(indeks!=-1){
            val alertDialogBuilder= AlertDialog.Builder(requireContext())
            alertDialogBuilder.setTitle(requireContext().resources.getString(R.string.delete))
            alertDialogBuilder.setPositiveButton(requireContext().resources.getString(R.string.yes)){_,_ ->
                viewModel.deleteNotes(dizi.get(indeks))
                dizi.clear()
            }
            alertDialogBuilder.setNegativeButton(requireContext().resources.getString(R.string.no)){_,_->
            }
            alertDialogBuilder.create().show()
        }
        if(dizi.size==0){
            binding.textView.show()
        }

    }
}