package com.exemplo.lanchonete

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PaoNaChapaActivity : AppCompatActivity() {

    // Cada recheio tem o seu próprio preço (só um pode ser escolhido)
    private val listaSabores = listOf(
        "Manteiga" to 6.00,
        "Requeijão" to 7.00,
        "Queijo" to 8.00,
        "Presunto e Queijo" to 10.00
    )
    private val quantidadeMaxima = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paonachapa)

        val cbOvo = findViewById<CheckBox>(R.id.cbOvo)
        val cbOregano = findViewById<CheckBox>(R.id.cbOregano)
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
            adicionais.append(", Recheio $nomeSabor")

            if (cbOvo.isChecked) {
                preco += 2.00
                adicionais.append(", Ovo")
            }
            if (cbOregano.isChecked) {
                preco += 0.50
                adicionais.append(", Orégano")
            }

            val descricao = "Pão na Chapa" + adicionais.toString()
            Carrinho.adicionar(ItemPedido(descricao, quantidade, preco))

            Toast.makeText(this, "Pão na Chapa adicionado ao carrinho!", Toast.LENGTH_SHORT).show()
            finish()
        }

        btnVoltar.setOnClickListener {
            finish()
        }
    }
}
