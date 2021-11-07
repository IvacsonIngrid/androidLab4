package com.example.lab4

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

const val EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE"

class MainActivity : AppCompatActivity() {
    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val selectButton = findViewById<Button>(R.id.button2)

        selectButton.setOnClickListener {
            var i = Intent(Intent.ACTION_PICK)
            i.type = ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE
            startActivityForResult(i, 111)
        }
    }

    var name = ""

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 111 && resultCode == Activity.RESULT_OK)
        {
            var contacturi = data?.data ?: return
            var cola = arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)

            var ra = contentResolver.query(contacturi, cola, null, null, null)
            if(ra?.moveToFirst() !!)
            {
                val number = findViewById<EditText>(R.id.editTextTextPersonName2)
                number.setText(ra.getString(0))

                name = ra.getString(1)
            }
        }
    }

    fun sendMessage(view: View) {

        Toast.makeText(getApplicationContext(),"Wait a minute...",Toast.LENGTH_SHORT).show()

        if (name == "")
        {
            //lab4 1-2 reszhez
            val editText = findViewById<EditText>(R.id.editTextTextPersonName2)
            val message = editText.text.toString()
            val intent = Intent(this, DisplayMessageActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, message)
            }
            startActivity(intent)
        }
        else
        {
            //lab4 3-4 reszhez
            val intent = Intent(this, DisplayMessageActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, name)
            }
            startActivity(intent)
        }
    }
}