package ru.spbstu.icc.kspt.lab2.continuewatch

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity2 : AppCompatActivity() {
    private var secondsElapsed: Int = 0
    private lateinit var textSecondsElapsed: TextView
    private var isvisible  : Boolean = true
    private lateinit var sharedPref: SharedPreferences

    private var backgroundThread = Thread {
        while (true) {
            if (isvisible) {
                textSecondsElapsed.post {
                    textSecondsElapsed.text = getString(R.string.sec_elapsed, secondsElapsed++)
                }
                Thread.sleep(1000)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
        sharedPref = getSharedPreferences("SEC", Context.MODE_PRIVATE)
        backgroundThread.start()
    }

    override fun onResume() {
        isvisible = true
        secondsElapsed = sharedPref.getInt("SEC", 0)
        super.onResume()
    }

    override fun onStop() {
        isvisible = false
        val editor = sharedPref.edit()
        editor.putInt("SEC", secondsElapsed)
        editor.apply()
        super.onStop()
    }
}