package com.sardonyx001.qqr

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext


class LinkDialogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val links = intent.getStringArrayListExtra("LINKS") ?: return
        setContent {
            if (links.isEmpty()) {
                AlertDialog(
                    onDismissRequest = { finish() },
                    title = { Text("No Links Found") },
                    text = { Text("No QR codes with URLs were detected.") },
                    confirmButton = {
                        TextButton(onClick = { finish() }) {
                            Text("OK")
                        }
                    }
                )
            } else {
                LinksDialog(links) { finish() }
            }
        }
    }
}

@Composable
fun LinksDialog(
    links: List<String>,
    onDismiss: () -> Unit = {}
) {
    val context = LocalContext.current
    AlertDialog(
        onDismissRequest = {
            onDismiss()
        },
        title = { Text("Scanned QR Codes") },
        text = {
            Column {
                links.forEach { link ->
                    TextButton(
                        onClick = {
                            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                            context.startActivity(browserIntent)
                            onDismiss()
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(link)
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onDismiss()
                }
            ) {
                Text("Cancel")
            }
        }
    )
}