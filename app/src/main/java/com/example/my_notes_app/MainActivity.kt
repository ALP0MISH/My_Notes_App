package com.example.my_notes_app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.my_notes_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val database by lazy {
        Database(context = this)
    }
    private val allNotesList by lazy {
        Database(this).getAllNotes().toMutableList()
    }

    private val adapter: NotesAdapter by lazy {
        NotesAdapter(navigateToEditNoteScreen = ::navigateToEditNoteScreen)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.floatingActionButton.setOnClickListener {
            handleActionButtonsClick(
                isSimpleNote = true,
                allNotesList = allNotesList
            )
        }
        binding.recycleView.adapter = adapter
        adapter.updateList(allNotesList)

        val swipeToDeleteCallBack = object : ItemTouchHelperCallback() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                allNotesList.removeAt(position)
                adapter.updateList(allNotesList)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallBack)
        itemTouchHelper.attachToRecyclerView(binding.recycleView)
    }

    private fun handleActionButtonsClick(
        isSimpleNote: Boolean,
        allNotesList: MutableList<Note>,
    ) {
        val note = database.saveNewNote(isSimpleNote = isSimpleNote)
        allNotesList.add(note)
        adapter.updateList(allNotesList)
        if (isSimpleNote) navigateToEditNoteScreen(note)

    }
//    private fun startSearch(
//        query: String
//    ) {
//        val sortedNoteList = allNotesList.filter { note: Note ->
//            val isSort = note.description.contains(query, ignoreCase = true)
//            isSort
//        }
//        adapter.updateList(sortedNoteList)
//    }

    override fun onStart() {
        super.onStart()
        adapter.updateList(database.getAllNotes())
    }

    private fun navigateToEditNoteScreen(note: Note) {
        val intent = Intent(this, SimpleNoteActivity::class.java)
        intent.putExtra(NOTE_KEY, note)
        startActivity(intent)
    }

    private fun navigateToSearchViewDetailsScreen() { val intent = Intent(this, SearchViewDetailsActivity::class.java)
        startActivity(intent)}

}
const val NOTE_KEY = "NOTE_KEY"
