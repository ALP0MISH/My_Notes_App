package com.example.my_notes_app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.my_notes_app.databinding.SimpleNoteItemBinding

class NotesAdapter(
    private val navigateToEditNoteScreen: (note: Note) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val notesList = mutableListOf<Note>()

    fun updateList(newList: List<Note>) {
        notesList.clear()
        notesList.addAll(newList)
        notifyDataSetChanged()
    }

    inner class SimpleNoteViewHolder(private val binding: SimpleNoteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {
            binding.title.text = note.title
            binding.root.setOnClickListener {
                navigateToEditNoteScreen(note)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.simple_note_item, parent, false)
        val binding = SimpleNoteItemBinding.bind(view)
        return SimpleNoteViewHolder(binding)
    }


    override fun getItemViewType(position: Int): Int {
        val note = notesList[position]
        return if (note.isSimpleNote) 0 else 1
    }

    override fun getItemCount(): Int = notesList.size


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as SimpleNoteViewHolder).bind(notesList[position])
    }
}
