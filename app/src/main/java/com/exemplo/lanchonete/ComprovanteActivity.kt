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
        val tvItens = findViewById<TextView>(R.id.tvItens)
        val tvTotal = findViewById<TextView>(R.id.tvTotal)
        val tvCodigo = findViewById<TextView>(R.id.tvCodigo)
        val btnNovoPedido = findViewById<Button>(R.id.btnNovoPedido)
        val btnAcompanhar = findViewById<Button>(R.id.btnAcompanhar)

        val nome = intent.getStringExtra("NOME") ?: ""
        val endereco = intent.getStringExtra("ENDERECO") ?: ""
        val codigo = intent.getIntExtra("CODIGO", 0)

        tvNome.text = "Cliente: $nome"
        tvEndereco.text = "Endereço: $endereco"
        tvCodigo.text = codigo.toString()

        // Mostra os itens e o total ANTES de limpar o carrinho
        tvItens.text = Carrinho.textoDosItens()
        tvTotal.text = "Total: R$ " + Carrinho.formatarPreco(Carrinho.total())

        // Limpa o carrinho, já que este pedido foi concluído
        Carrinho.limpar()

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
