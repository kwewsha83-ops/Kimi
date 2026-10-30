package app.kimi.mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                KimiApp()
            }
        }
    }
}

data class Chat(
    val name: String,
    val message: String,
    val type: String
)

@Composable
fun KimiApp() {
    var selectedTab by remember { mutableStateOf("همه") }
    var search by remember { mutableStateOf("") }

    val chats = listOf(
        Chat("دوست من", "سلام! خوبی؟", "خصوصی"),
        Chat("گروه دوستان", "امروز چه خبر؟", "گروه"),
        Chat("کانال کیمی", "به کیمی خوش آمدید", "کانال"),
        Chat("خانواده", "عکس جدید رسید", "گروه")
    )

    val filtered = chats.filter {
        (selectedTab == "همه" || it.type == selectedTab) &&
        it.name.contains(search, ignoreCase = true)
    }

    CompositionLocalProvider(
        LocalLayoutDirection provides androidx.compose.ui.unit.LayoutDirection.Rtl
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Column {
                            Text("KIMI", fontWeight = FontWeight.Bold)
                            Text("پیام‌رسان شما", fontSize = 12.sp)
                        }
                    },
                    actions = {
                        IconButton(onClick = {}) {
                            Icon(Icons.Default.Person, contentDescription = "پروفایل")
                        }
                    }
                )
            },
            bottomBar = {
                NavigationBar {
                    NavigationBarItem(
                        selected = true,
                        onClick = {},
                        icon = { Icon(Icons.Default.Chat, null) },
                        label = { Text("گفتگوها") }
                    )
                    NavigationBarItem(
                        selected = false,
                        onClick = {},
                        icon = { Icon(Icons.Default.Groups, null) },
                        label = { Text("گروه‌ها") }
                    )
                    NavigationBarItem(
                        selected = false,
                        onClick = {},
                        icon = { Icon(Icons.Default.Settings, null) },
                        label = { Text("تنظیمات") }
                    )
                }
            },
            floatingActionButton = {
                FloatingActionButton(onClick = {}) {
                    Icon(Icons.Default.Edit, contentDescription = "گفتگوی جدید")
                }
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = search,
                    onValueChange = { search = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("جستجوی گفتگوها") },
                    leadingIcon = {
                        Icon(Icons.Default.Search, null)
                    },
                    singleLine = true
                )

                Spacer(Modifier.height(16.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf("همه", "خصوصی", "گروه", "کانال").forEach { tab ->
                        FilterChip(
                            selected = selectedTab == tab,
                            onClick = { selectedTab = tab },
                            label = { Text(tab) }
                        )
                    }
                }

                Spacer(Modifier.height(12.dp))

                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(filtered) { chat ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Default.AccountCircle,
                                    contentDescription = null,
                                    modifier = Modifier.size(48.dp)
                                )
                                Spacer(Modifier.width(12.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        chat.name,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(chat.message)
                                }
                                Text(chat.type, fontSize = 11.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}
