package com.exemplo.lanchonete

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PicoleActivity : AppCompatActivity() {

    // Cada sabor tem o seu próprio preço (só um pode ser escolhido)
    private val listaSabores = listOf(
        "Limão" to 4.00,
        "Coco" to 5.00,
        "Chocolate" to 5.00,
        "Morango" to 5.00,
        "Uva" to 4.00
    )
    private val quantidadeMaxima = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picole)

        val cbBanhadoNoChocolate = findViewById<CheckBox>(R.id.cbBanhadoNoChocolate)
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
            adicionais.append(", Sabor $nomeSabor")

            if (cbBanhadoNoChocolate.isChecked) {
                preco += 1.50
                adicionais.append(", Banhado no Chocolate")
            }

            val descricao = "Picolé" + adicionais.toString()
            Carrinho.adicionar(ItemPedido(descricao, quantidade, preco))

            Toast.makeText(this, "Picolé adicionado ao carrinho!", Toast.LENGTH_SHORT).show()
            finish()
        }

        btnVoltar.setOnClickListener {
            finish()
        }
    }
}
