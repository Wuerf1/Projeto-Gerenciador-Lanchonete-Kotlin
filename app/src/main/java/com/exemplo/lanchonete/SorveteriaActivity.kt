package com.exemplo.lanchonete

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

// Cardápio da Sorveteria Borcelle: funciona igual ao cardápio da lanchonete (MainActivity)
class SorveteriaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sorveteria)

        // Cada produto é um cartão (LinearLayout) clicável
        val itemCasquinha = findViewById<LinearLayout>(R.id.itemCasquinha)
        val itemSundae = findViewById<LinearLayout>(R.id.itemSundae)
        val itemAcai = findViewById<LinearLayout>(R.id.itemAcai)
        val itemPicole = findViewById<LinearLayout>(R.id.itemPicole)
        val btnCarrinho = findViewById<Button>(R.id.btnCarrinho)

        // Cada produto abre sua própria tela de opções e adicionais
        itemCasquinha.setOnClickListener {
            startActivity(Intent(this, CasquinhaActivity::class.java))
        }

        itemSundae.setOnClickListener {
            startActivity(Intent(this, SundaeActivity::class.java))
        }

        itemAcai.setOnClickListener {
            startActivity(Intent(this, AcaiActivity::class.java))
        }

        itemPicole.setOnClickListener {
            startActivity(Intent(this, PicoleActivity::class.java))
        }

        btnCarrinho.setOnClickListener {
            startActivity(Intent(this, CarrinhoActivity::class.java))
        }
    }
}
