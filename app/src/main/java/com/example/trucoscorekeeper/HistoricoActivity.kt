package com.example.trucoscorekeeper

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.trucoscorekeeper.databinding.ActivityHistoricoBinding

class HistoricoActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_NOME_JOGADOR_1 = "extra_nome_jogador_1"
        const val EXTRA_NOME_JOGADOR_2 = "extra_nome_jogador_2"
        const val EXTRA_VITORIAS_JOGADOR_1 = "extra_vitorias_jogador_1"
        const val EXTRA_VITORIAS_JOGADOR_2 = "extra_vitorias_jogador_2"
    }

    private lateinit var binding: ActivityHistoricoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoricoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configurarToolbar()
        exibirHistorico()
    }

    private fun configurarToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
    }

    private fun exibirHistorico() {
        val nome1 = intent.getStringExtra(EXTRA_NOME_JOGADOR_1) ?: getString(R.string.jogador_1)
        val nome2 = intent.getStringExtra(EXTRA_NOME_JOGADOR_2) ?: getString(R.string.jogador_2)
        val vitorias1 = intent.getIntExtra(EXTRA_VITORIAS_JOGADOR_1, 0)
        val vitorias2 = intent.getIntExtra(EXTRA_VITORIAS_JOGADOR_2, 0)

        binding.tvHistoricoNomeJogador1.text = nome1
        binding.tvHistoricoNomeJogador2.text = nome2
        binding.tvHistoricoVitoriasJogador1.text = resources.getQuantityString(
            R.plurals.historico_vitorias, vitorias1, vitorias1
        )
        binding.tvHistoricoVitoriasJogador2.text = resources.getQuantityString(
            R.plurals.historico_vitorias, vitorias2, vitorias2
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
