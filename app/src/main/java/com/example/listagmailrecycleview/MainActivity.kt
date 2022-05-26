package com.example.listagmailrecycleview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.listagmailrecycleview.model.email
import com.example.listagmailrecycleview.model.fakeEmails
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mooveit.library.Fakeit
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: EmailAdapter

    inner class ItemTouchHelper(
        dragDirs: Int,
        swipeDirs: Int
    ): androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback(
        dragDirs,
        swipeDirs
    ){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            val from = viewHolder.adapterPosition
            val to = target.adapterPosition

            Collections.swap(adapter.emails, from, to)
            adapter.notifyItemMoved(from, to)
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            TODO("Not yet implemented")
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Fakeit.init()
        setContentView(R.layout.activity_main)

        adapter = EmailAdapter(fakeEmails())

        val recyclerViewMain: RecyclerView = findViewById(R.id.recycle_view_main)
        recyclerViewMain.adapter = adapter
        recyclerViewMain.layoutManager = LinearLayoutManager(this)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener{
            addEmail()
            recyclerViewMain.scrollToPosition(0)
        }

        val helper = ItemTouchHelper(
            ItemTouchHelper(
                androidx.recyclerview.widget.ItemTouchHelper.UP or
                    androidx.recyclerview.widget.ItemTouchHelper.DOWN,
                androidx.recyclerview.widget.ItemTouchHelper.LEFT
            )
        )

        helper.attachToRecyclerView(recyclerViewMain)
    }

    private fun addEmail() {
        val data: Date? = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR"))
            .parse(Fakeit.dateTime().dateFormatter())
        adapter.emails.add(0,
            email {
                star = false
                read = false
                user = Fakeit.name().firstName()
                subject = Fakeit.company().name()
                preview = mutableListOf<String>().apply {
                    repeat(10) {
                        add(Fakeit.lorem().words())
                    }
                }.joinToString(" ")
                date = data?.let { SimpleDateFormat("dd MMM", Locale("pt", "BR")).format(it) }.toString()
            }
        )

        adapter.notifyItemInserted(0)
    }
}

