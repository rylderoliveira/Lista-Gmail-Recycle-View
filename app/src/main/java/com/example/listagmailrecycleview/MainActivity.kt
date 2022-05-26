package com.example.listagmailrecycleview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.listagmailrecycleview.model.fakeEmails

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerViewMain: RecyclerView = findViewById(R.id.recycle_view_main)
        recyclerViewMain.adapter = EmailAdapter(fakeEmails())
        recyclerViewMain.layoutManager = LinearLayoutManager(this)
    }
}