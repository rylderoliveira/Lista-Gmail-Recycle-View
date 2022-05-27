package com.example.listagmailrecycleview

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.Typeface.BOLD
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.core.util.isNotEmpty
import androidx.recyclerview.widget.RecyclerView
import com.example.listagmailrecycleview.model.Email

class EmailAdapter(val emails: MutableList<Email>): RecyclerView.Adapter<EmailAdapter.EmailViewHolder>() {

    val selectedItems = SparseBooleanArray()
    private var currentSelectedPos: Int = -1

    var onItemClick: ((Int) -> Unit)? = null
    var onItemLongClick: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmailViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.email_item, parent, false)
        return EmailViewHolder(view)
    }

    override fun onBindViewHolder(holder: EmailViewHolder, position: Int) {
        holder.bind(emails[position])
        holder.itemView.setOnClickListener {
            if (selectedItems.isNotEmpty()) {
                onItemClick?.invoke(position)
            }
        }
        holder.itemView.setOnLongClickListener {
            onItemLongClick?.invoke(position)
            return@setOnLongClickListener true
        }

        if(currentSelectedPos == position) currentSelectedPos = -1
    }

    override fun getItemCount(): Int = emails.size
    fun toggleSelection(position: Int) {
        currentSelectedPos = position
        if (selectedItems[position,false]){
            selectedItems.delete(position)
            emails[position].selected = false
        } else {
            selectedItems.put(position, true)
            emails[position].selected = true
        }

        notifyItemChanged(position)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun deleteEmails() {
        emails.removeAll(
            emails.filter { it.selected }
        )
        notifyDataSetChanged()
        currentSelectedPos = -1
    }

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

            if (email.selected){
                itemView.findViewById<TextView>(R.id.txt_icon).background = itemView.oval(Color.BLUE)
                itemView.background = GradientDrawable().apply {
                    shape = GradientDrawable.RECTANGLE
                    cornerRadius = 32f
                    setColor(Color.rgb(194, 226, 252))
                }
            } else {
                itemView.background = GradientDrawable().apply {
                    shape = GradientDrawable.RECTANGLE
                    cornerRadius = 32f
                    setColor(Color.WHITE)
                }
            }

            if(selectedItems.isNotEmpty()){
                animate(itemView.findViewById<TextView>(R.id.txt_icon), email)
            }
        }


        private fun animate(view: TextView, email: Email){
            val oa1: ObjectAnimator = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0f)
            val oa2: ObjectAnimator = ObjectAnimator.ofFloat(view, "scaleX", 0f, 1f)

            oa1.interpolator = DecelerateInterpolator()
            oa2.interpolator = AccelerateInterpolator()

            oa1.duration = 200
            oa2.duration = 200

            oa1.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    if (email.selected) view.text = "\u2713"
                    oa2.start()
                }
            })
            oa1.start()
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