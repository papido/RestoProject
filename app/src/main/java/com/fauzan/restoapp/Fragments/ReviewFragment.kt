package com.fauzan.restoapp.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.fauzan.restoapp.Adapter.PersonDetailsAdapter
import com.fauzan.restoapp.R
import com.fauzan.restoapp.Room.AppDatabase
import com.fauzan.restoapp.Room.Person
import com.fauzan.restoapp.Room.PersonDao
import com.fauzan.restoapp.databinding.FragmentReviewBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class ReviewFragment : Fragment(), AddEditPersonFragment.AddEditPersonListener,
    PersonDetailsAdapter.PersonDetailsClickListener {

    private lateinit var binding: FragmentReviewBinding
    private var dao : PersonDao? = null
    private lateinit var adapter: PersonDetailsAdapter

    private lateinit var searchQueryLiveData: MutableLiveData<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReviewBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initVars()
        attachUiListener()
        subscribeDataStream()
    }

    private fun initVars() {
        dao = AppDatabase.getDatabase(requireContext()).personDao()
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = PersonDetailsAdapter(this)
        binding.recyclerView.adapter = adapter
        searchQueryLiveData = MutableLiveData("")

    }

    private fun subscribeDataStream() {
        // Combine search queries and database changes
        lifecycleScope.launch {
            dao?.getAllData()
                ?.combine(searchQueryLiveData.asFlow()) { allData, query ->
                    if (query.isBlank()) allData else dao?.getSearchedData(query)?.firstOrNull()
                }
                ?.collect { filteredData ->
                    adapter.submitList(filteredData)
                }
        }
    }


    private fun attachUiListener() {
        binding.floatingActionButton.setOnClickListener {
            showBottomSheet()
        }
        binding.searchcView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?) = false

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null)
                    onQueryChanged(newText)
                return true
            }
        })
    }

    private fun onQueryChanged(query: String) {
        searchQueryLiveData.postValue(query)
        lifecycleScope.launch {
                adapter.submitList(dao?.getSearchedData(query)?.first())
        }
    }



    private fun showBottomSheet(person: Person? = null) {
        val bottomSheet = AddEditPersonFragment.newInstance(person)
        bottomSheet.setListener(this)
        bottomSheet.show(parentFragmentManager,AddEditPersonFragment.TAG)
    }

    override fun onSaveBtnClicked(isUpdate: Boolean, person: Person) {

        lifecycleScope.launch(Dispatchers.IO) {
            if (isUpdate)
                dao?.updatePerson(person)
            else
                dao?.savePerson(person)
        }

    }

    override fun onEditPersonClick(person: Person) {
        showBottomSheet(person)
    }

    override fun onDeletePersonClick(person: Person) {
        lifecycleScope.launch(Dispatchers.IO) {
            dao?.deletePerson(person)
        }
    }


}

