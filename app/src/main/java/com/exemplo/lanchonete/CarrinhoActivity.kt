package com.exemplo.lanchonete

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class CarrinhoActivity : AppCompatActivity() {

    // Componentes usados também na função mostrarCarrinho()
    private lateinit var tvVazio: TextView
    private lateinit var tvItens: TextView
    private lateinit var tvTotal: TextView
    private lateinit var btnRemoverItens: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrinho)

        tvVazio = findViewById(R.id.tvVazio)
        tvItens = findViewById(R.id.tvItens)
        tvTotal = findViewById(R.id.tvTotal)
        btnRemoverItens = findViewById(R.id.btnRemoverItens)
        val btnFinalizarPedido = findViewById<Button>(R.id.btnFinalizarPedido)
        val btnContinuarComprando = findViewById<Button>(R.id.btnContinuarComprando)

        btnRemoverItens.setOnClickListener {
            escolherItemParaRemover()
        }

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

    // O onResume roda toda vez que a tela aparece, inclusive quando o cliente
    // volta para ela. Assim o carrinho mostrado está sempre atualizado.
    override fun onResume() {
        super.onResume()
        mostrarCarrinho()
    }

    private fun mostrarCarrinho() {
        if (Carrinho.itens.isEmpty()) {
            tvVazio.visibility = View.VISIBLE
            tvItens.visibility = View.GONE
            btnRemoverItens.visibility = View.GONE
            // Sem itens, não faz sentido mostrar "Total: R$ 0,00"
            tvTotal.visibility = View.GONE
        } else {
            tvVazio.visibility = View.GONE
            tvItens.visibility = View.VISIBLE
            btnRemoverItens.visibility = View.VISIBLE
            tvTotal.visibility = View.VISIBLE
            // Mostra todos os itens do carrinho (com a quantidade de cada um)
            tvItens.text = Carrinho.textoDosItens()
            tvTotal.text = "Total: R$ " + Carrinho.formatarPreco(Carrinho.total())
        }
    }

    // Abre uma janela com a lista dos itens: tocar em um item tira ele do carrinho
    private fun escolherItemParaRemover() {
        val opcoes = Array(Carrinho.itens.size) { posicao ->
            Carrinho.textoDoItem(Carrinho.itens[posicao])
        }

        AlertDialog.Builder(this)
            .setTitle("Toque no item que deseja remover")
            .setItems(opcoes) { _, posicao ->
                Carrinho.remover(posicao)
                Toast.makeText(this, "Item removido do carrinho", Toast.LENGTH_SHORT).show()
                mostrarCarrinho()
            }
            .setNeutralButton("Esvaziar carrinho") { _, _ ->
                confirmarEsvaziar()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    // Pede confirmação antes de apagar tudo, para evitar um toque sem querer
    private fun confirmarEsvaziar() {
        AlertDialog.Builder(this)
            .setTitle("Esvaziar carrinho?")
            .setMessage("Todos os itens escolhidos serão removidos.")
            .setPositiveButton("Esvaziar") { _, _ ->
                Carrinho.limpar()
                mostrarCarrinho()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
}
