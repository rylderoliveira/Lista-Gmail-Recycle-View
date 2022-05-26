package com.example.listagmailrecycleview

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.Typeface.BOLD
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.RecyclerView
import com.example.listagmailrecycleview.model.Email

class EmailAdapter(val emails: MutableList<Email>): RecyclerView.Adapter<EmailAdapter.EmailViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmailViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.email_item, parent, false)
        return EmailViewHolder(view)
    }

    override fun onBindViewHolder(holder: EmailViewHolder, position: Int) {
        holder.bind(emails[position])
    }

    override fun getItemCount(): Int = emails.size

    inner class EmailViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        @SuppressLint("CutPasteId")
        fun bind(email: Email) {
            val hash = email.user.hashCode()
            itemView.findViewById<TextView>(R.id.txt_icon).text = email.user.first().toString()
            itemView.findViewById<TextView>(R.id.txt_icon).background = itemView.oval(Color.rgb(hash / 2, 0, hash))
            itemView.findViewById<TextView>(R.id.txt_preview).text = email.preview
            itemView.findViewById<TextView>(R.id.txt_title).text = email.user
            itemView.findViewById<TextView>(R.id.txt_date).text = email.date
            itemView.findViewById<TextView>(R.id.txt_subject).text = email.subject

            // Arrumando a estrela
            if (email.star) {
                itemView.findViewById<ImageView>(R.id.img_star).setImageResource(R.drawable.ic_baseline_star_rate_24)
            } else {
                itemView.findViewById<ImageView>(R.id.img_star).setImageResource(R.drawable.ic_baseline_star_border_24)
            }

            if (!email.read) {
                itemView.findViewById<TextView>(R.id.txt_title).setTypeface(Typeface.DEFAULT, BOLD)
                itemView.findViewById<TextView>(R.id.txt_date).setTypeface(Typeface.DEFAULT, BOLD)
                itemView.findViewById<TextView>(R.id.txt_subject).setTypeface(Typeface.DEFAULT, BOLD)
            }
        }
    }
}

fun View.oval(@ColorInt color: Int): ShapeDrawable {
    val oval = ShapeDrawable(OvalShape())
    with(oval){
        intrinsicHeight = height
        intrinsicWidth = width
        paint.color = color
    }
    return oval
}