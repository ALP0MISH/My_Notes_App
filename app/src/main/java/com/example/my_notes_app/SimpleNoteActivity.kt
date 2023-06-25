package com.example.my_notes_app

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.my_notes_app.databinding.ActivitySimpleNoteBinding

class SimpleNoteActivity : AppCompatActivity() {

    private val binding: ActivitySimpleNoteBinding by lazy {
        ActivitySimpleNoteBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        Log.i("Baha", "5")
        val note = intent.extras?.getSerializable(NOTE_KEY) as Note
        initViews(note)
        val database = Database(this)
        binding.saveChangesButton.setOnClickListener {
            database.updateSimpleNote(
                oldNote = note,
                title = binding.textviewTitle.text.toString(),
                description = binding.descriptionTextView.text.toString(),
            )
            showToast("Вы успешно сохранили")
        }
        binding.navigationBarItemIconView.setOnClickListener {
            onBackPressed()
        }
    }

    private fun initViews(note: Note?) {
        if (note == null) return
        binding.textviewTitle.setText(note.title)
        binding.descriptionTextView.setText(note.description)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}