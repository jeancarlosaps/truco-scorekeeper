package com.example.trucoscorekeeper

import android.app.AlertDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.trucoscorekeeper.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var pontosJogador1 = 0
    private var pontosJogador2 = 0
    private var partidasGanhasJogador1 = 0
    private var partidasGanhasJogador2 = 0
    private var nomeJogador1 = "Jogador 1"
    private var nomeJogador2 = "Jogador 2"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configurarBotoes()
        atualizarPlacar()
    }

    private fun configurarBotoes() {
        binding.btnJ1Mais1.setOnClickListener  { adicionarPontos(1,  jogador = 1) }
        binding.btnJ1Mais3.setOnClickListener  { adicionarPontos(3,  jogador = 1) }
        binding.btnJ1Mais6.setOnClickListener  { adicionarPontos(6,  jogador = 1) }
        binding.btnJ1Mais9.setOnClickListener  { adicionarPontos(9,  jogador = 1) }
        binding.btnJ1Mais12.setOnClickListener { adicionarPontos(12, jogador = 1) }

        binding.btnJ2Mais1.setOnClickListener  { adicionarPontos(1,  jogador = 2) }
        binding.btnJ2Mais3.setOnClickListener  { adicionarPontos(3,  jogador = 2) }
        binding.btnJ2Mais6.setOnClickListener  { adicionarPontos(6,  jogador = 2) }
        binding.btnJ2Mais9.setOnClickListener  { adicionarPontos(9,  jogador = 2) }
        binding.btnJ2Mais12.setOnClickListener { adicionarPontos(12, jogador = 2) }
    }

    private fun adicionarPontos(valor: Int, jogador: Int) {
        if (jogador == 1) pontosJogador1 += valor else pontosJogador2 += valor
        atualizarPlacar()
        verificarVitoria()
    }

    private fun atualizarPlacar() {
        binding.tvNomeJogador1.text = nomeJogador1
        binding.tvNomeJogador2.text = nomeJogador2
        binding.tvPontuacaoJogador1.text = String.format("%02d", pontosJogador1)
        binding.tvPontuacaoJogador2.text = String.format("%02d", pontosJogador2)
    }

    private fun verificarVitoria() {
        when {
            pontosJogador1 >= 12 -> exibirDialogoVitoria(nomeJogador1, jogador = 1)
            pontosJogador2 >= 12 -> exibirDialogoVitoria(nomeJogador2, jogador = 2)
        }
    }

    private fun exibirDialogoVitoria(nomeVencedor: String, jogador: Int) {
        if (jogador == 1) partidasGanhasJogador1++ else partidasGanhasJogador2++

        AlertDialog.Builder(this)
            .setTitle("Temos um Vencedor!")
            .setMessage("O $nomeVencedor deitou o cabelo e ganhou a partida!")
            .setPositiveButton("Nova Partida") { _, _ -> reiniciarRodada() }
            .setCancelable(false)
            .show()
    }

    private fun reiniciarRodada() {
        pontosJogador1 = 0
        pontosJogador2 = 0
        atualizarPlacar()
    }
}
