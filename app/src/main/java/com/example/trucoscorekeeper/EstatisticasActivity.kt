package com.example.trucoscorekeeper

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.trucoscorekeeper.databinding.ActivityEstatisticasBinding

class EstatisticasActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_NOME_EQUIPE_1 = "extra_nome_equipe_1"
        const val EXTRA_NOME_EQUIPE_2 = "extra_nome_equipe_2"
        const val EXTRA_VITORIAS_EQUIPE_1 = "extra_vitorias_equipe_1"
        const val EXTRA_VITORIAS_EQUIPE_2 = "extra_vitorias_equipe_2"
        const val EXTRA_SEQUENCIA_EQUIPE_1 = "extra_sequencia_equipe_1"
        const val EXTRA_SEQUENCIA_EQUIPE_2 = "extra_sequencia_equipe_2"
    }

    private lateinit var binding: ActivityEstatisticasBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEstatisticasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configurarToolbar()
        exibirEstatisticas()
    }

    private fun configurarToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
    }

    private fun exibirEstatisticas() {
        val nome1 = intent.getStringExtra(EXTRA_NOME_EQUIPE_1) ?: getString(R.string.jogador_1)
        val nome2 = intent.getStringExtra(EXTRA_NOME_EQUIPE_2) ?: getString(R.string.jogador_2)
        val v1 = intent.getIntExtra(EXTRA_VITORIAS_EQUIPE_1, 0)
        val v2 = intent.getIntExtra(EXTRA_VITORIAS_EQUIPE_2, 0)
        val seq1 = intent.getIntExtra(EXTRA_SEQUENCIA_EQUIPE_1, 0)
        val seq2 = intent.getIntExtra(EXTRA_SEQUENCIA_EQUIPE_2, 0)
        val total = v1 + v2

        binding.tvEstatNomeEquipe1.text = nome1
        binding.tvEstatNomeEquipe2.text = nome2
        binding.tvEstatVitoriasEquipe1.text = resources.getQuantityString(R.plurals.historico_vitorias, v1, v1)
        binding.tvEstatVitoriasEquipe2.text = resources.getQuantityString(R.plurals.historico_vitorias, v2, v2)

        binding.tvEstatTotal.text = resources.getQuantityString(R.plurals.estatisticas_partidas, total, total)

        binding.tvEstatLider.text = when {
            total == 0 -> getString(R.string.estatisticas_sem_dados)
            v1 > v2   -> nome1
            v2 > v1   -> nome2
            else      -> getString(R.string.estatisticas_empate)
        }

        if (seq1 == 0 && seq2 == 0) {
            binding.tvEstatSequencia1.text = getString(R.string.estatisticas_sem_dados)
            binding.tvEstatSequencia2.visibility = View.GONE
        } else {
            binding.tvEstatSequencia1.text = buildString {
                append(nome1)
                append(" — ")
                append(resources.getQuantityString(R.plurals.estatisticas_sequencia, seq1, seq1))
            }
            binding.tvEstatSequencia2.text = buildString {
                append(nome2)
                append(" — ")
                append(resources.getQuantityString(R.plurals.estatisticas_sequencia, seq2, seq2))
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
