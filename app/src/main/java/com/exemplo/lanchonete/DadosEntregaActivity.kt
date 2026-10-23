package com.exemplo.lanchonete

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class DadosEntregaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dados_entrega)

        val etNome = findViewById<EditText>(R.id.etNome)
        val etEndereco = findViewById<EditText>(R.id.etEndereco)
        val rgPagamento = findViewById<RadioGroup>(R.id.rgPagamento)
        val btnConfirmarEntrega = findViewById<Button>(R.id.btnConfirmarEntrega)
        val btnVoltar = findViewById<Button>(R.id.btnVoltar)

        btnConfirmarEntrega.setOnClickListener {
            // Segurança extra: sem itens não existe pedido para confirmar
            if (Carrinho.itens.isEmpty()) {
                Toast.makeText(this, "Seu carrinho está vazio", Toast.LENGTH_SHORT).show()
                finish()
                return@setOnClickListener
            }

            val nome = etNome.text.toString().trim()
            val endereco = etEndereco.text.toString().trim()

            if (nome.isBlank()) {
                etNome.error = "Informe seu nome"
                Toast.makeText(this, "Por favor, informe seu nome", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (endereco.isBlank()) {
                etEndereco.error = "Informe o endereço"
                Toast.makeText(this, "Por favor, informe o endereço", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Forma de pagamento escolhida (só uma pode ser marcada)
            val pagamento = when (rgPagamento.checkedRadioButtonId) {
                R.id.rbCartao -> "Cartão de crédito ou débito"
                R.id.rbDinheiro -> "Dinheiro"
                else -> "Pix"
            }

            // Gera um código de 4 dígitos (de 1000 até 9999) para o cliente receber o pedido
            val codigo = Random.nextInt(1000, 10000)

            val intent = Intent(this, ComprovanteActivity::class.java)
            intent.putExtra("NOME", nome)
            intent.putExtra("ENDERECO", endereco)
            intent.putExtra("PAGAMENTO", pagamento)
            intent.putExtra("CODIGO", codigo)
            // O resumo do pedido vai junto, porque o carrinho é limpo logo abaixo.
            // Os extras ficam guardados na Intent, então o comprovante não perde
            // nada se a tela for recriada (por exemplo, ao girar o celular).
            intent.putExtra("ITENS", Carrinho.textoDosItens())
            intent.putExtra("TOTAL", Carrinho.total())

            // Pedido concluído: limpa o carrinho para o próximo
            Carrinho.limpar()

            startActivity(intent)
            // Fecha esta tela: ao voltar do comprovante, o cliente não cai de novo
            // no formulário e não consegue confirmar um pedido vazio
            finish()
        }

        btnVoltar.setOnClickListener {
            finish()
        }
    }
}
