package com.exemplo.lanchonete

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

// Tela de abertura: aparece por alguns segundos e depois abre a escolha de restaurantes
class SplashActivity : AppCompatActivity() {

    // Tempo que a abertura fica na tela, em milissegundos (2000 = 2 segundos)
    private val tempoDeAbertura = 2000L

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        handler.postDelayed({
            startActivity(Intent(this, RestaurantesActivity::class.java))
            // finish() fecha a abertura, para ela não aparecer de novo ao apertar voltar
            finish()
        }, tempoDeAbertura)
    }

    // Se o usuário fechar o app durante a abertura, cancela a ida para o cardápio
    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }
}
