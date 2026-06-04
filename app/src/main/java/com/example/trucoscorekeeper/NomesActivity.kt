package com.example.trucoscorekeeper

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.trucoscorekeeper.databinding.ActivityNomesBinding

class NomesActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_NOME_ATUAL_JOGADOR_1 = "extra_nome_atual_jogador_1"
        const val EXTRA_NOME_ATUAL_JOGADOR_2 = "extra_nome_atual_jogador_2"
        const val EXTRA_NOVO_NOME_JOGADOR_1 = "extra_novo_nome_jogador_1"
        const val EXTRA_NOVO_NOME_JOGADOR_2 = "extra_novo_nome_jogador_2"
    }

    private lateinit var binding: ActivityNomesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNomesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configurarToolbar()
        preencherNomesAtuais()
        configurarBotaoConfirmar()
    }

    private fun configurarToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
    }

    private fun preencherNomesAtuais() {
        binding.etNomeJogador1.setText(intent.getStringExtra(EXTRA_NOME_ATUAL_JOGADOR_1))
        binding.etNomeJogador2.setText(intent.getStringExtra(EXTRA_NOME_ATUAL_JOGADOR_2))
    }

    private fun configurarBotaoConfirmar() {
        binding.btnConfirmar.setOnClickListener {
            val novoNome1 = binding.etNomeJogador1.text.toString().trim()
                .takeIf { it.isNotBlank() } ?: getString(R.string.jogador_1)
            val novoNome2 = binding.etNomeJogador2.text.toString().trim()
                .takeIf { it.isNotBlank() } ?: getString(R.string.jogador_2)

            val resultIntent = Intent().apply {
                putExtra(EXTRA_NOVO_NOME_JOGADOR_1, novoNome1)
                putExtra(EXTRA_NOVO_NOME_JOGADOR_2, novoNome2)
            }
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
