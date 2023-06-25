package com.example.my_notes_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.my_notes_app.databinding.ActivitySearchViewDetailsBinding

class SearchViewDetailsActivity : AppCompatActivity() {

    private val binding:ActivitySearchViewDetailsBinding by lazy {
        ActivitySearchViewDetailsBinding.inflate(layoutInflater)
    }

    private val adapter = NotesAdapter( navigateToSimpleDetailsScreen = ::navigateToSimpleDetailsScreen,
    )
    private val database by lazy {Database(context = this)
    }
    private val allNotesList by lazy {Database(context = this).getAllNotes().toMutableList()
    }
    override fun onCreate(savedInstanceState: Bundle?) {super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.searchView.queryHint =Html.fromHtml("<font color = #ffffff>" + resources.getString(R.string.search_by_the_keyword) + "</font>")
        binding.searchView.setOnQueryTextListener(this)
        adapter.updateList(allNotesList)
    }
    private fun navigateToSimpleDetailsScreen(note: Note) {
        val intent = Intent(this, SimpleNoteDetailsActivity::class.java) intent.putExtra(NOTE_KEY, note)
        startActivity(intent)}
    override fun onQueryTextSubmit(query: String?): Boolean {
        val searchString = query ?: return false startSearch(searchString)
        return false    }
    override fun onQueryTextChange(query: String?): Boolean {
        val searchString = query ?: return false        startSearch(searchString)
        return false    }
    private fun startSearch(query: String) {
        if (query.isEmpty()) {            adapter.updateList(allNotesList)
            return        }
        val sortedNoteList = allNotesList.filter { note: Note ->            val isSort = note.title.contains(query, ignoreCase = true)
            isSort        }
        adapter.updateList(sortedNoteList)    }
    override fun onStart() {
        super.onStart()        adapter.updateList(database.getAllNotes())
    }}
}