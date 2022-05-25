package com.example.listagmailrecycleview.model

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

    fun build(): Email = Email(user, subject, preview, date, star, read, false)
}

fun email(block: EmailBuilder.() -> Unit): Email = EmailBuilder().apply(block).build()

fun fakeEmails() = listOf<Email>(
    email {
        user = "Facebook"
        subject = "Venha aproveitar as novas ofertas especiais"
        preview = "Olá Rylder, abra o e-mail e venha as ofertas que preparamos para você"
        date = "22 mai"
        star = false
    },
    email {
        user = "Instagram"
        subject = "Uma pessoa acabou de te seguir"
        preview = "Olá Rylder, você tem novos seguidores, clique e veja"
        date = "05 mai"
        star = false
    },
    email {
        user = "Americanas"
        subject = "Pedido Enviado"
        preview = "Seu pedido acaba de ser enviado a transportadora e chegará em breve na sua casa"
        date = "09 mai"
        star = false
    },
    email {
        user = "Linkedin"
        subject = "Novo post"
        preview = "O usuário Rodrigo acaba de publicar no feed, venha dar uma olhada"
        date = "12 mai"
        star = false
    },
    email {
        user = "Facebook"
        subject = "Venha aproveitar as novas ofertas especiais"
        preview = "Olá Rylder, abra o e-mail e venha as ofertas que preparamos para você"
        date = "22 mai"
        star = false
    },
    email {
        user = "Instagram"
        subject = "Uma pessoa acabou de te seguir"
        preview = "Olá Rylder, você tem novos seguidores, clique e veja"
        date = "05 mai"
        star = false
    },
    email {
        user = "Americanas"
        subject = "Pedido Enviado"
        preview = "Seu pedido acaba de ser enviado a transportadora e chegará em breve na sua casa"
        date = "09 mai"
        star = false
    },
    email {
        user = "Linkedin"
        subject = "Novo post"
        preview = "O usuário Rodrigo acaba de publicar no feed, venha dar uma olhada"
        date = "12 mai"
        star = false
    },
    email {
        user = "Facebook"
        subject = "Venha aproveitar as novas ofertas especiais"
        preview = "Olá Rylder, abra o e-mail e venha as ofertas que preparamos para você"
        date = "22 mai"
        star = false
    },
    email {
        user = "Instagram"
        subject = "Uma pessoa acabou de te seguir"
        preview = "Olá Rylder, você tem novos seguidores, clique e veja"
        date = "05 mai"
        star = false
    },
    email {
        user = "Americanas"
        subject = "Pedido Enviado"
        preview = "Seu pedido acaba de ser enviado a transportadora e chegará em breve na sua casa"
        date = "09 mai"
        star = false
    },
    email {
        user = "Linkedin"
        subject = "Novo post"
        preview = "O usuário Rodrigo acaba de publicar no feed, venha dar uma olhada"
        date = "12 mai"
        star = false
    },
)