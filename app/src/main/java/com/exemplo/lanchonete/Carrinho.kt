package com.exemplo.lanchonete

import java.util.Locale

// Representa um item que foi adicionado ao carrinho
data class ItemPedido(
    val descricao: String,
    val quantidade: Int,
    val precoUnitario: Double
) {
    // Valor deste item já multiplicado pela quantidade
    fun subtotal(): Double {
        return precoUnitario * quantidade
    }
}

// Objeto único (singleton) que guarda os itens do pedido em memória
// enquanto o app está aberto. Não é um banco de dados, é só uma
// lista compartilhada entre as telas.
object Carrinho {

    val itens = mutableListOf<ItemPedido>()

    fun adicionar(item: ItemPedido) {
        itens.add(item)
    }

    fun total(): Double {
        var soma = 0.0
        for (item in itens) {
            soma += item.subtotal()
        }
        return soma
    }

    // Monta um texto com todos os itens, um por linha.
    // Usado na tela do carrinho e no comprovante.
    fun textoDosItens(): String {
        val texto = StringBuilder()
        for (item in itens) {
            if (texto.isNotEmpty()) {
                texto.append("\n")
            }
            texto.append("• " + item.quantidade + "x " + item.descricao +
                    " - R$ " + formatarPreco(item.subtotal()))
        }
        return texto.toString()
    }

    // Formata um valor com vírgula, no padrão brasileiro (ex.: 8,50),
    // mesmo que o celular esteja configurado em outro idioma
    fun formatarPreco(valor: Double): String {
        return String.format(Locale("pt", "BR"), "%.2f", valor)
    }

    fun limpar() {
        itens.clear()
    }
}
