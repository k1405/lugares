package com.lugares.ui.lugar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.lugares.R
import com.lugares.adapter.LugarAdapter

import com.lugares.databinding.FragmentLugarBinding
import com.lugares.viewmodel.LugarViewModel

class LugarFragment : Fragment() {

    private var _binding: FragmentLugarBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val lugarViewModel =
            ViewModelProvider(this)[LugarViewModel::class.java]

        _binding = FragmentLugarBinding.inflate(inflater, container, false)

binding.addLugarFabButtin.setOnClickListener{

    findNavController().navigate(R.id.action_nav_lugar_to_addLugarFragment)
}

        val lugarAdapter=LugarAdapter()
        val reciclador = binding.reciclador
        reciclador.adapter= lugarAdapter
        reciclador.layoutManager=LinearLayoutManager(requireContext())

        lugarViewModel.getLugares.observe(viewLifecycleOwner){
            it -> lugarAdapter.setListaLugares(it)
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}