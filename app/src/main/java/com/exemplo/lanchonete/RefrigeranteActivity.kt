package com.exemplo.lanchonete

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RefrigeranteActivity : AppCompatActivity() {

    // Cada sabor tem o seu próprio preço (só um sabor pode ser escolhido)
    private val listaSabores = listOf(
        "Cola" to 6.00,
        "Guaraná" to 5.00,
        "Fanta Laranja" to 5.00,
        "Limão (Sprite)" to 5.00,
        "Fanta Uva" to 5.50
    )
    private val quantidadeMaxima = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_refrigerante)

        val cbTamanhoGrande = findViewById<CheckBox>(R.id.cbTamanhoGrande)
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
            val opcoes = StringBuilder()
            opcoes.append(", Sabor $nomeSabor")

            if (cbTamanhoGrande.isChecked) {
                preco += 2.00
                opcoes.append(", Tamanho Grande")
            }

            val descricao = "Refrigerante" + opcoes.toString()
            Carrinho.adicionar(ItemPedido(descricao, quantidade, preco))

            Toast.makeText(this, "Refrigerante adicionado ao carrinho!", Toast.LENGTH_SHORT).show()
            finish()
        }

        btnVoltar.setOnClickListener {
            finish()
        }
    }
}
