package com.example.my_first_application

import android.content.Intent
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.widget.EditText

const val EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE"
const val RESULT_PICK_CONTACT = 1

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    fun chooseContact(view: View) {
        val intent = Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI)
        startActivityForResult(intent, RESULT_PICK_CONTACT)

        var cursor: Cursor?

        try{
            var phoneNo: String? = null
            val uri: Uri? = intent.getData()
            cursor = uri?.let { getContentResolver().query(it, null, null, null, null) }
            if (cursor != null) {
                cursor.moveToFirst()
            }
            val phoneIndex = cursor?.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
            if (cursor != null) {
                phoneNo = phoneIndex?.let { cursor.getString(it) }
            }

            val intent2 = Intent(this, DisplayMessageActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, phoneNo)
            }
            startActivity(intent2)

        }
        catch(e: Exception){
            e.printStackTrace()
        }
    }


    fun sendMessage(view: View) {
        val editText = findViewById<EditText>(R.id.editTextTextPersonName)
        val message = editText.text.toString()
        val intent = Intent(this, DisplayMessageActivity::class.java).apply {
            putExtra(EXTRA_MESSAGE, message)
        }
        startActivity(intent)
    }
}