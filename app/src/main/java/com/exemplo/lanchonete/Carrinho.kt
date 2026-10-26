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

    // Se já existe um item igual (mesma descrição e mesmo preço), só soma a
    // quantidade, em vez de criar outra linha: "1x Coxinha" + "1x Coxinha" = "2x Coxinha"
    fun adicionar(item: ItemPedido) {
        for (posicao in itens.indices) {
            val existente = itens[posicao]
            if (existente.descricao == item.descricao && existente.precoUnitario == item.precoUnitario) {
                itens[posicao] = ItemPedido(item.descricao, existente.quantidade + item.quantidade, item.precoUnitario)
                return
            }
        }
        itens.add(item)
    }

    // Tira do carrinho o item que está na posição informada (0 = primeiro)
    fun remover(posicao: Int) {
        itens.removeAt(posicao)
    }

    fun total(): Double {
        var soma = 0.0
        for (item in itens) {
            soma += item.subtotal()
        }
        return soma
    }

    // Soma as quantidades (2 Hot Dogs + 1 Suco = 3 itens)
    fun quantidadeDeItens(): Int {
        var soma = 0
        for (item in itens) {
            soma += item.quantidade
        }
        return soma
    }

    // Texto do botão "Ver carrinho" dos cardápios: mostra quantos itens
    // já foram escolhidos e o total, para o cliente não precisar abrir o carrinho
    fun textoDoBotao(): String {
        val quantidade = quantidadeDeItens()
        if (quantidade == 0) {
            return "Ver carrinho"
        }
        val palavra = if (quantidade == 1) "item" else "itens"
        return "Carrinho · $quantidade $palavra · R$ " + formatarPreco(total())
    }

    // Versão curta, para botões estreitos (ex.: "Ver carrinho (3)")
    fun textoCurtoDoBotao(): String {
        val quantidade = quantidadeDeItens()
        if (quantidade == 0) {
            return "Ver carrinho"
        }
        return "Ver carrinho ($quantidade)"
    }

    // Monta um texto com todos os itens, um por linha.
    // Usado na tela do carrinho e no comprovante.
    fun textoDosItens(): String {
        val texto = StringBuilder()
        for (item in itens) {
            if (texto.isNotEmpty()) {
                texto.append("\n")
            }
            texto.append("• " + textoDoItem(item))
        }
        return texto.toString()
    }

    // Uma linha só, por exemplo: "2x Hot Dog, Sabor Frango - R$ 18,00".
    // O   é um espaço que não quebra a linha: assim o "R$" nunca fica
    // separado do valor quando o texto não cabe em uma linha só.
    fun textoDoItem(item: ItemPedido): String {
        return item.quantidade.toString() + "x " + item.descricao +
                " - R$ " + formatarPreco(item.subtotal())
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
