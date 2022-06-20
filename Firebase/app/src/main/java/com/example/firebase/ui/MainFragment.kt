package com.example.firebase.ui

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebase.R
import com.example.firebase.adapters.ItemsAdapter
import com.example.firebase.data.WatchedItem
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class MainFragment : Fragment() {
    companion object {
        var itemList : MutableList<WatchedItem> = mutableListOf()
    }
    private lateinit var databaseReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            : View {

        databaseReference = Firebase.database.getReference("items")

        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getData(view)

        view.findViewById<Button>(R.id.LogoutButton).apply {
            setOnClickListener {
                view.findNavController().navigate(R.id.action_mainFragment_to_loginFragment)
            }
        }

        view.findViewById<FloatingActionButton>(R.id.AddNewItemButton).apply {
            setOnClickListener {
                ItemTestFragment.currentItem = null
                view.findNavController().navigate(R.id.action_mainFragment_to_itemTestFragment)
            }
        }

        view.findViewById<EditText>(R.id.SearchItemInput).apply {
            doOnTextChanged { _, _, _, _ -> filterDataByName(view)}
        }

        view.findViewById<CheckBox>(R.id.IsWatchedFilterChecked).apply {
            setOnClickListener {
                filterDataByWatched(view)
            }
        }
    }

    private fun getData(view: View) {
        itemList = mutableListOf()
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                itemList = mutableListOf()
                // Get Post object and use the values to update the UI
                for(child in dataSnapshot.children) {
                    itemList.add(child.getValue<WatchedItem>()!!)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        databaseReference.addValueEventListener(postListener)

        setAdapter(view, itemList)
    }

    private fun filterDataByName(view: View) {
        val itemListCopy: MutableList<WatchedItem> = mutableListOf()

        for(item in itemList) {
            if (item.title!!.contains(view.findViewById<EditText>(R.id.SearchItemInput).text.toString(), ignoreCase = true)) {
                itemListCopy.add(item)
            }
        }

        setAdapter(view, itemListCopy)
    }

    private fun filterDataByWatched(view:View) {
        val itemListCopy: MutableList<WatchedItem> = mutableListOf()

        val value = view.findViewById<CheckBox>(R.id.IsWatchedFilterChecked)
        for(item in itemList) {
            if (item.isRead == value.isChecked) {
                itemListCopy.add(item)
            }
        }

        setAdapter(view, itemListCopy)
    }

    private fun setAdapter(view:View, itemListCopy : List<WatchedItem>) {
        val itemsAdapter = ItemsAdapter(itemListCopy)
        val layoutManager= LinearLayoutManager(view.context)
        view.findViewById<RecyclerView>(R.id.ItemsRecyclerView).let {
            it!!.adapter=itemsAdapter
            it.layoutManager=layoutManager
        }
    }
}