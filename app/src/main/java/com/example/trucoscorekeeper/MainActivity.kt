package com.example.trucoscorekeeper

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.trucoscorekeeper.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val nomesLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            result.data?.let { data ->
                nomeJogador1 = data.getStringExtra(NomesActivity.EXTRA_NOVO_NOME_JOGADOR_1) ?: nomeJogador1
                nomeJogador2 = data.getStringExtra(NomesActivity.EXTRA_NOVO_NOME_JOGADOR_2) ?: nomeJogador2
                atualizarPlacar()
            }
        }
    }

    private var pontosJogador1 = 0
    private var pontosJogador2 = 0
    private var partidasGanhasJogador1 = 0
    private var partidasGanhasJogador2 = 0
    private var nomeJogador1 = "Equipe A"
    private var nomeJogador2 = "Equipe B"
    private var ultimaJogadaEquipe1: Jogada? = null
    private var ultimaJogadaEquipe2: Jogada? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configurarBotoes()
        atualizarPlacar()
    }

    private fun configurarBotoes() {
        binding.btnHistorico.setOnClickListener { abrirHistorico() }
        binding.btnZerar.setOnClickListener { confirmarZerarHistorico() }
        binding.btnNomes.setOnClickListener { abrirTelaNomes() }

        binding.btnDesfazerEquipe1.setOnClickListener { desfazerJogada(equipe = 1) }
        binding.btnDesfazerEquipe2.setOnClickListener { desfazerJogada(equipe = 2) }

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
        if (jogador == 1) {
            ultimaJogadaEquipe1 = Jogada(equipe = 1, pontos = valor)
            pontosJogador1 += valor
        } else {
            ultimaJogadaEquipe2 = Jogada(equipe = 2, pontos = valor)
            pontosJogador2 += valor
        }
        atualizarPlacar()
        verificarVitoria()
    }

    private fun desfazerJogada(equipe: Int) {
        if (equipe == 1) {
            val jogada = ultimaJogadaEquipe1
            if (jogada == null) {
                Toast.makeText(this, R.string.sem_jogada_para_desfazer, Toast.LENGTH_SHORT).show()
                return
            }
            pontosJogador1 = maxOf(0, pontosJogador1 - jogada.pontos)
            ultimaJogadaEquipe1 = null
        } else {
            val jogada = ultimaJogadaEquipe2
            if (jogada == null) {
                Toast.makeText(this, R.string.sem_jogada_para_desfazer, Toast.LENGTH_SHORT).show()
                return
            }
            pontosJogador2 = maxOf(0, pontosJogador2 - jogada.pontos)
            ultimaJogadaEquipe2 = null
        }
        atualizarPlacar()
    }

    private fun atualizarPlacar() {
        binding.tvNomeJogador1.text = nomeJogador1
        binding.tvNomeJogador2.text = nomeJogador2
        binding.tvPontuacaoJogador1.text = String.format("%02d", pontosJogador1)
        binding.tvPontuacaoJogador2.text = String.format("%02d", pontosJogador2)
        atualizarEstadoBotoesDesfazer()
    }

    private fun atualizarEstadoBotoesDesfazer() {
        val temJogada1 = ultimaJogadaEquipe1 != null
        val temJogada2 = ultimaJogadaEquipe2 != null

        binding.btnDesfazerEquipe1.isEnabled = temJogada1
        binding.btnDesfazerEquipe1.alpha = if (temJogada1) 1f else 0.4f

        binding.btnDesfazerEquipe2.isEnabled = temJogada2
        binding.btnDesfazerEquipe2.alpha = if (temJogada2) 1f else 0.4f
    }

    private fun verificarVitoria() {
        when {
            pontosJogador1 >= 12 -> exibirDialogoVitoria(nomeJogador1, jogador = 1)
            pontosJogador2 >= 12 -> exibirDialogoVitoria(nomeJogador2, jogador = 2)
        }
    }

    private fun exibirDialogoVitoria(nomeVencedor: String, jogador: Int) {
        if (jogador == 1) partidasGanhasJogador1++ else partidasGanhasJogador2++

        AlertDialog.Builder(this, R.style.Theme_TrucoScorekeeper_AlertDialog)
            .setTitle("Temos um Vencedor!")
            .setMessage("O $nomeVencedor deitou o cabelo e ganhou a partida!")
            .setPositiveButton("Nova Partida") { _, _ -> reiniciarRodada() }
            .setCancelable(false)
            .show()
    }

    private fun reiniciarRodada() {
        pontosJogador1 = 0
        pontosJogador2 = 0
        ultimaJogadaEquipe1 = null
        ultimaJogadaEquipe2 = null
        atualizarPlacar()
    }

    private fun confirmarZerarHistorico() {
        AlertDialog.Builder(this, R.style.Theme_TrucoScorekeeper_AlertDialog)
            .setTitle(R.string.zerar_dialogo_titulo)
            .setMessage(R.string.zerar_dialogo_mensagem)
            .setNegativeButton(R.string.zerar_dialogo_cancelar, null)
            .setPositiveButton(R.string.zerar_dialogo_confirmar) { _, _ -> zerarHistorico() }
            .show()
    }

    private fun zerarHistorico() {
        reiniciarRodada()
        partidasGanhasJogador1 = 0
        partidasGanhasJogador2 = 0
        nomeJogador1 = getString(R.string.jogador_1)
        nomeJogador2 = getString(R.string.jogador_2)
        atualizarPlacar()
        Toast.makeText(this, R.string.historico_zerado, Toast.LENGTH_LONG).show()
    }

    private fun abrirTelaNomes() {
        val intent = Intent(this, NomesActivity::class.java).apply {
            putExtra(NomesActivity.EXTRA_NOME_ATUAL_JOGADOR_1, nomeJogador1)
            putExtra(NomesActivity.EXTRA_NOME_ATUAL_JOGADOR_2, nomeJogador2)
        }
        nomesLauncher.launch(intent)
    }

    private fun abrirHistorico() {
        val intent = Intent(this, HistoricoActivity::class.java).apply {
            putExtra(HistoricoActivity.EXTRA_NOME_JOGADOR_1, nomeJogador1)
            putExtra(HistoricoActivity.EXTRA_NOME_JOGADOR_2, nomeJogador2)
            putExtra(HistoricoActivity.EXTRA_VITORIAS_JOGADOR_1, partidasGanhasJogador1)
            putExtra(HistoricoActivity.EXTRA_VITORIAS_JOGADOR_2, partidasGanhasJogador2)
        }
        startActivity(intent)
    }
}
