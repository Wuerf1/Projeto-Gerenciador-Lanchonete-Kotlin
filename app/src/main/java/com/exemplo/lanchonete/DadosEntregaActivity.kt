package com.exemplo.lanchonete

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class DadosEntregaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dados_entrega)

        val etNome = findViewById<EditText>(R.id.etNome)
        val etEndereco = findViewById<EditText>(R.id.etEndereco)
        val btnConfirmarEntrega = findViewById<Button>(R.id.btnConfirmarEntrega)
        val btnVoltar = findViewById<Button>(R.id.btnVoltar)

        btnConfirmarEntrega.setOnClickListener {
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

            // Gera um código de 4 dígitos para o cliente receber o pedido
            val codigo = Random.nextInt(1000, 9999)

            val intent = Intent(this, ComprovanteActivity::class.java)
            intent.putExtra("NOME", nome)
            intent.putExtra("ENDERECO", endereco)
            intent.putExtra("CODIGO", codigo)
            startActivity(intent)
        }

        btnVoltar.setOnClickListener {
            finish()
        }
    }
}
