package com.example.listagmailrecycleview.model

import javax.security.auth.Subject

data class Email(
    val user: String,
    val subject: String,
    val preview: String,
    val date: String,
    val star: Boolean,
    val read: Boolean,
    var selected: Boolean = false,
)

class EmailBuilder {
    var user: String = ""
    var subject: String = ""
    var preview: String = ""
    var date: String = ""
    var star: Boolean = false
    var read: Boolean = false
}