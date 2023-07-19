package it.esercizi.ninethegame.logic

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import it.esercizi.ninethegame.R

class FeedbackClass {

    @Composable
    fun ShowFeedbackForm(onClose: () -> Unit) {

        val email = "ninethegame.support@gmail.com"
        val context = LocalContext.current

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = CenterHorizontally,
                modifier = Modifier
                    .background(color = Color.Gray)
                    .height(500.dp)
                    .width(300.dp)
                    .border(3.dp, color = Color.Black)
                    .padding(10.dp)
                //.padding(10.dp)
            ) {
                Text(
                    stringResource(R.string.LeaveUsFeedback),
                    modifier = Modifier
                        .padding(20.dp)
                        .weight(0.3f)
                        .align(CenterHorizontally),
                    style = MaterialTheme.typography.h4
                )
                Spacer(modifier = Modifier.weight(0.2f))
                Text(text = stringResource(R.string.WriteAnEmailWithOpinion), modifier = Modifier.align(CenterHorizontally).padding(10.dp))
                Spacer(modifier = Modifier.weight(0.4f))
                Row(modifier = Modifier
                    .background(color = Color.DarkGray)
                    .weight(0.1f)) {
                    Button(onClick = onClose, modifier = Modifier.padding(5.dp)) {
                        Text(stringResource(R.string.Exit))
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Button(onClick = { sendMail(email,context) }, modifier = Modifier.padding(5.dp)) {
                        Text(stringResource(R.string.SendMail))
                    }
                }

            }
        }
    }

    private fun sendMail(email: String, context: Context) {

        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        }
        context.startActivity(intent)
    }
}