package com.example.dictionaryapplicationkotlin.view


import android.content.Context
import android.content.SharedPreferences
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dictionaryapplicationkotlin.R
import com.example.dictionaryapplicationkotlin.adapter.RecyclerAdapter
import com.example.dictionaryapplicationkotlin.databinding.FragmentMainBinding
import com.example.dictionaryapplicationkotlin.model.DictionaryModel
import com.example.dictionaryapplicationkotlin.model.dbClass
import com.example.dictionaryapplicationkotlin.util.ExtensionFunctions.hide
import com.example.dictionaryapplicationkotlin.util.ExtensionFunctions.show
import com.example.dictionaryapplicationkotlin.viewmodel.historyViewModel
import com.example.dictionaryapplicationkotlin.viewmodel.mainViewModel
import kotlinx.coroutines.*

class MainFragment : Fragment() {
    private var _binding:FragmentMainBinding?=null
    private val binding get()= _binding!!
    lateinit var adapter:RecyclerAdapter
     var data:List<DictionaryModel>?=null
    private lateinit var viewModel:mainViewModel
    private lateinit var dbViewModel:historyViewModel
    lateinit var sharedPreference:SharedPreferences
    var dizi=ArrayList<dbClass>()
    private val args by navArgs<MainFragmentArgs>()


    override fun onCreate(savedInstanceState: Bundle?) {
        (requireActivity() as AppCompatActivity).window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentMainBinding.inflate(layoutInflater,container,false)
        sharedPreference=(requireActivity() as AppCompatActivity).getSharedPreferences("com.example.dictionaryapplicationkotlin.view",Context.MODE_PRIVATE)
        val wordss= sharedPreference.getString("word"," ")
        if(wordss!=" "&&wordss!=null){
            binding.editTextDictionary.setText(wordss)
        }
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel=ViewModelProvider(this).get(mainViewModel::class.java)
        binding.imageViewSound.visibility=View.INVISIBLE
        adapter= RecyclerAdapter()
        dbViewModel=ViewModelProvider(this).get(historyViewModel::class.java)
        dbViewModel.readAllWordsFromVM.observe(viewLifecycleOwner){
            dizi = it as ArrayList<dbClass>
        }
        if(args.sendback!=null){
            sharedPreference.edit().putString("word",args.sendback?.string.toString()).apply()
            observeData(args.sendback?.string.toString(),dizi)
        }else{
            binding.progressBarMainFragment.hide()
            binding.textViewUyari.hide()
            binding.buttonSearch.setOnClickListener {
                val word = binding.editTextDictionary.text.toString()
                if(!(word.isNullOrEmpty())){
                    binding.progressBarMainFragment.show()
                    observeData(word,dizi)
                    if(data?.size!=0){
                        sharedPreference.edit().putString("word",word).apply()
                    }
                    binding.imageViewSound.setOnClickListener {
                        if(data?.size!=0){
                            if(data?.get(0)?.phonetics?.size==0){
                                Toast.makeText(requireContext(),resources.getString(R.string.unfortune),Toast.LENGTH_LONG).show()
                            }else{
                                val mediaPlayer = MediaPlayer().apply { setAudioAttributes(AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).setUsage(AudioAttributes.USAGE_MEDIA).build())
                                    setDataSource("https:"+data?.get(0)?.phonetics?.get(0)?.audio)
                                    prepare()
                                    start()
                                }
                            }
                        }
                    }
                }else{
                    Toast.makeText(requireContext(),resources.getString(R.string.toast_message),Toast.LENGTH_LONG).show()
                }
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }
    var flag=0
    private fun observeData(word: String,dizi:ArrayList<dbClass>) {
        binding.editTextDictionary.setText(word)
        binding.recyclerView.layoutManager=LinearLayoutManager(requireContext())
        val a=DictionaryModel(word = null)
        viewModel.getDict(word)
        val temp_dizi=ArrayList<dbClass>()
        temp_dizi.addAll(dizi)
        var temp_flag=0

        viewModel.ListOfDatas.observe(viewLifecycleOwner){
            data=List<DictionaryModel>(it.size){a}
            data  =it
            Toast.makeText(requireContext(),it.size.toString(),Toast.LENGTH_LONG).show()
            GlobalScope.launch(Dispatchers.Main) {
                    withContext(Dispatchers.Main){

                        adapter.refreshData(it)
                        if(it.size!=0){
                            binding.imageViewSound.visibility=View.VISIBLE
                            binding.recyclerView.adapter=adapter
                               binding.textViewUyari.hide()

                        }else if(it.size==0){
                            binding.textViewUyari.show()

                        }
                    }
            }
        }
        dbViewModel.readAllWordsFromVM.observe(viewLifecycleOwner){
            val dizi=ArrayList<dbClass>()
            dizi.addAll(it as ArrayList<dbClass>)
            for(i in dizi){
                if(i.string==word){
                    temp_flag=1
                    break
                }
                else{
                    temp_flag=0
                }
            }
            if(temp_flag==1){

            }else{
                dbViewModel.addWordVM(dbClass(word))
            }
        }
        viewModel.loading.observe(viewLifecycleOwner){
            if(it==true){
                binding.progressBarMainFragment.show()
            }else if(it==false
            ){
                binding.progressBarMainFragment.hide()
            }
        }
        binding.imageViewSound.setOnClickListener {
            if(data?.get(0)?.phonetics?.get(0)?.audio!=null){
                val mediaPlayer = MediaPlayer().apply { setAudioAttributes(AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).setUsage(AudioAttributes.USAGE_MEDIA).build())
                    setDataSource("https:"+data?.get(0)?.phonetics?.get(0)?.audio)
                    prepare() // might take long! (for buffering, etc)
                    start()
                }
            }
        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
    override fun onStart() {

        super.onStart()
    }

}