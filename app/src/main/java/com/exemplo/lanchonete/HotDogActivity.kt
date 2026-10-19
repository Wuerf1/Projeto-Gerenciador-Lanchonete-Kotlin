package com.exemplo.lanchonete

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class HotDogActivity : AppCompatActivity() {

    // Cada sabor tem o seu próprio preço (só um sabor pode ser escolhido)
    private val listaSabores = listOf(
        "Tradicional" to 8.00,
        "Completo" to 10.00,
        "Frango" to 9.00,
        "Calabresa" to 9.00,
        "Carne Seca" to 11.00
    )
    private val quantidadeMaxima = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hotdog)

        val cbQueijo = findViewById<CheckBox>(R.id.cbQueijo)
        val cbBatataPalha = findViewById<CheckBox>(R.id.cbBatataPalha)
        val cbMolhoEspecial = findViewById<CheckBox>(R.id.cbMolhoEspecial)
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

            if (cbQueijo.isChecked) {
                preco += 1.00
                adicionais.append(", Queijo Ralado")
            }
            if (cbBatataPalha.isChecked) {
                preco += 0.50
                adicionais.append(", Batata Palha")
            }
            if (cbMolhoEspecial.isChecked) {
                preco += 1.00
                adicionais.append(", Molho Especial")
            }

            val descricao = "Hot Dog" + adicionais.toString()
            Carrinho.adicionar(ItemPedido(descricao, quantidade, preco))

            Toast.makeText(this, "Hot Dog adicionado ao carrinho!", Toast.LENGTH_SHORT).show()
            finish()
        }

        btnVoltar.setOnClickListener {
            finish()
        }
    }
}
