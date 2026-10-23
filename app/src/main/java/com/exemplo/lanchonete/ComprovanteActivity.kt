package com.exemplo.lanchonete

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ComprovanteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comprovante)

        val tvNome = findViewById<TextView>(R.id.tvNome)
        val tvEndereco = findViewById<TextView>(R.id.tvEndereco)
        val tvPagamento = findViewById<TextView>(R.id.tvPagamento)
        val tvItens = findViewById<TextView>(R.id.tvItens)
        val tvTotal = findViewById<TextView>(R.id.tvTotal)
        val tvCodigo = findViewById<TextView>(R.id.tvCodigo)
        val btnNovoPedido = findViewById<Button>(R.id.btnNovoPedido)
        val btnAcompanhar = findViewById<Button>(R.id.btnAcompanhar)

        // Tudo vem da tela de entrega pelo putExtra (o carrinho já foi limpo lá)
        val nome = intent.getStringExtra("NOME") ?: ""
        val endereco = intent.getStringExtra("ENDERECO") ?: ""
        val pagamento = intent.getStringExtra("PAGAMENTO") ?: ""
        val itens = intent.getStringExtra("ITENS") ?: ""
        val total = intent.getDoubleExtra("TOTAL", 0.0)
        val codigo = intent.getIntExtra("CODIGO", 0)

        tvNome.text = "Cliente: $nome"
        tvEndereco.text = "Endereço: $endereco"
        tvPagamento.text = "Pagamento na entrega: $pagamento"
        tvCodigo.text = codigo.toString()
        tvItens.text = itens
        tvTotal.text = "Total: R$ " + Carrinho.formatarPreco(total)

        // Abre a tela de acompanhamento levando o código do pedido
        btnAcompanhar.setOnClickListener {
            val abrirAcompanhamento = Intent(this, AcompanhamentoActivity::class.java)
            abrirAcompanhamento.putExtra("CODIGO", codigo)
            startActivity(abrirAcompanhamento)
        }

        btnNovoPedido.setOnClickListener {
            // CLEAR_TOP volta para a tela de restaurantes que já está aberta e fecha
            // as telas do pedido anterior, em vez de empilhar uma nova
            val irParaInicio = Intent(this, RestaurantesActivity::class.java)
            irParaInicio.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(irParaInicio)
            finish()
        }
    }
}
