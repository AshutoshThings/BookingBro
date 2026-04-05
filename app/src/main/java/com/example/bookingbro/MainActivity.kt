package com.example.bookingbro

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val logoCard = findViewById<View>(R.id.logoCard)
        val tvAppTitle = findViewById<TextView>(R.id.tvAppTitle)
        val tvSubtitle = findViewById<TextView>(R.id.tvSubtitle)
        val btnCustomer = findViewById<Button>(R.id.btnCustomer)
        val btnService = findViewById<Button>(R.id.btnService)

        val viewsToAnimate = listOf(logoCard, tvAppTitle, tvSubtitle, btnCustomer, btnService)

        viewsToAnimate.forEach { view ->
            view.alpha = 0f
            view.translationY = 50f
        }

        viewsToAnimate.forEachIndexed { index, view ->
            view.animate()
                .alpha(1f)
                .translationY(0f)
                .setStartDelay(index * 100L)
                .setDuration(600)
                .setInterpolator(DecelerateInterpolator())
                .start()
        }

        btnCustomer.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.putExtra("role", "customer")
            startActivity(intent)
        }

        btnService.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.putExtra("role", "provider")
            startActivity(intent)
        }
    }
}