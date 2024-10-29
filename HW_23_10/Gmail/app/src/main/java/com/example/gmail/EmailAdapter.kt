package com.example.gmail
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class EmailAdapter(private val emailList: List<Email>) : RecyclerView.Adapter<EmailAdapter.EmailViewHolder>() {

    // Màu sắc cho hình đại diện
    private val avatarColors = listOf(
        Color.parseColor("#3F51B5"),
        Color.parseColor("#009688"),
        Color.parseColor("#E91E63"),
        Color.parseColor("#FF9800"),
        Color.parseColor("#795548")
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmailViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_gmail, parent, false)
        return EmailViewHolder(view)
    }

    override fun onBindViewHolder(holder: EmailViewHolder, position: Int) {
        val email = emailList[position]
        holder.sender.text = email.sender
        holder.subject.text = email.subject
        holder.time.text = email.time

        // Set hình đại diện
        holder.avatar.text = email.avatar
        holder.avatar.setBackgroundColor(avatarColors[position % avatarColors.size])
    }

    override fun getItemCount(): Int {
        return emailList.size
    }

    class EmailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sender: TextView = itemView.findViewById(R.id.sender)
        val subject: TextView = itemView.findViewById(R.id.subject)
        val time: TextView = itemView.findViewById(R.id.time)
        val avatar: TextView = itemView.findViewById(R.id.avatar)
    }
}
