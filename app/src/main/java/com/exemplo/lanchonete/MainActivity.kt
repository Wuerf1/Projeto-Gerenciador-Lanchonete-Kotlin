package com.exemplo.lanchonete

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

// Cardápio da lanchonete Água na Boca
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Cada produto é um cartão (LinearLayout) clicável
        val itemHotDog = findViewById<LinearLayout>(R.id.itemHotDog)
        val itemXBurguer = findViewById<LinearLayout>(R.id.itemXBurguer)
        val itemPizza = findViewById<LinearLayout>(R.id.itemPizza)
        val itemRefrigerante = findViewById<LinearLayout>(R.id.itemRefrigerante)
        val itemCoxinha = findViewById<LinearLayout>(R.id.itemCoxinha)
        val itemPastel = findViewById<LinearLayout>(R.id.itemPastel)
        val itemBatataFrita = findViewById<LinearLayout>(R.id.itemBatataFrita)
        val itemPaoDeQueijo = findViewById<LinearLayout>(R.id.itemPaoDeQueijo)
        val itemSuco = findViewById<LinearLayout>(R.id.itemSuco)
        val btnCarrinho = findViewById<Button>(R.id.btnCarrinho)

        // Cada produto abre sua própria tela de opções e adicionais
        itemHotDog.setOnClickListener {
            startActivity(Intent(this, HotDogActivity::class.java))
        }

        itemXBurguer.setOnClickListener {
            startActivity(Intent(this, XBurguerActivity::class.java))
        }

        itemPizza.setOnClickListener {
            startActivity(Intent(this, PizzaActivity::class.java))
        }

        itemRefrigerante.setOnClickListener {
            startActivity(Intent(this, RefrigeranteActivity::class.java))
        }

        itemCoxinha.setOnClickListener {
            startActivity(Intent(this, CoxinhaActivity::class.java))
        }

        itemPastel.setOnClickListener {
            startActivity(Intent(this, PastelActivity::class.java))
        }

        itemBatataFrita.setOnClickListener {
            startActivity(Intent(this, BatataFritaActivity::class.java))
        }

        itemPaoDeQueijo.setOnClickListener {
            startActivity(Intent(this, PaoDeQueijoActivity::class.java))
        }

        itemSuco.setOnClickListener {
            startActivity(Intent(this, SucoActivity::class.java))
        }

        btnCarrinho.setOnClickListener {
            startActivity(Intent(this, CarrinhoActivity::class.java))
        }
    }
}
