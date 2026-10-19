package com.exemplo.lanchonete

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

// Cardápio da Borcelle Cafeteria: funciona igual ao cardápio da lanchonete (MainActivity)
class CafeteriaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cafeteria)

        // Cada produto é um cartão (LinearLayout) clicável
        val itemCafe = findViewById<LinearLayout>(R.id.itemCafe)
        val itemCappuccino = findViewById<LinearLayout>(R.id.itemCappuccino)
        val itemPaoNaChapa = findViewById<LinearLayout>(R.id.itemPaoNaChapa)
        val itemBolo = findViewById<LinearLayout>(R.id.itemBolo)
        val btnCarrinho = findViewById<Button>(R.id.btnCarrinho)

        // Cada produto abre sua própria tela de opções e adicionais
        itemCafe.setOnClickListener {
            startActivity(Intent(this, CafeActivity::class.java))
        }

        itemCappuccino.setOnClickListener {
            startActivity(Intent(this, CappuccinoActivity::class.java))
        }

        itemPaoNaChapa.setOnClickListener {
            startActivity(Intent(this, PaoNaChapaActivity::class.java))
        }

        itemBolo.setOnClickListener {
            startActivity(Intent(this, BoloActivity::class.java))
        }

        btnCarrinho.setOnClickListener {
            startActivity(Intent(this, CarrinhoActivity::class.java))
        }
    }
}
