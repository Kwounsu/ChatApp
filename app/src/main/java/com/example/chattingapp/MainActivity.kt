package com.example.chattingapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "ChatApp"
        private val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("message")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Recyclerview
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = ChatAdapter()
        recyclerView.adapter = adapter
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        // Send Chat Button
        button_send.setOnClickListener {
            write(editText_chat.text.toString())
            editText_chat.text.clear()
        }

        // Chatting Message Update
        read(adapter)
    }

    override fun onDestroy() {
        super.onDestroy()

    }

    private fun read(adapter:ChatAdapter) {
        myRef.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                /* Parsing string s gotten from firebase database into map. */
                /* String example: {msg=Hi, nickname=Android SDK built for x86} */
                var s = snapshot.value.toString()
                s = s.drop(1)
                s = s.dropLast(1)
                val map = s.split(", ").associate {
                    val (left, right) = it.split("=")
                    left to right
                }
                Log.d(TAG, "onDataChange => Value is: ${snapshot.value.toString()}")
                val chatData = ChatData(map["nickname"].toString(), map["msg"].toString())
                (adapter).addChat(chatData)
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
    }

    private fun write(msg: String) {
        val chatData = ChatData(android.os.Build.MODEL, msg)
        myRef.push().setValue(chatData)
    }
}