package com.exemplo.lanchonete

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

// Primeira tela depois da abertura: o usuário escolhe o restaurante
class RestaurantesActivity : AppCompatActivity() {

    // Botão do carrinho: o texto dele muda conforme os itens escolhidos
    private lateinit var btnCarrinho: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurantes)

        // Cada restaurante é um cartão (LinearLayout) clicável
        val cardLanchonete = findViewById<LinearLayout>(R.id.cardLanchonete)
        val cardSorveteria = findViewById<LinearLayout>(R.id.cardSorveteria)
        val cardCafeteria = findViewById<LinearLayout>(R.id.cardCafeteria)
        btnCarrinho = findViewById(R.id.btnCarrinho)
        val btnSobre = findViewById<Button>(R.id.btnSobre)

        // Cada restaurante abre o seu próprio cardápio.
        // O carrinho é o mesmo para os três.
        cardLanchonete.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        cardSorveteria.setOnClickListener {
            startActivity(Intent(this, SorveteriaActivity::class.java))
        }

        cardCafeteria.setOnClickListener {
            startActivity(Intent(this, CafeteriaActivity::class.java))
        }

        btnCarrinho.setOnClickListener {
            startActivity(Intent(this, CarrinhoActivity::class.java))
        }

        btnSobre.setOnClickListener {
            startActivity(Intent(this, SobreActivity::class.java))
        }
    }

    // O onResume roda toda vez que a tela aparece (também ao voltar de um produto),
    // então o botão sempre mostra a quantidade de itens atualizada.
    // Aqui o botão divide espaço com o "Sobre", por isso usa o texto curto.
    override fun onResume() {
        super.onResume()
        btnCarrinho.text = Carrinho.textoCurtoDoBotao()
    }
}
