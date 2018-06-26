package com.example.rohith.imagerecognition

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

import kotlinx.android.synthetic.main.activity_main.*
import com.google.android.gms.vision.text.TextBlock
import android.util.SparseArray
import android.R.attr.bitmap
import android.content.Intent
import android.util.Log
import android.view.View
import com.google.android.gms.vision.Frame
import com.google.android.gms.vision.text.TextRecognizer

class MainActivity : AppCompatActivity() {

    private lateinit var imageView : ImageView
    private lateinit var btnProcess : Button
    private lateinit var textView : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        imageView = findViewById(R.id.image);
        btnProcess = findViewById(R.id.showText);
        val bitmap   = BitmapFactory.decodeResource(
                getApplicationContext().getResources(),
                R.drawable.text2
        );
        imageView.setImageBitmap(bitmap);
        btnProcess.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                val textRecognizer = TextRecognizer.Builder(applicationContext).build()
                    val frame = Frame.Builder().setBitmap(bitmap).build()
                    val items = textRecognizer.detect(frame)
                    val stringBuilder = StringBuilder()
                    for (i in 0 until items.size()) {
                        val item = items.valueAt(i)
                        stringBuilder.append(item.value)
                        stringBuilder.append("\n")
                    }
                 //   txtResult.setText(stringBuilder.toString())
                    Log.i("text",stringBuilder.toString())
                    btnProcess.setOnClickListener{
                        val intent = Intent(this@MainActivity,TextActivity::class.java)
                        intent.putExtra("text",stringBuilder.toString())
                        startActivity(intent)
                    }

                }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
