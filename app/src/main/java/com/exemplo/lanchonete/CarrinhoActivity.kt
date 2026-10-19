package com.exemplo.lanchonete

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CarrinhoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrinho)

        val tvVazio = findViewById<TextView>(R.id.tvVazio)
        val tvItens = findViewById<TextView>(R.id.tvItens)
        val tvTotal = findViewById<TextView>(R.id.tvTotal)
        val btnFinalizarPedido = findViewById<Button>(R.id.btnFinalizarPedido)
        val btnContinuarComprando = findViewById<Button>(R.id.btnContinuarComprando)

        if (Carrinho.itens.isEmpty()) {
            tvVazio.visibility = View.VISIBLE
            tvItens.visibility = View.GONE
            // Sem itens, não faz sentido mostrar "Total: R$ 0,00"
            tvTotal.visibility = View.GONE
        } else {
            tvVazio.visibility = View.GONE
            tvItens.visibility = View.VISIBLE
            // Mostra todos os itens do carrinho (com a quantidade de cada um)
            tvItens.text = Carrinho.textoDosItens()
        }

        tvTotal.text = "Total: R$ " + Carrinho.formatarPreco(Carrinho.total())

        btnFinalizarPedido.setOnClickListener {
            if (Carrinho.itens.isEmpty()) {
                Toast.makeText(this, "Adicione algum item antes de finalizar", Toast.LENGTH_SHORT).show()
            } else {
                startActivity(Intent(this, DadosEntregaActivity::class.java))
            }
        }

        btnContinuarComprando.setOnClickListener {
            finish()
        }
    }
}
