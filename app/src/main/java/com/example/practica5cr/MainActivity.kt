package com.example.practica5cr

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.practica5cr.dataBase.AppDatabase
import com.example.practica5cr.ui.theme.Practica5CRTheme
import kotlinx.coroutines.launch
import com.example.practica5cr.datadao.InterAlumnoDao
import com.example.practica5cr.datadao.Alumno

object NavigationGuard {
    private var lastClickTime = 0L
    private const val CLICK_INTERVAL = 800L

    fun canNavigate(): Boolean {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastClickTime >= CLICK_INTERVAL) {
            lastClickTime = currentTime
            return true
        }
        return false
    }
}


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Practica5CRTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavigation()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current

    val dao = remember { AppDatabase.getDatabase(context).interAlumnoDao() }
    val scope = rememberCoroutineScope()

    var alummnoEditar by remember { mutableStateOf<Alumno?>(null) }

    NavHost(
        navController = navController,
        startDestination = "tabla_alumnos"
    ) {
        composable("tabla_alumnos") {
            var listaAlumnos by remember { mutableStateOf<List<Alumno>>(emptyList()) }
            LaunchedEffect(Unit) {
                listaAlumnos  = dao.getAlumnos()
            }

            PantallaAlumnos(
                listaAlumnos = listaAlumnos,
                onNavigationOne = {
                    if (NavigationGuard.canNavigate()) {
                    alummnoEditar = null
                    navController.navigate("agg_edit") }
                },
                onNavigationTwo = {
                    if (NavigationGuard.canNavigate()) {
                        navController.navigate("delete")
                    }
                },
                onEditarAlumno = { alumno ->
                    if (NavigationGuard.canNavigate()) {
                        alummnoEditar = alumno
                        navController.navigate("agg_edit")
                    }
                },
                onEliminarBoton = { alumno ->
                    if (NavigationGuard.canNavigate()) {
                    scope.launch {
                        dao.eliminarPorMatricula(alumno.matricula)
                        listaAlumnos = dao.getAlumnos()
                    }
                    }
                }
            )
        }

        composable("agg_edit") {
            PantallaAggEdit(
                alummnoEditar = alummnoEditar,
                onHome = {
                    navController.navigate("tabla_alumnos") {
                        popUpTo("tabla_alumnos") { inclusive = true }
                    }
                }
            )
        }

        composable("delete") {
            PantallaDelete(
                onHome = {
                    navController.navigate("tabla_alumnos") {
                        popUpTo("tabla_alumnos") { inclusive = true }
                    }
                }
            )
        }
    }
}

@Composable
fun PantallaAlumnos(
    listaAlumnos: List<Alumno>,
    onNavigationOne: () -> Unit,
    onNavigationTwo: () -> Unit,
    onEditarAlumno: (Alumno) -> Unit,
    onEliminarBoton: (Alumno) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Registro de Alumnos",
            color = Color.Blue,
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(listaAlumnos, key = { it.matricula }) { alumno ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = alumno.nombre,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = "Matricula: ${alumno.matricula} | Edad ${alumno.edad}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = "Grupo : ${alumno.grupo}° ${alumno.seccion}",
                                style = MaterialTheme.typography.labelLarge
                            )
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Button(
                                onClick = { onEliminarBoton(alumno) },
                                modifier = Modifier.size(36.dp),
                                shape = RectangleShape,
                                contentPadding = PaddingValues(0.dp)
                            ) {
                                Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                            }
                            Button(
                                onClick = { onEditarAlumno(alumno) },
                                modifier = Modifier.size(36.dp),
                                shape = RectangleShape,
                                contentPadding = PaddingValues(0.dp)
                            ) {
                                Icon(Icons.Default.Edit, contentDescription = "Editar")
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = onNavigationOne) {
                Text("NIU")
            }
            Button(onClick = onNavigationTwo) {
                Text("DELETEA")
            }
        }
    }
}

