package com.example.listagmailrecycleview

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.view.ActionMode
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

    private var actionMode: ActionMode? = null
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
            adapter.emails.removeAt(viewHolder.adapterPosition)
            adapter.notifyItemRemoved(viewHolder.adapterPosition)
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
                0,
                androidx.recyclerview.widget.ItemTouchHelper.LEFT
            )
        )

        helper.attachToRecyclerView(recyclerViewMain)
        adapter.onItemClick = {
            enableActionMode(it)
        }
        adapter.onItemLongClick = {
            enableActionMode(it)
        }
    }

    private fun enableActionMode(position: Int) {
        if (actionMode == null) actionMode = startSupportActionMode(object: ActionMode.Callback{
            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                mode?.menuInflater?.inflate(R.menu.menu_delete, menu)
                return true
            }

            override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                return false
            }

            override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                if (item?.itemId == R.id.action_delete) {
                    adapter.deleteEmails()
                    mode?.finish()
                    return true
                }
                return false
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onDestroyActionMode(mode: ActionMode?) {
                adapter.selectedItems.clear()
                adapter.emails.filter { it.selected == true }
                    .forEach { it.selected = false }
                adapter.notifyDataSetChanged()
                actionMode = null
            }
        })

        adapter.toggleSelection(position)
        val size = adapter.selectedItems.size()
        if (size == 0) actionMode?.finish()
        else {
            actionMode?.title = "$size"
            actionMode?.invalidate()
        }
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

