package com.example.interest.ui.messenger
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.interest.R
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class MessengerActivity : AppCompatActivity() {
    var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    var myRef: DatabaseReference? = null
    var mEditTextMessages: EditText? = null
    var mSendButton: Button? = null
    var mMessagerRecycler: RecyclerView? = null
    var messages = ArrayList<String>()
    protected override fun onCreate(savedInstanceState: Bundle?) {
        val chatId: String = getIntent().getStringExtra("chatId")
        myRef = database.getReference("messages/$chatId")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messenger)
        mSendButton = findViewById(R.id.send_message_b)
        mEditTextMessages = findViewById(R.id.message_input)
        mMessagerRecycler = findViewById(R.id.messaging_recycler)
        mMessagerRecycler?.setLayoutManager(LinearLayoutManager(this))
        val dataAdapter = DataAdapter(this, messages)
        mMessagerRecycler?.setAdapter(dataAdapter)
        mSendButton!!.setOnClickListener(View.OnClickListener {
            val msg = mEditTextMessages!!.text.toString()
            if (msg == "") {
                Toast.makeText(getApplicationContext(), "Input your message", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            if (msg.length > 1000) {
                Toast.makeText(getApplicationContext(), "Too many symbols", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            myRef?.push()?.setValue(msg)
            mEditTextMessages!!.setText("")
        })
        myRef?.addChildEventListener(object : ChildEventListener() {
            override fun onChildAdded(@NonNull dataSnapshot: DataSnapshot, @Nullable s: String?) {
                val msg: String = dataSnapshot?.getValue(String::class.java).toString()
                messages.add(msg)
                dataAdapter.notifyDataSetChanged()
                mMessagerRecycler?.smoothScrollToPosition(messages.size)
            }


        })
    }
}

