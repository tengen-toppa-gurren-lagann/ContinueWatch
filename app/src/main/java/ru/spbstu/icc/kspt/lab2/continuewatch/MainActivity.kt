package ru.spbstu.icc.kspt.lab2.continuewatch

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var secondsElapsed: Int = 0
    private lateinit var textSecondsElapsed: TextView
    private var visibility  : Boolean = true

    @SuppressLint("SetTextI18n")
    var backgroundThread = Thread {
        while (true) {
            if (visibility) {
                textSecondsElapsed.post {
                    textSecondsElapsed.text = "Seconds elapsed: " + secondsElapsed++
                }
                Thread.sleep(1000)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
        backgroundThread.start()
    }

    override fun onResume() {
        visibility  = true
        super.onResume()
    }

    override fun onStop() {
        visibility  = false
        super.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run{putInt("SEC", secondsElapsed)}
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState.run{secondsElapsed = getInt("SEC")}
    }
}