package com.exemplo.lanchonete

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CafeActivity : AppCompatActivity() {

    // Cada tipo tem o seu próprio preço (só um pode ser escolhido)
    private val listaSabores = listOf(
        "Expresso" to 5.00,
        "Expresso Duplo" to 7.00,
        "Coado" to 4.00,
        "Pingado" to 6.00
    )
    private val quantidadeMaxima = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cafe)

        val cbChantilly = findViewById<CheckBox>(R.id.cbChantilly)
        val cbCanela = findViewById<CheckBox>(R.id.cbCanela)
        val cbCopoGrande = findViewById<CheckBox>(R.id.cbCopoGrande)
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
            // Opção escolhida (posição no grupo) e o preço dela
            val marcado = rgSabor.findViewById<RadioButton>(rgSabor.checkedRadioButtonId)
            val (nomeSabor, precoSabor) = listaSabores[rgSabor.indexOfChild(marcado)]
            var preco = precoSabor
            val adicionais = StringBuilder()
            adicionais.append(", Tipo $nomeSabor")

            if (cbChantilly.isChecked) {
                preco += 1.50
                adicionais.append(", Chantilly")
            }
            if (cbCanela.isChecked) {
                preco += 0.50
                adicionais.append(", Canela")
            }
            if (cbCopoGrande.isChecked) {
                preco += 2.00
                adicionais.append(", Copo Grande")
            }

            val descricao = "Café" + adicionais.toString()
            Carrinho.adicionar(ItemPedido(descricao, quantidade, preco))

            Toast.makeText(this, "Café adicionado ao carrinho!", Toast.LENGTH_SHORT).show()
            finish()
        }

        btnVoltar.setOnClickListener {
            finish()
        }
    }
}
