import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.CanvasBasedWindow
import api.GetConferenceDataQuery
import com.apollographql.apollo3.ApolloClient

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    val client = ApolloClient.Builder()
        .serverUrl("https://confetti-app.dev/graphql")
        //.serverUrl("http://localhost:8083/graphql")
        .addHttpHeader("conference", "droidconsf2023")
        .build()
    CanvasBasedWindow("Confetti", canvasElementId = "canvas") {

        val flow = remember {
            client.query(GetConferenceDataQuery()).toFlow()
        }
        val state = flow.collectAsState(null)

        Box(modifier = Modifier.fillMaxSize()) {
            val response = state.value
            when {
                response == null -> CircularProgressIndicator()
                response.data != null -> Conference(response.data!!.config.name, response.data!!.sessions.nodes)
                else -> Text("Oops c'est tout cassé")
            }
        }
    }
}

@Composable
fun Conference(title: String, nodes: List<GetConferenceDataQuery.Node>) {
    Column {
        Text(title, fontSize = 30.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn {
            nodes.forEach {
                item {
                    Text(it.title, fontSize = 18.sp)
                    Text(it.startsAt.toString())
                    Spacer(modifier = Modifier.height(6.dp))
                }
            }
        }
    }
}
