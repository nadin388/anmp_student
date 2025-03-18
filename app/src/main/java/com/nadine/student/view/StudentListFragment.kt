package com.nadine.student.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.nadine.student.R
import com.nadine.student.databinding.FragmentStudentListBinding
import com.nadine.student.viewmodel.ListViewModel


class StudentListFragment : Fragment() {
    private lateinit var binding:FragmentStudentListBinding
    private val adapter = StudentListAdapter(arrayListOf()) //membuat arraylist kosongan
    private lateinit var viewModel:ListViewModel //berhubungan lateinit jadi hrs di set up(di initialize) objeknya -> buat objeknya di onviewcreated

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) { //dipakai kalo sdh loading dan tinggal nulis logikanya aja
        super.onViewCreated(view, savedInstanceState)

        //init viewmodel
        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        viewModel.refresh()

        //init recycle view
        binding.recView.layoutManager = LinearLayoutManager(context)
        binding.recView.adapter = adapter

        //implement swiperefresh
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refresh()
            binding.swipeRefresh.isRefreshing = false
        }

        observeViewModel()
    }

    fun observeViewModel() {
        viewModel.studentsLD.observe(viewLifecycleOwner, Observer {
            adapter.updateStudentList(it) //apabila function kalian ada 1 parameter pakai it, kalo ada 2 parameter gabisa pakai it
        })

        viewModel.loadingLD.observe(viewLifecycleOwner, Observer {
            if(it==true)
            {
                binding.progressLoad.visibility = View.VISIBLE
            } else {
                binding.progressLoad.visibility = View.GONE
            }
        })

        viewModel.errorLD.observe(viewLifecycleOwner, Observer {
            if(it==true)
            {
                binding.txtError.visibility = View.VISIBLE
            } else {
                binding.txtError.visibility = View.GONE
            }
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStudentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    //WIP - Game Setup

}