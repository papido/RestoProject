package com.fauzan.restoapp.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fauzan.restoapp.Room.Person
import com.fauzan.restoapp.databinding.FragmentAddEditPersonBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class AddEditPersonFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentAddEditPersonBinding
    private var listener: AddEditPersonListener? = null
    private var person: Person? = null

    fun setListener(listener: AddEditPersonListener){
        this.listener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddEditPersonBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Handle the case when the person data is set before view creation
        if (arguments != null) {
            person = arguments?.getParcelable("person", Person::class.java)
        }

        attachUiListener()

        // Update UI with data after the fragment view is created
        person?.let { setExistingDataOnUi(it) }
    }

    private fun setExistingDataOnUi(person: Person) {
        binding.personNameEt.setText(person.name)
        binding.personReviewEt.setText(person.review)
        binding.saveBtn.text = "Update"
    }

    private fun attachUiListener() {
        binding.saveBtn.setOnClickListener {
            val name = binding.personNameEt.text.toString()
            val review = binding.personReviewEt.text.toString()
            if (name.isNotEmpty() && review.isNotEmpty()) {
                val person1 = Person(person?.pId ?: 0, name, review)
                listener?.onSaveBtnClicked(person != null, person1)
            }
            dismiss()
        }
    }

    companion object {
        const val TAG = "AddEditPersonFragment"

        @JvmStatic
        fun newInstance(person: Person?) = AddEditPersonFragment().apply {
            arguments = Bundle().apply {
                putParcelable("person", person)
            }
        }
    }

    interface AddEditPersonListener {
        fun onSaveBtnClicked(isUpdate: Boolean, person: Person)
    }
}
