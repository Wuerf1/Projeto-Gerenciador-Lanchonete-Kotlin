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

    // Quantidade escolhida pelo cliente (começa em 1). Fica fora do onCreate
    // para poder ser guardada quando a tela gira (veja onSaveInstanceState)
    private var quantidade = 1

    // Componentes usados também nas funções montarItem() e atualizarValor()
    private lateinit var cbBanhadoNoChocolate: CheckBox
    private lateinit var rgSabor: RadioGroup
    private lateinit var btnAdicionarCarrinho: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picole)

        cbBanhadoNoChocolate = findViewById(R.id.cbBanhadoNoChocolate)
        rgSabor = findViewById(R.id.rgSabor)
        btnAdicionarCarrinho = findViewById(R.id.btnAdicionarCarrinho)
        val btnVoltar = findViewById<Button>(R.id.btnVoltar)
        val tvQuantidade = findViewById<TextView>(R.id.tvQuantidade)
        val btnMenos = findViewById<Button>(R.id.btnMenos)
        val btnMais = findViewById<Button>(R.id.btnMais)

        // Se a tela girou, recupera a quantidade que o cliente já tinha escolhido
        if (savedInstanceState != null) {
            quantidade = savedInstanceState.getInt("QUANTIDADE", 1)
            tvQuantidade.text = quantidade.toString()
        }

        // Sempre que o cliente troca a opção ou marca um adicional, o valor do botão é refeito
        cbBanhadoNoChocolate.setOnCheckedChangeListener { _, _ -> atualizarValor() }
        rgSabor.setOnCheckedChangeListener { _, _ -> atualizarValor() }

        btnMais.setOnClickListener {
            if (quantidade < quantidadeMaxima) {
                quantidade++
                tvQuantidade.text = quantidade.toString()
                atualizarValor()
            } else {
                Toast.makeText(this, "Máximo de $quantidadeMaxima unidades por item", Toast.LENGTH_SHORT).show()
            }
        }

        btnMenos.setOnClickListener {
            if (quantidade > 1) {
                quantidade--
                tvQuantidade.text = quantidade.toString()
                atualizarValor()
            }
        }

        btnAdicionarCarrinho.setOnClickListener {
            Carrinho.adicionar(montarItem())

            Toast.makeText(this, "Picolé adicionado ao carrinho!", Toast.LENGTH_SHORT).show()
            finish()
        }

        btnVoltar.setOnClickListener {
            finish()
        }

        // Mostra o valor inicial no botão
        atualizarValor()
    }

    // Monta o item com a opção escolhida, os adicionais marcados e a quantidade.
    // É usado tanto para mostrar o valor no botão quanto para guardar no carrinho.
    private fun montarItem(): ItemPedido {
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
        return ItemPedido(descricao, quantidade, preco)
    }

    // Mostra no botão quanto o pedido deste item vai custar (preço x quantidade)
    private fun atualizarValor() {
        // Enquanto a tela é recriada, pode não haver opção marcada ainda
        if (rgSabor.checkedRadioButtonId == -1) {
            return
        }
        btnAdicionarCarrinho.text = "Adicionar · R$ " + Carrinho.formatarPreco(montarItem().subtotal())
    }

    // Guarda a quantidade antes de a tela ser recriada (por exemplo, ao girar o celular)
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("QUANTIDADE", quantidade)
    }
}
