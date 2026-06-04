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
        binding.btnHistorico.setOnClickListener { abrirHistorico() }
        binding.btnZerar.setOnClickListener { confirmarZerarHistorico() }
        binding.btnNomes.setOnClickListener { abrirTelaNomes() }

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