@Composable
fun PantallaAggEdit(
    alummnoEditar: Alumno?,
    onHome: () -> Unit
) {
    val context = LocalContext.current
    val dao = remember { AppDatabase.getDatabase(context).interAlumnoDao() }
    val scope = rememberCoroutineScope()

    var matricula by remember { mutableStateOf(alummnoEditar?.matricula ?: "") }
    var nombre by remember { mutableStateOf(alummnoEditar?.nombre ?: "") }
    var edad by remember { mutableStateOf(alummnoEditar?.edad?.toString() ?: "") }
    var grupo by remember { mutableStateOf(alummnoEditar?.grupo?.toString() ?: "") }
    var seccion by remember { mutableStateOf(alummnoEditar?.seccion?.toString() ?: "") }
    var mensajeError by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = if (alummnoEditar != null) "Editar alumno" else "Agregando Alumno",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Matrícula
        OutlinedTextField(
            value = matricula,
            onValueChange = {
                if (Alumno.matriculaIsValid(it)) {
                    matricula = it
                }
            },
            label = { Text("Matrícula") },
            readOnly = alummnoEditar != null,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        // Nombre
        OutlinedTextField(
            value = nombre,
            onValueChange = {
                if (it.all { char -> char.isLetter() || char.isWhitespace() }) {
                    nombre = it
                }
            },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        // Edad
        OutlinedTextField(
            value = edad,
            onValueChange = {
                if (Alumno.edadIsValid(it) || it.isEmpty()) {
                    edad = it
                }
            },
            label = { Text("Edad") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        // Grupo y Sección juntos en una Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedTextField(
                value = grupo,
                onValueChange = {
                    if (it.length <= 1 && it.all { char -> char.isDigit() }) {
                        grupo = it
                    }
                },
                label = { Text("Grupo") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )

            OutlinedTextField(
                value = seccion,
                onValueChange = {
                    if (it.length <= 1 && it.all { char -> char.isLetter() }) {
                        seccion = it
                    }
                },
                label = { Text("Sección") },
                modifier = Modifier.weight(1f)
            )
        }

        if (mensajeError.isNotEmpty()) {
            Text(text = mensajeError, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Botón Guardar / Actualizar
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                mensajeError = ""

                if (matricula.isBlank() || nombre.isBlank() || edad.isBlank() || grupo.isBlank() || seccion.isBlank()) {
                    mensajeError = "Llena todos los campos vacíos"
                }

                scope.launch {
                    val listaActual = dao.getAlumnos()

                    if (alummnoEditar == null && Alumno.matriculaExiste(matricula, listaActual)) {
                        mensajeError = "Error: La matrícula $matricula ya se encuentra registrada"
                    } else {
                        val alumnoGuardar = Alumno(
                            matricula = matricula,
                            nombre = nombre.trim(),
                            edad = edad.toInt(),
                            grupo = grupo.toInt(),
                            seccion = seccion.first().uppercaseChar()
                        )
                        dao.insertarOActualizar(alumnoGuardar)
                        onHome()
                    }
                }
            }
        ) {
            Text(if (alummnoEditar != null) "Actualizar alumno" else "Guardar alumno")
        }
    }
}


//deleteo
@Composable
fun PantallaDelete(
    onHome: () -> Unit
) {
    val context = LocalContext.current
    val dao = remember { AppDatabase.getDatabase(context).interAlumnoDao() }
    val scope = rememberCoroutineScope()

    var eliminacionMatricula by remember { mutableStateOf("") }
    var mensajeError by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Eliminar Alumno por Matrícula", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = eliminacionMatricula,
            onValueChange = {
                if (Alumno.matriculaIsValid(it)) {
                    eliminacionMatricula = it
                }
            },
            label = { Text("Matrícula a eliminar") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (eliminacionMatricula.isBlank()) {
                    mensajeError = "Ingresa una matrícula válida"
                    return@Button
                }

                scope.launch {
                    val eliminados = dao.eliminarPorMatricula(eliminacionMatricula)
                    if (eliminados > 0) {
                        onHome()
                    } else {
                        mensajeError = "Alumno no encontrado"
                    }
                }
            },
            modifier = Modifier.size(56.dp),
            shape = RectangleShape,
            contentPadding = PaddingValues(0.dp)
        ) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = "Borrar por matrícula")
        }

        if (mensajeError.isNotEmpty()) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = mensajeError, color = MaterialTheme.colorScheme.error)
        }
    }
}