package com.exemplo.lanchonete

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AcompanhamentoActivity : AppCompatActivity() {

    // Etapas do pedido, na ordem em que acontecem
    private val etapas = listOf(
        "Pedido recebido",
        "Em preparo",
        "Saiu para entrega",
        "Entregue"
    )

    // Tempo entre uma etapa e outra, em milissegundos (5000 = 5 segundos)
    private val tempoEntreEtapas = 5000L

    // O Handler agenda a troca de etapa para daqui a alguns segundos
    private val handler = Handler(Looper.getMainLooper())
    private var etapaAtual = 0

    private lateinit var tvStatus: TextView
    private lateinit var progresso: ProgressBar
    private lateinit var linhasEtapas: List<TextView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_acompanhamento)

        val tvCodigo = findViewById<TextView>(R.id.tvCodigo)
        val btnNovoPedido = findViewById<Button>(R.id.btnNovoPedido)
        tvStatus = findViewById(R.id.tvStatus)
        progresso = findViewById(R.id.pbProgresso)
        linhasEtapas = listOf(
            findViewById(R.id.tvEtapa0),
            findViewById(R.id.tvEtapa1),
            findViewById(R.id.tvEtapa2),
            findViewById(R.id.tvEtapa3)
        )

        // O código vem do comprovante, igual ao nome e endereço vieram da entrega
        val codigo = intent.getIntExtra("CODIGO", 0)
        tvCodigo.text = "Código de entrega: $codigo"

        progresso.max = etapas.size - 1
        mostrarEtapa()
        agendarProximaEtapa()

        btnNovoPedido.setOnClickListener {
            // CLEAR_TOP volta para a tela de restaurantes que já está aberta e fecha
            // as telas do pedido anterior, em vez de empilhar uma nova
            val irParaInicio = Intent(this, RestaurantesActivity::class.java)
            irParaInicio.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(irParaInicio)
            finish()
        }
    }

    // Depois do tempo definido, avança uma etapa. Repete até chegar em "Entregue".
    private fun agendarProximaEtapa() {
        handler.postDelayed({
            etapaAtual++
            mostrarEtapa()
            if (etapaAtual < etapas.size - 1) {
                agendarProximaEtapa()
            } else {
                Toast.makeText(this, "Pedido entregue! Bom apetite!", Toast.LENGTH_LONG).show()
            }
        }, tempoEntreEtapas)
    }

    // Atualiza o status, a barra e as bolinhas de cada etapa
    private fun mostrarEtapa() {
        tvStatus.text = etapas[etapaAtual]
        progresso.progress = etapaAtual
        for (i in linhasEtapas.indices) {
            val bolinha = if (i <= etapaAtual) "●" else "○"
            linhasEtapas[i].text = bolinha + "  " + etapas[i]
        }
    }

    // Se o usuário sair da tela, cancela as trocas de etapa que ainda estavam agendadas
    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }
}
