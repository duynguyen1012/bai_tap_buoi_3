package com.example.baitapbuoi1

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "screen1"
    ) {
        composable("screen1") {
            Screen1(navController = navController)
        }
        composable(
            route = "screen2/{name}/{studentId}",
            arguments = listOf(
                navArgument("name") { type = NavType.StringType },
                navArgument("studentId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: ""
            val studentId = backStackEntry.arguments?.getString("studentId") ?: ""
            Screen2(
                navController = navController,
                name = name,
                studentId = studentId
            )
        }
    }
}

@Composable
fun Screen1(navController: NavController) {
    val context = LocalContext.current
    var name by remember { mutableStateOf("") }
    var studentId by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // NỬA TRÊN: Layout 6 khối màu
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            // Hàng 1: 2 khối Xanh dương, Đỏ
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                ColorBox(modifier = Modifier.weight(1f), text = "1", color = Color(0xFF2182F1))
                Spacer(modifier = Modifier.width(8.dp))
                ColorBox(modifier = Modifier.weight(1f), text = "2", color = Color(0xFFF34135))
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Hàng 2: 3 khối Vàng, Xanh lá, Tím
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                ColorBox(modifier = Modifier.weight(1f), text = "3", color = Color(0xFFFFC000), textColor = Color.Black)
                Spacer(modifier = Modifier.width(8.dp))
                ColorBox(modifier = Modifier.weight(1f), text = "4", color = Color(0xFF4CAE50))
                Spacer(modifier = Modifier.width(8.dp))
                ColorBox(modifier = Modifier.weight(1f), text = "5", color = Color(0xFF8A2CE2))
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Hàng 3: 1 khối Cam
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                ColorBox(modifier = Modifier.weight(1f), text = "6", color = Color(0xFFFF9800))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // NỬA DƯỚI: Form nhập thông tin sinh viên & Nút Click me
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Nhap thong tin sinh vien",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                placeholder = { Text("Enter your name") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = studentId,
                onValueChange = { studentId = it },
                placeholder = { Text("Enter your student ID") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    val trimmedName = name.trim()
                    val trimmedId = studentId.trim()

                    if (trimmedName.isEmpty() || trimmedId.isEmpty()) {
                        Toast.makeText(context, "Dữ liệu không được để trống", Toast.LENGTH_SHORT).show()
                    } else if (trimmedName.equals("duy nguyễn", ignoreCase = true) &&
                        trimmedId.equals("bit240080", ignoreCase = true)
                    ) {
                        val encodedName = Uri.encode(trimmedName)
                        val encodedId = Uri.encode(trimmedId)
                        navController.navigate("screen2/$encodedName/$encodedId")
                    } else {
                        Toast.makeText(context, "Thông tin sinh viên không chính xác!", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Text("Click me")
            }
        }
    }
}

@Composable
fun Screen2(
    navController: NavController,
    name: String,
    studentId: String
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Vị trí top-left: Button chứa text "Back"
        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.align(Alignment.TopStart)
        ) {
            Text("Back")
        }

        // Vị trí center: Column căn giữa chứa 3 dòng Text
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Screen 2",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(
                text = "Name: $name",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Student ID: $studentId",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
fun ColorBox(modifier: Modifier, text: String, color: Color, textColor: Color = Color.White) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .background(color),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
    }
}