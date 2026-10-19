package com.exemplo.lanchonete

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class XBurguerActivity : AppCompatActivity() {

    // Cada sabor tem o seu próprio preço (só um sabor pode ser escolhido)
    private val listaSabores = listOf(
        "X-Bacon" to 14.00,
        "X-Salada" to 12.00,
        "X-Tudo" to 17.00,
        "X-Frango" to 13.00,
        "X-Calabresa" to 13.00
    )
    private val quantidadeMaxima = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_xburguer)

        val cbBacon = findViewById<CheckBox>(R.id.cbBacon)
        val cbOvo = findViewById<CheckBox>(R.id.cbOvo)
        val cbQueijoExtra = findViewById<CheckBox>(R.id.cbQueijoExtra)
        val rgSabor = findViewById<RadioGroup>(R.id.rgSabor)
        val btnAdicionarCarrinho = findViewById<Button>(R.id.btnAdicionarCarrinho)
        val btnVoltar = findViewById<Button>(R.id.btnVoltar)
        val tvQuantidade = findViewById<TextView>(R.id.tvQuantidade)
        val btnMenos = findViewById<Button>(R.id.btnMenos)
        val btnMais = findViewById<Button>(R.id.btnMais)

        // Quantidade escolhida pelo cliente (começa em 1)
        var quantidade = 1

        btnMais.setOnClickListener {
            if (quantidade < quantidadeMaxima) {
                quantidade++
                tvQuantidade.text = quantidade.toString()
            } else {
                Toast.makeText(this, "Máximo de $quantidadeMaxima unidades por item", Toast.LENGTH_SHORT).show()
            }
        }

        btnMenos.setOnClickListener {
            if (quantidade > 1) {
                quantidade--
                tvQuantidade.text = quantidade.toString()
            }
        }

        btnAdicionarCarrinho.setOnClickListener {
            // Sabor escolhido (posição no grupo) e o preço dele
            val marcado = rgSabor.findViewById<RadioButton>(rgSabor.checkedRadioButtonId)
            val (nomeSabor, precoSabor) = listaSabores[rgSabor.indexOfChild(marcado)]
            var preco = precoSabor
            val adicionais = StringBuilder()
            adicionais.append(", Sabor $nomeSabor")

            if (cbBacon.isChecked) {
                preco += 2.00
                adicionais.append(", Bacon")
            }
            if (cbOvo.isChecked) {
                preco += 1.50
                adicionais.append(", Ovo")
            }
            if (cbQueijoExtra.isChecked) {
                preco += 1.50
                adicionais.append(", Queijo Extra")
            }

            val descricao = "X-Burguer" + adicionais.toString()
            Carrinho.adicionar(ItemPedido(descricao, quantidade, preco))

            Toast.makeText(this, "X-Burguer adicionado ao carrinho!", Toast.LENGTH_SHORT).show()
            finish()
        }

        btnVoltar.setOnClickListener {
            finish()
        }
    }
}
