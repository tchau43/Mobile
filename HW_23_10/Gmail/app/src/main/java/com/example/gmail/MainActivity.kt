package com.example.gmail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val emailList = listOf(
            Email("Netflix", "New movies and shows added!", "4:15 PM", "N"),
            Email("Amazon", "Your package has been shipped", "3:50 PM", "A"),
            Email("Google Drive", "Your storage is almost full", "2:20 PM", "G"),
            Email("Slack", "New messages in #general", "1:30 PM", "S"),
            Email("Coursera", "New courses based on your interests", "12:10 PM", "C"),
            Email("Reddit", "Trending posts in your favorite communities", "11:45 AM", "R"),
            Email("Pinterest", "Ideas for your next project", "10:25 AM", "P"),
            Email("GitHub", "New pull requests in your repository", "9:15 AM", "G"),
            Email("Apple", "Your subscription renews soon", "8:45 AM", "A"),
            Email("Twitter", "You have new followers", "7:30 AM", "T")
        )


        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = EmailAdapter(emailList)
    }
}