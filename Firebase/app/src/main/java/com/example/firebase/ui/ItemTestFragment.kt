package com.example.firebase.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RatingBar
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.firebase.R
import com.example.firebase.data.WatchedItem
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ItemTestFragment : Fragment() {
    private lateinit var databaseReference: DatabaseReference

    companion object {
        var currentItem: WatchedItem? = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            : View {

        databaseReference = Firebase.database.getReference("items")
        return inflater.inflate(R.layout.fragment_item_test, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var titleInput = view.findViewById<EditText>(R.id.ItemNameInput)
        var descriptionInput = view.findViewById<EditText>(R.id.ItemDescriptionInput)
        var ratio = view.findViewById<RatingBar>(R.id.RatingItem)
        var isWatched = view.findViewById<CheckBox>(R.id.ItemWatchChecker)

        if (currentItem != null) {
            titleInput.setText(currentItem!!.title)
            descriptionInput.setText(currentItem!!.description)
            ratio.rating = currentItem!!.ratio!!
            isWatched.isChecked  = currentItem!!.isRead!!
        }

        view.findViewById<FloatingActionButton>(R.id.SaveItemButton).apply {
            setOnClickListener {
                var newItem = WatchedItem(
                    titleInput.text.toString(),
                    descriptionInput.text.toString(),
                    ratio.rating,
                    isWatched.isChecked
                )
                databaseReference.child(newItem.title!!).setValue(newItem)

                currentItem = null
                view.findNavController().navigate(R.id.action_itemTestFragment_to_mainFragment)
            }
        }
    }
}