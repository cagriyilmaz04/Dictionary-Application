package com.example.dictionaryapplicationkotlin.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dictionaryapplicationkotlin.R
import com.example.dictionaryapplicationkotlin.adapter.wordAdapter
import com.example.dictionaryapplicationkotlin.databinding.FragmentSecondBinding
import com.example.dictionaryapplicationkotlin.util.ExtensionFunctions.hide


class SecondFragment : Fragment() {
    private var _binding:FragmentSecondBinding?=null
    private val binding get() = _binding!!
    private val args by navArgs<SecondFragmentArgs>()
    var AdapterSynonym=wordAdapter()
    var AdapterAntonym=wordAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentSecondBinding.inflate(layoutInflater,container,false)
        val const=args.sendingDictionary
        binding.textViewDictionary.text=resources.getString(R.string.word) + const?.word
        if(const!=null){
            if(const.detailDef?.antonyms?.size==0){
                    binding.textViewAntonyms.hide()
                    binding.recyclerViewAntonm.hide()
            }else{
                binding.recyclerViewAntonm.layoutManager=LinearLayoutManager(requireContext())
                AdapterAntonym.refreshData(const.detailDef?.antonyms!!)
                binding.recyclerViewAntonm.adapter=AdapterAntonym
                binding.textViewThereIsNoAntonym.hide()
            }
            if(const.detailDef.synonyms?.size==0){
                binding.textViewSynonyms.hide()
                binding.recyclerViewSynonym.hide()
            }else{
                binding.recyclerViewSynonym.layoutManager=LinearLayoutManager(requireContext())
                AdapterSynonym.refreshData(const.detailDef?.synonyms!!)
                binding.recyclerViewSynonym.adapter=AdapterSynonym
                binding.textViewThereIsNoSynonym.hide()
            }
            if(const.partOfSpeech!=null){
                binding.textViewPartOfSpeech.text= resources.getString(R.string.type) + const.partOfSpeech
            }else{
                binding.textViewPartOfSpeech.hide()
            }
            if(const.detailDef.definition !=null){
                binding.textViewExplain.text=resources.getString(R.string.detail)+const.detailDef.definition
            }else{
                binding.textViewExplain.hide()
            }
            if(const.detailDef.example !=null){
                binding.textViewExample.text= resources.getString(R.string.example)+const.detailDef.example
            }else{
                binding.textViewExample.hide()
            }


        }
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}