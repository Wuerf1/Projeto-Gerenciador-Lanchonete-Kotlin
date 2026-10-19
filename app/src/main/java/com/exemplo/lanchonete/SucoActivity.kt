package com.exemplo.lanchonete

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SucoActivity : AppCompatActivity() {

    // Cada sabor tem o seu próprio preço (só um sabor pode ser escolhido)
    private val listaSabores = listOf(
        "Laranja Natural" to 6.00,
        "Laranja com Acerola" to 7.00,
        "Laranja com Cenoura" to 7.00,
        "Laranja com Morango" to 8.00,
        "Laranja com Limão" to 7.00
    )
    private val quantidadeMaxima = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_suco)

        val cbCopoGrande = findViewById<CheckBox>(R.id.cbCopoGrande)
        val cbMel = findViewById<CheckBox>(R.id.cbMel)
        val cbHortela = findViewById<CheckBox>(R.id.cbHortela)
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

            if (cbCopoGrande.isChecked) {
                preco += 2.00
                adicionais.append(", Copo Grande")
            }
            if (cbMel.isChecked) {
                preco += 1.00
                adicionais.append(", Adoçado com Mel")
            }
            if (cbHortela.isChecked) {
                preco += 0.50
                adicionais.append(", Com Hortelã")
            }

            val descricao = "Suco" + adicionais.toString()
            Carrinho.adicionar(ItemPedido(descricao, quantidade, preco))

            Toast.makeText(this, "Suco adicionado ao carrinho!", Toast.LENGTH_SHORT).show()
            finish()
        }

        btnVoltar.setOnClickListener {
            finish()
        }
    }
}
